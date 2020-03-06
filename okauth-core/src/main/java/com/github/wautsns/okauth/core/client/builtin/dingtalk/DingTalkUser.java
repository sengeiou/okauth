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

import com.github.wautsns.okauth.core.OpenPlatform;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatform;
import com.github.wautsns.okauth.core.client.kernel.model.OAuthUser;
import com.github.wautsns.okauth.core.http.model.OAuthResponse;

/**
 * DingTalk user.
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
 * @since Mar 04, 2020
 */
public class DingTalkUser extends OAuthUser {

    private static final long serialVersionUID = -2179997396663957887L;

    /**
     * Construct DingTalk user.
     *
     * @param response correct response, require nonnull
     */
    public DingTalkUser(OAuthResponse response) {
        super(response);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatform.DINGTALK;
    }

    @Override
    public String getOpenid() {
        return getOriginalDataMap().getAsString("openid");
    }

    /**
     * {@inheritDoc}
     *
     * @return unionid
     */
    @Override
    public String getUnionid() {
        return getOriginalDataMap().getAsString("unionid");
    }

    /**
     * {@inheritDoc}
     *
     * @return nickname
     */
    @Override
    public String getNickname() {
        return getOriginalDataMap().getAsString("nick");
    }

}
