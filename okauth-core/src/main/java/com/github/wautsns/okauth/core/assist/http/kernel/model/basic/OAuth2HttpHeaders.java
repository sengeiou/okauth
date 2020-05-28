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
package com.github.wautsns.okauth.core.assist.http.kernel.model.basic;

import java.util.Base64;
import java.util.Map;

/**
 * OAuth2 http headers.
 *
 * @author wautsns
 * @since May 16, 2020
 */
public class OAuth2HttpHeaders extends NameValuePairs {

    private static final long serialVersionUID = 5369216222593086487L;

    /**
     * Add `Accept` with value {@code "application/json"}
     *
     * @return self reference
     */
    public OAuth2HttpHeaders addAcceptWithValueJson() {
        return add("Accept", "application/json");
    }

    /**
     * Add `Content-Type` with value {@code "application/json"}
     *
     * @return self reference
     */
    public OAuth2HttpHeaders addContentTypeWithValueJson() {
        return add("Content-Type", "application/json");
    }

    /**
     * Add `Authorization`(Basic Auth).
     *
     * @param username username
     * @param password password
     * @return self reference
     */
    public OAuth2HttpHeaders addAuthorizationBasic(String username, String password) {
        byte[] src = (username + ':' + password).getBytes();
        String base64 = Base64.getEncoder().encodeToString(src);
        return addAuthorization("Basic", base64);
    }

    /**
     * Add header `Authorization`(Bearer Auth).
     *
     * @param token access token
     * @return self reference
     */
    public OAuth2HttpHeaders addAuthorizationBearer(String token) {
        return addAuthorization("Bearer", token);
    }

    /**
     * Add header `Authorization`.
     *
     * @param type auth type(eg. Basic, Bearer...)
     * @param content content
     * @return self reference
     */
    public OAuth2HttpHeaders addAuthorization(String type, String content) {
        return add("Authorization", type + ' ' + content);
    }

    // #################### common ######################################################

    @Override
    public OAuth2HttpHeaders add(String name, String value) {
        return (OAuth2HttpHeaders) super.add(name, value);
    }

    @Override
    public OAuth2HttpHeaders add(String name, String value, String defaultValue) {
        return (OAuth2HttpHeaders) super.add(name, value, defaultValue);
    }

    @Override
    public OAuth2HttpHeaders addAll(Map<String, String> nameValuePairs) {
        return (OAuth2HttpHeaders) super.addAll(nameValuePairs);
    }

    @Override
    public OAuth2HttpHeaders set(String name, String value) {
        return (OAuth2HttpHeaders) super.set(name, value);
    }

    @Override
    public OAuth2HttpHeaders copy() {
        OAuth2HttpHeaders copy = new OAuth2HttpHeaders();
        copy.getOrigin().addAll(this.getOrigin());
        return copy;
    }

}
