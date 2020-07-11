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
package com.github.wautsns.okauth.core.client.builtin.wechatofficialaccount.model;

import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatforms;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2RefreshableToken;
import com.github.wautsns.okauth.core.client.kernel.openplatform.OpenPlatform;

/**
 * WechatOfficialAccount oauth2 token.
 *
 * @author wautsns
 * @since May 23, 2020
 */
public class WechatOfficialAccountOAuth2Token extends OAuth2RefreshableToken {

    private static final long serialVersionUID = 6551303389398223705L;

    /**
     * Construct a WechatOfficialAccount oauth2 refreshable token.
     *
     * @param originalDataMap original data map
     */
    public WechatOfficialAccountOAuth2Token(DataMap originalDataMap) {
        super(originalDataMap);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatforms.WECHAT_OFFICIAL_ACCOUNT;
    }

    @Override
    public String getAccessToken() {
        return getOriginalDataMap().getAsString("access_token");
    }

    @Override
    public Integer getAccessTokenExpirationSeconds() {
        return getOriginalDataMap().getAsInteger("expires_in");
    }

    @Override
    public String getRefreshToken() {
        return getOriginalDataMap().getAsString("refresh_token");
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
        return getOriginalDataMap().getAsString("openid");
    }

    /**
     * Get scopes(delimiter: comma).
     *
     * <p>User authorization scope.
     *
     * @return scopes
     */
    public String getScopes() {
        return getOriginalDataMap().getAsString("scope");
    }

}
