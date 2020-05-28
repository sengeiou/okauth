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

import java.io.IOException;

/**
 * OAuth2 IO exception.
 *
 * @author wautsns
 * @since May 16, 2020
 */
public class OAuth2IOException extends OAuth2Exception {

    private static final long serialVersionUID = 4092905520240221126L;

    /**
     * Construct an {@code OAuth2Exception}.
     *
     * @param e io exception
     */
    public OAuth2IOException(IOException e) {
        super(e);
    }

}
