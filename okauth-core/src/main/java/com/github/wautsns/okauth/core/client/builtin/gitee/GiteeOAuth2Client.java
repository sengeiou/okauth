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
package com.github.wautsns.okauth.core.client.builtin.gitee;

import com.github.wautsns.okauth.core.assist.http.builtin.httpclient4.HttpClient4OAuth2HttpClient;
import com.github.wautsns.okauth.core.assist.http.kernel.OAuth2HttpClient;
import com.github.wautsns.okauth.core.assist.http.kernel.model.OAuth2HttpRequest;
import com.github.wautsns.okauth.core.assist.http.kernel.model.OAuth2HttpResponse;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.OAuth2Url;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatformNames;
import com.github.wautsns.okauth.core.client.builtin.gitee.model.GiteeOAuth2Token;
import com.github.wautsns.okauth.core.client.builtin.gitee.model.GiteeOAuth2User;
import com.github.wautsns.okauth.core.client.kernel.TokenRefreshableOAuth2Client;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeRedirectUriQueryForToken;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeTokenForOpenid;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeTokenForUser;
import com.github.wautsns.okauth.core.client.kernel.api.RefreshToken;
import com.github.wautsns.okauth.core.exception.OAuth2ErrorException;
import com.github.wautsns.okauth.core.exception.OAuth2Exception;
import com.github.wautsns.okauth.core.exception.specific.token.ExpiredAccessTokenException;
import com.github.wautsns.okauth.core.exception.specific.token.ExpiredRefreshTokenException;

/**
 * Gitee oauth2 client.
 *
 * @author wautsns
 * @see <a href="https://gitee.com/api/v5/oauth_doc">Gitee OAuth2 doc</a>
 * @since May 17, 2020
 */
public class GiteeOAuth2Client
        extends TokenRefreshableOAuth2Client<GiteeOAuth2AppInfo, GiteeOAuth2Token, GiteeOAuth2User> {

    /**
     * Construct a Gitee oauth2 client.
     *
     * @param appInfo oauth2 app info
     */
    public GiteeOAuth2Client(GiteeOAuth2AppInfo appInfo) {
        super(
                appInfo, new HttpClient4OAuth2HttpClient(),
                TokenRefreshCallback.IGNORE);
    }

    /**
     * Construct a Gitee oauth2 client.
     *
     * @param appInfo oauth2 app info
     * @param httpClient oauth2 http client
     * @param tokenRefreshCallback token refresh callback
     */
    public GiteeOAuth2Client(
            GiteeOAuth2AppInfo appInfo, OAuth2HttpClient httpClient,
            TokenRefreshCallback tokenRefreshCallback) {
        super(appInfo, httpClient, tokenRefreshCallback);
    }

    @Override
    public String getOpenPlatform() {
        return BuiltInOpenPlatformNames.GITEE;
    }

    // #################### initialize api ##############################################

    @Override
    protected InitializeAuthorizeUrl initApiInitializeAuthorizeUrl() {
        String url = "https://gitee.com/oauth/authorize";
        OAuth2Url basic = new OAuth2Url(url);
        basic.getQuery()
                .addClientId(appInfo.getClientId())
                .addRedirectUri(appInfo.getRedirectUri())
                .addResponseTypeWithValueCode()
                .addScope(GiteeOAuth2AppInfo.Scope.joinWith(appInfo.getScopes(), " "));
        return state -> {
            OAuth2Url authorizeUrl = basic.copy();
            authorizeUrl.getQuery().addState(state);
            return authorizeUrl;
        };
    }

    @Override
    protected ExchangeRedirectUriQueryForToken<GiteeOAuth2Token> initApiExchangeRedirectUriQueryForToken() {
        String url = "https://gitee.com/oauth/token";
        OAuth2HttpRequest basic = OAuth2HttpRequest.initPost(url);
        basic.getUrl().getQuery()
                .addGrantTypeWithValueAuthorizationCode()
                .addClientId(appInfo.getClientId())
                .addClientSecret(appInfo.getClientSecret())
                .addRedirectUri(appInfo.getRedirectUri());
        return redirectUriQuery -> {
            OAuth2HttpRequest request = basic.copy();
            request.getUrl().getQuery().addCode(redirectUriQuery.getCode());
            return new GiteeOAuth2Token(executeGetOrRefreshTokenAndCheck(request));
        };
    }

    @Override
    protected RefreshToken<GiteeOAuth2Token> initApiRefreshToken() {
        String url = "https://gitee.com/oauth/token";
        OAuth2HttpRequest basic = OAuth2HttpRequest.initPost(url);
        basic.getUrl().getQuery().addGrantTypeWithValueRefreshToken();
        return token -> {
            OAuth2HttpRequest request = basic.copy();
            request.getUrl().getQuery().addRefreshToken(token.getRefreshToken());
            return new GiteeOAuth2Token(executeGetOrRefreshTokenAndCheck(request));
        };
    }

    @Override
    protected ExchangeTokenForOpenid<GiteeOAuth2Token> initApiExchangeTokenForOpenid() {
        return token -> exchangeForUser(token).getOpenid();
    }

    @Override
    protected ExchangeTokenForUser<GiteeOAuth2Token, GiteeOAuth2User> initApiExchangeTokenForUser() {
        String url = "https://gitee.com/api/v5/user";
        OAuth2HttpRequest basic = OAuth2HttpRequest.initGet(url);
        return token -> {
            OAuth2HttpRequest request = basic.copy();
            request.getUrl().getQuery().addAccessToken(token.getAccessToken());
            return new GiteeOAuth2User(executeNotGetOrRefreshTokenAndCheck(request));
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
        if ("invalid_grant".equals(error)) {
            throw new ExpiredRefreshTokenException(getOpenPlatform(), error, errorDescription);
        } else {
            throw new OAuth2ErrorException(getOpenPlatform(), error, errorDescription);
        }
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
        if (response.getStatus() < 400) { return dataMap; }
        String message = dataMap.getAsString("message");
        if ("401 Unauthorized: Access token is expired".equals(message)) {
            throw new ExpiredAccessTokenException(getOpenPlatform(), "401 Unauthorized", "Access token is expired");
        } else {
            String[] errorAndDescription = message.split(": ", 2);
            throw new OAuth2ErrorException(getOpenPlatform(), errorAndDescription[0], errorAndDescription[1]);
        }
    }

}
