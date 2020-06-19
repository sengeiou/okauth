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
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2User;
import lombok.Data;

import java.util.List;

/**
 * WeChatOfficialAccount oauth2 user.
 *
 * <pre>
 * {
 *   "openid":" OPENID",
 *   "nickname": NICKNAME,
 *   "sex":"1",
 *   "province":"PROVINCE",
 *   "city":"CITY",
 *   "country":"COUNTRY",
 *   "headimgurl": "http://thirdwx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46",
 *   "privilege":["PRIVILEGE1" "PRIVILEGE2"],
 *   "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
 * }
 * </pre>
 *
 * @author wautsns
 * @since Jun 19, 2020
 */
@Data
public class WeChatOfficialAccountOAuth2User implements OAuth2User {

    private static final long serialVersionUID = 133319205583865255L;

    /** Original data map. */
    private final DataMap originalDataMap;

    @Override
    public String getOpenPlatform() {
        return BuiltInOpenPlatformNames.WECHAT_OFFICIAL_ACCOUNT;
    }

    @Override
    public String getOpenid() {
        return originalDataMap.getAsString("openid");
    }

    @Override
    public String getUnionid() {
        return originalDataMap.getAsString("unionid");
    }

    @Override
    public String getNickname() {
        return originalDataMap.getAsString("nickname");
    }

    /**
     * Get head img url.
     *
     * @return head img url
     */
    public String getHeadimgurl() {
        return originalDataMap.getAsString("headimgurl");
    }

    /**
     * Get sex.
     *
     * <ul>
     * Optional values:
     * <li>{@code Gender.MALE}</li>
     * <li>{@code Gender.FEMALE}</li>
     * <li>{@code Gender.UNKNOWN}</li>
     * </ul>
     *
     * @return Gender enumeration.
     */
    public Gender getSex() {
        String sex = originalDataMap.getAsString("sex");
        if ("1".equals(sex)) {
            return Gender.MALE;
        } else if ("2".equals(sex)) {
            return Gender.FEMALE;
        } else {
            return Gender.UNKNOWN;
        }
    }

    /**
     * Get country(eg. China: {@code "CN"}).
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

    /**
     * Get privilege.
     *
     * <p>User privilege information, such as WeChat Woka user(chinaunicom).
     *
     * @return privilege
     */
    public List<String> getPrivilege() {
        return originalDataMap.getAs("privilege");
    }

    // #################### amendment ###################################################

    @Override
    public String getAvatarUrl() {
        return getHeadimgurl();
    }

    @Override
    public Gender getGender() {
        return getSex();
    }

}
