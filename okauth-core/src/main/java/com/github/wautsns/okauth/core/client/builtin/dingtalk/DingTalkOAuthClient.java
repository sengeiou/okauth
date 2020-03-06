/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.wautsns.okauth.core.client.builtin.dingtalk;

import com.github.wautsns.okauth.core.OpenPlatform;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatform;
import com.github.wautsns.okauth.core.client.kernel.OAuthAppProperties;
import com.github.wautsns.okauth.core.client.kernel.OAuthClient;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeRedirectUriQueryForOpenid;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeRedirectUriQueryForUser;
import com.github.wautsns.okauth.core.client.kernel.api.InitializeAuthorizeUrl;
import com.github.wautsns.okauth.core.exception.OAuthErrorException;
import com.github.wautsns.okauth.core.http.HttpClient;
import com.github.wautsns.okauth.core.http.model.OAuthRequest;
import com.github.wautsns.okauth.core.http.model.OAuthResponse;
import com.github.wautsns.okauth.core.http.model.basic.OAuthUrl;
import com.github.wautsns.okauth.core.http.util.DataMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * DingTalk oauth client.
 *
 * @author wautsns
 * @see <a href="https://ding-doc.dingtalk.com/doc#/serverapi2/kymkv6">DingTalk OAuth doc</a>
 * @since Mar 04, 2020
 */
public class DingTalkOAuthClient extends OAuthClient<DingTalkUser> {

    /**
     * Construct DingTalk oauth client.
     *
     * @param app oauth app properties, require nonnull
     * @param httpClient http client, require nonnull
     */
    public DingTalkOAuthClient(OAuthAppProperties app, HttpClient httpClient) {
        super(app, httpClient);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatform.DINGTALK;
    }

    @Override
    protected InitializeAuthorizeUrl initApiInitializeAuthorizeUrl() {
        OAuthUrl basic = new OAuthUrl("https://oapi.dingtalk.com/connect/qrconnect");
        basic.getQuery()
                .addAppid(app.getClientId())
                .addResponseTypeWithValueCode()
                .addScope("snsapi_login")
                .addRedirectUri(app.getRedirectUri());
        return state -> {
            OAuthUrl url = basic.copy();
            url.getQuery().addState(state);
            return url;
        };
    }

    @Override
    protected ExchangeRedirectUriQueryForOpenid initExchangeRedirectUriQueryForOpenid() {
        return redirectUriQuery -> exchangeForUser(redirectUriQuery).getOpenid();
    }

    @Override
    protected ExchangeRedirectUriQueryForUser<DingTalkUser> initExchangeRedirectUriQueryForUser() {
        String url = "https://oapi.dingtalk.com/sns/getuserinfo_bycode";
        OAuthRequest basic = OAuthRequest.forPost(url);
        basic.getUrlQuery().add("accessKey", app.getClientId());
        byte[] secretBytes = app.getClientSecret().getBytes();
        return redirectUriQuery -> {
            OAuthRequest request = basic.copy();
            String timestamp = Long.toString(System.currentTimeMillis());
            request.getUrlQuery()
                    .add("timestamp", timestamp)
                    .add("signature", sign(secretBytes, timestamp));
            request.getForm()
                    .add("tmp_auth_code", redirectUriQuery.getCode());
            return new DingTalkUser(check(execute(request)));
        };
    }

    /**
     * Check response.
     *
     * @param response response, require nonnull
     * @return correct response
     * @throws OAuthErrorException if the response is incorrect
     */
    private static OAuthResponse check(OAuthResponse response) throws OAuthErrorException {
        DataMap dataMap = response.getDataMap();
        Integer errcode = dataMap.getInteger("errcode");
        if (errcode == 0) { return response; }
        String errmsg = dataMap.getAsString("errmsg");
        throw new OAuthErrorException(errcode.toString(), errmsg);
    }

    /**
     * Sign string.
     *
     * @param secretBytes secret bytes, require nonnull
     * @param string string to sign, require nonnull
     * @return signature
     */
    private static String sign(byte[] secretBytes, String string) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secretBytes, "HmacSHA256"));
            byte[] signatureBytes = mac.doFinal(string.getBytes());
            return Base64.getEncoder().encodeToString(signatureBytes);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

}
