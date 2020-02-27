/**
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
package com.github.wautsns.okauth.core.client.core.dto;

import com.github.wautsns.okauth.core.client.core.OpenPlatform;
import com.github.wautsns.okauth.core.client.util.http.OkAuthResponse;

/**
 * Abstract open platform user info.
 *
 * @since Feb 27, 2020
 * @author wautsns
 */
public abstract class OAuthUser extends OAuthResponseDataMap {

    /**
     * Construct an open platform user info.
     *
     * @param response okauth response
     */
    public OAuthUser(OkAuthResponse response) {
        super(response.getData());
    }

    /**
     * Get the open platform to which the user belongs.
     *
     * @return the open platform to which the user belongs
     */
    public abstract OpenPlatform getOpenPlatform();

    /**
     * Get the identifier of the open platform to which the user belongs.
     *
     * @return the identifier of the open platform to which the user belongs
     */
    public String getOpenPlatformIdentifier() {
        return getOpenPlatform().getIdentifier();
    }

    /**
     * Get open id of the user.
     *
     * @return open id of the user
     */
    public abstract String getOpenId();

    /**
     * Get nickname of the user.
     *
     * @return nickname of the user
     */
    public abstract String getNickname();

    /**
     * Get avatar url of the user.
     *
     * @return avatar url of the user
     */
    public abstract String getAvatarUrl();

}
