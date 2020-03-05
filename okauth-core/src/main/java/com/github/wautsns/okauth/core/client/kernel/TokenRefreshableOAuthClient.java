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

import com.github.wautsns.okauth.core.client.kernel.api.RefreshToken;
import com.github.wautsns.okauth.core.client.kernel.model.OAuthToken;
import com.github.wautsns.okauth.core.client.kernel.model.OAuthUser;
import com.github.wautsns.okauth.core.exception.OAuthErrorException;
import com.github.wautsns.okauth.core.exception.OAuthIOException;
import com.github.wautsns.okauth.core.http.HttpClient;
import com.github.wautsns.okauth.core.http.model.OAuthRequest;

/**
 * Token refreshable oauth client.
 *
 * @author wautsns
 * @since Mar 04, 2020
 */
public abstract class TokenRefreshableOAuthClient<U extends OAuthUser> extends TokenAvailableOAuthClient<U> {

    /** api: refresh token */
    private final RefreshToken refreshToken;

    /**
     * Construct token refreshable oauth client.
     *
     * @param app oauth app properties, require nonnull
     * @param httpClient http client, require nonnull
     */
    public TokenRefreshableOAuthClient(OAuthAppProperties app, HttpClient httpClient) {
        super(app, httpClient);
        this.refreshToken = initApiRefreshToken();
    }

    /**
     * Refresh token.
     *
     * @param token token, require nonnull
     * @return refreshed token
     * @throws OAuthErrorException if open platform gives error message
     * @throws OAuthIOException if IO exception occurs
     */
    public final OAuthToken refreshToken(OAuthToken token) throws OAuthErrorException, OAuthIOException {
        return refreshToken.apply(token);
    }

    // -------------------- internal --------------------------------

    /**
     * Initialize api: refresh token
     *
     * @return api: refresh token
     * @see #app
     * @see #execute(OAuthRequest)
     */
    protected abstract RefreshToken initApiRefreshToken();

}
