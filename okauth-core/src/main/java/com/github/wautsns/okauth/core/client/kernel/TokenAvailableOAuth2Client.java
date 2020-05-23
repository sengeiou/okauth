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

import com.github.wautsns.okauth.core.assist.http.kernel.OAuth2HttpClient;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeRedirectUriQueryForOpenid;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeRedirectUriQueryForToken;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeRedirectUriQueryForUser;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeTokenForOpenid;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeTokenForUser;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2RedirectUriQuery;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2Token;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2User;
import com.github.wautsns.okauth.core.exception.OAuth2Exception;

import java.util.Objects;

/**
 * Token available oauth2 client.
 *
 * @author wautsns
 * @since May 17, 2020
 */
public abstract class TokenAvailableOAuth2Client<A extends OAuth2AppInfo, T extends OAuth2Token, U extends OAuth2User>
        extends OAuth2Client<A, U> {

    /** API: exchange redirect uri query for token. */
    protected final ExchangeRedirectUriQueryForToken<T> apiExchangeRedirectUriQueryForToken;
    /** API: exchange token for open id. */
    protected final ExchangeTokenForOpenid<T> apiExchangeTokenForOpenid;
    /** API: exchange token for user. */
    protected final ExchangeTokenForUser<T, U> apiExchangeTokenForUser;

    /**
     * Construct a token available oauth2 client.
     *
     * @param appInfo oauth2 app info
     * @param httpClient oauth2 http client
     */
    public TokenAvailableOAuth2Client(A appInfo, OAuth2HttpClient httpClient) {
        super(appInfo, httpClient);
        this.apiExchangeRedirectUriQueryForToken = Objects.requireNonNull(initApiExchangeRedirectUriQueryForToken());
        this.apiExchangeTokenForOpenid = Objects.requireNonNull(initApiExchangeTokenForOpenid());
        this.apiExchangeTokenForUser = Objects.requireNonNull(initApiExchangeTokenForUser());
    }

    /**
     * Exchange redirect uri query for token.
     *
     * @param redirectUriQuery redirect uri query
     * @return token
     * @throws OAuth2Exception if oauth2 failed
     */
    public T exchangeForToken(OAuth2RedirectUriQuery redirectUriQuery) throws OAuth2Exception {
        return apiExchangeRedirectUriQueryForToken.execute(redirectUriQuery);
    }

    /**
     * Exchange token query for openid.
     *
     * @param token token
     * @return openid
     * @throws OAuth2Exception if oauth2 failed
     */
    public String exchangeForOpenid(T token) throws OAuth2Exception {
        return apiExchangeTokenForOpenid.execute(token);
    }

    /**
     * Exchange token query for user.
     *
     * @param token token
     * @return user
     * @throws OAuth2Exception if oauth2 failed
     */
    public U exchangeForUser(T token) throws OAuth2Exception {
        return apiExchangeTokenForUser.execute(token);
    }

    // #################### initialize api ##############################################

    /**
     * Initialize API: exchange redirect uri query for token
     *
     * @return API: exchange redirect uri query for token
     */
    protected abstract ExchangeRedirectUriQueryForToken<T> initApiExchangeRedirectUriQueryForToken();

    /**
     * Initialize API: exchange token for openid
     *
     * @return API: exchange token for openid
     */
    protected abstract ExchangeTokenForOpenid<T> initApiExchangeTokenForOpenid();

    /**
     * Initialize API: exchange token for user
     *
     * @return API: exchange token for user
     */
    protected abstract ExchangeTokenForUser<T, U> initApiExchangeTokenForUser();

    @Override
    protected ExchangeRedirectUriQueryForOpenid initApiExchangeRedirectUriQueryForOpenid() {
        return redirectUriQuery -> exchangeForOpenid(exchangeForToken(redirectUriQuery));
    }

    @Override
    protected ExchangeRedirectUriQueryForUser<U> initApiExchangeRedirectUriQueryForUser() {
        return redirectUriQuery -> exchangeForUser(exchangeForToken(redirectUriQuery));
    }

}
