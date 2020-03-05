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

import com.github.wautsns.okauth.core.OpenPlatform;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatform;
import com.github.wautsns.okauth.core.client.kernel.OAuthAppProperties;
import com.github.wautsns.okauth.core.client.kernel.TokenAvailableOAuthClient;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeRedirectUriQueryForToken;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeTokenForOpenid;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeTokenForUser;
import com.github.wautsns.okauth.core.client.kernel.api.InitializeAuthorizeUrl;
import com.github.wautsns.okauth.core.client.kernel.model.OAuthToken;
import com.github.wautsns.okauth.core.exception.ExpiredAccessTokenException;
import com.github.wautsns.okauth.core.exception.OAuthErrorException;
import com.github.wautsns.okauth.core.http.HttpClient;
import com.github.wautsns.okauth.core.http.model.OAuthRequest;
import com.github.wautsns.okauth.core.http.model.OAuthResponse;
import com.github.wautsns.okauth.core.http.model.basic.OAuthUrl;
import com.github.wautsns.okauth.core.http.util.DataMap;

/**
 * GitHub oauth client.
 *
 * @author wautsns
 * @see <a href="https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/">GitHub OAuth doc</a>
 * @since Mar 04, 2020
 */
public class GitHubOAuthClient extends TokenAvailableOAuthClient<GitHubUser> {

    /**
     * Construct GitHub oauth client.
     *
     * @param app oauth app properties, require nonnull
     * @param httpClient http client, require nonnull
     */
    public GitHubOAuthClient(OAuthAppProperties app, HttpClient httpClient) {
        super(app, httpClient);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatform.GITHUB;
    }

    @Override
    protected InitializeAuthorizeUrl initApiInitializeAuthorizeUrl() {
        OAuthUrl basic = new OAuthUrl("https://github.com/login/oauth/authorize");
        basic.getQuery()
            .addClientId(app.getClientId())
            .addRedirectUri(app.getRedirectUri());
        return state -> {
            OAuthUrl url = basic.copy();
            url.getQuery().addState(state);
            return url;
        };
    }

    @Override
    protected ExchangeRedirectUriQueryForToken initApiExchangeRedirectUriQueryForToken() {
        String url = "https://github.com/login/oauth/access_token";
        OAuthRequest basic = OAuthRequest.forPost(url);
        basic.getHeaders().addAcceptWithValueJson();
        basic.getUrlQuery()
            .addClientId(app.getClientId())
            .addClientSecret(app.getClientSecret())
            .addRedirectUri(app.getRedirectUri());
        return redirectUriQuery -> {
            OAuthRequest request = basic.copy();
            request.getUrlQuery().addCode(redirectUriQuery.getCode());
            return new OAuthToken(checkToken(execute(request)));
        };
    }

    @Override
    protected ExchangeTokenForOpenid initApiExchangeTokenForOpenid() {
        return token -> exchangeForUser(token).getOpenid();
    }

    @Override
    protected ExchangeTokenForUser<GitHubUser> initApiExchangeTokenForUser() {
        return token -> {
            String url = "https://api.github.com/user";
            OAuthRequest request = OAuthRequest.forGet(url);
            request.getHeaders().addAuthorization("token", token.getAccessToken());
            return new GitHubUser(checkNotToken(execute(request)));
        };
    }

    /**
     * Check token response.
     *
     * @param response response, require nonnull
     * @return correct response
     * @throws OAuthErrorException if the response is incorrect
     */
    private static OAuthResponse checkToken(OAuthResponse response) throws OAuthErrorException {
        DataMap dataMap = response.getDataMap();
        String errorCode = dataMap.getAsString("error");
        if (errorCode == null) { return response; }
        String errorMsg = dataMap.getAsString("error_description");
        throw new OAuthErrorException(errorCode, errorMsg);
    }

    /**
     * Check not token or refresh token response.
     *
     * @param response response, require nonnull
     * @return correct response
     * @throws OAuthErrorException if the response is incorrect
     */
    private static OAuthResponse checkNotToken(OAuthResponse response) throws OAuthErrorException {
        if (response.getStatus() < 400) { return response; }
        String errorCode = Integer.toString(response.getStatus());
        String message = response.getDataMap().getAsString("message");
        if ("Bad credentials".equals(message)) {
            throw new ExpiredAccessTokenException(errorCode, message);
        } else {
            throw new OAuthErrorException(errorCode, message);
        }
    }

}
