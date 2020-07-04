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
package com.github.wautsns.okauth.core.exception.specific.token;

import com.github.wautsns.okauth.core.client.kernel.openplatform.OpenPlatform;
import com.github.wautsns.okauth.core.exception.OAuth2ErrorException;

/**
 * Invalid refresh token exception.
 *
 * @author wautsns
 * @since May 16, 2020
 */
public class InvalidRefreshTokenException extends OAuth2ErrorException {

    private static final long serialVersionUID = -1539348247553344950L;

    /**
     * Construct an InvalidRefreshTokenException.
     *
     * @param openPlatform open platform
     * @param errorCode error code
     * @param message error message
     */
    public InvalidRefreshTokenException(OpenPlatform openPlatform, String errorCode, String message) {
        super(openPlatform, errorCode, message);
    }

}
