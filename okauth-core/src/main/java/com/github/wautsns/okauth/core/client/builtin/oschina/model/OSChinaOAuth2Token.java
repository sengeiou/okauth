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
package com.github.wautsns.okauth.core.client.builtin.oschina.model;

import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatforms;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2RefreshableToken;
import com.github.wautsns.okauth.core.client.kernel.openplatform.OpenPlatform;

/**
 * OSChina oauth2 token.
 *
 * <pre>
 * {
 * 	"access_token": "ACCESS_TOKEN(36)",
 * 	"refresh_token": "REFRESH_TOKEN(36)",
 * 	"uid": 4153817,
 * 	"token_type": "bearer",
 * 	"expires_in": 604799
 * }
 * </pre>
 *
 * @author wautsns
 * @since May 22, 2020
 */
public class OSChinaOAuth2Token extends OAuth2RefreshableToken {

    private static final long serialVersionUID = 1387178519741274078L;

    /**
     * Construct an OSChina oauth2 refreshable token.
     *
     * @param originalDataMap original data map
     */
    public OSChinaOAuth2Token(DataMap originalDataMap) {
        super(originalDataMap);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatforms.OSCHINA;
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

    /** FIXME OSChina oauth2 refresh token expires in ??(Assume 7 days). */
    private static final Integer REFRESH_TOKEN_EXPIRATION_SECONDS = 7 * 24 * 3600;

    @Override
    public Integer getRefreshTokenExpirationSeconds() {
        return REFRESH_TOKEN_EXPIRATION_SECONDS;
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
     * Get uid.
     *
     * @return uid
     */
    public String getUid() {
        return getOriginalDataMap().getAsString("uid");
    }

}
