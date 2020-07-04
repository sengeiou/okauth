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

import com.github.wautsns.okauth.core.client.kernel.OAuth2AppInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * DingTalk oauth2 app info.
 *
 * @author wautsns
 * @since Jun 22, 2020
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class DingTalkOAuth2AppInfo extends OAuth2AppInfo {

    /** App id. */
    private String appId;
    /** App secret. */
    private String appSecret;
    /** Authorize type. */
    private AuthorizeType authorizeType;
    /** Redirect uri. */
    private String redirectUri;

    /** Authorize type. */
    public enum AuthorizeType {
        /**
         * Use the DingTalk client to scan the code and confirm login to your web system to obtain the DingTalk identity
         * of the user being accessed in your system without the user having to enter the account password.
         */
        QR_CODE,
        /**
         * When the developed system (H5 page) is only opened in the DingTalk client, but not a DingTalk application,
         * the system can automatically obtain the DingTalk identity information of the user who is accessing, without
         * the user having to enter the account password again.
         */
        WEB,
        /**
         * You can log in to a third-party independent web system by manually entering the DingTalk account, password
         * and obtain the DingTalk identity of the user who is accessing.
         */
        PASSWORD,
    }

}
