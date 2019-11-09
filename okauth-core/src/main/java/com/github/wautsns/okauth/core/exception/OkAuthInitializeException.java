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
 * {@code OkauthInitializeException} is the superclass of those okauth initialization exceptions.
 *
 * @author wautsns
 */
public class OkAuthInitializeException extends RuntimeException {

    /** serialVersionUID */
    private static final long serialVersionUID = -54863435286239355L;

    /**
     * Constructs an okauth initialize exception.
     *
     * @param message the detail message. The detail message is saved for
     *        later retrieval by the {@link #getMessage()} method.
     */
    public OkAuthInitializeException(String message) {
        super(message);
    }

    /**
     * Constructs an okauth initialize exception.
     *
     * @param cause the cause (which is saved for later retrieval by the
     *        {@link #getCause()} method). (A <tt>null</tt> value is
     *        permitted, and indicates that the cause is nonexistent or
     *        unknown.)
     */
    public OkAuthInitializeException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs an okauth initialize exception.
     *
     * @param message message the detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method).
     * @param cause the cause (which is saved for later retrieval by the
     *        {@link #getCause()} method). (A <tt>null</tt> value is
     *        permitted, and indicates that the cause is nonexistent or
     *        unknown.)
     */
    public OkAuthInitializeException(String message, Throwable cause) {
        super(message, cause);
    }

}
