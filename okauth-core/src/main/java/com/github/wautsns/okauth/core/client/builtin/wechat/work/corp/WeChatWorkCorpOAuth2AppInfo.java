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
package com.github.wautsns.okauth.core.client.builtin.wechat.work.corp;

import com.github.wautsns.okauth.core.client.kernel.OAuth2AppInfo;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * WeChatWorkCorp OAuth2 app info.
 *
 * @author wautsns
 * @since May 23, 2020
 */
@Data
@Accessors(chain = true)
public class WeChatWorkCorpOAuth2AppInfo implements OAuth2AppInfo {

    /** Corp id. */
    private String corpId;
    /** Corp secret. */
    private String corpSecret;
    /** See {@link AuthorizeType} for details(default is {@link AuthorizeType#WEB}). */
    private AuthorizeType authorizeType = AuthorizeType.WEB;
    /** Agent id(required if the authorize type is {@link AuthorizeType#QR_CODE}). */
    private String agentId;
    /** Redirect uri. */
    private String redirectUri;

    // #################### enum ########################################################

    /** Authorize type. */
    public enum AuthorizeType {
        /** Web page authorization link. */
        WEB,
        /** QR code login link. */
        QR_CODE
    }

}
