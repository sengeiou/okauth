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
package com.github.wautsns.okauth.core.client.kernel.http.model.basic;

import java.io.Serializable;

import java.util.Base64;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.BiConsumer;

/**
 * OAuth headers.
 *
 * @since Feb 29, 2020
 * @author wautsns
 */
public class OAuthHeaders implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1366613194926296553L;

    /** oauth headers data */
    private final LinkedList<String> data = new LinkedList<>();

    /**
     * Return {@code true} if the oauth headers have no data, otherwise {@code false}
     *
     * @return {@code true} if the oauth headers have no data, otherwise {@code false}
     */
    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * For each header.
     *
     * @param action action for headers, require nonnull
     */
    public void forEach(BiConsumer<String, String> action) {
        for (Iterator<String> i = data.iterator(); i.hasNext();) {
            action.accept(i.next(), i.next());
        }
    }

    /**
     * Create and return a copy of this oauth headers.
     *
     * @return a copy of this oauth headers
     */
    public OAuthHeaders copy() {
        OAuthHeaders copy = new OAuthHeaders();
        copy.data.addAll(this.data);
        return copy;
    }

    // -------------------- add -------------------------------------

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

    /**
     * Add header.
     *
     * <p>If the value is {@code null}, it is not added.
     *
     * @param name header name, require nonnull
     * @param value header value
     * @return self reference
     */
    public OAuthHeaders add(String name, String value) {
        if (value != null) {
            data.add(name);
            data.add(value);
        }
        return this;
    }

}
