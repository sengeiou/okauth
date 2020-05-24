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
package com.github.wautsns.okauth.core.client.builtin.wechat.work.corp.model;

import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatformNames;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2User;
import lombok.Data;

/**
 * WeChatWorkCorp OAuth2 user.
 *
 * @author wautsns
 * @since May 23, 2020
 */
@Data
public class WeChatWorkCorpOAuth2User implements OAuth2User {

    private static final long serialVersionUID = -8284465889855788132L;

    /** Original data map. */
    private final DataMap originalDataMap;

    @Override
    public String getOpenPlatform() {
        return BuiltInOpenPlatformNames.WECHAT_WORK_CORP;
    }

    public String getUserId() {
        return originalDataMap.getAsString("UserId");
    }

    public String getDeviceId() {
        return originalDataMap.getAsString("DeviceId");
    }

    // #################### - ###########################################################

    @Override
    public String getOpenid() {
        return getUserId();
    }

    @Override
    public String getUid() {
        return getUserId();
    }

}
