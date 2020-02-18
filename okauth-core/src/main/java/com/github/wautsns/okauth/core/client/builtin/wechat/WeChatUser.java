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
package com.github.wautsns.okauth.core.client.builtin.wechat;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatform;
import com.github.wautsns.okauth.core.client.core.dto.OAuthUser;
import com.github.wautsns.okauth.core.client.util.http.Response;

/**
 * WeChat user.
 *
 * @deprecated not tested
 * @since Feb 18, 2020
 * @author wautsns
 */
@Deprecated
@JsonNaming(SnakeCaseStrategy.class)
public class WeChatUser extends OAuthUser {

    /**
     * Construct a WeChat user.
     *
     * @param response response, require nonnull
     */
    public WeChatUser(Response response) {
        super(response);
    }

    @Override
    public String getOpenPlatformIdentifier() {
        return BuiltInOpenPlatform.WECHAT.getIdentifier();
    }

    @Override
    public String getOpenId() {
        return getString("unionid");
    }

    @Override
    public String getNickname() {
        return getString("nickname");
    }

    @Override
    public String getAvatarUrl() {
        return getString("headimgurl");
    }

}
