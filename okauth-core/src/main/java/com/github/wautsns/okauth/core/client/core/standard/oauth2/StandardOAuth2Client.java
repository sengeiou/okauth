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
package com.github.wautsns.okauth.core.client.core.standard.oauth2;

import com.github.wautsns.okauth.core.client.core.OkAuthClient;
import com.github.wautsns.okauth.core.client.core.dto.OAuthAuthorizeUrlExtendedQuery;
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
 * Standard OAuth2.0 client.
 *
 * @author wautsns
 * @see <a href="https://oauth.net/2/grant-types/authorization-code/">standard oauth2.0 doc</a>
 */
public abstract class StandardOAuth2Client extends OkAuthClient {

    /** authorize url prototype */
    protected final Url authorizeUrlPrototype;
    /** token request prototype */
    protected final Request tokenRequestPrototype;
    /** user request prototype */
    protected final Request userRequestPrototype;

    /**
     * Construct a standard oauth2.0 client.
     *
     * @param oauthAppInfo oauth application info, require nonnull
     * @param requester requester, require nonnull
     */
    public StandardOAuth2Client(OAuthAppInfo oauthAppInfo, Requester requester) {
        super(oauthAppInfo, requester);
        authorizeUrlPrototype = new Url(getAuthorizeUrl())
            .addQueryParam("response_type", "code")
            .addQueryParam("client_id", oauthAppInfo.getClientId())
            .addQueryParam("redirect_uri", oauthAppInfo.getRedirectUri());
        tokenRequestPrototype = requester
            .post(getTokenUrl())
            .addFormItem("grant_type", "authorization_code")
            .addFormItem("redirect_uri", oauthAppInfo.getRedirectUri())
            .addFormItem("client_id", oauthAppInfo.getClientId())
            .addFormItem("client_secret", oauthAppInfo.getClientSecret());
        userRequestPrototype = initUserRequest(requester);
    }

    @Override
    public String doInitAuthorizeUrl(OAuthAuthorizeUrlExtendedQuery query) {
        return authorizeUrlPrototype.mutate()
            .addQueryParam("state", query.getState())
            .addQueryParam("scope", query.getScope())
            .toString();
    }

    @Override
    public OAuthToken exchangeForToken(OAuthRedirectUriQuery query)
            throws OkAuthErrorException, OkAuthIOException {
        return initOAuthToken(exchangeAndCheck(mutateTokenRequest(query)));
    }

    @Override
    public OAuthUser exchangeForUser(OAuthToken token)
            throws OkAuthErrorException, OkAuthIOException {
        return initOAuthUser(exchangeAndCheck(mutateUserRequest(token)));
    }

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
        return checkResponse(request.exchangeForJson(), "error", "error_description");
    }

    /**
     * Mutate a token request.
     *
     * @param query redirect uri query, require nonnull
     * @return token request
     */
    protected Request mutateTokenRequest(OAuthRedirectUriQuery query) {
        return tokenRequestPrototype.mutate()
            .addFormItem("code", query.getCode());
    }

    /**
     * Initialize an oauth token.
     *
     * @param response response after checking
     * @return oauth token
     * @see StandardOAuth2Token
     */
    protected OAuthToken initOAuthToken(Response response) {
        return new StandardOAuth2Token(response);
    }

    // ------------------------- BEGIN -------------------------
    // -- The following info is provided by the open platform ---
    // ---------------------------------------------------------

    /** Get authorize url. */
    protected abstract String getAuthorizeUrl();

    /** Get token url. */
    protected abstract String getTokenUrl();

    /**
     * Initialize a user request.
     *
     * @param requester requester
     * @return user request
     */
    protected abstract Request initUserRequest(Requester requester);

    /**
     * Mutate a user request.
     *
     * @param token oauth token, require nonnull
     * @return user request
     */
    protected abstract Request mutateUserRequest(OAuthToken token);

    /**
     * Initialize an oauth user.
     *
     * @param response response after checking
     * @return oauth user
     */
    protected abstract OAuthUser initOAuthUser(Response response);

}
