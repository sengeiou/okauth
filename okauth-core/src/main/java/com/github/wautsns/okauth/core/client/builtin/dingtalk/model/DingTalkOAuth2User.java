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
package com.github.wautsns.okauth.core.client.builtin.dingtalk.model;

import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatformNames;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2User;
import com.github.wautsns.okauth.core.client.kernel.openplatform.OpenPlatform;

/**
 * DingTalk oauth2 user.
 *
 * <pre>
 * {
 *  "nick": "张三",
 *  "openid": "liSii8KCxxxxx",
 *  "unionid": "7Huu46kk"
 * }
 * </pre>
 *
 * @author wautsns
 * @since Jun 22, 2020
 */
public class DingTalkOAuth2User extends OAuth2User {

    private static final long serialVersionUID = -4496518945943399963L;

    /**
     * Construct a DingTalk oauth2 user.
     *
     * @param originalDataMap original data map
     */
    public DingTalkOAuth2User(DataMap originalDataMap) {
        super(originalDataMap);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatformNames.DING_TALK;
    }

    @Override
    public String getOpenid() {
        return getOriginalDataMap().getAsString("openid");
    }

    @Override
    public String getUnionid() {
        return getOriginalDataMap().getAsString("unionid");
    }

    @Override
    public String getNickname() {
        return getOriginalDataMap().getAsString("nick");
    }

}
