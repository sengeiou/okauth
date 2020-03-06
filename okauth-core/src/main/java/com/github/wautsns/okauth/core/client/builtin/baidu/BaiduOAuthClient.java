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
import com.github.wautsns.okauth.core.exception.OAuthErrorException;
import com.github.wautsns.okauth.core.http.HttpClient;
import com.github.wautsns.okauth.core.http.model.OAuthRequest;
import com.github.wautsns.okauth.core.http.model.OAuthResponse;
import com.github.wautsns.okauth.core.http.model.basic.OAuthUrl;
import com.github.wautsns.okauth.core.http.util.DataMap;

/**
 * Baidu oauth client.
 *
 * @author wautsns
 * @see <a href="http://developer.baidu.com/wiki/index.php?title=docs/oauth">Baidu OAuth doc</a>
 * @since Mar 04, 2020
 */
public class BaiduOAuthClient extends TokenRefreshableOAuthClient<BaiduUser> {

    /**
     * Construct Baidu oauth client.
     *
     * @param app oauth app properties, require nonnull
     * @param httpClient http client, require nonnull
     */
    public BaiduOAuthClient(OAuthAppProperties app, HttpClient httpClient) {
        super(app, httpClient);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatform.BAIDU;
    }

    @Override
    protected InitializeAuthorizeUrl initApiInitializeAuthorizeUrl() {
        OAuthUrl basic = new OAuthUrl("http://openapi.baidu.com/oauth/2.0/authorize");
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
        String url = "https://openapi.baidu.com/oauth/2.0/token";
        OAuthRequest basic = OAuthRequest.forGet(url);
        basic.getUrlQuery()
                .addGrantTypeWithValueAuthorizationCode()
                .addClientId(app.getClientId())
                .addClientSecret(app.getClientSecret())
                .addRedirectUri(app.getRedirectUri());
        return redirectUriQuery -> {
            OAuthRequest request = basic.copy();
            request.getUrlQuery().addCode(redirectUriQuery.getCode());
            return new OAuthToken(check(execute(request)));
        };
    }

    @Override
    protected RefreshToken initApiRefreshToken() {
        String url = "https://openapi.baidu.com/oauth/2.0/token";
        OAuthRequest basic = OAuthRequest.forGet(url);
        basic.getUrlQuery()
                .addGrantTypeWithValueRefreshToken()
                .addClientId(app.getClientId())
                .addClientSecret(app.getClientSecret());
        return token -> {
            OAuthRequest request = basic.copy();
            request.getUrlQuery().addRefreshToken(token.getRefreshToken());
            return new OAuthToken(check(execute(request)));
        };
    }

    @Override
    protected ExchangeTokenForOpenid initApiExchangeTokenForOpenid() {
        return token -> exchangeForUser(token).getOpenid();
    }

    @Override
    protected ExchangeTokenForUser<BaiduUser> initApiExchangeTokenForUser() {
        return token -> {
            String url = "https://openapi.baidu.com/rest/2.0/passport/users/getInfo";
            OAuthRequest request = OAuthRequest.forGet(url);
            request.getUrlQuery().addAccessToken(token.getAccessToken());
            return new BaiduUser(check(execute(request)));
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
        String description = dataMap.getAsString("error_description");
        if ("expired_token".equals(error)) {
            throw new ExpiredAccessTokenException(error, description);
        } else {
            // Baidu refresh token expires in 10 years. Ignore expired refresh token
            throw new OAuthErrorException(error, description);
        }
    }

}
