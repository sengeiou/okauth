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
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.OAuth2UrlEncodedEntries;
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

    /** request method */
    private final String method;
    /** request url */
    private final OAuth2Url url;
    /** request headers */
    private OAuth2HttpHeaders headers;
    /** request form */
    private OAuth2UrlEncodedEntries form;

    public OAuth2HttpHeaders getHeaders() {
        if (headers != null) { return headers; }
        headers = new OAuth2HttpHeaders();
        return headers;
    }

    /**
     * For each header.
     *
     * @param action action for header
     */
    public void forEachHeader(BiConsumer<String, String> action) {
        if (headers != null) { headers.forEach(action); }
    }

    public OAuth2UrlEncodedEntries getForm() {
        if (form != null) { return form; }
        form = new OAuth2UrlEncodedEntries();
        return form;
    }

    /**
     * For each form item.
     *
     * @param action action for form item
     */
    public void forEachFormItem(BiConsumer<String, String> action) {
        if (form != null) { form.forEach(action); }
    }

    // #################### initialization ##############################################

    /**
     * Create and return a copy of this object.
     *
     * @return a copy of this object
     */
    public OAuth2HttpRequest copy() {
        OAuth2HttpRequest copy = new OAuth2HttpRequest(method, url.copy());
        copy.headers = (headers == null) ? null : headers.copy();
        copy.form = (form == null) ? null : form.copy();
        return copy;
    }

    /**
     * Initialize request(GET).
     *
     * @param url url
     * @return request
     */
    public static OAuth2HttpRequest initGet(String url) {
        return init("GET", url);
    }

    /**
     * Initialize request(POST).
     *
     * @param url url
     * @return request
     */
    public static OAuth2HttpRequest initPost(String url) {
        return init("POST", url);
    }

    /**
     * Initialize request.
     *
     * @param method method
     * @param url url
     * @return request
     */
    public static OAuth2HttpRequest init(String method, String url) {
        return new OAuth2HttpRequest(method, new OAuth2Url(url));
    }

}
