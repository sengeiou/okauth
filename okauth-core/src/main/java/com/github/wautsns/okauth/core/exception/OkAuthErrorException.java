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
package com.github.wautsns.okauth.core.exception;

import com.github.wautsns.okauth.core.client.core.OpenPlatform;

/**
 * OkAuth error exception(eg. code is invalid...).
 *
 * @since Feb 27, 2020
 * @author wautsns
 */
public class OkAuthErrorException extends OkAuthException {

    /** serialVersionUID */
    private static final long serialVersionUID = -5934099746259324746L;

    /** open platform of the exception */
    private final OpenPlatform openPlatform;
    /** error info */
    private final String error;
    /** error description */
    private final String errorDescription;

    public OkAuthErrorException(OpenPlatform openPlatform, String error, String errorDescription) {
        super(error + ':' + errorDescription);
        this.openPlatform = openPlatform;
        this.error = error;
        this.errorDescription = errorDescription;
    }

    /**
     * Get open platform of the exception.
     *
     * @return open platform
     */
    public OpenPlatform getOpenPlatform() {
        return openPlatform;
    }

    /**
     * Get error info.
     *
     * @return error info
     * @see #getMessage()
     */
    public String getError() {
        return error;
    }

    /**
     * Get error description.
     *
     * @return error description
     */
    public String getErrorDescription() {
        return errorDescription;
    }

}
