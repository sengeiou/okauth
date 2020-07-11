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
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatforms;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2RefreshableToken;
import com.github.wautsns.okauth.core.client.kernel.openplatform.OpenPlatform;

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
public class ElemeShopIsvOAuth2Token extends OAuth2RefreshableToken {

    private static final long serialVersionUID = 105807095993919241L;

    /**
     * Construct an ElemeShopIsv oauth2 refreshable token.
     *
     * @param originalDataMap original data map
     */
    public ElemeShopIsvOAuth2Token(DataMap originalDataMap) {
        super(originalDataMap);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatforms.ELEME_SHOP_ISV;
    }

    @Override
    public String getAccessToken() {
        return getOriginalDataMap().getAsString("access_token");
    }

    @Override
    public Integer getAccessTokenExpirationSeconds() {
        return getOriginalDataMap().getAsInteger("expires_in");
    }

    @Override
    public String getRefreshToken() {
        return getOriginalDataMap().getAsString("refresh_token");
    }

    @Override
    public Integer getRefreshTokenExpirationSeconds() {
        // *** The value `refresh_token_expires_in` is added by okauth.
        return getOriginalDataMap().getAsInteger("refresh_token_expires_in");
    }

    /**
     * Get token type.
     *
     * @return token type
     */
    public String getTokenType() {
        return getOriginalDataMap().getAsString("token_type");
    }

    /**
     * Get scopes.
     *
     * @return scopes
     */
    public String getScopes() {
        return getOriginalDataMap().getAsString("scope");
    }

}
