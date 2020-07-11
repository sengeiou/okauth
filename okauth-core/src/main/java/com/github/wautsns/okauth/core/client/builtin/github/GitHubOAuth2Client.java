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
package com.github.wautsns.okauth.core.client.builtin.github;

import com.github.wautsns.okauth.core.assist.http.builtin.httpclient4.HttpClient4OAuth2HttpClient;
import com.github.wautsns.okauth.core.assist.http.kernel.OAuth2HttpClient;
import com.github.wautsns.okauth.core.assist.http.kernel.model.OAuth2HttpRequest;
import com.github.wautsns.okauth.core.assist.http.kernel.model.OAuth2HttpResponse;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.OAuth2Url;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatforms;
import com.github.wautsns.okauth.core.client.builtin.github.model.GitHubOAuth2Token;
import com.github.wautsns.okauth.core.client.builtin.github.model.GitHubOAuth2User;
import com.github.wautsns.okauth.core.client.kernel.TokenAvailableOAuth2Client;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeRedirectUriQueryForOpenid;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeRedirectUriQueryForToken;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeRedirectUriQueryForUser;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeTokenForOpenid;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeTokenForUser;
import com.github.wautsns.okauth.core.client.kernel.openplatform.OpenPlatform;
import com.github.wautsns.okauth.core.exception.OAuth2ErrorException;
import com.github.wautsns.okauth.core.exception.OAuth2Exception;
import com.github.wautsns.okauth.core.exception.specific.token.InvalidAccessTokenException;
import com.github.wautsns.okauth.core.exception.specific.user.UserRefusedAuthorizationException;

/**
 * GitHub oauth2 client.
 *
 * @author wautsns
 * @see <a href="https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/">GitHub OAuth2 doc</a>
 * @since May 17, 2020
 */
public class GitHubOAuth2Client
        extends TokenAvailableOAuth2Client<GitHubOAuth2AppInfo, GitHubOAuth2Token, GitHubOAuth2User> {

    /**
     * Construct a GitHub oauth2 client.
     *
     * @param appInfo oauth2 app info
     */
    public GitHubOAuth2Client(GitHubOAuth2AppInfo appInfo) {
        this(appInfo, new HttpClient4OAuth2HttpClient());
    }

    /**
     * Construct a GitHub oauth2 client.
     *
     * @param appInfo oauth2 app info
     * @param httpClient oauth2 http client
     */
    public GitHubOAuth2Client(GitHubOAuth2AppInfo appInfo, OAuth2HttpClient httpClient) {
        super(appInfo, httpClient);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatforms.GITHUB;
    }

    // #################### initialize api ##############################################

    @Override
    protected InitializeAuthorizeUrl initApiInitializeAuthorizeUrl() {
        String url = "https://github.com/login/oauth/authorize";
        OAuth2Url basic = new OAuth2Url(url);
        basic.getQuery()
                .addClientId(appInfo.getClientId())
                .addRedirectUri(appInfo.getRedirectUri())
                .addScope(GitHubOAuth2AppInfo.Scope.joinWith(appInfo.getScopes(), " "));
        GitHubOAuth2AppInfo.ExtraAuthorizeUrlQuery extra = appInfo.getExtraAuthorizeUrlQuery();
        basic.getQuery()
                .add("login", extra.getLogin())
                .add("allow_signup", extra.getAllowSignup().value);
        return state -> {
            OAuth2Url authorizeUrl = basic.copy();
            authorizeUrl.getQuery().addState(state);
            return authorizeUrl;
        };
    }

    @Override
    protected ExchangeRedirectUriQueryForToken<GitHubOAuth2Token> initApiExchangeRedirectUriQueryForToken() {
        String url = "https://github.com/login/oauth/access_token";
        OAuth2HttpRequest basic = OAuth2HttpRequest.initPost(url);
        basic.getHeaders().addAcceptWithValueJson();
        basic.getUrl().getQuery()
                .addClientId(appInfo.getClientId())
                .addClientSecret(appInfo.getClientSecret());
        // not required: .addRedirectUri(appInfo.getRedirectUri());
        return redirectUriQuery -> {
            String code = redirectUriQuery.getCode();
            if (code != null) {
                OAuth2HttpRequest request = basic.copy();
                request.getUrl().getQuery()
                        .addCode(redirectUriQuery.getCode());
                // not required: .addState(redirectUriQuery.getState());
                return new GitHubOAuth2Token(executeGetOrRefreshTokenAndCheck(request));
            } else {
                String error = redirectUriQuery.getError();
                String description = redirectUriQuery.getErrorDescription();
                if ("access_denied".equals(error)) {
                    throw new UserRefusedAuthorizationException(getOpenPlatform());
                } else {
                    throw new OAuth2ErrorException(getOpenPlatform(), error, description);
                }
            }
        };
    }

    @Override
    protected ExchangeTokenForOpenid<GitHubOAuth2Token> initApiExchangeTokenForOpenid() {
        return token -> exchangeForUser(token).getOpenid();
    }

    @Override
    protected ExchangeTokenForUser<GitHubOAuth2Token, GitHubOAuth2User> initApiExchangeTokenForUser() {
        String url = "https://api.github.com/user";
        OAuth2HttpRequest basic = OAuth2HttpRequest.initGet(url);
        return token -> {
            OAuth2HttpRequest request = basic.copy();
            request.getHeaders().addAuthorization("token", token.getAccessToken());
            return new GitHubOAuth2User(executeNotGetOrRefreshTokenAndCheck(request));
        };
    }

    @Override
    protected ExchangeRedirectUriQueryForOpenid initApiExchangeRedirectUriQueryForOpenid() {
        return redirectUriQuery -> exchangeForUser(redirectUriQuery).getOpenid();
    }

    @Override
    protected ExchangeRedirectUriQueryForUser<GitHubOAuth2User> initApiExchangeRedirectUriQueryForUser() {
        return redirectUriQuery -> exchangeForUser(exchangeForToken(redirectUriQuery));
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
        if (response.getStatus() < 400) { return dataMap; }
        String error = Integer.toString(response.getStatus());
        String message = dataMap.getAsString("message");
        if ("Bad credentials".equals(message)) {
            throw new InvalidAccessTokenException(getOpenPlatform(), error, message);
        } else {
            throw new OAuth2ErrorException(getOpenPlatform(), error, message);
        }
    }

}
