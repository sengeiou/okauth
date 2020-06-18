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
package com.github.wautsns.okauth.core.client.builtin.github.model;

import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatformNames;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2Token;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * GitHub oauth2 token.
 *
 * <pre>
 * {
 * 	"access_token": "ACCESS_TOKEN(length:40)",
 * 	"token_type": "bearer",
 * 	"scope": "SCOPE(delimiter:comma)"
 * }
 * </pre>
 *
 * @author wautsns
 * @since May 17, 2020
 */
@Data
@Accessors(chain = true)
public class GitHubOAuth2Token implements OAuth2Token {

    private static final long serialVersionUID = 7155633421437700449L;

    /** Token id. */
    private String tokenId;
    /** Original data map. */
    private final DataMap originalDataMap;

    @Override
    public String getOpenPlatform() {
        return BuiltInOpenPlatformNames.GITHUB;
    }

    @Override
    public String getAccessToken() {
        return originalDataMap.getAsString("access_token");
    }

    /** FIXME GitHub oauth2 access token expires in ??(Assume 1 day). */
    private static final Integer ACCESS_TOKEN_EXPIRATION_SECONDS = 24 * 3600;

    @Override
    public Integer getAccessTokenExpirationSeconds() {
        return ACCESS_TOKEN_EXPIRATION_SECONDS;
    }

    /**
     * Get scope(delimiter: comma).
     *
     * @return scope
     */
    public String getScope() {
        return originalDataMap.getAsString("scope");
    }

    /**
     * Get token type: {@code "bearer"}.
     *
     * @return {@code "bearer"}
     */
    public String getTokenType() {
        return originalDataMap.getAsString("token_type");
    }

}
