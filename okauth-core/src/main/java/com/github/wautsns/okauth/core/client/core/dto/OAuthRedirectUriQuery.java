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
package com.github.wautsns.okauth.core.client.core.dto;

/**
 *
 * @author wautsns
 */
public class OAuthRedirectUriQuery {

    private String code;
    private String state;

    /** Get {@link #code}. */
    public String getCode() {
        return code;
    }

    /** Set {@link #code}. */
    public OAuthRedirectUriQuery setCode(String code) {
        this.code = code;
        return this;
    }

    /** Get {@link #state}. */
    public String getState() {
        return state;
    }

    /** Set {@link #state}. */
    public OAuthRedirectUriQuery setState(String state) {
        this.state = state;
        return this;
    }

}