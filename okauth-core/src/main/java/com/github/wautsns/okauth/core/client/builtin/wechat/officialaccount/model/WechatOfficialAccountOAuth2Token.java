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
package com.github.wautsns.okauth.core.client.builtin.wechat.officialaccount.model;

import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatformNames;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2RefreshableToken;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * WechatOfficialAccount oauth2 token.
 *
 * @author wautsns
 * @since May 23, 2020
 */
@Data
@Accessors(chain = true)
public class WechatOfficialAccountOAuth2Token implements OAuth2RefreshableToken {

    private static final long serialVersionUID = 6551303389398223705L;

    /** Token id. */
    private String tokenId;
    /** Original data map. */
    private final DataMap originalDataMap;

    @Override
    public String getOpenPlatform() {
        return BuiltInOpenPlatformNames.WECHAT_OFFICIAL_ACCOUNT;
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

    /** WechatOfficialAccount oauth2 refresh token expires in thirty days. */
    private static final Integer REFRESH_TOKEN_EXPIRATION_SECONDS = 30 * 24 * 3600;

    @Override
    public Integer getRefreshTokenExpirationSeconds() {
        return REFRESH_TOKEN_EXPIRATION_SECONDS;
    }

    /**
     * Get openid.
     *
     * <p>User unique identification, please note that when a user does not follow the official account, the user will
     * also generate a unique openid and official account when you visit the official account web page.
     *
     * @return openid
     */
    public String getOpenId() {
        return originalDataMap.getAsString("openid");
    }

    /**
     * Get scopes(delimiter: comma).
     *
     * <p>User authorization scope.
     *
     * @return scopes
     */
    public String getScopes() {
        return originalDataMap.getAsString("scope");
    }

}
