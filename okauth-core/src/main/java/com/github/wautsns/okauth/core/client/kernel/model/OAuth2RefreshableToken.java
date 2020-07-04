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
package com.github.wautsns.okauth.core.client.kernel.model;

import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;

/**
 * OAuth2 refreshable token.
 *
 * @author wautsns
 * @since May 23, 2020
 */
public abstract class OAuth2RefreshableToken extends OAuth2Token {

    private static final long serialVersionUID = 5373620882337328043L;

    /**
     * Construct an oauth2 refreshable token.
     *
     * @param originalDataMap original data map
     */
    public OAuth2RefreshableToken(DataMap originalDataMap) {
        super(originalDataMap);
    }

    /**
     * Get refresh token.
     *
     * @return refresh token
     */
    public abstract String getRefreshToken();

    /**
     * Get refresh token expiration seconds.
     *
     * @return refresh token expiration seconds
     */
    public abstract Integer getRefreshTokenExpirationSeconds();

}
