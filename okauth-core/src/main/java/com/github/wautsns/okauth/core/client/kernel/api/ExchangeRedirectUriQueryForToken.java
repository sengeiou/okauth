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

import com.github.wautsns.okauth.core.client.kernel.model.dto.OAuthRedirectUriQuery;
import com.github.wautsns.okauth.core.client.kernel.model.dto.OAuthToken;
import com.github.wautsns.okauth.core.exception.OAuthIOException;
import com.github.wautsns.okauth.core.exception.error.OAuthErrorException;

/**
 * Exchange redirect uri query for token.
 *
 * @since Feb 28, 2020
 * @author wautsns
 */
@FunctionalInterface
public interface ExchangeRedirectUriQueryForToken {

    /**
     * Exchange redirect uri query for token.
     *
     * @param redirectUriQuery oauth redirect uri query, require nonnull
     * @return oauth token
     * @throws OAuthErrorException if the oauth response is not correct
     * @throws OAuthIOException if IO exception occurs
     */
    OAuthToken requestForToken(OAuthRedirectUriQuery redirectUriQuery)
            throws OAuthErrorException, OAuthIOException;

}
