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
package com.github.wautsns.okauth.core.client.builtin.wechat.officialaccount;

import com.github.wautsns.okauth.core.client.kernel.OAuth2AppInfo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * WeChatOfficialAccount OAuth2 app info.
 *
 * @author wautsns
 * @since May 23, 2020
 */
@Data
@Accessors(chain = true)
public class WeChatOfficialAccountOAuth2AppInfo implements OAuth2AppInfo {

    /** Official account unique identifier. */
    private String uniqueIdentifier;
    /** Official account app secret. */
    private String appSecret;
    /** Redirect uri. */
    private String redirectUri;
    /** See {@link Scope} for details. */
    private Scope scope = Scope.SNSAPI_BASE;

    /** Application authorization scope. */
    @RequiredArgsConstructor
    public enum Scope {

        /** Do not pop up the authorization page, jump directly, you can only get the user openid. */
        SNSAPI_BASE("snsapi_base"),
        /**
         * The authorization page pops up, you can get the nickname, gender, and location through openid. And, even in
         * the case of not paying attention, as long as the user authorizes, they can also obtain their information.
         */
        SNSAPI_USER_INFO("snsapi_userinfo");

        /** Value. */
        public final String value;

    }

}
