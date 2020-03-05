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

import com.github.wautsns.okauth.core.http.model.OAuthResponse;
import com.github.wautsns.okauth.core.http.util.DataMap;

import java.io.Serializable;

/**
 * OAuth token.
 *
 * @author wautsns
 * @since Mar 04, 2020
 */
public class OAuthToken implements Serializable {

    private static final long serialVersionUID = -6384702933569220456L;

    private final DataMap originalDataMap;

    /**
     * Construct oauth token.
     *
     * @param response correct response, require nonnull
     */
    public OAuthToken(OAuthResponse response) {
        this.originalDataMap = response.getDataMap();
    }

    /**
     * Get original data map.
     *
     * @return original data map
     */
    public DataMap getOriginalDataMap() {
        return originalDataMap;
    }

    /**
     * Get value of `access_token`.
     *
     * @return access token
     */
    public String getAccessToken() {
        return originalDataMap.getAsString("access_token");
    }

    /**
     * Get value of `refresh_token`.
     *
     * @return refresh token, or {@code null} if not present
     */
    public String getRefreshToken() {
        return originalDataMap.getAsString("refresh_token");
    }

    /**
     * Get value of `expires_in`.
     *
     * @return expires in, or {@code null} if not present
     */
    public Integer getExpiresIn() {
        return (Integer) originalDataMap.get("expires_in");
    }

}
