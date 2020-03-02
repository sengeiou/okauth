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
package com.github.wautsns.okauth.core.exception;

import com.github.wautsns.okauth.core.client.OpenPlatform;

/**
 * Unsupported open platform exception.
 *
 * @author wautsns
 * @since Feb 29, 2020
 */
public class UnsupportedOpenPlatformException extends OAuthException {

    private static final long serialVersionUID = 2205026007276072378L;

    public UnsupportedOpenPlatformException(OpenPlatform openPlatform) {
        this(openPlatform.name());
    }

    public UnsupportedOpenPlatformException(String openPlatformName) {
        super("Unsupported open platform: " + openPlatformName);
    }

}
