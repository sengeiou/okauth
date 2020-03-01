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
package com.github.wautsns.okauth.core.client.kernel;

import com.github.wautsns.okauth.core.client.OpenPlatform;
import com.github.wautsns.okauth.core.client.kernel.api.RefreshToken;
import com.github.wautsns.okauth.core.client.kernel.http.OAuthRequestExecutor;
import com.github.wautsns.okauth.core.client.kernel.http.model.dto.OAuthRequest;
import com.github.wautsns.okauth.core.client.kernel.http.model.dto.OAuthResponse;
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
 * @since Feb 29, 2020
 * @author wautsns
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

    // -------------------- execute ---------------------------------

    /**
     * Execute token related request, check response and return correct response. <strong>If the
     * access token has expired</strong>, {@linkplain #refreshToken(OAuthToken)} will be called
     * automatically, and reexecute the request. Refresh callback will be called after reexecuting.
     *
     * @param token token
     * @param tokenToRequest token to request
     * @param refreshCallback refresh callback
     * @return
     * @throws OAuthErrorException if the oauth response is not correct
     * @throws OAuthIOException if an IO exception occurs
     * @throws Throwable if refreshCallback throws
     */
    protected final OAuthResponse execute(
            OAuthToken token, TokenToRequest tokenToRequest, RefreshCallback refreshCallback)
            throws OAuthErrorException, OAuthIOException, Throwable {
        try {
            return execute(tokenToRequest.accept(token));
        } catch (AccessTokenHasExpiredException e) {
            token = refreshToken(token);
            OAuthResponse response = execute(tokenToRequest.accept(token));
            refreshCallback.accept(token);
            return response;
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
     * @return {@code true} if the error mean that refresh token has expired, otherwise
     *         {@code false}
     */
    protected abstract boolean doesTheErrorMeanThatRefreshTokenHasExpired(String error);

    // -------------------- utils -----------------------------------

    /**
     * Produce token related oauth request.
     *
     * @since Mar 01, 2020
     * @author wautsns
     */
    protected interface TokenToRequest {

        /**
         * Produce token related oauth request.
         *
         * @param token oauth token
         * @return token relared oauth request
         */
        OAuthRequest accept(OAuthToken token);

    }

    /**
     * Refresh callback.
     *
     * @since Mar 01, 2020
     * @author wautsns
     */
    public interface RefreshCallback {

        /**
         * Refresh callback.
         *
         * @param token refreshed oauth token
         * @throws Throwable if any throwable occurs
         */
        void accept(OAuthToken token) throws Throwable;

    }

}
