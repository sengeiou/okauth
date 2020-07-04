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

import com.github.wautsns.okauth.core.assist.http.builtin.httpclient4.HttpClient4OAuth2HttpClient;
import com.github.wautsns.okauth.core.assist.http.kernel.OAuth2HttpClient;
import com.github.wautsns.okauth.core.assist.http.kernel.model.OAuth2HttpRequest;
import com.github.wautsns.okauth.core.assist.http.kernel.model.OAuth2HttpResponse;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.OAuth2Url;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatformNames;
import com.github.wautsns.okauth.core.client.builtin.dingtalk.model.DingTalkOAuth2User;
import com.github.wautsns.okauth.core.client.kernel.OAuth2Client;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeRedirectUriQueryForOpenid;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeRedirectUriQueryForUser;
import com.github.wautsns.okauth.core.client.kernel.openplatform.OpenPlatform;
import com.github.wautsns.okauth.core.client.kernel.util.Encryptor;
import com.github.wautsns.okauth.core.client.kernel.util.Encryptors;
import com.github.wautsns.okauth.core.exception.OAuth2ErrorException;
import com.github.wautsns.okauth.core.exception.OAuth2Exception;

/**
 * DingTalk oauth2 client.
 *
 * @author wautsns
 * @see <a href="https://ding-doc.dingtalk.com/doc#/serverapi3/mrugr3">DingTalk OAuth2 doc</a>
 * @since Jun 22, 2020
 */
public class DingTalkOAuth2Client extends OAuth2Client<DingTalkOAuth2AppInfo, DingTalkOAuth2User> {

    /**
     * Construct a DingTalk oauth2 client.
     *
     * @param appInfo oauth2 app info
     */
    public DingTalkOAuth2Client(DingTalkOAuth2AppInfo appInfo) {
        this(appInfo, new HttpClient4OAuth2HttpClient());
    }

    /**
     * Construct a DingTalk oauth2 client.
     *
     * @param appInfo oauth2 app info
     * @param httpClient oauth2 http client
     */
    public DingTalkOAuth2Client(DingTalkOAuth2AppInfo appInfo, OAuth2HttpClient httpClient) {
        super(appInfo, httpClient);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatformNames.DING_TALK;
    }

    // #################### initialize api ##############################################

    @Override
    protected InitializeAuthorizeUrl initApiInitializeAuthorizeUrl() {
        OAuth2Url basic = initBasicAuthorizeUrl(appInfo.getAuthorizeType());
        return state -> {
            OAuth2Url authorizeUrl = basic.copy();
            authorizeUrl.getQuery().addState(state);
            return authorizeUrl;
        };
    }

    /**
     * Initialize basic authorize url.
     *
     * @param authorizeType authorize type
     * @return basic authorize url
     */
    private OAuth2Url initBasicAuthorizeUrl(DingTalkOAuth2AppInfo.AuthorizeType authorizeType) {
        switch (authorizeType) {
            case QR_CODE:
                return initBasicAuthorizeUrlForQRCode();
            case WEB:
                return initBasicAuthorizeUrlForWeb();
            case PASSWORD:
                return initBasicAuthorizeUrlForPassword();
            default:
                throw new IllegalStateException("Unsupported authorize type: " + authorizeType);
        }
    }

    /**
     * Initialize basic authorize url for {@linkplain DingTalkOAuth2AppInfo.AuthorizeType#QR_CODE QR_CODE}
     *
     * @return basic authorize url
     */
    private OAuth2Url initBasicAuthorizeUrlForQRCode() {
        String url = "https://oapi.dingtalk.com/connect/qrconnect";
        OAuth2Url basic = new OAuth2Url(url);
        basic.getQuery()
                .addAppid(appInfo.getAppId())
                .addResponseTypeWithValueCode()
                .addScope("snsapi_login")
                .addRedirectUri(appInfo.getRedirectUri());
        return basic;
    }

    /**
     * Initialize basic authorize url for {@linkplain DingTalkOAuth2AppInfo.AuthorizeType#WEB WEB}
     *
     * @return basic authorize url
     */
    private OAuth2Url initBasicAuthorizeUrlForWeb() {
        String url = "https://oapi.dingtalk.com/connect/oauth2/sns_authorize";
        OAuth2Url basic = new OAuth2Url(url);
        basic.getQuery()
                .addAppid(appInfo.getAppId())
                .addResponseTypeWithValueCode()
                .addScope("snsapi_auth")
                .addRedirectUri(appInfo.getRedirectUri());
        return basic;
    }

    /**
     * Initialize basic authorize url for {@linkplain DingTalkOAuth2AppInfo.AuthorizeType#PASSWORD PASSWORD}
     *
     * @return basic authorize url
     */
    private OAuth2Url initBasicAuthorizeUrlForPassword() {
        String url = "https://oapi.dingtalk.com/connect/oauth2/sns_authorize";
        OAuth2Url basic = new OAuth2Url(url);
        basic.getQuery()
                .addAppid(appInfo.getAppId())
                .addResponseTypeWithValueCode()
                .addScope("snsapi_login")
                .addRedirectUri(appInfo.getRedirectUri());
        return basic;
    }

    @Override
    protected ExchangeRedirectUriQueryForOpenid initApiExchangeRedirectUriQueryForOpenid() {
        return redirectUriQuery -> exchangeForUser(redirectUriQuery).getOpenid();
    }

    @Override
    protected ExchangeRedirectUriQueryForUser<DingTalkOAuth2User> initApiExchangeRedirectUriQueryForUser() {
        String url = "https://oapi.dingtalk.com/sns/getuserinfo_bycode";
        OAuth2HttpRequest basic = OAuth2HttpRequest.initGet(url);
        basic.getUrl().getQuery().add("accessKey", appInfo.getAppId());
        Encryptor encryptor = Encryptors.hmacSha256(appInfo.getAppSecret());
        return redirectUriQuery -> {
            String timestamp = Long.toString(System.currentTimeMillis());
            OAuth2HttpRequest request = basic.copy();
            request.getUrl().getQuery()
                    .add("timestamp", timestamp)
                    .add("signature", encryptor.encrypt(timestamp))
                    .add("tmp_auth_code", redirectUriQuery.getCode());
            return new DingTalkOAuth2User(executeAndCheck(request));
        };
    }

    // #################### execute request and check response ##########################

    /**
     * Execute request and check response.
     *
     * @param request request
     * @return correct data map
     * @throws OAuth2Exception if oauth2 failed
     */
    protected DataMap executeAndCheck(OAuth2HttpRequest request) throws OAuth2Exception {
        OAuth2HttpResponse response = httpClient.execute(request);
        DataMap dataMap = response.readJsonAsDataMap();
        String errcode = dataMap.getAsString("errcode");
        String errmsg = dataMap.getAsString("errmsg");
        switch (errcode) {
            case "0":
                return dataMap.getAsDataMap("user_info");
            case "88":
                String subCode = dataMap.getAsString("sub_code");
                String subMsg = dataMap.getAsString("sub_msg");
                throw new OAuth2ErrorException(getOpenPlatform(), subCode, subMsg);
            default:
                throw new OAuth2ErrorException(getOpenPlatform(), errcode, errmsg);
        }
    }

}
