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
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatformNames;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2RefreshableToken;
import lombok.Data;
import lombok.experimental.Accessors;

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
@Data
@Accessors(chain = true)
public class OSChinaOAuth2Token implements OAuth2RefreshableToken {

    private static final long serialVersionUID = 1387178519741274078L;

    /** Token id. */
    private String tokenId;
    /** Original data map. */
    private final DataMap originalDataMap;

    @Override
    public String getOpenPlatform() {
        return BuiltInOpenPlatformNames.OSCHINA;
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
        return originalDataMap.getAsString("token_type");
    }

    /**
     * Get uid.
     *
     * @return uid
     */
    public String getUid() {
        return originalDataMap.getAsString("uid");
    }

}
