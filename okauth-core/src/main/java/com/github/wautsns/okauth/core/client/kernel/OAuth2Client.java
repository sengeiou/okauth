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
import com.github.wautsns.okauth.core.client.kernel.api.InitializeAuthorizeUrl;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2RedirectUriQuery;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2User;
import com.github.wautsns.okauth.core.client.kernel.model.OpenPlatformSupplier;
import com.github.wautsns.okauth.core.exception.OAuth2Exception;

import java.util.Objects;

/**
 * OAuth2 client.
 *
 * @author wautsns
 * @since May 17, 2020
 */
public abstract class OAuth2Client<A extends OAuth2AppInfo, U extends OAuth2User> implements OpenPlatformSupplier {

    /** oauth2 app info */
    protected final A appInfo;
    /** oauth2 http client */
    protected final OAuth2HttpClient httpClient;

    /** API: initialize authorize url */
    private final InitializeAuthorizeUrl apiInitializeAuthorizeUrl;
    /** API: exchange redirect uri query for open id */
    private final ExchangeRedirectUriQueryForOpenid apiExchangeRedirectUriQueryForOpenid;
    /** API: exchange redirect uri query for user */
    private final ExchangeRedirectUriQueryForUser<U> apiExchangeRedirectUriQueryForUser;

    /**
     * Construct oauth2 client.
     *
     * @param appInfo oauth2 app info
     * @param httpClient oauth2 http client
     */
    protected OAuth2Client(A appInfo, OAuth2HttpClient httpClient) {
        this.appInfo = appInfo;
        this.httpClient = httpClient;
        this.apiInitializeAuthorizeUrl = initApiInitializeAuthorizeUrl();
        this.apiExchangeRedirectUriQueryForUser = initApiExchangeRedirectUriQueryForUser();
        this.apiExchangeRedirectUriQueryForOpenid = initApiExchangeRedirectUriQueryForOpenid();
    }

    public final OAuth2Url initAuthorizeUrl(String state) {
        return apiInitializeAuthorizeUrl.execute(state);
    }

    public final String exchangeForOpenid(OAuth2RedirectUriQuery redirectUriQuery) throws OAuth2Exception {
        return apiExchangeRedirectUriQueryForOpenid.execute(redirectUriQuery);
    }

    public final U exchangeForUser(OAuth2RedirectUriQuery redirectUriQuery) throws OAuth2Exception {
        return apiExchangeRedirectUriQueryForUser.execute(redirectUriQuery);
    }

    // #################### initialize api ##############################################

    /**
     * Initialize API: initialize authorize url.
     *
     * @return API: initialize authorize url
     * @see #appInfo
     */
    protected abstract InitializeAuthorizeUrl initApiInitializeAuthorizeUrl();

    /**
     * Initialize API: exchange redirect uri query for openid.
     *
     * @return API: exchange redirect uri query for openid
     * @see #appInfo
     */
    protected abstract ExchangeRedirectUriQueryForOpenid initApiExchangeRedirectUriQueryForOpenid();

    /**
     * Initialize API: exchange redirect uri query for user.
     *
     * @return API: exchange redirect uri query for user
     * @see #appInfo
     */
    protected abstract ExchangeRedirectUriQueryForUser<U> initApiExchangeRedirectUriQueryForUser();

    // #################### initialize api ##############################################

    /**
     * Get value or default value if the value is {@code null}.
     *
     * @param value value
     * @param defaultValue default value
     * @param <T> type of value
     * @return value or default value if the value is {@code null}
     */
    protected static <T> T coalesce(T value, T defaultValue) {
        return (value != null) ? value : Objects.requireNonNull(defaultValue);
    }

}
