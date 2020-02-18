/**
 * Copyright 2019 the original author or authors.
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
package com.github.wautsns.okauth.core.client.core;

import com.github.wautsns.okauth.core.client.core.dto.OAuthRedirectUriQuery;
import com.github.wautsns.okauth.core.client.core.dto.OAuthToken;
import com.github.wautsns.okauth.core.client.core.dto.OAuthUser;
import com.github.wautsns.okauth.core.client.core.properties.OAuthAppInfo;
import com.github.wautsns.okauth.core.client.util.http.Request;
import com.github.wautsns.okauth.core.client.util.http.Requester;
import com.github.wautsns.okauth.core.client.util.http.Response;
import com.github.wautsns.okauth.core.client.util.http.RequestUrl;
import com.github.wautsns.okauth.core.exception.OkAuthErrorException;
import com.github.wautsns.okauth.core.exception.OkAuthIOException;

/**
 * Standard OAuth2.0 client.
 *
 * @since Feb 18, 2020
 * @author wautsns
 * @see <a href="https://oauth.net/2/grant-types/authorization-code/">standard oauth2.0 doc</a>
 */
public abstract class StandardOkAuthClient extends OkAuthClient {

    /**
     * Construct a standard oauth2.0 client.
     *
     * @param oauthAppInfo oauth application info, require nonnull
     * @param requester requester, require nonnull
     */
    public StandardOkAuthClient(OAuthAppInfo oauthAppInfo, Requester requester) {
        super(oauthAppInfo, requester);
    }

    // -------------------- authorize url ----------------------------------------

    /** Get authorize url. */
    protected abstract String getAuthorizeUrl();

    @Override
    protected RequestUrl initAuthorizeUrlPrototype() {
        return new RequestUrl(getAuthorizeUrl())
            .addQueryParam("response_type", "code")
            .addQueryParam("client_id", oauthAppInfo.getClientId())
            .addQueryParam("redirect_uri", oauthAppInfo.getRedirectUri());
    }

    // --------------------- oauth token ------------------------------------------

    /** Get token url. */
    protected abstract String getTokenUrl();

    /**
     * Initialize token request prototype.
     *
     * @return token request prototype
     */
    @Override
    protected Request initTokenRequestPrototype() {
        return Request.initPost(getTokenUrl())
            .addFormItem("grant_type", "authorization_code")
            .addFormItem("client_id", oauthAppInfo.getClientId())
            .addFormItem("client_secret", oauthAppInfo.getClientSecret())
            .addFormItem("redirect_uri", oauthAppInfo.getRedirectUri());
    }

    /**
     * Mutate a token request.
     *
     * @param query redirect uri query, require nonnull
     * @return token request
     */
    @Override
    protected Request mutateTokenRequest(OAuthRedirectUriQuery query) {
        return tokenRequestPrototype.mutate()
            .addFormItem("code", query.getCode());
    }

    /**
     * Initialize an oauth token.
     *
     * @param response response after checking
     * @return oauth token
     * @see StandardOAuthToken
     */
    protected OAuthToken initOAuthToken(Response response) {
        return new OAuthToken(response);
    }

    @Override
    public OAuthToken exchangeQueryForToken(OAuthRedirectUriQuery query)
            throws OkAuthErrorException, OkAuthIOException {
        return initOAuthToken(exchangeAndCheck(mutateTokenRequest(query)));
    }

    // --------------------- oauth user ------------------------------------------

    /**
     * Initialize an oauth user.
     *
     * @param response response after checking, require nonnull
     * @return oauth user
     */
    protected abstract OAuthUser initOAuthUser(Response response);

    @Override
    public OAuthUser exchangeTokenForUser(OAuthToken token)
            throws OkAuthErrorException, OkAuthIOException {
        return initOAuthUser(exchangeAndCheck(mutateUserRequest(token)));
    }

    // ----------------------- utils ----------------------------------------------

    /**
     * Exchange and check.
     *
     * @param request request, require nonnull
     * @return response after checking
     * @throws OkAuthErrorException if an oauth error occurs
     * @throws OkAuthIOException if an IO exception occurs
     */
    protected Response exchangeAndCheck(Request request)
            throws OkAuthErrorException, OkAuthIOException {
        return checkResponse(requester.exchange(request), "error", "error_description");
    }

}
