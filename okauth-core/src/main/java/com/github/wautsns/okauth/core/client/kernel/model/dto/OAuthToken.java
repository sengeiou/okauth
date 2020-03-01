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
package com.github.wautsns.okauth.core.client.kernel.model.dto;

import java.io.Serializable;

import java.util.Map;

import com.github.wautsns.okauth.core.client.kernel.http.model.dto.OAuthResponse;

/**
 * OAuth token.
 *
 * @since Feb 28, 2020
 * @author wautsns
 */
public class OAuthToken extends OAuthResponseDataMap {

    /** serialVersionUID */
    private static final long serialVersionUID = -1515246267803686756L;

    /**
     * Construct oauth token.
     *
     * @param originalDataMap original data map, require nonnull
     */
    public OAuthToken(Map<String, Serializable> originalDataMap) {
        super(originalDataMap);
    }

    /**
     * Construct oauth token.
     *
     * @param response oauth response, require nonnull
     */
    public OAuthToken(OAuthResponse response) {
        super(response);
    }

    /**
     * Get value of the name "access_token".
     *
     * @return access token
     */
    public String getAccessToken() {
        return getString("access_token");
    }

    /**
     * Get value of the name "refresh_token".
     *
     * @return refresh token({@code null} if not present)
     */
    public String getRefreshToken() {
        return getString("refresh_token");
    }

    /**
     * Get value of the name "expires_in".
     *
     * @return expires in({@code null} if not present)
     */
    public Integer getExpiresIn() {
        return (Integer) get("expires_in");
    }

}
