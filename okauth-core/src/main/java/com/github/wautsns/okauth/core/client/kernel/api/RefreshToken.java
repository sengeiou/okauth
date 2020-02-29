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
package com.github.wautsns.okauth.core.client.kernel.api;

import com.github.wautsns.okauth.core.client.kernel.http.model.dto.OAuthRequest;
import com.github.wautsns.okauth.core.client.kernel.http.model.dto.OAuthRequest.Method;
import com.github.wautsns.okauth.core.client.kernel.model.dto.OAuthToken;
import com.github.wautsns.okauth.core.client.kernel.model.properties.OAuthAppProperties;
import com.github.wautsns.okauth.core.exception.OAuthIOException;
import com.github.wautsns.okauth.core.exception.error.OAuthErrorException;

/**
 * Refresh token.
 *
 * @since Feb 28, 2020
 * @author wautsns
 */
@FunctionalInterface
public interface RefreshToken {

    /**
     * Refresh token.
     *
     * @param token oauth token, require nonnull
     * @return oauth token after refreshing
     * @throws OAuthErrorException if the oauth response is not correct
     * @throws OAuthIOException if an IO exception occurs
     */
    OAuthToken refreshToken(OAuthToken token) throws OAuthErrorException, OAuthIOException;

    /**
     * Initialize a basic standard request.
     *
     * <p>Query items(GET)/Form items(POST) added are as follows:
     * <ul>
     * <li>grant_type: {@code "refresh_token"}</li>
     * <li>client_id: {@code app.getClientId()}</li>
     * <li>client_secret: {@code app.getClientSecret()}</li>
     * </ul>
     *
     * @param method request method, require nonnull
     * @param url request url, require nonnull
     * @param app oauth app properties, require nonnull
     * @return a basic standard request for refreshing token
     */
    static OAuthRequest initBasicStandardRequest(
            Method method, String url, OAuthAppProperties app) {
        OAuthRequest basic = OAuthRequest.init(method, url);
        basic.getParamsByMethod()
            .addGrantTypeWithValueRefreshToken()
            .addClientId(app.getClientId())
            .addClientSecret(app.getClientSecret());
        return basic;
    }

}
