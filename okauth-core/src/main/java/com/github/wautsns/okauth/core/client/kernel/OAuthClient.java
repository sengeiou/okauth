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

import java.io.IOException;

import com.github.wautsns.okauth.core.client.OpenPlatform;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeRedirectUriQueryForUser;
import com.github.wautsns.okauth.core.client.kernel.http.OAuthRequestExecutor;
import com.github.wautsns.okauth.core.client.kernel.http.model.basic.OAuthUrl;
import com.github.wautsns.okauth.core.client.kernel.http.model.dto.OAuthRequest;
import com.github.wautsns.okauth.core.client.kernel.http.model.dto.OAuthResponse;
import com.github.wautsns.okauth.core.client.kernel.model.dto.OpenPlatformUser;
import com.github.wautsns.okauth.core.client.kernel.model.properties.OAuthAppProperties;
import com.github.wautsns.okauth.core.exception.OAuthIOException;
import com.github.wautsns.okauth.core.exception.error.OAuthErrorException;

/**
 * OAuth client.
 *
 * @since Feb 28, 2020
 * @author wautsns
 */
public abstract class OAuthClient<U extends OpenPlatformUser>
        implements ExchangeRedirectUriQueryForUser<U> {

    /** oauth app properties */
    protected final OAuthAppProperties app;
    /** oauth request executor */
    private final OAuthRequestExecutor executor;

    /**
     * Construct oauth client.
     *
     * @param app oauth app properties, require nonnull
     * @param executor oauth request executor, require nonnull
     */
    public OAuthClient(OAuthAppProperties app, OAuthRequestExecutor executor) {
        this.app = app;
        this.executor = executor;
    }

    /**
     * Get open platform of the client.
     *
     * @return open platform of the client
     */
    public abstract OpenPlatform getOpenPlatform();

    // -------------------- authorize url ---------------------------

    /**
     * Initialize authorize url.
     *
     * @param state state
     * @return authorize url
     */
    public abstract OAuthUrl initAuthorizeUrl(String state);

    // -------------------- execute ---------------------------------

    /**
     * Execute request, check response and return correct response.
     *
     * @param request oauth request, require nonnull
     * @return correct response
     * @throws OAuthErrorException if the oauth response is not correct
     * @throws OAuthIOException if IO exception occurs
     */
    protected final OAuthResponse execute(OAuthRequest request)
            throws OAuthErrorException, OAuthIOException {
        try {
            return checkResponse(executor.execute(request));
        } catch (IOException e) {
            throw new OAuthIOException(getOpenPlatform(), request, e);
        }
    }

    // -------------------- error -----------------------------------

    /**
     * Check response.
     *
     * @param response okauth response, require nonnull
     * @return correct response
     * @throws OAuthErrorException if the oauth response is not correct
     * @see #getErrorFromResponse(OAuthResponse)
     * @see #getErrorDescriptionFromResponse(OAuthResponse)
     * @see #newOAuthErrorException(OpenPlatform, String, String)
     */
    private final OAuthResponse checkResponse(OAuthResponse response) throws OAuthErrorException {
        String error = getErrorFromResponse(response);
        if (error == null) {
            int status = response.getStatus();
            if (status >= 400) {
                error = Integer.toString(status);
            } else {
                return response;
            }
        }
        String errorDescription = getErrorDescriptionFromResponse(response);
        throw newOAuthErrorException(getOpenPlatform(), error, errorDescription);
    }

    /**
     * Get error from response.
     *
     * @param response response, require nonnull
     * @return error({@code null} if the response is correct)
     */
    protected abstract String getErrorFromResponse(OAuthResponse response);

    /**
     * Get error description from response.
     *
     * @param response incorrect response, require nonnull
     * @return error description
     */
    protected abstract String getErrorDescriptionFromResponse(OAuthResponse response);

    /**
     * New oauth error exception.
     *
     * @param openPlatform open platform, require nonnull
     * @param error error, require nonnull
     * @param errorDescription error description
     * @return oauth error exception
     */
    protected OAuthErrorException newOAuthErrorException(
            OpenPlatform openPlatform, String error, String errorDescription) {
        return new OAuthErrorException(openPlatform, error, errorDescription);
    }

}
