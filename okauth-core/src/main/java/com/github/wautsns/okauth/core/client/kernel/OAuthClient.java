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
package com.github.wautsns.okauth.core.client.kernel;

import com.github.wautsns.okauth.core.OpenPlatform;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeRedirectUriQueryForOpenid;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeRedirectUriQueryForUser;
import com.github.wautsns.okauth.core.client.kernel.api.InitializeAuthorizeUrl;
import com.github.wautsns.okauth.core.client.kernel.model.OAuthRedirectUriQuery;
import com.github.wautsns.okauth.core.client.kernel.model.OAuthUser;
import com.github.wautsns.okauth.core.exception.OAuthErrorException;
import com.github.wautsns.okauth.core.exception.OAuthIOException;
import com.github.wautsns.okauth.core.http.HttpClient;
import com.github.wautsns.okauth.core.http.model.OAuthRequest;
import com.github.wautsns.okauth.core.http.model.OAuthResponse;
import com.github.wautsns.okauth.core.http.model.basic.OAuthUrl;

import java.io.IOException;
import java.util.Objects;

/**
 * OAuth client.
 *
 * @author wautsns
 * @since Mar 04, 2020
 */
public abstract class OAuthClient<U extends OAuthUser> {

    /** oauth app properties */
    protected final OAuthAppProperties app;
    /** http client */
    private final HttpClient httpClient;
    /** api: initialize authorize url */
    private final InitializeAuthorizeUrl initializeAuthorizeUrl;
    /** api: exchange redirect uri query for openid */
    private final ExchangeRedirectUriQueryForOpenid exchangeRedirectUriQueryForOpenid;
    /** api: exchange redirect uri query for user */
    private final ExchangeRedirectUriQueryForUser<U> exchangeRedirectUriQueryForUser;

    /**
     * Construct oauth client.
     *
     * @param app oauth app properties, require nonnull
     * @param httpClient http client, require nonnull
     */
    public OAuthClient(OAuthAppProperties app, HttpClient httpClient) {
        this.app = Objects.requireNonNull(app);
        this.httpClient = Objects.requireNonNull(httpClient);
        // BEGIN check app
        Objects.requireNonNull(app.getClientId());
        Objects.requireNonNull(app.getClientSecret());
        Objects.requireNonNull(app.getRedirectUri());
        // _END_ check app
        this.initializeAuthorizeUrl = initApiInitializeAuthorizeUrl();
        this.exchangeRedirectUriQueryForOpenid = initExchangeRedirectUriQueryForOpenid();
        this.exchangeRedirectUriQueryForUser = initExchangeRedirectUriQueryForUser();
    }

    /**
     * Get open platform.
     *
     * @return open platform
     */
    public abstract OpenPlatform getOpenPlatform();

    /**
     * Initialize authorize url.
     *
     * <p>If the state is {@code null}, the `state` will not be added to query.
     *
     * @param state state
     * @return authorize url
     */
    public final OAuthUrl initAuthorizeUrl(String state) {
        return initializeAuthorizeUrl.apply(state);
    }

    /**
     * Exchange redirect uri query for openid.
     *
     * @param redirectUriQuery redirect uri query, require nonnull
     * @return openid
     * @throws OAuthErrorException if open platform gives error message
     * @throws OAuthIOException if IO exception occurs
     */
    public final String exchangeForOpenid(OAuthRedirectUriQuery redirectUriQuery)
            throws OAuthErrorException, OAuthIOException {
        return exchangeRedirectUriQueryForOpenid.apply(redirectUriQuery);
    }

    /**
     * Exchange redirect uri query for user.
     *
     * @param redirectUriQuery redirect uri query, require nonnull
     * @return user
     * @throws OAuthErrorException if open platform gives error message
     * @throws OAuthIOException if IO exception occurs
     */
    public final U exchangeForUser(OAuthRedirectUriQuery redirectUriQuery)
            throws OAuthErrorException, OAuthIOException {
        return exchangeRedirectUriQueryForUser.apply(redirectUriQuery);
    }

    // -------------------- internal --------------------------------

    /**
     * Initialize api: initialize authorize url.
     *
     * @return api: initialize authorize url
     * @see #app
     * @see #execute(OAuthRequest)
     */
    protected abstract InitializeAuthorizeUrl initApiInitializeAuthorizeUrl();

    /**
     * Initialize api: exchange redirect uri query for openid
     *
     * @return api: exchange redirect uri query for openid
     * @see #app
     * @see #execute(OAuthRequest)
     */
    protected abstract ExchangeRedirectUriQueryForOpenid initExchangeRedirectUriQueryForOpenid();

    /**
     * Initialize api: exchange redirect uri query for user
     *
     * @return api: exchange redirect uri query for user
     * @see #app
     * @see #execute(OAuthRequest)
     */
    protected abstract ExchangeRedirectUriQueryForUser<U> initExchangeRedirectUriQueryForUser();

    /**
     * Execute request and return response.
     *
     * @param request oauth request, require nonnull
     * @return response
     * @throws OAuthIOException if IO exception occurs
     */
    protected final OAuthResponse execute(OAuthRequest request) throws OAuthIOException {
        try {
            return httpClient.execute(request);
        } catch (IOException e) {
            throw new OAuthIOException(e);
        }
    }

}
