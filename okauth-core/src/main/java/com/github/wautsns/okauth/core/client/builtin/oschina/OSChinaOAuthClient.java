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
package com.github.wautsns.okauth.core.client.builtin.oschina;

import com.github.wautsns.okauth.core.client.OpenPlatform;
import com.github.wautsns.okauth.core.client.builtin.OpenPlatforms;
import com.github.wautsns.okauth.core.client.builtin.StandardTokenRefreshableOAuthClient;
import com.github.wautsns.okauth.core.client.kernel.http.OAuthRequestExecutor;
import com.github.wautsns.okauth.core.client.kernel.http.model.dto.OAuthRequest;
import com.github.wautsns.okauth.core.client.kernel.model.dto.OAuthToken;
import com.github.wautsns.okauth.core.client.kernel.model.properties.OAuthAppProperties;
import com.github.wautsns.okauth.core.exception.OAuthIOException;
import com.github.wautsns.okauth.core.exception.error.OAuthErrorException;

/**
 * OSChina oauth client.
 *
 * @since Mar 01, 2020
 * @author wautsns
 * @see <a href="https://www.oschina.net/openapi/docs">OSChina oauth doc</a>
 */
public class OSChinaOAuthClient extends StandardTokenRefreshableOAuthClient<OSChinaUser> {

    /**
     * Construct OSChina oauth client.
     *
     * @param app oauth app properties, require nonnull
     * @param executor oauth request executor, require nonnull
     */
    public OSChinaOAuthClient(OAuthAppProperties app, OAuthRequestExecutor executor) {
        super(app, executor);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return OpenPlatforms.OSCHINA;
    }

    @Override
    protected String getAuthorizeUrl() {
        return "https://www.oschina.net/action/oauth2/authorize";
    }

    @Override
    protected OAuthRequest initBasicTokenRequest() {
        return OAuthRequest.forGet("https://www.oschina.net/action/openapi/token");
    }

    @Override
    protected OAuthRequest initBasicRefreshTokenRequest() {
        return OAuthRequest.forGet("https://www.oschina.net/action/openapi/token");
    }

    /**
     * Exchange token for openid.
     *
     * <p>This implementation returns {@code token.getString("uid")}.
     *
     * @param token {@inheritDoc}
     * @return {@inheritDoc}
     * @throws OAuthErrorException {@inheritDoc}
     * @throws OAuthIOException {@inheritDoc}
     */
    @Override
    public String requestForOpenid(OAuthToken token) throws OAuthErrorException, OAuthIOException {
        return token.getString("uid");
    }

    @Override
    public OSChinaUser requestForUser(OAuthToken token)
            throws OAuthErrorException, OAuthIOException {
        String url = "https://www.oschina.net/action/openapi/user";
        OAuthRequest request = OAuthRequest.forGet(url);
        request.getQuery()
            .addAccessToken(token.getAccessToken());
        return new OSChinaUser(execute(request));
    }

    @Override
    protected boolean doesTheErrorMeanThatRefreshTokenHasExpired(String error) {
        // FIXME not found in doc
        return false;
    }

    @Override
    protected boolean doesTheErrorMeanThatAccessTokenHasExpired(String error) {
        // FIXME not found in doc
        return false;
    }

}
