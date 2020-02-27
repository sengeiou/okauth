/**
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
package com.github.wautsns.okauth.core.client.core.dto;

import com.github.wautsns.okauth.core.client.util.http.OkAuthResponse;

/**
 * OAuth token.
 *
 * @since Feb 27, 2020
 * @author wautsns
 */
public class OAuthToken extends OAuthResponseDataMap {

    /**
     * Construct an oauth token.
     *
     * @param response correct okauth response, require nonnull
     */
    public OAuthToken(OkAuthResponse response) {
        super(response.getData());
    }

    /**
     * Get value of the name "access_token".
     *
     * @return access token
     */
    public String getAccessToken() {
        return getString("access_token");
    }

}
