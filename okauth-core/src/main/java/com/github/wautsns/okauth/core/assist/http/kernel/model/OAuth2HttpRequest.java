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
package com.github.wautsns.okauth.core.assist.http.kernel.model;

import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.OAuth2HttpHeaders;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.OAuth2Url;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.entity.OAuth2HttpEntity;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.entity.builtin.OAuth2HttpFormUrlEncodedEntity;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.entity.builtin.OAuth2HttpJsonEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.function.BiConsumer;

/**
 * OAuth2 http request.
 *
 * @author wautsns
 * @since May 16, 2020
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuth2HttpRequest implements Serializable {

    private static final long serialVersionUID = -5239420641606727328L;

    /** Request method. */
    public enum Method {
        GET, POST, PUT, PATCH, DELETE,
        OPTIONS, HEAD, TRACE
    }

    /** Request method. */
    private final Method method;
    /** Request url. */
    private final OAuth2Url url;
    /** Request headers. */
    private OAuth2HttpHeaders headers;
    /** Request entity. */
    private OAuth2HttpEntity entity;

    /**
     * Get headers.
     *
     * @return headers
     */
    public OAuth2HttpHeaders getHeaders() {
        if (headers != null) { return headers; }
        headers = new OAuth2HttpHeaders();
        return headers;
    }

    /**
     * Iterate over each header.
     *
     * @param action the action to be performed for each header
     */
    public void forEachHeader(BiConsumer<String, String> action) {
        if (headers != null) { headers.forEach(action); }
    }

    /**
     * Get entity: form url encoded.
     *
     * @return entity: form url encoded
     */
    public OAuth2HttpFormUrlEncodedEntity getEntityFormUrlEncoded() {
        if (entity == null) { entity = new OAuth2HttpFormUrlEncodedEntity(); }
        return (OAuth2HttpFormUrlEncodedEntity) entity;
    }

    /**
     * Get entity: json.
     *
     * @return entity: json
     */
    public OAuth2HttpJsonEntity getEntityJson() {
        if (entity == null) { entity = new OAuth2HttpJsonEntity(); }
        return (OAuth2HttpJsonEntity) entity;
    }

    /**
     * Create and return a copy of this object.
     *
     * @return a copy of this object
     */
    public OAuth2HttpRequest copy() {
        OAuth2HttpRequest copy = new OAuth2HttpRequest(method, url.copy());
        copy.headers = (this.headers == null) ? null : this.headers.copy();
        copy.entity = (this.entity == null) ? null : this.entity.copy();
        return copy;
    }

    // #################### initialization ##############################################

    /**
     * Initialize request(GET).
     *
     * @param url url
     * @return request(GET)
     */
    public static OAuth2HttpRequest initGet(String url) {
        return init(Method.GET, url);
    }

    /**
     * Initialize request(POST).
     *
     * @param url url
     * @return request(POST)
     */
    public static OAuth2HttpRequest initPost(String url) {
        return init(Method.POST, url);
    }

    /**
     * Initialize request(PUT).
     *
     * @param url url
     * @return request(PUT)
     */
    public static OAuth2HttpRequest initPut(String url) {
        return init(Method.PUT, url);
    }

    /**
     * Initialize request(PATCH).
     *
     * @param url url
     * @return request(PATCH)
     */
    public static OAuth2HttpRequest initPatch(String url) {
        return init(Method.PATCH, url);
    }

    /**
     * Initialize request(DELETE).
     *
     * @param url url
     * @return request(DELETE)
     */
    public static OAuth2HttpRequest initDelete(String url) {
        return init(Method.DELETE, url);
    }

    /**
     * Initialize request.
     *
     * @param method method
     * @param url url
     * @return request
     */
    public static OAuth2HttpRequest init(Method method, String url) {
        return new OAuth2HttpRequest(method, new OAuth2Url(url));
    }

}
