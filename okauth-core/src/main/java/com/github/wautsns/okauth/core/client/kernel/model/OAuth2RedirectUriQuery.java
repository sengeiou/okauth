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

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * OAuth2 redirect uri query.
 *
 * @author wautsns
 * @since May 17, 2020
 */
@Data
@Accessors(chain = true)
public class OAuth2RedirectUriQuery implements Serializable {

    private static final long serialVersionUID = -1461949917732766274L;

    /** Authorize code. */
    private String code;
    /**
     * The application generates a random string and includes it in the request. It should then check that the same
     * value is returned after the user authorizes the app. This is used to prevent CSRF attacks.
     */
    private String state;

    /** Error. */
    private String error;
    /** Error description. */
    private String errorDescription;

    // #################### special setters #############################################

    /**
     * Set error_description.
     *
     * <p>Apply to: GitHub, ElemeShopIsv
     *
     * @param errorDescription error description
     * @return self reference
     */
    public OAuth2RedirectUriQuery setError_description(String errorDescription) {
        return setErrorDescription(errorDescription);
    }

}
