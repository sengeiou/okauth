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
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeRedirectUriQueryForToken;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeTokenForUser;
import com.github.wautsns.okauth.core.client.kernel.http.OAuthRequestExecutor;
import com.github.wautsns.okauth.core.client.kernel.model.dto.OAuthRedirectUriQuery;
import com.github.wautsns.okauth.core.client.kernel.model.dto.OpenPlatformUser;
import com.github.wautsns.okauth.core.client.kernel.model.properties.OAuthAppProperties;
import com.github.wautsns.okauth.core.exception.OAuthIOException;
import com.github.wautsns.okauth.core.exception.error.AccessTokenHasExpiredException;
import com.github.wautsns.okauth.core.exception.error.OAuthErrorException;

/**
 * Token available oauth.
 *
 * @since Feb 29, 2020
 * @author wautsns
 */
public abstract class TokenAvailableOAuthClient<U extends OpenPlatformUser>
        extends OAuthClient<U>
        implements ExchangeRedirectUriQueryForToken, ExchangeTokenForUser<U> {

    /**
     * Construct a token available oauth client.
     *
     * @param app oauth app properties, require nonnull
     * @param executor oauth request executor, require nonnull
     */
    public TokenAvailableOAuthClient(OAuthAppProperties app, OAuthRequestExecutor executor) {
        super(app, executor);
    }

    // -------------------- oauth user ------------------------------

    /**
     * {@inheritDoc}
     *
     * <p>Request process is as follows:
     * <ol>
     * <li>redirectUriQuery -> token</li>
     * <li>token -> user</li>
     * </ol>
     *
     * @param redirectUriQuery {@inheritDoc}
     * @return {@inheritDoc}
     * @throws OAuthErrorException {@inheritDoc}
     * @throws OAuthIOException {@inheritDoc}
     */
    @Override
    public U requestForUser(OAuthRedirectUriQuery redirectUriQuery)
            throws OAuthErrorException, OAuthIOException {
        return requestForUser(requestForToken(redirectUriQuery));
    }

    // -------------------- error -----------------------------------

    @Override
    protected OAuthErrorException newOAuthErrorException(
            OpenPlatform openPlatform, String error, String errorDescription) {
        if (doesTheErrorMeanThatAccessTokenHasExpired(error)) {
            return new AccessTokenHasExpiredException(openPlatform);
        } else {
            return super.newOAuthErrorException(openPlatform, error, errorDescription);
        }
    }

    /**
     * Does the error mean that access token has expired.
     *
     * @param error error, require nonnull
     * @return {@code true} if the error mean that access token has expired, otherwise {@code false}
     */
    protected abstract boolean doesTheErrorMeanThatAccessTokenHasExpired(String error);

}
