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
package com.github.wautsns.okauth.core.client.builtin.oschina;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatform;
import com.github.wautsns.okauth.core.client.core.OpenPlatform;
import com.github.wautsns.okauth.core.client.core.dto.OAuthUser;
import com.github.wautsns.okauth.core.client.util.http.Response;

/**
 * OSChina user.
 *
 * @since Feb 18, 2020
 * @author wautsns
 */
@JsonNaming(SnakeCaseStrategy.class)
public class OSChinaUser extends OAuthUser {

    /**
     * Construct a OSChina user.
     *
     * @param response response, require nonnull
     */
    public OSChinaUser(Response response) {
        super(response);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatform.OSCHINA;
    }

    @Override
    public String getOpenId() {
        return getString("id");
    }

    @Override
    public String getNickname() {
        return getString("name");
    }

    @Override
    public String getAvatarUrl() {
        return getString("avatar");
    }

}
