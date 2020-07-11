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
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatforms;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2RefreshableToken;
import com.github.wautsns.okauth.core.client.kernel.openplatform.OpenPlatform;

/**
 * Baidu oauth2 token.
 *
 * <pre>
 * {
 * 	"expires_in": 2592000,
 * 	"refresh_token": "REFRESH_TOKEN",
 * 	"access_token": "ACCESS_TOKEN",
 * 	"session_secret": "",
 * 	"session_key": "",
 * 	"scope": "SCOPE"
 * }
 * </pre>
 *
 * @author wautsns
 * @since May 17, 2020
 */
public class BaiduOAuth2Token extends OAuth2RefreshableToken {

    private static final long serialVersionUID = 626454809091111659L;

    /**
     * Construct a Baidu oauth2 token.
     *
     * @param originalDataMap original data map
     */
    public BaiduOAuth2Token(DataMap originalDataMap) {
        super(originalDataMap);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatforms.BAIDU;
    }

    /**
     * Get scopes(delimiter: space).
     *
     * <p>Final access scope of access token, that is the list of permissions actually granted by the user (the user
     * may cancel some requested permissions when authorizing the page)
     *
     * @return scopes
     * @see com.github.wautsns.okauth.core.client.builtin.baidu.BaiduOAuth2AppInfo.Scope
     */
    public String getScopes() {
        return getOriginalDataMap().getAsString("scope");
    }

}
