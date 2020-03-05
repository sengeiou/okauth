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

/**
 * OAuth error exception.
 *
 * <p>When open platform gives error message, the exception will be thrown.
 *
 * @author wautsns
 * @since Mar 04, 2020
 */
public class OAuthErrorException extends OAuthException {

    private static final long serialVersionUID = 5271329869899694038L;

    /** error code */
    private final String errorCode;

    /**
     * Construct oauth error exception.
     *
     * @param errorCode error code, require nonnull
     * @param message error message, require nonnull
     */
    public OAuthErrorException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
