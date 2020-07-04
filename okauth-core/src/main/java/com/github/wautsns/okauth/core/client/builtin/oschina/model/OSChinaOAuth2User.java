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
package com.github.wautsns.okauth.core.client.builtin.oschina.model;

import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatformNames;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2User;
import com.github.wautsns.okauth.core.client.kernel.openplatform.OpenPlatform;

/**
 * OSChina oauth2 user.
 *
 * <pre>
 * {
 * 	"gender": "male",
 * 	"name": "wautsns",
 * 	"location": "上海 普陀",
 * 	"id": 4153817,
 * 	"avatar": "https://static.oschina.net/uploads/user/2076/4153817_50.jpg?t=1561458936000",
 * 	"email": "wautsns@foxmail.com",
 * 	"url": "https://my.oschina.net/u/4153817"
 * }
 * </pre>
 *
 * @author wautsns
 * @since May 22, 2020
 */
public class OSChinaOAuth2User extends OAuth2User {

    private static final long serialVersionUID = -5177778649683021808L;

    /**
     * Construct an OSChina oauth2 user.
     *
     * @param originalDataMap original data map
     */
    public OSChinaOAuth2User(DataMap originalDataMap) {
        super(originalDataMap);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatformNames.OSCHINA;
    }

    public String getId() {
        return getOriginalDataMap().getAsString("id");
    }

    @Override
    public String getEmail() {
        return getOriginalDataMap().getAsString("email");
    }

    public String getName() {
        return getOriginalDataMap().getAsString("name");
    }

    @Override
    public Gender getGender() {
        String gender = getOriginalDataMap().getAsString("gender");
        if ("male".equals(gender)) {
            return Gender.MALE;
        } else if ("female".equals(gender)) {
            return Gender.FEMALE;
        } else {
            return Gender.UNKNOWN;
        }
    }

    @Override
    public String getAvatarUrl() {
        return getOriginalDataMap().getAsString("avatar");
    }

    public String getLocation() {
        return getOriginalDataMap().getAsString("location");
    }

    public String getUrl() {
        return getOriginalDataMap().getAsString("url");
    }

    // #################### amendment ###################################################

    @Override
    public String getOpenid() {
        return getId();
    }

    @Override
    public String getUid() {
        return getId();
    }

    @Override
    public String getNickname() {
        return getName();
    }

}
