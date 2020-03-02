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
package com.github.wautsns.okauth.core.client.builtin.tencentcloud;

import com.github.wautsns.okauth.core.client.OpenPlatform;
import com.github.wautsns.okauth.core.client.builtin.OpenPlatforms;
import com.github.wautsns.okauth.core.client.kernel.http.model.dto.OAuthResponse;
import com.github.wautsns.okauth.core.client.kernel.model.dto.OpenPlatformUser;
import java.io.Serializable;
import java.util.Map;

/**
 * TencentCloud user.
 *
 * <p>response data map:
 *
 * <pre>
 * {
 *     "code": 0,
 *     "data": {
 *         "tags_str": "云计算追随者, 技术风向标",
 *         "tags": "19,32",
 *         "job_str": "产品",
 *         "job": 2,
 *         "sex": 0,
 *         "phone": "18600000000",
 *         "birthday": "2014-07-16",
 *         "location": "广东 深圳  ",
 *         "company": "Coding.net",
 *         "slogan": "让开发更简单",
 *         "website": "",
 *         "introduction": "",
 *         "avatar": "https://dn-coding-net-avatar.codehub.cn/89628160-f594-4e52-8c77-fda089fcc7dd.jpg",
 *         "gravatar": "https://dn-coding-net-avatar.codehub.cn/89628160-f594-4e52-8c77-fda089fcc7dd.jpg",
 *         "lavatar": "https://dn-coding-net-avatar.codehub.cn/89628160-f594-4e52-8c77-fda089fcc7dd.jpg",
 *         "created_at": 1399045779000,
 *         "last_logined_at": 1502121619000,
 *         "last_activity_at": 1502173432514,
 *         "global_key": "coding",
 *         "name": "coding",
 *         "name_pinyin": "coding",
 *         "updated_at": 1502121621000,
 *         "path": "/u/coding",
 *         "status": 1,
 *         "email": "coding@dev.tencent.com",
 *         "is_member": 0,
 *         "id": 93,
 *         "points_left": 0.44,
 *         "vip": 4,
 *         "vip_expired_at": 1502467200000,
 *         "skills": [
 *             {
 *                 "skillName": "Java",
 *                 "skillId": 1,
 *                 "level": 4
 *             },
 *             {
 *                 "skillName": "Ruby",
 *                 "skillId": 3,
 *                 "level": 3
 *             },
 *             {
 *                 "skillName": "JavaScript",
 *                 "skillId": 11,
 *                 "level": 3
 *             }
 *         ],
 *         "degree": 3,
 *         "school": "",
 *         "follows_count": 83,
 *         "fans_count": 191,
 *         "tweets_count": 110,
 *         "phone_country_code": "+86",
 *         "country": "cn",
 *         "followed": false,
 *         "follow": false,
 *         "is_phone_validated": true,
 *         "email_validation": 1,
 *         "phone_validation": 1,
 *         "twofa_enabled": 1
 *     }
 * }
 * </pre>
 *
 * @author wautsns
 * @since Mar 01, 2020
 */
public class TencentCloudUser extends OpenPlatformUser {

    private static final long serialVersionUID = 1654577901626169343L;

    /**
     * Construct TencentCloud user.
     *
     * @param response okauth response, require nonnull
     */
    @SuppressWarnings("unchecked")
    public TencentCloudUser(OAuthResponse response) {
        super((Map<String, Serializable>) response.getData().get("data"));
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return OpenPlatforms.TENCENTCLOUD;
    }

    @Override
    public String getOpenid() {
        return getAsString("id");
    }

    @Override
    public String getNickname() {
        return getAsString("name");
    }

    @Override
    public String getAvatarUrl() {
        return getAsString("avatar");
    }

    @Override
    public Gender getGender() {
        String gender = getAsString("sex");
        if ("1".equals(gender)) {
            return Gender.MALE;
        } else if ("0".equals(gender)) {
            return Gender.FEMALE;
        } else {
            return Gender.UNKNOWN;
        }
    }

}
