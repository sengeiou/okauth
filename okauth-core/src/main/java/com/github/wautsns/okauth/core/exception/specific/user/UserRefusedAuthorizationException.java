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
package com.github.wautsns.okauth.core.exception.specific.user;

import com.github.wautsns.okauth.core.client.kernel.openplatform.OpenPlatform;
import com.github.wautsns.okauth.core.exception.OAuth2Exception;
import lombok.Getter;

/**
 * User refused authorization exception.
 *
 * @author wautsns
 * @since May 16, 2020
 */
@Getter
public class UserRefusedAuthorizationException extends OAuth2Exception {

    private static final long serialVersionUID = 3286545599334883829L;

    /** Open platform. */
    private final OpenPlatform openPlatform;

    /**
     * Construct a UserRefusedAuthorizationException.
     *
     * @param openPlatform open platform
     */
    public UserRefusedAuthorizationException(OpenPlatform openPlatform) {
        super("User refused authorization.");
        this.openPlatform = openPlatform;
    }

}
