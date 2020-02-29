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
package com.github.wautsns.okauth.core.client.kernel.model.dto;

import java.io.Serializable;

/**
 * OAuth redirect uri query.
 *
 * @since Feb 28, 2020
 * @author wautsns
 */
public class OAuthRedirectUriQuery implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 285186839588163899L;

    /** authorization code */
    private String code;
    /**
     * The application generates a random string and includes it in the request. It should then
     * check that the same value is returned after the user authorizes the app. This is used to
     * prevent CSRF attacks.
     */
    private String state;

    /**
     * Get authorization code.
     *
     * @return authorization code;
     */
    public String getCode() {
        return code;
    }

    /**
     * Set authorization code.
     *
     * @param code authorization code
     * @return self reference
     */
    public OAuthRedirectUriQuery setCode(String code) {
        this.code = code;
        return this;
    }

    /**
     * Get state.
     *
     * <p><strong>state</strong>: The application generates a random string and includes it in the
     * request. It should then check that the same value is returned after the user authorizes the
     * app. This is used to prevent CSRF attacks.
     *
     * @return state
     */
    public String getState() {
        return state;
    }

    /**
     * Set state.
     *
     * <p><strong>state</strong>: The application generates a random string and includes it in the
     * request. It should then check that the same value is returned after the user authorizes the
     * app. This is used to prevent CSRF attacks.
     *
     * @param state
     * @return self reference
     */
    public OAuthRedirectUriQuery setState(String state) {
        this.state = state;
        return this;
    }

}
