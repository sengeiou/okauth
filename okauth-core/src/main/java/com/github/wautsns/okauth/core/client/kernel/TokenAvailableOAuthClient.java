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

import com.github.wautsns.okauth.core.client.kernel.api.ExchangeRedirectUriQueryForOpenid;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeRedirectUriQueryForToken;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeRedirectUriQueryForUser;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeTokenForOpenid;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeTokenForUser;
import com.github.wautsns.okauth.core.client.kernel.model.OAuthRedirectUriQuery;
import com.github.wautsns.okauth.core.client.kernel.model.OAuthToken;
import com.github.wautsns.okauth.core.client.kernel.model.OAuthUser;
import com.github.wautsns.okauth.core.exception.OAuthErrorException;
import com.github.wautsns.okauth.core.exception.OAuthIOException;
import com.github.wautsns.okauth.core.http.HttpClient;
import com.github.wautsns.okauth.core.http.model.OAuthRequest;

/**
 * Token available oauth client.
 *
 * @author wautsns
 * @since Mar 04, 2020
 */
public abstract class TokenAvailableOAuthClient<U extends OAuthUser> extends OAuthClient<U> {

    private final ExchangeRedirectUriQueryForToken exchangeRedirectUriQueryForToken;
    private final ExchangeTokenForOpenid exchangeTokenForOpenid;
    private final ExchangeTokenForUser<U> exchangeTokenForUser;

    /**
     * Construct token available oauth client.
     *
     * @param app oauth app properties, require nonnull
     * @param httpClient http client, require nonnull
     */
    public TokenAvailableOAuthClient(OAuthAppProperties app, HttpClient httpClient) {
        super(app, httpClient);
        this.exchangeRedirectUriQueryForToken = initApiExchangeRedirectUriQueryForToken();
        this.exchangeTokenForOpenid = initApiExchangeTokenForOpenid();
        this.exchangeTokenForUser = initApiExchangeTokenForUser();
    }

    /**
     * Exchange redirect uri query for token.
     *
     * @param redirectUriQuery redirect uri query, require nonnull
     * @return oauth token
     * @throws OAuthErrorException if open platform gives error message
     * @throws OAuthIOException if IO exception occurs
     */
    public final OAuthToken exchangeForToken(OAuthRedirectUriQuery redirectUriQuery)
            throws OAuthErrorException, OAuthIOException {
        return exchangeRedirectUriQueryForToken.apply(redirectUriQuery);
    }

    /**
     * Exchange token for openid.
     *
     * @param token oauth token, require nonnull
     * @return openid
     * @throws OAuthErrorException if open platform gives error message
     * @throws OAuthIOException if IO exception occurs
     */
    public final String exchangeForOpenid(OAuthToken token) throws OAuthErrorException, OAuthIOException {
        return exchangeTokenForOpenid.apply(token);
    }

    /**
     * Exchange token for user.
     *
     * @param token oauth token, require nonnull
     * @return user
     * @throws OAuthErrorException if open platform gives error message
     * @throws OAuthIOException if IO exception occurs
     */
    public final U exchangeForUser(OAuthToken token) throws OAuthErrorException, OAuthIOException {
        return exchangeTokenForUser.apply(token);
    }

    // -------------------- internal --------------------------------

    /**
     * Initialize api: exchange redirect uri query for token
     *
     * @return api: exchange redirect uri query for token
     * @see #app
     * @see #execute(OAuthRequest)
     */
    protected abstract ExchangeRedirectUriQueryForToken initApiExchangeRedirectUriQueryForToken();

    /**
     * Initialize api: exchange token for openid
     *
     * @return api: exchange token for openid
     * @see #app
     * @see #execute(OAuthRequest)
     */
    protected abstract ExchangeTokenForOpenid initApiExchangeTokenForOpenid();

    /**
     * Initialize api: exchange token for user
     *
     * @return api: exchange token for user
     * @see #app
     * @see #execute(OAuthRequest)
     */
    protected abstract ExchangeTokenForUser<U> initApiExchangeTokenForUser();

    @Override
    protected final ExchangeRedirectUriQueryForOpenid initExchangeRedirectUriQueryForOpenid() {
        return redirectUriQuery -> exchangeForOpenid(exchangeForToken(redirectUriQuery));
    }

    @Override
    protected final ExchangeRedirectUriQueryForUser<U> initExchangeRedirectUriQueryForUser() {
        return redirectUriQuery -> exchangeForUser(exchangeForToken(redirectUriQuery));
    }

}
