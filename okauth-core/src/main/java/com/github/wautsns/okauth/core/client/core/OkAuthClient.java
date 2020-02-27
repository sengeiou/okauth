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

import java.util.Objects;

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
 * Abstract okauth client.
 *
 * @since Feb 27, 2020
 * @author wautsns
 */
public abstract class OkAuthClient {

    /** oauth application info */
    protected final OAuthAppInfo oauthAppInfo;
    /** requester */
    protected final OkAuthRequester requester;

    /** authorize url prototype */
    protected final OkAuthUrl authorizeUrlPrototype;
    /** token request prototype */
    protected final OkAuthRequest tokenRequestPrototype;
    /** user request prototype */
    protected final OkAuthRequest userRequestPrototype;

    /**
     * Construct an okauth client.
     *
     * @param oauthAppInfo oauth app info, require nonnull
     * @param requester requester for request, require nonnull
     */
    public OkAuthClient(OAuthAppInfo oauthAppInfo, OkAuthRequester requester) {
        this.oauthAppInfo = Objects.requireNonNull(oauthAppInfo);
        this.requester = Objects.requireNonNull(requester);
        this.authorizeUrlPrototype = initAuthorizeUrlPrototype();
        this.tokenRequestPrototype = initTokenRequestPrototype();
        this.userRequestPrototype = initUserRequestPrototype();
    }

    /**
     * Get the open platform of the client.
     *
     * @retuen the open platform of the client
     */
    public abstract OpenPlatform getOpenPlatform();

    /**
     * Get the oauth app info of the client.
     *
     * @return the oauth app info of the client
     */
    public OAuthAppInfo getOAuthAppInfo() {
        return oauthAppInfo;
    }

    // -------------------- authorize url ----------------------------------------

    /**
     * Initialize an authorize okauth url prototype.
     *
     * @return an authorize url prototype
     */
    protected abstract OkAuthUrl initAuthorizeUrlPrototype();

    /**
     * Initialize an authorize url.
     *
     * @return an authorize url.
     * @see #initAuthorizeUrl(String)
     *
     * @deprecated Use {@link #initAuthorizeUrl(String)} instead(parameter `state` is important)
     */
    @Deprecated
    public String initAuthorizeUrl() {
        return authorizeUrlPrototype.toString();
    }

    /**
     * Initialize an authorize url with state.
     *
     * @param state see {@linkplain OAuthRedirectUriQuery#state} for details
     * @return an authorize url
     */
    public String initAuthorizeUrl(String state) {
        return authorizeUrlPrototype.mutate()
            .addQueryParam("state", state)
            .toString();
    }

    // --------------------- oauth token ------------------------------------------

    /**
     * Initialize token request prototype.
     *
     * @return token request prototype
     */
    protected abstract OkAuthRequest initTokenRequestPrototype();

    /**
     * Mutate the {@linkplain #tokenRequestPrototype} with oauth redirect uri query.
     *
     * @param query redirect uri query, require nonnull
     * @return a token request mutated with oauth redirect uri query
     */
    protected abstract OkAuthRequest mutateTokenRequest(OAuthRedirectUriQuery query);

    /**
     * Request token with oauth redirect uri query.
     *
     * @param query redirect uri query, require nonnull
     * @return oauth token
     * @throws OkAuthErrorException if an oauth exception occurs
     * @throws OkAuthIOException if an IO exception occurs
     */
    public abstract OAuthToken requestToken(OAuthRedirectUriQuery query)
            throws OkAuthErrorException, OkAuthIOException;

    // --------------------- oauth user ------------------------------------------

    /**
     * Initialize a user request prototype.
     *
     * @return a user request prototype
     */
    protected abstract OkAuthRequest initUserRequestPrototype();

    /**
     * Mutate the {@linkplain #userRequestPrototype} with oauth token.
     *
     * @param token oauth token, require nonnull
     * @return a new user request mutated with oauth token
     */
    protected abstract OkAuthRequest mutateUserRequest(OAuthToken token);

    /**
     * Request open platform user info with oauth token.
     *
     * @param token oauth token, require nonnull
     * @return open platform user info
     * @throws OkAuthErrorException if an oauth exception occurs
     * @throws OkAuthIOException if an IO exception occurs
     */
    public abstract OAuthUser requestUser(OAuthToken token)
            throws OkAuthErrorException, OkAuthIOException;

    /**
     * Request open platform user info with oauth redirect uri query.
     *
     * <p><strong>The request method will lose the oauth token.</strong>
     *
     * @param query redirect uri query, require nonnull
     * @return open platform user info
     * @throws OkAuthErrorException if an oauth exception occurs
     * @throws OkAuthIOException if an IO exception occurs
     */
    public OAuthUser requestUser(OAuthRedirectUriQuery query)
            throws OkAuthErrorException, OkAuthIOException {
        return requestUser(requestToken(query));
    }

    // ----------------------- utils ----------------------------------------------

    /**
     * Check oauth response.
     *
     * @param response oauth response, require nonnull
     * @param errorName error name
     * @param errorDescriptionName error description name
     * @return correct oauth response
     * @throws OkAuthErrorException if the oauth response is not correct.
     */
    protected OkAuthResponse checkResponse(
            OkAuthResponse response, String errorName, String errorDescriptionName)
            throws OkAuthErrorException {
        Object temp = response.getData().get(errorName);
        String error;
        String errorDescription = null;
        if (temp != null) {
            error = temp.toString();
            temp = response.getData().get(errorDescriptionName);
            if (temp != null) { errorDescription = temp.toString(); }
        } else {
            int statusCode = response.getStatusCode();
            if (statusCode < 300 && statusCode >= 200) {
                return response;
            } else {
                error = String.valueOf(statusCode);
            }
        }
        if (errorDescription == null) { errorDescription = error; }
        throw new OkAuthErrorException(getOpenPlatform(), error, errorDescription);
    }

}
