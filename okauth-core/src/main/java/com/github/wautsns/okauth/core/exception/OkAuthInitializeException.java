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

/**
 * OkauthInitializeException is the superclass of those okauth initialization exceptions.
 *
 * @since Feb 27, 2020
 * @author wautsns
 */
public class OkAuthInitializeException extends RuntimeException {

    /** serialVersionUID */
    private static final long serialVersionUID = -54863435286239355L;

    public OkAuthInitializeException(String message) {
        super(message);
    }

    public OkAuthInitializeException(Throwable cause) {
        super(cause);
    }

    public OkAuthInitializeException(String message, Throwable cause) {
        super(message, cause);
    }

}
