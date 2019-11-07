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
package com.github.wautsns.okauth.core.exception;

import com.github.wautsns.okauth.core.client.core.OpenPlatform;

/**
 *
 * @author wautsns
 */
public class OkAuthException extends RuntimeException {

    /** serialVersionUID */
    private static final long serialVersionUID = -5934099746259324746L;

    private final OpenPlatform openPlatform;
    private final String error;

    public OkAuthException(OpenPlatform openPlatform, String error, String errorDescription) {
        super(errorDescription);
        this.openPlatform = openPlatform;
        this.error = error;
    }

    /** Get {@link #openPlatform}. */
    public OpenPlatform getOpenPlatform() {
        return openPlatform;
    }

    /** Get {@link #error}. */
    public String getError() {
        return error;
    }

}
