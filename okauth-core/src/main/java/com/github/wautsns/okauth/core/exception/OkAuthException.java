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

/**
 *
 * @author wautsns
 */
public class OkAuthException extends Exception {

    /** serialVersionUID */
    private static final long serialVersionUID = -4029242788275281134L;

    public OkAuthException() {}

    public OkAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public OkAuthException(String message) {
        super(message);
    }

    public OkAuthException(Throwable cause) {
        super(cause);
    }

}
