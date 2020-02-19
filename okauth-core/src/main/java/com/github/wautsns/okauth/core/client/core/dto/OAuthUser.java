/**
 * Copyright 2019 the original author or authors.
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
import com.github.wautsns.okauth.core.client.util.http.Response;

/**
 * Abstract oauth user.
 *
 * @since Feb 18, 2020
 * @author wautsns
 */
public abstract class OAuthUser extends OAuthData {

    /**
     * Construct an oauth user.
     *
     * @param response, require nonnull
     */
    public OAuthUser(Response response) {
        super(response.getData());
    }

    /** Get open platform. */
    public abstract OpenPlatform getOpenPlatform();

    /** Get open platform identifier. */
    public String getOpenPlatformIdentifier() {
        return getOpenPlatform().getIdentifier();
    }

    /** Get open id. */
    public abstract String getOpenId();

    /** Get nickname. */
    public abstract String getNickname();

    /** Get avatar url. */
    public abstract String getAvatarUrl();

}
