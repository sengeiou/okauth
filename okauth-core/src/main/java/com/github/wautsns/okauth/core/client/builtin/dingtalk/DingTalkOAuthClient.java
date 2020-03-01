/**
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

import java.util.Base64;

import com.github.wautsns.okauth.core.client.OpenPlatform;
import com.github.wautsns.okauth.core.client.builtin.OpenPlatforms;
import com.github.wautsns.okauth.core.client.kernel.OAuthClient;
import com.github.wautsns.okauth.core.client.kernel.http.OAuthRequestExecutor;
import com.github.wautsns.okauth.core.client.kernel.http.model.basic.OAuthUrl;
import com.github.wautsns.okauth.core.client.kernel.http.model.dto.OAuthRequest;
import com.github.wautsns.okauth.core.client.kernel.http.model.dto.OAuthResponse;
import com.github.wautsns.okauth.core.client.kernel.model.dto.OAuthRedirectUriQuery;
import com.github.wautsns.okauth.core.client.kernel.model.properties.OAuthAppProperties;
import com.github.wautsns.okauth.core.exception.OAuthIOException;
import com.github.wautsns.okauth.core.exception.error.OAuthErrorException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * DingTalk oauth client.
 *
 * @since Mar 01, 2020
 * @author wautsns
 * @see <a href="https://ding-doc.dingtalk.com/doc#/serverapi2/kymkv6">dingtalk oauth doc</a>
 */
public class DingTalkOAuthClient extends OAuthClient<DingTalkUser> {

    /**
     * basic authorize url
     *
     * <p>Query items added are as follows:
     * <ul>
     * <li>appid: {@code app.getClientId()}</li>
     * <li>response_type: {@code "code"}</li>
     * <li>scope: {@code "snsapi_login"}</li>
     * <li>redirect_uri: {@code app.getRedirectUri()}</li>
     * </ul>
     */
    private final OAuthUrl basicAuthorizeUrl;
    /**
     * basic user request
     *
     * <p>Query items added are as follows:
     * <ul>
     * <li>accessKey: {@code app.getClientId()}</li>
     * </ul>
     */
    private final OAuthRequest basicUserRequest;

    /**
     * Construct DingTalk oauth client.
     *
     * @param app oauth app properties, require nonnull
     * @param executor oauth request executor, require nonnull
     */
    public DingTalkOAuthClient(OAuthAppProperties app, OAuthRequestExecutor executor) {
        super(app, executor);
        // basic authorize url
        String authorizeUrl = "https://oapi.dingtalk.com/connect/qrconnect";
        basicAuthorizeUrl = new OAuthUrl(authorizeUrl);
        basicAuthorizeUrl.getQuery()
            .add("appid", app.getClientId())
            .add("response_type", "code")
            .add("scope", "snsapi_login")
            .add("redirect_uri", app.getRedirectUri());
        // basic user request
        String userRequestUrl = "https://oapi.dingtalk.com/sns/getuserinfo_bycode";
        basicUserRequest = OAuthRequest.forPost(userRequestUrl);
        basicUserRequest.getQuery()
            .add("accessKey", app.getClientId());
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return OpenPlatforms.DINGTALK;
    }

    @Override
    public OAuthUrl initAuthorizeUrl(String state) {
        OAuthUrl url = basicAuthorizeUrl.copy();
        url.getQuery().addState(state);
        return url;
    }

    @Override
    public DingTalkUser requestForUser(OAuthRedirectUriQuery redirectUriQuery)
            throws OAuthErrorException, OAuthIOException {
        OAuthRequest request = basicUserRequest.copy();
        String timestamp = Long.toString(System.currentTimeMillis());
        request.getQuery()
            .add("timestamp", timestamp)
            .add("signature", sign(timestamp));
        request.getForm()
            .add("tmp_auth_code", redirectUriQuery.getCode());
        return new DingTalkUser(execute(request));
    }

    @Override
    protected String getErrorFromResponse(OAuthResponse response) {
        Integer errcode = (Integer) response.getData().get("errcode");
        return (errcode == 0) ? null : errcode.toString();
    }

    @Override
    protected String getErrorDescriptionFromResponse(OAuthResponse response) {
        return (String) response.getData().get("errmsg");
    }

    /**
     * Sign string.
     *
     * @param string string to sign, require nonnull
     * @return signature
     */
    private String sign(String string) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(app.getClientSecret().getBytes(), "HmacSHA256"));
            byte[] signatureBytes = mac.doFinal(string.getBytes());
            return Base64.getEncoder().encodeToString(signatureBytes);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

}
