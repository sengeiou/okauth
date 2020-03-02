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

import com.github.wautsns.okauth.core.client.OpenPlatform;
import com.github.wautsns.okauth.core.client.kernel.api.RefreshToken;
import com.github.wautsns.okauth.core.client.kernel.http.OAuthRequestExecutor;
import com.github.wautsns.okauth.core.client.kernel.model.dto.OAuthToken;
import com.github.wautsns.okauth.core.client.kernel.model.dto.OpenPlatformUser;
import com.github.wautsns.okauth.core.client.kernel.model.properties.OAuthAppProperties;
import com.github.wautsns.okauth.core.exception.OAuthIOException;
import com.github.wautsns.okauth.core.exception.error.AccessTokenHasExpiredException;
import com.github.wautsns.okauth.core.exception.error.OAuthErrorException;
import com.github.wautsns.okauth.core.exception.error.RefreshTokenHasExpiredException;

/**
 * Token refreshable oauth client.
 *
 * @author wautsns
 * @since Feb 29, 2020
 */
public abstract class TokenRefreshableOAuthClient<U extends OpenPlatformUser>
    extends TokenAvailableOAuthClient<U> implements RefreshToken {

    /**
     * Construct a token refreshable oauth client.
     *
     * @param app oauth app properties, require nonnull
     * @param executor oauth request executor, require nonnull
     */
    public TokenRefreshableOAuthClient(OAuthAppProperties app, OAuthRequestExecutor executor) {
        super(app, executor);
    }

    // -------------------- oauth user ------------------------------

    /**
     * Exchange token for openid.
     *
     * <p><strong>If the access token has expired</strong>, {@link #refreshToken(OAuthToken)} will be called
     * automatically, and exchange refreshed token for openid. Refresh callback will be called with refreshed token
     * after requesting.
     *
     * @param token token, require nonnull
     * @param refreshCallback refresh callback, require nonnull
     * @return openid
     * @throws OAuthErrorException if the oauth response is not correct
     * @throws OAuthIOException if IO exception occurs
     * @throws Exception if refreshCallback throws
     * @see #requestForUser(OAuthToken)
     * @see #request(OAuthToken, TokenRequiredApi, RefreshCallback)
     */
    public final String requestForOpenid(OAuthToken token, RefreshCallback refreshCallback) throws Exception {
        return request(token, this::requestForOpenid, refreshCallback);
    }

    /**
     * Exchange token for user.
     *
     * <p><strong>If the access token has expired</strong>, {@link #refreshToken(OAuthToken)} will be called
     * automatically, and exchange refreshed token for user. Refresh callback will be called with refreshed token.
     *
     * @param token token, require nonnull
     * @param refreshCallback refresh callback, require nonnull
     * @return open platform user
     * @throws OAuthErrorException if the oauth response is not correct
     * @throws OAuthIOException if IO exception occurs
     * @throws Exception if refreshCallback throws
     * @see #requestForUser(OAuthToken)
     * @see #request(OAuthToken, TokenRequiredApi, RefreshCallback)
     */
    public final U requestForUser(OAuthToken token, RefreshCallback refreshCallback) throws Exception {
        return request(token, this::requestForUser, refreshCallback);
    }

    // -------------------- any token required api ------------------

    /**
     * Request token required api.
     *
     * <p><strong>If the access token has expired</strong>, {@link #refreshToken(OAuthToken)} will be called
     * automatically, and request the api with refreshed token. Refresh callback will be called after requesting.
     *
     * @param token token, require nonnull
     * @param api token required api, require nonnull
     * @param refreshCallback refresh callback, require nonnull
     * @return
     * @throws OAuthErrorException if the oauth response is not correct
     * @throws OAuthIOException if IO exception occurs
     * @throws Exception if refreshCallback throws
     */
    public final <T> T request(OAuthToken token, TokenRequiredApi<T> api, RefreshCallback refreshCallback)
        throws Exception {
        try {
            return api.request(token);
        } catch (AccessTokenHasExpiredException e) {
            token = refreshToken(token);
            T rst = api.request(token);
            refreshCallback.accept(token);
            return rst;
        }
    }

    // -------------------- error -----------------------------------

    @Override
    protected OAuthErrorException newOAuthErrorException(
        OpenPlatform openPlatform, String error, String errorDescription) {
        if (doesTheErrorMeanThatRefreshTokenHasExpired(error)) {
            return new RefreshTokenHasExpiredException(openPlatform);
        } else {
            return super.newOAuthErrorException(openPlatform, error, errorDescription);
        }
    }

    /**
     * Does the error mean that refresh token has expired.
     *
     * @param error error, require nonnull
     * @return {@code true} if the error mean that refresh token has expired, otherwise {@code false}
     */
    protected abstract boolean doesTheErrorMeanThatRefreshTokenHasExpired(String error);

    // -------------------- utils -----------------------------------

    /** Token required api. */
    @FunctionalInterface
    public interface TokenRequiredApi<T> {

        /**
         * Exchange token for T.
         *
         * @param token oauth token, require nonnull
         * @return T
         * @throws OAuthErrorException if the oauth response is not correct
         * @throws OAuthIOException if IO exception occurs
         */
        T request(OAuthToken token) throws OAuthErrorException, OAuthIOException;

    }

    @FunctionalInterface
    public interface RefreshCallback {

        /**
         * Refresh callback.
         *
         * @param token refreshed oauth token, require nonnull
         * @throws Exception if any exception occurs
         */
        void accept(OAuthToken token) throws Exception;

    }

}
