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
package com.github.wautsns.okauth.core.client.builtin;

import com.github.wautsns.okauth.core.client.kernel.TokenAvailableOAuthClient;
import com.github.wautsns.okauth.core.client.kernel.http.OAuthRequestExecutor;
import com.github.wautsns.okauth.core.client.kernel.http.model.basic.OAuthUrl;
import com.github.wautsns.okauth.core.client.kernel.http.model.dto.OAuthRequest;
import com.github.wautsns.okauth.core.client.kernel.http.model.dto.OAuthResponse;
import com.github.wautsns.okauth.core.client.kernel.model.dto.OAuthRedirectUriQuery;
import com.github.wautsns.okauth.core.client.kernel.model.dto.OAuthToken;
import com.github.wautsns.okauth.core.client.kernel.model.dto.OpenPlatformUser;
import com.github.wautsns.okauth.core.client.kernel.model.properties.OAuthAppProperties;
import com.github.wautsns.okauth.core.exception.OAuthIOException;
import com.github.wautsns.okauth.core.exception.error.OAuthErrorException;

/**
 * Standard oauth client.
 *
 * @since Feb 28, 2020
 * @author wautsns
 * @see <a href="https://oauth.net/2/grant-types/authorization-code/">standard oauth2.0 doc</a>
 */
public abstract class StandardTokenAvailableOAuthClient<U extends OpenPlatformUser>
        extends TokenAvailableOAuthClient<U> {

    private final OAuthUrl basicAuthorizeUrl;
    private final OAuthRequest basicTokenRequest;

    /**
     * Construct a standard token available oauth client.
     *
     * @param app oauth app properties, require nonnull
     * @param executor oauth request executor, require nonnull
     */
    public StandardTokenAvailableOAuthClient(
            OAuthAppProperties app, OAuthRequestExecutor executor) {
        super(app, executor);
        // basic authorize url
        basicAuthorizeUrl = new OAuthUrl(getAuthorizeUrl());
        basicAuthorizeUrl.getQuery()
            .addResponseTypeWithValueCode()
            .addClientId(app.getClientId())
            .addRedirectUri(app.getRedirectUri());
        // basic token request
        basicTokenRequest = initBasicTokenRequest();
        basicTokenRequest.getParamsByMethod()
            .addGrantTypeWithValueAuthorizationCode()
            .addClientId(app.getClientId())
            .addClientSecret(app.getClientSecret())
            .addRedirectUri(app.getRedirectUri());
    }

    // -------------------- authorize url ---------------------------

    /**
     * Initialize a basic authorize url. <strong>Just need</strong> url.
     *
     * @return a basic authorize url
     */
    protected abstract String getAuthorizeUrl();

    /**
     * Initialize standard authorize url.
     *
     * <p>Query items are as follows:
     * <ul>
     * <li>response_type: {@code "code"}</li>
     * <li>client_id: {@code app.getClientId()}</li>
     * <li>redirect_uri: {@code app.getRedirectUri()}</li>
     * <li>state: {@code state}</li>
     * </ul>
     *
     * @param state {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public final OAuthUrl initAuthorizeUrl(String state) {
        OAuthUrl copy = basicAuthorizeUrl.copy();
        copy.getQuery().addState(state);
        return copy;
    }

    // -------------------- oauth token -----------------------------

    /**
     * Initialize a basic token request.
     *
     * <p>No need to set `grant_type`, `client_id`, `client_secret` and `redirect_uri`.
     *
     * @return a basic token request
     * @see #requestForToken(OAuthRedirectUriQuery)
     */
    protected abstract OAuthRequest initBasicTokenRequest();

    /**
     * {@inheritDoc}
     *
     * <p>Query items(GET)/Form items(POST) added are as follows:
     * <ul>
     * <li>grant_type: {@code "authorization_code"}</li>
     * <li>client_id: {@code app.getClientId()}</li>
     * <li>client_secret: {@code app.getClientSecret()}</li>
     * <li>redirect_uri: {@code app.getRedirectUri()}</li>
     * <li>code: {@code redirectUriQuery.getCode()}</li>
     * </ul>
     *
     * @param redirectUriQuery {@inheritDoc}
     * @return oauth token {@inheritDoc}
     * @throws OAuthIOException {@inheritDoc}
     * @throws OAuthErrorException {@inheritDoc}
     */
    @Override
    public final OAuthToken requestForToken(OAuthRedirectUriQuery redirectUriQuery)
            throws OAuthErrorException, OAuthIOException {
        OAuthRequest copy = basicTokenRequest.copy();
        copy.getParamsByMethod().addCode(redirectUriQuery.getCode());
        return new OAuthToken(execute(copy));
    }

    // -------------------- error -----------------------------------

    /**
     * {@inheritDoc}
     *
     * <p>Standard oauth client read `error` from response data.
     *
     * @param response {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    protected String getErrorFromResponse(OAuthResponse response) {
        return (String) response.getData().get("error");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Standard oauth client read `error_description` from response data.
     *
     * @param response {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    protected String getErrorDescriptionFromResponse(OAuthResponse response) {
        return (String) response.getData().get("error_description");
    }

}
