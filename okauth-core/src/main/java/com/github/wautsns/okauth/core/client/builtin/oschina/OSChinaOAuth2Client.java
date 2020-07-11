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
package com.github.wautsns.okauth.core.client.builtin.oschina;

import com.github.wautsns.okauth.core.assist.http.builtin.httpclient4.HttpClient4OAuth2HttpClient;
import com.github.wautsns.okauth.core.assist.http.kernel.OAuth2HttpClient;
import com.github.wautsns.okauth.core.assist.http.kernel.model.OAuth2HttpRequest;
import com.github.wautsns.okauth.core.assist.http.kernel.model.OAuth2HttpResponse;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.OAuth2Url;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatforms;
import com.github.wautsns.okauth.core.client.builtin.oschina.model.OSChinaOAuth2Token;
import com.github.wautsns.okauth.core.client.builtin.oschina.model.OSChinaOAuth2User;
import com.github.wautsns.okauth.core.client.kernel.TokenRefreshableOAuth2Client;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeRedirectUriQueryForToken;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeTokenForOpenid;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeTokenForUser;
import com.github.wautsns.okauth.core.client.kernel.api.RefreshToken;
import com.github.wautsns.okauth.core.client.kernel.openplatform.OpenPlatform;
import com.github.wautsns.okauth.core.exception.OAuth2ErrorException;
import com.github.wautsns.okauth.core.exception.OAuth2Exception;
import com.github.wautsns.okauth.core.exception.specific.token.InvalidAccessTokenException;
import com.github.wautsns.okauth.core.exception.specific.token.InvalidRefreshTokenException;

/**
 * OSChina oauth2 client.
 *
 * @author wautsns
 * @see <a href="https://www.oschina.net/openapi/docs">OSChina OAuth2 doc</a>
 * @since May 22, 2020
 */
public class OSChinaOAuth2Client
        extends TokenRefreshableOAuth2Client<OSChinaOAuth2AppInfo, OSChinaOAuth2Token, OSChinaOAuth2User> {

    /**
     * Construct an OSChina oauth2 client.
     *
     * @param appInfo oauth2 app info
     */
    public OSChinaOAuth2Client(OSChinaOAuth2AppInfo appInfo) {
        this(appInfo, new HttpClient4OAuth2HttpClient(), TokenRefreshCallback.IGNORE);
    }

    /**
     * Construct an OSChina oauth2 client.
     *
     * @param appInfo oauth2 app info
     * @param httpClient oauth2 http client
     * @param tokenRefreshCallback token refresh callback
     */
    public OSChinaOAuth2Client(
            OSChinaOAuth2AppInfo appInfo, OAuth2HttpClient httpClient,
            TokenRefreshCallback tokenRefreshCallback) {
        super(appInfo, httpClient, tokenRefreshCallback);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatforms.OSCHINA;
    }

    // #################### initialize api ##############################################

    @Override
    protected InitializeAuthorizeUrl initApiInitializeAuthorizeUrl() {
        String url = "https://www.oschina.net/action/oauth2/authorize";
        OAuth2Url basic = new OAuth2Url(url);
        basic.getQuery()
                .addClientId(appInfo.getClientId())
                .addResponseTypeWithValueCode()
                .addRedirectUri(appInfo.getRedirectUri());
        return state -> {
            OAuth2Url authorizeUrl = basic.copy();
            authorizeUrl.getQuery().addState(state);
            return authorizeUrl;
        };
    }

    @Override
    protected ExchangeRedirectUriQueryForToken<OSChinaOAuth2Token> initApiExchangeRedirectUriQueryForToken() {
        String url = "https://www.oschina.net/action/openapi/token";
        OAuth2HttpRequest basic = OAuth2HttpRequest.initGet(url);
        basic.getUrl().getQuery()
                .addClientId(appInfo.getClientId())
                .addClientSecret(appInfo.getClientSecret())
                .addGrantTypeWithValueAuthorizationCode()
                .addRedirectUri(appInfo.getRedirectUri());
        return redirectUriQuery -> {
            OAuth2HttpRequest request = basic.copy();
            request.getUrl().getQuery().addCode(redirectUriQuery.getCode());
            return new OSChinaOAuth2Token(executeAndCheck(request));
        };
    }

    @Override
    protected RefreshToken<OSChinaOAuth2Token> initApiRefreshToken() {
        String url = "https://www.oschina.net/action/openapi/token";
        OAuth2HttpRequest basic = OAuth2HttpRequest.initGet(url);
        basic.getUrl().getQuery()
                .addClientId(appInfo.getClientId())
                .addClientSecret(appInfo.getClientSecret())
                .addGrantTypeWithValueRefreshToken()
                .addRedirectUri(appInfo.getRedirectUri());
        return token -> {
            OAuth2HttpRequest request = basic.copy();
            request.getUrl().getQuery().addRefreshToken(token.getRefreshToken());
            return new OSChinaOAuth2Token(executeAndCheck(request));
        };
    }

    @Override
    protected ExchangeTokenForOpenid<OSChinaOAuth2Token> initApiExchangeTokenForOpenid() {
        return OSChinaOAuth2Token::getUid;
    }

    @Override
    protected ExchangeTokenForUser<OSChinaOAuth2Token, OSChinaOAuth2User> initApiExchangeTokenForUser() {
        String url = "https://www.oschina.net/action/openapi/user";
        OAuth2HttpRequest basic = OAuth2HttpRequest.initGet(url);
        return token -> {
            OAuth2HttpRequest request = basic.copy();
            request.getUrl().getQuery().addAccessToken(token.getAccessToken());
            return new OSChinaOAuth2User(executeAndCheck(request));
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
        String error = dataMap.getAsString("error");
        if (error == null) { return dataMap; }
        String errorDescription = dataMap.getAsString("error_description");
        if ("invalid_token".equals(error) && errorDescription.startsWith("Invalid access token")) {
            throw new InvalidAccessTokenException(getOpenPlatform(), error, errorDescription);
        } else if ("400".equals(error) && errorDescription.startsWith("Invalid refresh token")) {
            throw new InvalidRefreshTokenException(getOpenPlatform(), error, errorDescription);
        } else {
            throw new OAuth2ErrorException(getOpenPlatform(), error, errorDescription);
        }
    }

}
