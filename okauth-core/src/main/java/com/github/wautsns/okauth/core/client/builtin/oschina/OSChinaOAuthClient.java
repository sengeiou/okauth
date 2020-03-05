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
import com.github.wautsns.okauth.core.exception.OAuthErrorException;
import com.github.wautsns.okauth.core.http.HttpClient;
import com.github.wautsns.okauth.core.http.model.OAuthRequest;
import com.github.wautsns.okauth.core.http.model.OAuthResponse;
import com.github.wautsns.okauth.core.http.model.basic.OAuthUrl;
import com.github.wautsns.okauth.core.http.util.DataMap;

/**
 * OSChina oauth client.
 *
 * @author wautsns
 * @see <a href="https://www.oschina.net/openapi/docs">OSChina OAuth doc</a>
 * @since Mar 04, 2020
 */
public class OSChinaOAuthClient extends TokenRefreshableOAuthClient<OSChinaUser> {

    /**
     * Construct OSChina oauth oauth client.
     *
     * @param app oauth app properties, require nonnull
     * @param httpClient http client, require nonnull
     */
    public OSChinaOAuthClient(OAuthAppProperties app, HttpClient httpClient) {
        super(app, httpClient);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatform.OSCHINA;
    }

    @Override
    protected InitializeAuthorizeUrl initApiInitializeAuthorizeUrl() {
        OAuthUrl basic = new OAuthUrl("https://www.oschina.net/action/oauth2/authorize");
        basic.getQuery()
            .addClientId(app.getClientId())
            .addResponseTypeWithValueCode()
            .addRedirectUri(app.getRedirectUri());
        return state -> {
            OAuthUrl url = basic.copy();
            url.getQuery().addState(state);
            return url;
        };
    }

    @Override
    protected ExchangeRedirectUriQueryForToken initApiExchangeRedirectUriQueryForToken() {
        String url = "https://www.oschina.net/action/openapi/token";
        OAuthRequest basic = OAuthRequest.forGet(url);
        basic.getUrlQuery()
            .addClientId(app.getClientId())
            .addClientSecret(app.getClientSecret())
            .addGrantTypeWithValueAuthorizationCode()
            .addRedirectUri(app.getRedirectUri());
        return redirectUriQuery -> {
            OAuthRequest request = basic.copy();
            request.getUrlQuery().addCode(redirectUriQuery.getCode());
            return new OAuthToken(check(execute(request)));
        };
    }

    @Override
    protected RefreshToken initApiRefreshToken() {
        String url = "https://www.oschina.net/action/openapi/token";
        OAuthRequest basic = OAuthRequest.forGet(url);
        basic.getUrlQuery().addGrantTypeWithValueRefreshToken();
        return token -> {
            OAuthRequest request = basic.copy();
            request.getUrlQuery().addCode(token.getRefreshToken());
            return new OAuthToken(check(execute(request)));
        };
    }

    @Override
    protected ExchangeTokenForOpenid initApiExchangeTokenForOpenid() {
        return token -> token.getOriginalDataMap().getAsString("uid");
    }

    @Override
    protected ExchangeTokenForUser<OSChinaUser> initApiExchangeTokenForUser() {
        return token -> {
            String url = "https://www.oschina.net/action/openapi/user";
            OAuthRequest request = OAuthRequest.forGet(url);
            request.getUrlQuery().addAccessToken(token.getAccessToken());
            return new OSChinaUser(check(execute(request)));
        };
    }

    /**
     * Check response.
     *
     * @param response response, require nonnull
     * @return correct response
     * @throws OAuthErrorException if the response is incorrect
     */
    private static OAuthResponse check(OAuthResponse response) throws OAuthErrorException {
        DataMap dataMap = response.getDataMap();
        String error = dataMap.getAsString("error");
        if (error == null) { return response; }
        String errorDescription = dataMap.getAsString("error_description");
        // FIXME expired access_token/refresh_token
        throw new OAuthErrorException(error, errorDescription);
    }

}
