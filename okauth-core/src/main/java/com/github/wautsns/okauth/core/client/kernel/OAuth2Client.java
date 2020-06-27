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
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.OAuth2Url;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeRedirectUriQueryForOpenid;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeRedirectUriQueryForUser;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2RedirectUriQuery;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2User;
import com.github.wautsns.okauth.core.client.kernel.model.OpenPlatformSupplier;
import com.github.wautsns.okauth.core.exception.OAuth2Exception;
import lombok.Getter;

import java.util.Objects;

/**
 * OAuth2 client.
 *
 * @author wautsns
 * @since May 17, 2020
 */
public abstract class OAuth2Client<A extends OAuth2AppInfo, U extends OAuth2User> implements OpenPlatformSupplier {

    /** OAuth2 app info. */
    @Getter
    protected final A appInfo;
    /** OAuth2 http client. */
    protected final OAuth2HttpClient httpClient;

    /** API: Initialize authorize url. */
    protected final InitializeAuthorizeUrl apiInitializeAuthorizeUrl;
    /** API: exchange redirect uri query for open id. */
    protected final ExchangeRedirectUriQueryForOpenid apiExchangeRedirectUriQueryForOpenid;
    /** API: exchange redirect uri query for user. */
    protected final ExchangeRedirectUriQueryForUser<U> apiExchangeRedirectUriQueryForUser;

    /**
     * Construct an oauth2 client.
     *
     * @param appInfo oauth2 app info
     * @param httpClient oauth2 http client
     */
    public OAuth2Client(A appInfo, OAuth2HttpClient httpClient) {
        this.appInfo = Objects.requireNonNull(appInfo);
        this.httpClient = Objects.requireNonNull(httpClient);
        this.apiInitializeAuthorizeUrl = Objects.requireNonNull(initApiInitializeAuthorizeUrl());
        this.apiExchangeRedirectUriQueryForUser = Objects.requireNonNull(initApiExchangeRedirectUriQueryForUser());
        this.apiExchangeRedirectUriQueryForOpenid = Objects.requireNonNull(initApiExchangeRedirectUriQueryForOpenid());
    }

    /**
     * Initialize authorize url.
     *
     * @param state state
     * @return authorize url
     */
    public OAuth2Url initAuthorizeUrl(String state) {
        return apiInitializeAuthorizeUrl.execute(state);
    }

    /**
     * Exchange redirect uri query for openid.
     *
     * @param redirectUriQuery redirect uri query
     * @return openid
     * @throws OAuth2Exception if oauth2 failed
     */
    public String exchangeForOpenid(OAuth2RedirectUriQuery redirectUriQuery) throws OAuth2Exception {
        return apiExchangeRedirectUriQueryForOpenid.execute(redirectUriQuery);
    }

    /**
     * Exchange redirect uri query for user.
     *
     * @param redirectUriQuery redirect uri query
     * @return user
     * @throws OAuth2Exception if oauth2 failed
     */
    public U exchangeForUser(OAuth2RedirectUriQuery redirectUriQuery) throws OAuth2Exception {
        return apiExchangeRedirectUriQueryForUser.execute(redirectUriQuery);
    }

    // #################### initialize api ##############################################

    /**
     * Initialize API: initialize authorize url.
     *
     * @return API: initialize authorize url
     * @see #appInfo
     * @see #httpClient
     */
    protected abstract InitializeAuthorizeUrl initApiInitializeAuthorizeUrl();

    /**
     * Initialize API: exchange redirect uri query for openid.
     *
     * @return API: exchange redirect uri query for openid
     * @see #appInfo
     * @see #httpClient
     */
    protected abstract ExchangeRedirectUriQueryForOpenid initApiExchangeRedirectUriQueryForOpenid();

    /**
     * Initialize API: exchange redirect uri query for user.
     *
     * @return API: exchange redirect uri query for user
     * @see #appInfo
     * @see #httpClient
     */
    protected abstract ExchangeRedirectUriQueryForUser<U> initApiExchangeRedirectUriQueryForUser();

    /** Initialize authorize url. */
    @FunctionalInterface
    protected interface InitializeAuthorizeUrl {

        /**
         * Initialize authorize url.
         *
         * @param state state
         * @return authorize url
         */
        OAuth2Url execute(String state);

    }

}
