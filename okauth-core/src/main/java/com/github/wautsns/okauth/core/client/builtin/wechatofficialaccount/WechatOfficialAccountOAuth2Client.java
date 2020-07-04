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
package com.github.wautsns.okauth.core.client.builtin.wechatofficialaccount;

import com.github.wautsns.okauth.core.assist.http.builtin.httpclient4.HttpClient4OAuth2HttpClient;
import com.github.wautsns.okauth.core.assist.http.kernel.OAuth2HttpClient;
import com.github.wautsns.okauth.core.assist.http.kernel.model.OAuth2HttpRequest;
import com.github.wautsns.okauth.core.assist.http.kernel.model.OAuth2HttpResponse;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.OAuth2Url;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatformNames;
import com.github.wautsns.okauth.core.client.builtin.wechatofficialaccount.model.WechatOfficialAccountOAuth2Token;
import com.github.wautsns.okauth.core.client.builtin.wechatofficialaccount.model.WechatOfficialAccountOAuth2User;
import com.github.wautsns.okauth.core.client.kernel.TokenRefreshableOAuth2Client;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeRedirectUriQueryForToken;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeTokenForOpenid;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeTokenForUser;
import com.github.wautsns.okauth.core.client.kernel.api.RefreshToken;
import com.github.wautsns.okauth.core.client.kernel.openplatform.OpenPlatform;
import com.github.wautsns.okauth.core.exception.OAuth2ErrorException;
import com.github.wautsns.okauth.core.exception.OAuth2Exception;
import com.github.wautsns.okauth.core.exception.specific.token.ExpiredAccessTokenException;
import com.github.wautsns.okauth.core.exception.specific.token.ExpiredRefreshTokenException;
import com.github.wautsns.okauth.core.exception.specific.token.InvalidAccessTokenException;
import com.github.wautsns.okauth.core.exception.specific.token.InvalidRefreshTokenException;

/**
 * WechatOfficialAccount oauth2 client.
 *
 * @author wautsns
 * @see <a href="https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/Wechat_webpage_authorization.html">
 * WechatOfficialAccount OAuth2 doc</a>
 * @since May 23, 2020
 */
public class WechatOfficialAccountOAuth2Client
        extends
        TokenRefreshableOAuth2Client<WechatOfficialAccountOAuth2AppInfo, WechatOfficialAccountOAuth2Token, WechatOfficialAccountOAuth2User> {

    /**
     * Construct a WechatOfficialAccount oauth2 client.
     *
     * @param appInfo oauth2 app info
     */
    public WechatOfficialAccountOAuth2Client(WechatOfficialAccountOAuth2AppInfo appInfo) {
        this(appInfo, new HttpClient4OAuth2HttpClient(), TokenRefreshCallback.IGNORE);
    }

    /**
     * Construct a WechatOfficialAccount oauth2 client.
     *
     * @param appInfo oauth2 app info
     * @param httpClient oauth2 http client
     * @param tokenRefreshCallback token refresh callback
     */
    public WechatOfficialAccountOAuth2Client(
            WechatOfficialAccountOAuth2AppInfo appInfo, OAuth2HttpClient httpClient,
            TokenRefreshCallback tokenRefreshCallback) {
        super(appInfo, httpClient, tokenRefreshCallback);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatformNames.WECHAT_OFFICIAL_ACCOUNT;
    }

    // #################### initialize api ##############################################

    @Override
    protected InitializeAuthorizeUrl initApiInitializeAuthorizeUrl() {
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize";
        OAuth2Url basic = new OAuth2Url(url);
        basic.getQuery()
                .addAppid(appInfo.getUniqueIdentifier())
                .addRedirectUri(appInfo.getRedirectUri())
                .addResponseTypeWithValueCode()
                .addScope(appInfo.getScope().value);
        basic.setAnchor("wechat_redirect");
        return state -> {
            OAuth2Url authorizeUrl = basic.copy();
            authorizeUrl.getQuery().addState(state);
            return authorizeUrl;
        };
    }

    @Override
    protected ExchangeRedirectUriQueryForToken<WechatOfficialAccountOAuth2Token> initApiExchangeRedirectUriQueryForToken() {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
        OAuth2HttpRequest basic = OAuth2HttpRequest.initGet(url);
        basic.getUrl().getQuery()
                .addAppid(appInfo.getUniqueIdentifier())
                .addSecret(appInfo.getAppSecret())
                .addGrantTypeWithValueAuthorizationCode()
                .addRedirectUri(appInfo.getRedirectUri())
                .addResponseTypeWithValueCode()
                .addScope(appInfo.getScope().value);
        return redirectUriQuery -> {
            OAuth2HttpRequest request = basic.copy();
            request.getUrl().getQuery().addCode(redirectUriQuery.getCode());
            return new WechatOfficialAccountOAuth2Token(executeAndCheck(request));
        };
    }

    @Override
    protected RefreshToken<WechatOfficialAccountOAuth2Token> initApiRefreshToken() {
        String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
        OAuth2HttpRequest basic = OAuth2HttpRequest.initGet(url);
        basic.getUrl().getQuery()
                .addAppid(appInfo.getUniqueIdentifier())
                .addGrantTypeWithValueRefreshToken();
        return token -> {
            OAuth2HttpRequest request = basic.copy();
            request.getUrl().getQuery().addRefreshToken(token.getRefreshToken());
            return new WechatOfficialAccountOAuth2Token(executeAndCheck(request));
        };
    }

    @Override
    protected ExchangeTokenForOpenid<WechatOfficialAccountOAuth2Token> initApiExchangeTokenForOpenid() {
        return WechatOfficialAccountOAuth2Token::getOpenId;
    }

    @Override
    protected ExchangeTokenForUser<WechatOfficialAccountOAuth2Token, WechatOfficialAccountOAuth2User> initApiExchangeTokenForUser() {
        String url = "https://api.weixin.qq.com/sns/userinfo";
        OAuth2HttpRequest basic = OAuth2HttpRequest.initGet(url);
        return token -> {
            OAuth2HttpRequest request = basic.copy();
            request.getUrl().getQuery()
                    .addAccessToken(token.getAccessToken())
                    .add("openid", token.getOpenId());
            return new WechatOfficialAccountOAuth2User(executeAndCheck(request));
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
                dataMap.remove("errcode");
                dataMap.remove("errmsg");
                return dataMap;
            case "40014":
                throw new InvalidAccessTokenException(getOpenPlatform(), errcode, errmsg);
            case "42001":
                throw new ExpiredAccessTokenException(getOpenPlatform(), errcode, errmsg);
            case "42002":
                throw new ExpiredRefreshTokenException(getOpenPlatform(), errcode, errmsg);
            case "40030":
                throw new InvalidRefreshTokenException(getOpenPlatform(), errcode, errmsg);
            default:
                throw new OAuth2ErrorException(getOpenPlatform(), errcode, errmsg);
        }
    }

}
