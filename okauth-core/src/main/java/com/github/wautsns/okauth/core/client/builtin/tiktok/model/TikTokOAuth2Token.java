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
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatforms;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2RefreshableToken;
import com.github.wautsns.okauth.core.client.kernel.openplatform.OpenPlatform;

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
public class TikTokOAuth2Token extends OAuth2RefreshableToken {

    private static final long serialVersionUID = -7768424686277910638L;

    /**
     * Construct a TikTok oauth2 refreshable token.
     *
     * @param originalDataMap original data map
     */
    public TikTokOAuth2Token(DataMap originalDataMap) {
        super(originalDataMap);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatforms.TIK_TOK;
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
        return getOriginalDataMap().getAsInteger("refresh_expires_in");
    }

    /**
     * Get openid.
     *
     * @return openid
     */
    public String getOpenid() {
        return getOriginalDataMap().getAsString("open_id");
    }

    /**
     * Get scopes(delimiter: comma).
     *
     * @return scopes
     */
    public String getScopes() {
        return getOriginalDataMap().getAsString("scope");
    }

}
