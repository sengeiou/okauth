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
import com.github.wautsns.okauth.core.client.kernel.openplatform.OpenPlatformSupplier;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * OAuth2 token.
 *
 * @author wautsns
 * @since May 17, 2020
 */
@Data
@Accessors(chain = true)
public abstract class OAuth2Token implements OpenPlatformSupplier, Serializable {

    private static final long serialVersionUID = 4059179893255816617L;

    /** Token id. */
    private String tokenId;
    /** Original data map. */
    private final DataMap originalDataMap;

    /**
     * Construct an oauth2 token.
     *
     * @param originalDataMap original data map
     */
    public OAuth2Token(DataMap originalDataMap) {
        this.originalDataMap = originalDataMap;
    }

    /**
     * Get access token.
     *
     * @return access token
     */
    public abstract String getAccessToken();

    /**
     * Get access token expiration seconds.
     *
     * @return access token expiration seconds
     */
    public abstract Integer getAccessTokenExpirationSeconds();

}
