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
package com.github.wautsns.okauth.core.client.builtin.baidu.model;

import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatformNames;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2RefreshableToken;
import lombok.Data;

/**
 * Baidu oauth2 token.
 *
 * <pre>
 * {
 * 	"expires_in": 2592000,
 * 	"refresh_token": "REFRESH_TOKEN(length:83)",
 * 	"access_token": "ACCESS_TOKEN(length:83)",
 * 	"session_secret": "",
 * 	"session_key": "",
 * 	"scope": "SCOPE(delimiter:space)"
 * }
 * </pre>
 *
 * @author wautsns
 * @since May 17, 2020
 */
@Data
public class BaiduOAuth2Token implements OAuth2RefreshableToken {

    private static final long serialVersionUID = 626454809091111659L;

    /** Original data map. */
    private final DataMap originalDataMap;

    @Override
    public String getOpenPlatform() {
        return BuiltInOpenPlatformNames.BAIDU;
    }

    @Override
    public String getAccessToken() {
        return originalDataMap.getAsString("access_token");
    }

    @Override
    public Integer getAccessTokenExpirationSeconds() {
        return originalDataMap.getAsInteger("expire_in");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Final access scope of access token, that is the list of permissions actually granted by the user (the user
     * may cancel some requested permissions when authorizing the page)
     *
     * @return {@inheritDoc}
     * @see <a href="http://developer.baidu.com/wiki/index.php?title=docs/oauth/list">optioanl scopes</a>
     */
    public String getScope() {
        return originalDataMap.getAsString("scope");
    }

    @Override
    public String getRefreshToken() {
        return originalDataMap.getAsString("refresh_token");
    }

    /** Baidu oauth2 refresh token expires in ten years. */
    private static final Integer REFRESH_TOKEN_EXPIRATION_SECONDS = 10 * 365 * 24 * 3600;

    @Override
    public Integer getRefreshTokenExpirationSeconds() {
        return REFRESH_TOKEN_EXPIRATION_SECONDS;
    }

}
