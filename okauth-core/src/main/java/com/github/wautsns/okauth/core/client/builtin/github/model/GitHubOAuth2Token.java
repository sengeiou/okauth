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

/**
 * GitHub oauth2 token.
 *
 * @author wautsns
 * @since May 17, 2020
 */
@Data
public class GitHubOAuth2Token implements OAuth2Token {

    private static final long serialVersionUID = 7155633421437700449L;

    private final DataMap origin;

    @Override
    public String getOpenPlatform() {
        return BuiltInOpenPlatformNames.GIT_HUB;
    }

    @Override
    public String getAccessToken() {
        return origin.getAsString("access_token");
    }

    /**
     * FIXME GitHub access token expires in
     *
     * <p>There is no access token expiration time in the response, nor is it mentioned in the official doc. Assume the
     * expiration time is 1 day.
     */
    private static final Integer ACCESS_TOKEN_EXPIRES_IN = 24 * 3600;

    @Override
    public Integer getAccessTokenExpirationSeconds() {
        return ACCESS_TOKEN_EXPIRES_IN;
    }

    @Override
    public String getScope() {
        return origin.getAsString("scope");
    }

    public String getTokenType() {
        return origin.getAsString("token_type");
    }

}
