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
package com.github.wautsns.okauth.core.client.builtin.oschina;

import com.github.wautsns.okauth.core.OpenPlatform;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatform;
import com.github.wautsns.okauth.core.client.kernel.model.OAuthUser;
import com.github.wautsns.okauth.core.http.model.OAuthResponse;

/**
 * OSChina user.
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
 * @author wautsns
 * @since Mar 04, 2020
 */
public class OSChinaUser extends OAuthUser {

    /** serialVersionUID */
    private static final long serialVersionUID = -2692000764813082128L;

    /**
     * Construct OSChina user.
     *
     * @param response correct response, require nonnull
     */
    public OSChinaUser(OAuthResponse response) {
        super(response);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatform.OSCHINA;
    }

    @Override
    public String getOpenid() {
        return getOriginalDataMap().getInteger("id").toString();
    }

    /**
     * {@inheritDoc}
     *
     * @return nickname
     */
    @Override
    public String getNickname() {
        return getOriginalDataMap().getAsString("name");
    }

    /**
     * {@inheritDoc}
     *
     * @return avatar url
     */
    @Override
    public String getAvatarUrl() {
        return getOriginalDataMap().getAsString("avatar");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Possible values: {@linkplain com.github.wautsns.okauth.core.client.kernel.model.OAuthUser.Gender#MALE MALE},
     * {@linkplain com.github.wautsns.okauth.core.client.kernel.model.OAuthUser.Gender#FEMALE FEMALE}, {@linkplain
     * com.github.wautsns.okauth.core.client.kernel.model.OAuthUser.Gender#UNKNOWN UNKNOWN}.
     *
     * @return gender
     */
    @Override
    public Gender getGender() {
        String gender = getOriginalDataMap().getAsString("gender");
        if (gender.equals("male")) {
            return Gender.MALE;
        } else if (gender.equals("female")) {
            return Gender.FEMALE;
        } else {
            return Gender.UNKNOWN;
        }
    }

}
