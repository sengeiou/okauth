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
package com.github.wautsns.okauth.core.client.builtin.baidu;

import com.github.wautsns.okauth.core.assist.http.builtin.httpclient4.HttpClient4OAuth2HttpClient;
import com.github.wautsns.okauth.core.assist.http.kernel.OAuth2HttpClient;
import com.github.wautsns.okauth.core.assist.http.kernel.model.OAuth2HttpRequest;
import com.github.wautsns.okauth.core.assist.http.kernel.model.OAuth2HttpResponse;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.OAuth2Url;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatforms;
import com.github.wautsns.okauth.core.client.builtin.baidu.model.BaiduOAuth2Token;
import com.github.wautsns.okauth.core.client.builtin.baidu.model.BaiduOAuth2User;
import com.github.wautsns.okauth.core.client.kernel.TokenRefreshableOAuth2Client;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeRedirectUriQueryForToken;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeTokenForOpenid;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeTokenForUser;
import com.github.wautsns.okauth.core.client.kernel.api.RefreshToken;
import com.github.wautsns.okauth.core.client.kernel.openplatform.OpenPlatform;
import com.github.wautsns.okauth.core.exception.OAuth2ErrorException;
import com.github.wautsns.okauth.core.exception.OAuth2Exception;
import com.github.wautsns.okauth.core.exception.specific.token.ExpiredAccessTokenException;

/**
 * Baidu oauth2 client.
 *
 * @author wautsns
 * @see <a href="http://developer.baidu.com/wiki/index.php?title=docs/oauth">Baidu OAuth2 doc</a>
 * @since May 17, 2020
 */
