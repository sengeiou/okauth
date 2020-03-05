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
package com.github.wautsns.okauth.core.client.builtin.microblog;

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
 * MicroBlog oauth client.
 *
 * @author wautsns
 * @see <a href="https://open.weibo.com/wiki">MicroBlog OAuth doc</a>
 * @since Mar 04, 2020
 */
public class MicroBlogOAuthClient extends TokenAvailableOAuthClient<MicroBlogUser> {

    /**
     * Construct MicroBlog oauth client.
     *
     * @param app oauth app properties, require nonnull
     * @param httpClient http client, require nonnull
     */
    public MicroBlogOAuthClient(OAuthAppProperties app, HttpClient httpClient) {
        super(app, httpClient);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatform.MICROBLOG;
    }

    @Override
    protected InitializeAuthorizeUrl initApiInitializeAuthorizeUrl() {
        OAuthUrl basic = new OAuthUrl("https://api.weibo.com/oauth2/authorize");
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
        String url = "https://api.weibo.com/oauth2/access_token";
        OAuthRequest basic = OAuthRequest.forPost(url);
        basic.getUrlQuery()
            .addClientId(app.getClientId())
            .addClientSecret(app.getClientSecret())
            .addRedirectUri(app.getRedirectUri())
            .addGrantTypeWithValueAuthorizationCode();
        return redirectUriQuery -> {
            OAuthRequest request = basic.copy();
            request.getUrlQuery().addCode(redirectUriQuery.getCode());
            return new OAuthToken(check(execute(request)));
        };
    }

    @Override
    protected ExchangeTokenForOpenid initApiExchangeTokenForOpenid() {
        return token -> token.getOriginalDataMap().getAsString("uid");
    }

    @Override
    protected ExchangeTokenForUser<MicroBlogUser> initApiExchangeTokenForUser() {
        return token -> {
            String url = "https://api.weibo.com/2/users/show.json";
            OAuthRequest request = OAuthRequest.forGet(url);
            request.getUrlQuery()
                .addAccessToken(token.getAccessToken())
                .add("uid", token.getOriginalDataMap().getAsString("uid"));
            return new MicroBlogUser(check(execute(request)));
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
        Integer errorCode = dataMap.getInteger("error_code");
        if (errorCode == null) { return response; }
        String error = dataMap.getAsString("error");
        // FIXME two error codes mean that access token has expired?
        switch (errorCode) {
            case 21315:
            case 21317:
                throw new ExpiredAccessTokenException(errorCode.toString(), error);
            default:
                throw new OAuthErrorException(errorCode.toString(), error);
        }
    }

}
