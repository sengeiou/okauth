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
import com.github.wautsns.okauth.core.client.kernel.api.RefreshToken;
import com.github.wautsns.okauth.core.client.kernel.api.basic.TokenRelatedApi;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2Token;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2User;
import com.github.wautsns.okauth.core.exception.OAuth2Exception;
import com.github.wautsns.okauth.core.exception.specific.token.ExpiredAccessTokenException;

import java.util.Objects;

/**
 * Token refreshable oauth2 client.
 *
 * @author wautsns
 * @since May 17, 2020
 */
public abstract class TokenRefreshableOAuth2Client<A extends OAuth2AppInfo, T extends OAuth2Token, U extends OAuth2User>
        extends TokenAvailableOAuth2Client<A, T, U> {

    /** API: refresh token */
    protected final RefreshToken<T> apiRefreshToken;

    /** extra: token refresh callback */
    protected final TokenRefreshCallback tokenRefreshCallback;

    /**
     * Construct token refreshable oauth2 client.
     *
     * @param appInfo oauth2 app info
     * @param httpClient oauth2 http client
     * @param tokenRefreshCallback token refresh call back
     */
    public TokenRefreshableOAuth2Client(
            A appInfo, OAuth2HttpClient httpClient,
            TokenRefreshCallback tokenRefreshCallback) {
        super(appInfo, httpClient);
        this.apiRefreshToken = Objects.requireNonNull(initApiRefreshToken());
        this.tokenRefreshCallback = Objects.requireNonNull(tokenRefreshCallback);
    }

    @Override
    public String exchangeForOpenid(T token) throws OAuth2Exception {
        return refreshIfAccessTokenExpired(apiExchangeTokenForOpenid, token);
    }

    @Override
    public U exchangeForUser(T token) throws OAuth2Exception {
        return refreshIfAccessTokenExpired(apiExchangeTokenForUser, token);
    }

    /**
     * Refresh token.
     *
     * @param token token
     * @return token
     * @throws OAuth2Exception if oauth2 failed
     */
    public final T refreshToken(T token) throws OAuth2Exception {
        tokenRefreshCallback.beforeRefreshing(getOpenPlatform(), token);
        T newToken = apiRefreshToken.execute(token);
        tokenRefreshCallback.afterRefreshing(getOpenPlatform(), token, newToken);
        return newToken;
    }

    /**
     * Auto-refresh access token if the api throws {@code ExpiredAccessTokenException}.
     *
     * @param tokenRelatedApi token related api
     * @param token token
     * @param <R> type of result
     * @return result of the api
     * @throws OAuth2Exception if oauth2 failed
     */
    protected <R> R refreshIfAccessTokenExpired(TokenRelatedApi<T, R> tokenRelatedApi, T token) throws OAuth2Exception {
        try {
            return tokenRelatedApi.execute(token);
        } catch (ExpiredAccessTokenException e) {
            return tokenRelatedApi.execute(refreshToken(token));
        }
    }

    // #################### initialize api ##############################################

    /**
     * Initialize API: refresh token
     *
     * @return API: refresh token
     */
    protected abstract RefreshToken<T> initApiRefreshToken();

    // #################### extra #######################################################

    /**
     * Token refresh callback.
     *
     * @author wautsns
     * @since May 19, 2020
     */
    public interface TokenRefreshCallback {

        /**
         * Before refreshing.
         *
         * @param openPlatform open platform
         * @param token token
         */
        default void beforeRefreshing(String openPlatform, OAuth2Token token) {}

        /**
         * After refreshing.
         *
         * @param oldToken old token
         * @param newToken new token
         */
        void afterRefreshing(String openPlatform, OAuth2Token oldToken, OAuth2Token newToken);

        // #################### instance ####################################################

        /** default token refresh callback */
        TokenRefreshCallback DEFAULT = (openPlatform, oldToken, newToken) -> {};

    }

}
