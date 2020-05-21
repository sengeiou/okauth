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
package com.github.wautsns.okauth.core.client.kernel.model;

import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;

/**
 * OAuth2 redirect uri query.
 *
 * @author wautsns
 * @since May 17, 2020
 */
public class OAuth2RedirectUriQuery extends DataMap {

    private static final long serialVersionUID = -1461949917732766274L;

    /**
     * Get code.
     *
     * @return code
     */
    public String getCode() {
        return getAsString("code");
    }

    /**
     * Get state.
     *
     * <p>The application generates a random string and includes it in the request. It should then check that the same
     * value is returned after the user authorizes the app. This is used to prevent CSRF attacks.
     *
     * @return state
     */
    public String getState() {
        return getAsString("state");
    }

}
