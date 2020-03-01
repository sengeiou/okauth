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
package com.github.wautsns.okauth.core.client.builtin.oschina;

import com.github.wautsns.okauth.core.client.OpenPlatform;
import com.github.wautsns.okauth.core.client.builtin.OpenPlatforms;
import com.github.wautsns.okauth.core.client.kernel.http.model.dto.OAuthResponse;
import com.github.wautsns.okauth.core.client.kernel.model.dto.OpenPlatformUser;

/**
 * OSChina user.
 *
 * <p>response data map:
 *
 * <pre>
 * {
 *     "gender": "male",
 *     "name": "wautsns",
 *     "location": "上海 普陀",
 *     "id": 41xxxxx,
 *     "avatar": "https://static.oschina.net/uploads/user/2076/41xxxxx_50.jpg?t=1561458936000",
 *     "email": "wautsns@foxmail.com",
 *     "url": "https://my.oschina.net/u/41xxxxx"
 * }
 * </pre>
 *
 * @since Mar 01, 2020
 * @author wautsns
 */
public class OSChinaUser extends OpenPlatformUser {

    /** serialVersionUID */
    private static final long serialVersionUID = -2692000764813082128L;

    /**
     * Construct OSChina user.
     *
     * @param originalDataMap original data map, require nonnull
     */
    public OSChinaUser(OAuthResponse response) {
        super(response);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return OpenPlatforms.OSCHINA;
    }

    @Override
    public String getOpenid() {
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

    @Override
    public Gender getGender() {
        String gender = getString("gender");
        if (gender.equals("male")) {
            return Gender.MALE;
        } else if (gender.equals("female")) {
            return Gender.FEMALE;
        } else {
            return Gender.UNKNOWN;
        }
    }

}
