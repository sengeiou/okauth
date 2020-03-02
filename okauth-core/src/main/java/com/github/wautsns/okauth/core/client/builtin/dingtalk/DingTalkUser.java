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
package com.github.wautsns.okauth.core.client.builtin.dingtalk;

import com.github.wautsns.okauth.core.client.OpenPlatform;
import com.github.wautsns.okauth.core.client.builtin.OpenPlatforms;
import com.github.wautsns.okauth.core.client.kernel.http.model.dto.OAuthResponse;
import com.github.wautsns.okauth.core.client.kernel.model.dto.OpenPlatformUser;
import java.io.Serializable;
import java.util.Map;

/**
 * DingTalk user.
 *
 * <p>response data map:
 *
 * <pre>
 * {
 *     "errcode": 0,
 *     "errmsg": "ok",
 *     "user_info": {
 *         "nick": "张三",
 *         "openid": "liSii8KCxxxxx",
 *         "unionid": "7Huu46kk"
 *     }
 * }
 * </pre>
 *
 * @author wautsns
 * @since Mar 01, 2020
 */
public class DingTalkUser extends OpenPlatformUser {

    private static final long serialVersionUID = -3396777913969297784L;

    /**
     * Construct DingTalk user.
     *
     * @param response original data map, require nonnull
     */
    @SuppressWarnings("unchecked")
    public DingTalkUser(OAuthResponse response) {
        super((Map<String, Serializable>) response.getData().get("user_info"));
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return OpenPlatforms.DINGTALK;
    }

    @Override
    public String getOpenid() {
        return getAsString("openid");
    }

    @Override
    public String getUnionid() {
        return getAsString("unionid");
    }

    @Override
    public String getNickname() {
        return getAsString("nick");
    }

}
