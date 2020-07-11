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
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatforms;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2RefreshableToken;
import com.github.wautsns.okauth.core.client.kernel.openplatform.OpenPlatform;

/**
 * Gitee oauth2 token.
 *
 * <pre>
 * {
 * 	"access_token": "ACCESS_TOKEN",
 * 	"token_type": "bearer",
 * 	"expires_in": 86400,
 * 	"refresh_token": "REFRESH_TOKEN",
 * 	"scope": "SCOPE",
 * 	"created_at": 1590230103
 * }
 * </pre>
 *
 * @author wautsns
 * @since May 17, 2020
 */
public class GiteeOAuth2Token extends OAuth2RefreshableToken {

    private static final long serialVersionUID = 7155633421437700449L;

    /**
     * Construct a Gitee oauth2 refreshable token.
     *
     * @param originalDataMap original data map
     */
    public GiteeOAuth2Token(DataMap originalDataMap) {
        super(originalDataMap);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatforms.GITEE;
    }

    /**
     * Get scopes(delimiter: space).
     *
     * @return scopes
     * @see com.github.wautsns.okauth.core.client.builtin.gitee.GiteeOAuth2AppInfo.Scope
     */
    public String getScopes() {
        return getOriginalDataMap().getAsString("scope");
    }

    /**
     * Get created at(seconds timestamp).
     *
     * @return created at(seconds timestamp)
     */
    public Long getCreatedAt() {
        return getOriginalDataMap().getAsLong("created_at");
    }

}
