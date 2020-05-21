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
package com.github.wautsns.okauth.core.client.builtin.gitee.model;

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
public class GiteeOAuth2Token implements OAuth2Token {

    private static final long serialVersionUID = 7155633421437700449L;

    private final DataMap origin;

    @Override
    public String getOpenPlatform() {
        return BuiltInOpenPlatformNames.GITEE;
    }

    @Override
    public String getAccessToken() {
        return origin.getAsString("access_token");
    }

    @Override
    public String getRefreshToken() {
        return origin.getAsString("refresh_token");
    }

    @Override
    public Integer getAccessTokenExpirationSeconds() {
        return origin.getAsInteger("expires_in");
    }

}
