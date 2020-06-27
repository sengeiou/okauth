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
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatformNames;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2User;
import lombok.Data;

import java.time.LocalDate;

/**
 * Baidu oauth2 user.
 *
 * <pre>
 * {
 * 	"userid": "USERID",
 * 	"portrait": "afc577617574736e73c6d3",
 * 	"username": "w***s",
 * 	"is_bind_mobile": "1",
 * 	"is_realname": "1",
 * 	"birthday": "1998-02-05",
 * 	"sex": "1",
 * 	"openid": "OPENID"
 * }
 * </pre>
 *
 * @author wautsns
 * @since May 17, 2020
 */
@Data
public class BaiduOAuth2User implements OAuth2User {

    private static final long serialVersionUID = 1596112334287788877L;

    /** Original data map. */
    private final DataMap originalDataMap;

    @Override
    public String getOpenPlatform() {
        return BuiltInOpenPlatformNames.BAIDU;
    }

    @Override
    public String getOpenid() {
        return originalDataMap.getAsString("openid");
    }

    @Override
    public String getUid() {
        return originalDataMap.getAsString("userid");
    }

    /**
     * {@inheritDoc}
     *
     * <p>The username is masked. eg. {@code "w***s"}.
     *
     * @return {@inheritDoc}
     */
    @Override
    public String getUsername() {
        return originalDataMap.getAsString("username");
    }

    /**
     * Get portrait.
     *
     * <p>The currently logged in user's avatar.
     * <p>If you need avatar url, please use {@linkplain #getAvatarUrl()} instead.
     *
     * @return portrait
     */
    public String getPortrait() {
        return originalDataMap.getAsString("portrait");
    }

    @Override
    public Gender getGender() {
        String sex = originalDataMap.getAsString("sex");
        if ("1".equals(sex)) {
            return Gender.MALE;
        } else if ("0".equals(sex)) {
            return Gender.FEMALE;
        } else {
            return Gender.UNKNOWN;
        }
    }

    @Override
    public LocalDate getBirthday() {
        return LocalDate.parse(originalDataMap.getAsString("birthday"));
    }

    /**
     * Whether the currently logged in user is bind mobile.
     *
     * @return {@code true} if the currently logged in user is bind mobile, otherwise {@code false}
     */
    public Boolean isBindMobile() {
        return "1".equals(originalDataMap.getAsString("is_bind_mobile"));
    }

    /**
     * Whether the currently logged in user is real name verified.
     *
     * @return {@code true} if the currently logged in user is real name verified, otherwise {@code false}
     */
    public Boolean isRealName() {
        return "1".equals(originalDataMap.getAsString("is_real_name"));
    }

    // #################### amendment ###################################################

    @Override
    public String getAvatarUrl() {
        return "http://tb.himg.baidu.com/sys/portrait/item/" + getPortrait();
    }

}
