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
package com.github.wautsns.okauth.core.client.builtin.elemeshopisv.model;

import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatformNames;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2RefreshableToken;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * ElemeShopIsv oauth2 token.
 *
 * <pre>
 * {
 *  "access_token": "b43192a80bbb24be0c97522f44a96dc35ee37cb9",
 *  "expires_in": 86400,
 *  "open_id": "o356a19dae6eef6f5ljP5G9Y",
 * }
 * </pre>
 *
 * @author wautsns
 * @since Jun 25, 2020
 */
@Data
@Accessors(chain = true)
public class ElemeShopIsvOAuth2Token implements OAuth2RefreshableToken {

    private static final long serialVersionUID = 105807095993919241L;

    /** Token id. */
    private String tokenId;
    /** Original data map. */
    private final DataMap originalDataMap;

    @Override
    public String getOpenPlatform() {
        return BuiltInOpenPlatformNames.ELEME_SHOP_ISV;
    }

    @Override
    public String getAccessToken() {
        return originalDataMap.getAsString("access_token");
    }

    @Override
    public Integer getAccessTokenExpirationSeconds() {
        return originalDataMap.getAsInteger("expires_in");
    }

    @Override
    public String getRefreshToken() {
        return originalDataMap.getAs("refresh_token");
    }

    @Override
    public Integer getRefreshTokenExpirationSeconds() {
        // *** The value `refresh_token_expires_in` is added by okauth.
        return originalDataMap.getAsInteger("refresh_token_expires_in");
    }

    /**
     * Get token type.
     *
     * @return token type
     */
    public String getTokenType() {
        return originalDataMap.getAsString("token_type");
    }

    /**
     * Get scopes.
     *
     * @return scopes
     */
    public String getScopes() {
        return originalDataMap.getAsString("scope");
    }

}
