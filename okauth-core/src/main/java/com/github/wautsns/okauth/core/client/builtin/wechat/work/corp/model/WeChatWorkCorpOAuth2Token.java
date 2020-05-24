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
package com.github.wautsns.okauth.core.client.builtin.wechat.work.corp.model;

import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatformNames;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2Token;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * WeChatWorkCorp OAuth2 token.
 *
 * @author wautsns
 * @since May 23, 2020
 */
@Data
@Accessors(chain = true)
public class WeChatWorkCorpOAuth2Token implements OAuth2Token {

    private static final long serialVersionUID = -8354114542294039343L;

    /** Token id. */
    private String tokenId;
    /** Original data map. */
    private final DataMap originalDataMap;

    @Override
    public String getOpenPlatform() {
        return BuiltInOpenPlatformNames.WECHAT_WORK_CORP;
    }

    @Override
    public String getAccessToken() {
        return originalDataMap.getAsString("access_token");
    }

    @Override
    public Integer getAccessTokenExpirationSeconds() {
        return originalDataMap.getAsInteger("expires_in");
    }

    /**
     * Set access token expiration seconds.
     *
     * @param accessTokenExpirationSeconds access token expiration seconds
     * @return self reference
     */
    public WeChatWorkCorpOAuth2Token setAccessTokenExpirationSeconds(Integer accessTokenExpirationSeconds) {
        originalDataMap.put("expires_in", accessTokenExpirationSeconds);
        return this;
    }

}
