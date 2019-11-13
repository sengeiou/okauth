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
import com.github.wautsns.okauth.core.client.util.http.Url;
import com.github.wautsns.okauth.core.exception.OkAuthErrorException;
import com.github.wautsns.okauth.core.exception.OkAuthIOException;

/**
 * Abstract okauth client.
 *
 * @author wautsns
 */
public abstract class OkAuthClient {

    /** oauth application info */
    protected final OAuthAppInfo oauthAppInfo;
    /** requester */
    protected final Requester requester;

    /** authorize url prototype */
    protected final Url authorizeUrlPrototype;
    /** token request prototype */
    protected final Request tokenRequestPrototype;
    /** user request prototype */
    protected final Request userRequestPrototype;

    /**
     * Construct an okauth client.
     *
     * @param oauthAppInfo oauth app info, require nonnull
     * @param requester requester, require nonnull
     */
    public OkAuthClient(OAuthAppInfo oauthAppInfo, Requester requester) {
        this.oauthAppInfo = oauthAppInfo;
        this.requester = requester;
        this.authorizeUrlPrototype = initAuthorizeUrlPrototype();
        this.tokenRequestPrototype = initTokenRequestPrototype();
        this.userRequestPrototype = initUserRequestPrototype();
    }

    /** Get open platform. */
    public abstract OpenPlatform getOpenPlatform();

    // ------------------------- BEGIN -------------------------
    // --------------------- authorize url ---------------------
    // ---------------------------------------------------------

    /**
     * Initialize authorize url prototype.
     *
     * @return authorize url prototype
     */
    protected abstract Url initAuthorizeUrlPrototype();

    /**
     * Initialize an authorize url.
     *
     * @param state the param is used to prevent CSRF attacks
     * @return authorize url
     */
    public String initAuthorizeUrl(String state) {
        return authorizeUrlPrototype.mutate()
            .addQueryParam("state", state)
            .toString();
    }

    // ------------------------- BEGIN -------------------------
    // ---------------------- oauth token ----------------------
    // ---------------------------------------------------------

    /**
     * Initialize token request prototype.
     *
     * @return token request prototype
     */
    protected abstract Request initTokenRequestPrototype();

    /**
     * Mutate a token request.
     *
     * @param query redirect uri query, require nonnull
     * @return token request
     */
    protected abstract Request mutateTokenRequest(OAuthRedirectUriQuery query);

    /**
     * Exchange query for oauth token.
     *
     * @param query redirect uri query, require nonnull
     * @return oauth token
     * @throws OkAuthErrorException if an oauth exception occurs
     * @throws OkAuthIOException if an IO exception occurs
     */
    public abstract OAuthToken exchangeQueryForToken(OAuthRedirectUriQuery query)
            throws OkAuthErrorException, OkAuthIOException;

    // ------------------------- BEGIN -------------------------
    // ----------------------- oauth user ----------------------
    // ---------------------------------------------------------

    /**
     * Initialize a user request.
     *
     * @return user request
     */
    protected abstract Request initUserRequestPrototype();

    /**
     * Mutate a user request.
     *
     * @param token oauth token, require nonnull
     * @return user request
     */
    protected abstract Request mutateUserRequest(OAuthToken token);

    /**
     * Exchange token for oauth user.
     *
     * @param token oauth token, require nonnull
     * @return oauth user
     * @throws OkAuthErrorException if an oauth exception occurs
     * @throws OkAuthIOException if an IO exception occurs
     */
    public abstract OAuthUser exchangeTokenForUser(OAuthToken token)
            throws OkAuthErrorException, OkAuthIOException;

    /**
     * Exchange query for user.
     *
     * @param query redirect uri query, require nonnull
     * @return oauth user
     * @throws OkAuthErrorException if an oauth exception occurs
     * @throws OkAuthIOException if an IO exception occurs
     */
    public OAuthUser exchangeQueryForUser(OAuthRedirectUriQuery query)
            throws OkAuthErrorException, OkAuthIOException {
        return exchangeTokenForUser(exchangeQueryForToken(query));
    }

    // ------------------------- BEGIN -------------------------
    // ------------------------ assists ------------------------
    // ---------------------------------------------------------

    /**
     * Check response.
     *
     * @param response response, require nonnull
     * @param errorName error name
     * @param errorDescriptionName error description name
     * @return response
     * @throws OkAuthErrorException if an oauth exception occurs
     */
    protected Response checkResponse(
            Response response, String errorName, String errorDescriptionName)
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
