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
package com.github.wautsns.okauth.core.client.builtin.baidu;

import com.github.wautsns.okauth.core.OpenPlatform;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatform;
import com.github.wautsns.okauth.core.client.kernel.model.OAuthUser;
import com.github.wautsns.okauth.core.http.model.OAuthResponse;

import java.time.LocalDate;

/**
 * Baidu user.
 *
 * <pre>
 * {
 *     "userid": "33192xxxxx",
 *     "portrait": "afc577617574736e7xxxxx",
 *     "username": "w***s",
 *     "is_bind_mobile": "1",
 *     "is_realname": "1",
 *     "birthday": "1998-02-05",
 *     "sex": "1",
 *     "openid": "oF6xVTPl83G5Q2tAeh5w5j1uNyxxxxx"
 * }
 * </pre>
 *
 * @author wautsns
 * @since Mar 04, 2020
 */
public class BaiduUser extends OAuthUser {

    private static final long serialVersionUID = -6258757118243380879L;

    /**
     * Construct Baidu user.
     *
     * @param response correct response, require nonnull
     */
    public BaiduUser(OAuthResponse response) {
        super(response);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatform.BAIDU;
    }

    @Override
    public String getOpenid() {
        return getOriginalDataMap().getAsString("openid");
    }

    /**
     * {@inheritDoc}
     *
     * @return avatar url
     */
    @Override
    public String getAvatarUrl() {
        return "http://tb.himg.baidu.com/sys/portrait/item/"
            + getOriginalDataMap().getAsString("portrait");
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
        String gender = getOriginalDataMap().getAsString("sex");
        if ("1".equals(gender)) {
            return Gender.MALE;
        } else if ("0".equals(gender)) {
            return Gender.FEMALE;
        } else {
            return Gender.UNKNOWN;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return birthday
     */
    @Override
    public LocalDate getBirthday() {
        return LocalDate.parse(getOriginalDataMap().getAsString("birthday"));
    }

}
