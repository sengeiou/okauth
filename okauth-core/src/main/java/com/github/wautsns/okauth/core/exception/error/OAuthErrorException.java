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
package com.github.wautsns.okauth.core.exception.error;

import com.github.wautsns.okauth.core.client.OpenPlatform;
import com.github.wautsns.okauth.core.exception.OAuthException;

/**
 * OAuth error exception.
 *
 * @since Feb 28, 2020
 * @author wautsns
 */
public class OAuthErrorException extends OAuthException {

    /** serialVersionUID */
    private static final long serialVersionUID = 775296624639159639L;

    private final OpenPlatform openPlatform;
    private final String error;
    private final String errorDescription;

    public OAuthErrorException(OpenPlatform openPlatform, String error, String errorDescription) {
        super(errorDescription);
        this.openPlatform = openPlatform;
        this.error = error;
        this.errorDescription = errorDescription;
    }

    public OpenPlatform getOpenPlatform() {
        return openPlatform;
    }

    public String getError() {
        return error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

}
