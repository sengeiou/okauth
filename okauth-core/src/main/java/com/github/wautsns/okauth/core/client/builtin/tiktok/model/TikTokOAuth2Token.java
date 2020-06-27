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
package com.github.wautsns.okauth.core.client.builtin.tiktok.model;

import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatformNames;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2RefreshableToken;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * TikTok oauth2 token.
 *
 * <pre>
 * {
 *  "access_token": "access_token",
 *  "expires_in": 86400,
 *  "refresh_token": "refresh_token",
 *  "refresh_expires_in": 86400,
 *  "open_id": "aaa-bbb-ccc",
 *  "scope": "user_info"
 * }
 * </pre>
 *
 * @author wautsns
 * @since Jun 23, 2020
 */
@Data
@Accessors(chain = true)
public class TikTokOAuth2Token implements OAuth2RefreshableToken {

    private static final long serialVersionUID = -7768424686277910638L;

    /** Token id. */
    private String tokenId;
    /** Original data map. */
    private final DataMap originalDataMap;

    @Override
    public String getOpenPlatform() {
        return BuiltInOpenPlatformNames.TIK_TOK;
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
        return originalDataMap.getAsString("refresh_token");
    }

    @Override
    public Integer getRefreshTokenExpirationSeconds() {
        return originalDataMap.getAsInteger("refresh_expires_in");
    }

    /**
     * Get openid.
     *
     * @return openid
     */
    public String getOpenid() {
        return originalDataMap.getAsString("open_id");
    }

    /**
     * Get scopes(delimiter: comma).
     *
     * @return scopes
     */
    public String getScopes() {
        return originalDataMap.getAsString("scope");
    }

}
