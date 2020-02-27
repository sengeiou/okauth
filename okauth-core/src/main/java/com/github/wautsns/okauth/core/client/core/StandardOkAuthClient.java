/**
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
package com.github.wautsns.okauth.core.client.core;

import com.github.wautsns.okauth.core.client.core.dto.OAuthRedirectUriQuery;
import com.github.wautsns.okauth.core.client.core.dto.OAuthToken;
import com.github.wautsns.okauth.core.client.core.dto.OAuthUser;
import com.github.wautsns.okauth.core.client.core.properties.OAuthAppInfo;
import com.github.wautsns.okauth.core.client.util.http.OkAuthRequest;
import com.github.wautsns.okauth.core.client.util.http.OkAuthRequester;
import com.github.wautsns.okauth.core.client.util.http.OkAuthResponse;
import com.github.wautsns.okauth.core.client.util.http.OkAuthUrl;
import com.github.wautsns.okauth.core.exception.OkAuthErrorException;
import com.github.wautsns.okauth.core.exception.OkAuthIOException;

/**
 * Standard OAuth2.0 client.
 *
 * @since Feb 27, 2020
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
    public StandardOkAuthClient(OAuthAppInfo oauthAppInfo, OkAuthRequester requester) {
        super(oauthAppInfo, requester);
    }

    // -------------------- authorize url ----------------------------------------

    /**
     * Get authorize url.
     *
     * @return authorize url
     */
    protected abstract String getAuthorizeUrl();

    /**
     * Initialize authorize url prototype with query param `response_type`(="code"), `client_id` and
     * `redirect_uri`.
     *
     * @return authorized url prototype
     */
    @Override
    protected OkAuthUrl initAuthorizeUrlPrototype() {
        return new OkAuthUrl(getAuthorizeUrl())
            .addQueryParam("response_type", "code")
            .addQueryParam("client_id", oauthAppInfo.getClientId())
            .addQueryParam("redirect_uri", oauthAppInfo.getRedirectUri());
    }

    // --------------------- oauth token ------------------------------------------

    /**
     * Get token request url.
     *
     * @return token request url
     */
    protected abstract String getTokenRequestUrl();

    /**
     * Initialize token request prototype(method: POST) with form item
     * `grant_type`(="authorization_code"), `client_id`, `client_secret` and `redirect_uri`.
     *
     * @return token request prototype
     */
    @Override
    protected OkAuthRequest initTokenRequestPrototype() {
        return OkAuthRequest.forPost(getTokenRequestUrl())
            .addFormItem("grant_type", "authorization_code")
            .addFormItem("client_id", oauthAppInfo.getClientId())
            .addFormItem("client_secret", oauthAppInfo.getClientSecret())
            .addFormItem("redirect_uri", oauthAppInfo.getRedirectUri());
    }

    /**
     * Mutate the {@linkplain OkAuthClient#tokenRequestPrototype} with form item "code".
     *
     * @param query redirect uri query, require nonnull
     * @return a token request mutated with oauth redirect uri query
     */
    @Override
    protected OkAuthRequest mutateTokenRequest(OAuthRedirectUriQuery query) {
        return tokenRequestPrototype.mutate()
            .addFormItem("code", query.getCode());
    }

    /**
     * New oauth token with oauth response.
     *
     * @param response correct oauth response, require nonnull
     * @return oauth token
     */
    protected OAuthToken newOAuthToken(OkAuthResponse response) {
        return new OAuthToken(response);
    }

    @Override
    public OAuthToken requestToken(OAuthRedirectUriQuery query)
            throws OkAuthErrorException, OkAuthIOException {
        return newOAuthToken(requestAndCheck(mutateTokenRequest(query)));
    }

    // --------------------- oauth user ------------------------------------------

    /**
     * Mutate the {@linkplain OkAuthClient#userRequestPrototype} with query param "access_token".
     *
     * @param token oauth token, require nonnull
     * @return a new user request mutated with oauth token
     */
    @Override
    protected OkAuthRequest mutateUserRequest(OAuthToken token) {
        return userRequestPrototype.mutate()
            .addQueryParam("access_token", token.getAccessToken());
    }

    /**
     * New open platform user info.
     *
     * @param correct oauth response, require nonnull
     * @return open platform user info
     */
    protected abstract OAuthUser newOAuthUser(OkAuthResponse response);

    @Override
    public OAuthUser requestUser(OAuthToken token)
            throws OkAuthErrorException, OkAuthIOException {
        return newOAuthUser(requestAndCheck(mutateUserRequest(token)));
    }

    // ----------------------- utils ----------------------------------------------

    /**
     * Request and check.
     *
     * @param request request, require nonnull
     * @return correct oauth response
     * @throws OkAuthErrorException if an oauth error occurs(error name is "error" and error
     *         description name is "error_description")
     * @throws OkAuthIOException if an IO exception occurs
     */
    protected OkAuthResponse requestAndCheck(OkAuthRequest request)
            throws OkAuthErrorException, OkAuthIOException {
        return checkResponse(requester.exchange(request), "error", "error_description");
    }

}
