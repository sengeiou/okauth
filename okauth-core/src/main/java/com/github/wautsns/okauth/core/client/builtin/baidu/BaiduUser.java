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
package com.github.wautsns.okauth.core.client.builtin.baidu;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatform;
import com.github.wautsns.okauth.core.client.core.OpenPlatform;
import com.github.wautsns.okauth.core.client.core.dto.OAuthUser;
import com.github.wautsns.okauth.core.client.util.http.OkAuthResponse;

/**
 * Baidu user.
 *
 * @since Feb 27, 2020
 * @author wautsns
 */
@JsonNaming(SnakeCaseStrategy.class)
public class BaiduUser extends OAuthUser {

    /**
     * Construct a Baidu user.
     *
     * @param response correct okauth response, require nonnull
     */
    public BaiduUser(OkAuthResponse response) {
        super(response);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatform.BAIDU;
    }

    @Override
    public String getOpenId() {
        return getString("userid");
    }

    @Override
    public String getNickname() {
        return getString("username");
    }

    @Override
    public String getAvatarUrl() {
        return "http://tb.himg.baidu.com/sys/portrait/item/" + getString("portrait");
    }

}
