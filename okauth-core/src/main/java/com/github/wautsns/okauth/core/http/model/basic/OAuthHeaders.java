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
package com.github.wautsns.okauth.core.http.model.basic;

import com.github.wautsns.okauth.core.http.util.Parameters;

import java.util.Base64;

/**
 * OAuth headers.
 *
 * @author wautsns
 * @since Mar 03, 2020
 */
public class OAuthHeaders extends Parameters {

    private static final long serialVersionUID = -9137560246810546204L;

    /**
     * Add header `Accept` with value {@code "application/json"}
     *
     * @return self reference
     */
    public OAuthHeaders addAcceptWithValueJson() {
        return add("Accept", "application/json");
    }

    /**
     * Add header `Content-Type` with value {@code "application/json"}
     *
     * @return self reference
     */
    public OAuthHeaders addContentTypeWithValueJson() {
        return add("Content-Type", "application/json");
    }

    /**
     * Add header `Authorization`(Basic Auth).
     *
     * @param username username, require nonnull
     * @param password password, require nonnull
     * @return self reference
     */
    public OAuthHeaders addAuthorizationBasic(String username, String password) {
        byte[] src = (username + ':' + password).getBytes();
        String base64 = Base64.getEncoder().encodeToString(src);
        return addAuthorization("Basic", base64);
    }

    /**
     * Add header `Authorization`(Bearer Auth).
     *
     * @param token access token, require nonnull
     * @return self reference
     */
    public OAuthHeaders addAuthorizationBearer(String token) {
        return addAuthorization("Bearer", token);
    }

    /**
     * Add header `Authorization`.
     *
     * @param type auth type(eg. Basic, Bearer...), require nonnull
     * @param content content, require nonnull
     * @return self reference
     */
    public OAuthHeaders addAuthorization(String type, String content) {
        return add("Authorization", type + ' ' + content);
    }

    @Override
    public OAuthHeaders add(String name, String value) {
        return (OAuthHeaders) super.add(name, value);
    }

    /**
     * Create and return a copy of {@link OAuthHeaders}.
     *
     * @return copy of {@link OAuthHeaders}
     */
    @Override
    public OAuthHeaders copy() {
        OAuthHeaders copy = new OAuthHeaders();
        copy.getNameValuePairs().addAll(getNameValuePairs());
        return copy;
    }

}
