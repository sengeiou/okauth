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
package com.github.wautsns.okauth.core.client.builtin.tiktok.model;

import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatformNames;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2User;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * TikTok oauth2 user.
 *
 * <pre>
 * {
 *  "open_id": "OPEN_ID",
 *  "union_id": "UNION_ID",
 *  "nickname": "NICKNAME",
 *  "avatar": "AVATAR",
 *  "city": "CITY",
 *  "province": "PROVINCE",
 *  "country": "COUNTRY",
 *  "gender": GENDER,
 *  "e_account_role": "E_ACCOUNT_ROLE",
 * }
 * </pre>
 *
 * @author wautsns
 * @since Jun 23, 2020
 */
@Data
@Accessors(chain = true)
public class TikTokOAuth2User implements OAuth2User {

    private static final long serialVersionUID = -3175318888587047072L;

    /** Original data map. */
    private final DataMap originalDataMap;

    @Override
    public String getOpenPlatform() {
        return BuiltInOpenPlatformNames.TIK_TOK;
    }

    @Override
    public String getOpenid() {
        return originalDataMap.getAsString("open_id");
    }

    @Override
    public String getUnionid() {
        return originalDataMap.getAsString("union_id");
    }

    @Override
    public String getNickname() {
        return originalDataMap.getAsString("nickname");
    }

    @Override
    public String getAvatarUrl() {
        return originalDataMap.getAsString("avatar");
    }

    @Override
    public Gender getGender() {
        String gender = originalDataMap.getAsString("gender");
        if ("1".equals(gender)) {
            return Gender.MALE;
        } else if ("2".equals(gender)) {
            return Gender.FEMALE;
        } else {
            return Gender.UNKNOWN;
        }
    }

    /**
     * Get country.
     *
     * @return country
     */
    public String getCountry() {
        return originalDataMap.getAsString("country");
    }

    /**
     * Get province.
     *
     * @return province
     */
    public String getProvince() {
        return originalDataMap.getAsString("province");
    }

    /**
     * Get city.
     *
     * @return city
     */
    public String getCity() {
        return originalDataMap.getAsString("city");
    }

}
