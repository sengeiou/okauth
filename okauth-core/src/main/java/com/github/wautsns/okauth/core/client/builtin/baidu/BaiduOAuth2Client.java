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

import com.github.wautsns.okauth.core.assist.http.builtin.okhttp3.OkHttp3OAuth2HttpClient;
import com.github.wautsns.okauth.core.assist.http.kernel.OAuth2HttpClient;
import com.github.wautsns.okauth.core.assist.http.kernel.model.OAuth2HttpRequest;
import com.github.wautsns.okauth.core.assist.http.kernel.model.OAuth2HttpResponse;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.OAuth2Url;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatformNames;
import com.github.wautsns.okauth.core.client.builtin.baidu.model.BaiduOAuth2Token;
import com.github.wautsns.okauth.core.client.builtin.baidu.model.BaiduOAuth2User;
import com.github.wautsns.okauth.core.client.kernel.TokenRefreshableOAuth2Client;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeRedirectUriQueryForToken;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeTokenForOpenid;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeTokenForUser;
import com.github.wautsns.okauth.core.client.kernel.api.InitializeAuthorizeUrl;
import com.github.wautsns.okauth.core.client.kernel.api.RefreshToken;
import com.github.wautsns.okauth.core.exception.OAuth2ErrorException;
import com.github.wautsns.okauth.core.exception.OAuth2Exception;
import com.github.wautsns.okauth.core.exception.specific.token.ExpiredAccessTokenException;

import java.util.Objects;

/**
 * Baidu oauth2 client.
 *
 * @author wautsns
 * @see <a href="http://developer.baidu.com/wiki/index.php?title=docs/oauth">Baidu OAuth doc</a>
 * @since May 17, 2020
 */
public class BaiduOAuth2Client
        extends TokenRefreshableOAuth2Client<BaiduOAuth2AppInfo, BaiduOAuth2Token, BaiduOAuth2User> {

    /** display switcher */
    private final BaiduOAuth2AppInfo.ExtraAuthorizeUrlQuery.DisplaySupplier displaySupplier;

    /**
     * Construct Baidu oauth2 client.
     *
     * @param appInfo oauth2 app info
     */
    public BaiduOAuth2Client(BaiduOAuth2AppInfo appInfo) {
        this(
                appInfo, OkHttp3OAuth2HttpClient.DEFAULT,
                TokenRefreshCallback.DEFAULT,
                BaiduOAuth2AppInfo.ExtraAuthorizeUrlQuery.DisplaySupplier.DEFAULT);
    }

    /**
     * Construct Baidu oauth2 client.
     *
     * @param appInfo oauth2 app info
     * @param httpClient oauth2 http client
     * @param tokenRefreshCallback token refresh callback
     * @param displaySupplier display switcher
     */
    public BaiduOAuth2Client(
            BaiduOAuth2AppInfo appInfo, OAuth2HttpClient httpClient,
            TokenRefreshCallback tokenRefreshCallback,
            BaiduOAuth2AppInfo.ExtraAuthorizeUrlQuery.DisplaySupplier displaySupplier) {
        super(appInfo, httpClient, tokenRefreshCallback);
        this.displaySupplier = Objects.requireNonNull(displaySupplier);
    }

    @Override
    public String getOpenPlatform() {
        return BuiltInOpenPlatformNames.BAIDU;
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
                .addScope(BaiduOAuth2AppInfo.Scope.join(appInfo.getScope()));
        BaiduOAuth2AppInfo.ExtraAuthorizeUrlQuery extra = appInfo.getExtraAuthorizeUrlQuery();
        basic.getQuery()
                .add("display", extra.getDisplay().value)
                .add("force_login", extra.getForceLogin().value)
                .add("confirm_login", extra.getConfirmLogin().value)
                .add("login_type", extra.getLoginType().value);
        return state -> {
            OAuth2Url oauth2Url = basic.copy();
            oauth2Url.getQuery()
                    .addState(state)
                    .set("display", displaySupplier.get(state).value);
            return oauth2Url;
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
                .addScope(BaiduOAuth2AppInfo.Scope.join(appInfo.getScope()));
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
            try {
                return new BaiduOAuth2User(executeNotGetOrRefreshTokenAndCheck(request));
            } catch (ExpiredAccessTokenException e) {
                return exchangeForUser(refreshToken(token));
            }
        };
    }

    // #################### execute request and check response ##########################

    /**
     * Execute get or refresh token request and check response.
     *
     * @param request request
     * @return correct data map
     * @throws OAuth2Exception if oauth2 failed
     */
    private DataMap executeGetOrRefreshTokenAndCheck(OAuth2HttpRequest request) throws OAuth2Exception {
        OAuth2HttpResponse response = httpClient.execute(request);
        DataMap dataMap = response.getEntity().readJsonAsDataMap();
        String error = dataMap.getAsString("error");
        if (error == null) { return dataMap; }
        String errorDescription = dataMap.getAsString("error_description");
        throw new OAuth2ErrorException(getOpenPlatform(), error, errorDescription);
    }

    /**
     * Execute not get or refresh token request and check response.
     *
     * @param request request
     * @return correct data map
     * @throws OAuth2Exception if oauth2 failed
     */
    private DataMap executeNotGetOrRefreshTokenAndCheck(OAuth2HttpRequest request) throws OAuth2Exception {
        OAuth2HttpResponse response = httpClient.execute(request);
        DataMap dataMap = response.getEntity().readJsonAsDataMap();
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
