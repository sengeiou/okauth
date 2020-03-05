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

import com.github.wautsns.okauth.core.OpenPlatform;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatform;
import com.github.wautsns.okauth.core.client.kernel.OAuthAppProperties;
import com.github.wautsns.okauth.core.client.kernel.TokenRefreshableOAuthClient;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeRedirectUriQueryForToken;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeTokenForOpenid;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeTokenForUser;
import com.github.wautsns.okauth.core.client.kernel.api.InitializeAuthorizeUrl;
import com.github.wautsns.okauth.core.client.kernel.api.RefreshToken;
import com.github.wautsns.okauth.core.client.kernel.model.OAuthToken;
import com.github.wautsns.okauth.core.exception.ExpiredAccessTokenException;
import com.github.wautsns.okauth.core.exception.ExpiredRefreshTokenException;
import com.github.wautsns.okauth.core.exception.OAuthErrorException;
import com.github.wautsns.okauth.core.http.HttpClient;
import com.github.wautsns.okauth.core.http.model.OAuthRequest;
import com.github.wautsns.okauth.core.http.model.OAuthResponse;
import com.github.wautsns.okauth.core.http.model.basic.OAuthUrl;
import com.github.wautsns.okauth.core.http.util.DataMap;

/**
 * Gitee oauth client.
 *
 * @author wautsns
 * @see <a href="https://gitee.com/api/v5/oauth_doc">Gitee OAuth doc</a>
 * @since Mar 04, 2020
 */
public class GiteeOAuthClient extends TokenRefreshableOAuthClient<GiteeUser> {

    /**
     * Construct Gitee oauth client.
     *
     * @param app oauth app properties, require nonnull
     * @param httpClient http client, require nonnull
     */
    public GiteeOAuthClient(OAuthAppProperties app, HttpClient httpClient) {
        super(app, httpClient);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatform.GITEE;
    }

    @Override
    protected InitializeAuthorizeUrl initApiInitializeAuthorizeUrl() {
        OAuthUrl basic = new OAuthUrl("https://gitee.com/oauth/authorize");
        basic.getQuery()
            .addResponseTypeWithValueCode()
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
        String url = "https://gitee.com/oauth/token";
        OAuthRequest basic = OAuthRequest.forPost(url);
        basic.getUrlQuery()
            .addGrantTypeWithValueAuthorizationCode()
            .addClientId(app.getClientId())
            .addClientSecret(app.getClientSecret())
            .addRedirectUri(app.getRedirectUri());
        return redirectUriQuery -> {
            OAuthRequest request = basic.copy();
            request.getUrlQuery().addCode(redirectUriQuery.getCode());
            return new OAuthToken(checkTokenOrRefreshToken(execute(request)));
        };
    }

    @Override
    protected RefreshToken initApiRefreshToken() {
        String url = "https://gitee.com/oauth/token";
        OAuthRequest basic = OAuthRequest.forPost(url);
        basic.getUrlQuery().addGrantTypeWithValueRefreshToken();
        return token -> {
            OAuthRequest request = basic.copy();
            request.getUrlQuery().addRefreshToken(token.getRefreshToken());
            return new OAuthToken(checkTokenOrRefreshToken(execute(request)));
        };
    }

    @Override
    protected ExchangeTokenForOpenid initApiExchangeTokenForOpenid() {
        return token -> exchangeForUser(token).getOpenid();
    }

    @Override
    protected ExchangeTokenForUser<GiteeUser> initApiExchangeTokenForUser() {
        return token -> {
            String url = "https://gitee.com/api/v5/user";
            OAuthRequest request = OAuthRequest.forGet(url);
            request.getUrlQuery().addAccessToken(token.getAccessToken());
            return new GiteeUser(checkNotTokenOrRefreshToken(execute(request)));
        };
    }

    /**
     * Check token or refresh token response.
     *
     * @param response response, require nonnull
     * @return correct response
     * @throws OAuthErrorException if the response is incorrect
     */
    private static OAuthResponse checkTokenOrRefreshToken(OAuthResponse response) throws OAuthErrorException {
        DataMap dataMap = response.getDataMap();
        String error = dataMap.getAsString("error");
        if (error == null) { return response; }
        String errorDescription = dataMap.getAsString("error_description");
        if ("invalid_grant".equals(error)) {
            throw new ExpiredRefreshTokenException(error, errorDescription);
        } else {
            throw new OAuthErrorException(error, errorDescription);
        }
    }

    /**
     * Check not token or refresh token response.
     *
     * @param response response, require nonnull
     * @return correct response
     * @throws OAuthErrorException if the response is incorrect
     */
    private static OAuthResponse checkNotTokenOrRefreshToken(OAuthResponse response) throws OAuthErrorException {
        if (response.getStatus() < 400) { return response; }
        String[] tmp = response.getDataMap().getAsString("message").split(": ", 2);
        if ("Access token is expired".equals(tmp[1])) {
            throw new ExpiredAccessTokenException(tmp[0], tmp[1]);
        } else {
            throw new OAuthErrorException(tmp[0], tmp[1]);
        }
    }

}
