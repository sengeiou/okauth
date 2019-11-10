/**
 * Copyright 2019 the original author or authors.
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
package com.github.wautsns.okauth.core.client.core.standard.oauth2;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.wautsns.okauth.core.client.core.dto.OAuthToken;
import com.github.wautsns.okauth.core.client.util.http.Response;

/**
 * Standard oauth token.
 *
 * @author wautsns
 */
@JsonNaming(SnakeCaseStrategy.class)
public class StandardOAuth2Token extends OAuthToken {

    /**
     * Construct a standard oauth token.
     *
     * @param response response, require nonnull
     */
    public StandardOAuth2Token(Response response) {
        super(response);
    }

    @Override
    public String getAccessToken() {
        return getString("access_token");
    }

    /** Get token type. */
    public String getTokenType() {
        return getString("token_type");
    }

    /** Get expires in. */
    public Long getExpiresIn() {
        return get("expires_in");
    }

    /** Get refresh token. */
    public String getRefreshToken() {
        return getString("refresh_token");
    }

    /** Get scope. */
    public String getScope() {
        return getString("scope");
    }

}