public class BaiduOAuth2Client
        extends TokenRefreshableOAuth2Client<BaiduOAuth2AppInfo, BaiduOAuth2Token, BaiduOAuth2User> {

    /**
     * Construct a Baidu oauth2 client.
     *
     * @param appInfo oauth2 app info
     */
    public BaiduOAuth2Client(BaiduOAuth2AppInfo appInfo) {
        this(appInfo, new HttpClient4OAuth2HttpClient(), TokenRefreshCallback.IGNORE);
    }

    /**
     * Construct a Baidu oauth2 client.
     *
     * @param appInfo oauth2 app info
     * @param httpClient oauth2 http client
     * @param tokenRefreshCallback token refresh callback
     */
    public BaiduOAuth2Client(
            BaiduOAuth2AppInfo appInfo, OAuth2HttpClient httpClient,
            TokenRefreshCallback tokenRefreshCallback) {
        super(appInfo, httpClient, tokenRefreshCallback);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatforms.BAIDU;
    }

    // #################### initialize api ##############################################

    @Override
    protected InitializeAuthorizeUrl initApiInitializeAuthorizeUrl() {
        String url = "http://openapi.baidu.com/oauth/2.0/authorize";
        OAuth2Url basic = new OAuth2Url(url);
        basic.getQuery()
                .addClientId(appInfo.getApiKey())
                .addResponseTypeWithValueCode()
                .addRedirectUri(appInfo.getRedirectUri())
                .addScope(BaiduOAuth2AppInfo.Scope.joinWith(appInfo.getScopes(), " "));
        BaiduOAuth2AppInfo.ExtraAuthorizeUrlQuery extra = appInfo.getExtraAuthorizeUrlQuery();
        basic.getQuery()
                .add("display", extra.getDisplay().value)
                .add("force_login", extra.getForceLogin().value)
                .add("confirm_login", extra.getConfirmLogin().value)
                .add("login_type", extra.getLoginType().value);
        return state -> {
            OAuth2Url authorizeUrl = basic.copy();
            authorizeUrl.getQuery().addState(state);
            return authorizeUrl;
        };
    }

    @Override
    protected ExchangeRedirectUriQueryForToken<BaiduOAuth2Token> initApiExchangeRedirectUriQueryForToken() {
        String url = "https://openapi.baidu.com/oauth/2.0/token";
        OAuth2HttpRequest basic = OAuth2HttpRequest.initGet(url);
        basic.getUrl().getQuery()
                .addGrantTypeWithValueAuthorizationCode()
                .addClientId(appInfo.getApiKey())
                .addClientSecret(appInfo.getSecretKey())
                .addRedirectUri(appInfo.getRedirectUri());
        return redirectUriQuery -> {
            OAuth2HttpRequest request = basic.copy();
            request.getUrl().getQuery().addCode(redirectUriQuery.getCode());
            return new BaiduOAuth2Token(executeGetOrRefreshTokenAndCheck(request));
        };
    }

    @Override
    protected RefreshToken<BaiduOAuth2Token> initApiRefreshToken() {
        String url = "https://openapi.baidu.com/oauth/2.0/token";
        OAuth2HttpRequest basic = OAuth2HttpRequest.initGet(url);
        basic.getUrl().getQuery()
                .addGrantTypeWithValueRefreshToken()
                .addClientId(appInfo.getApiKey())
                .addClientSecret(appInfo.getSecretKey())
                .addScope(BaiduOAuth2AppInfo.Scope.joinWith(appInfo.getScopes(), " "));
        return token -> {
            OAuth2HttpRequest request = basic.copy();
            request.getUrl().getQuery().addRefreshToken(token.getRefreshToken());
            return new BaiduOAuth2Token(executeGetOrRefreshTokenAndCheck(request));
        };
    }

    @Override
    protected ExchangeTokenForOpenid<BaiduOAuth2Token> initApiExchangeTokenForOpenid() {
        return token -> exchangeForUser(token).getOpenid();
    }

    @Override
    protected ExchangeTokenForUser<BaiduOAuth2Token, BaiduOAuth2User> initApiExchangeTokenForUser() {
        String url = "https://openapi.baidu.com/rest/2.0/passport/users/getInfo";
        OAuth2HttpRequest basic = OAuth2HttpRequest.initGet(url);
        return token -> {
            OAuth2HttpRequest request = basic.copy();
            request.getUrl().getQuery().addAccessToken(token.getAccessToken());
            return new BaiduOAuth2User(executeNotGetOrRefreshTokenAndCheck(request));
        };
    }

    // #################### execute request and check response ##########################

    /**
     * Execute request that is GET_TOKEN or REFRESH_TOKEN, and check response.
     *
     * @param request request
     * @return correct data map
     * @throws OAuth2Exception if oauth2 failed
     */
    protected DataMap executeGetOrRefreshTokenAndCheck(OAuth2HttpRequest request) throws OAuth2Exception {
        OAuth2HttpResponse response = httpClient.execute(request);
        DataMap dataMap = response.readJsonAsDataMap();
        String error = dataMap.getAsString("error");
        if (error == null) { return dataMap; }
        String errorDescription = dataMap.getAsString("error_description");
        throw new OAuth2ErrorException(getOpenPlatform(), error, errorDescription);
    }

    /**
     * Execute request that is neither GET_TOKEN nor REFRESH_TOKEN, and check response.
     *
     * @param request request
     * @return correct data map
     * @throws OAuth2Exception if oauth2 failed
     */
    protected DataMap executeNotGetOrRefreshTokenAndCheck(OAuth2HttpRequest request) throws OAuth2Exception {
        OAuth2HttpResponse response = httpClient.execute(request);
        DataMap dataMap = response.readJsonAsDataMap();
        String errorCode = dataMap.getAsString("error_code");
        if (errorCode == null) { return dataMap; }
        String errorMsg = dataMap.getAsString("error_msg");
        if ("111".equals(errorCode)) {
            throw new ExpiredAccessTokenException(getOpenPlatform(), errorCode, errorMsg);
        } else {
            // Baidu refresh token expires in 10 years.
            throw new OAuth2ErrorException(getOpenPlatform(), errorCode, errorMsg);
        }
    }

}
