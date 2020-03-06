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
package com.github.wautsns.okauth.core.client.kernel.api;

import com.github.wautsns.okauth.core.client.kernel.model.OAuthRedirectUriQuery;
import com.github.wautsns.okauth.core.client.kernel.model.OAuthToken;
import com.github.wautsns.okauth.core.exception.OAuthErrorException;
import com.github.wautsns.okauth.core.exception.OAuthIOException;

/**
 * Exchange redirect uri query for token.
 *
 * @author wautsns
 * @since Mar 04, 2020
 */
@FunctionalInterface
public interface ExchangeRedirectUriQueryForToken {

    /**
     * Exchange redirect uri query for token
     *
     * @param redirectUriQuery redirect uri query, require nonnull
     * @return token
     * @throws OAuthErrorException if open platform gives error message
     * @throws OAuthIOException if IO exception occurs
     */
    OAuthToken apply(OAuthRedirectUriQuery redirectUriQuery) throws OAuthErrorException, OAuthIOException;

}
