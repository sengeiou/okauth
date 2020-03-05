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
package com.github.wautsns.okauth.core.http.model;

import com.github.wautsns.okauth.core.http.model.basic.OAuthHeaders;
import com.github.wautsns.okauth.core.http.model.basic.OAuthParameters;
import com.github.wautsns.okauth.core.http.model.basic.OAuthUrl;
import com.github.wautsns.okauth.core.http.util.ReadInputStreamAsDataMap;

import java.io.Serializable;
import java.util.function.BiConsumer;

/**
 * OAuth request.
 *
 * @author wautsns
 * @since Mar 03, 2020
 */
public class OAuthRequest implements Serializable {

    private static final long serialVersionUID = -2224293407973624448L;

    /** http method enumeration */
    public enum Method {GET, POST, PUT, PATCH, DELETE}

    /** oauth request method */
    private final Method method;
    /** oauth request url */
    private final OAuthUrl url;
    /** oauth request headers */
    private OAuthHeaders headers;
    /** oauth request form */
    private OAuthParameters form;
    /** oauth response input stream */
    private final ReadInputStreamAsDataMap responseDataReader;

    /**
     * Get oauth request method.
     *
     * @return oauth request method
     */
    public Method getMethod() {
        return method;
    }

    /**
     * Get oauth request url.
     *
     * @return oauth request url
     */
    public OAuthUrl getUrl() {
        return url;
    }

    /**
     * Get oauth url query.
     *
     * @return oauth url query
     */
    public OAuthParameters getUrlQuery() {
        return url.getQuery();
    }

    /**
     * Get oauth request headers.
     *
     * @return oauth request headers
     * @see #forEachHeader(BiConsumer)
     */
    public OAuthHeaders getHeaders() {
        if (headers != null) { return headers; }
        headers = new OAuthHeaders();
        return headers;
    }

    /**
     * For each header.
     *
     * @param consumer header consumer, require nonnull
     */
    public void forEachHeader(BiConsumer<String, String> consumer) {
        if (headers != null) { headers.forEach(consumer); }
    }

    /**
     * Get oauth request form.
     *
     * @return oauth request form
     * @see #forEachFormItem(BiConsumer)
     */
    public OAuthParameters getForm() {
        if (form != null) { return form; }
        form = new OAuthParameters();
        return form;
    }

    /**
     * For each form item.
     *
     * @param consumer form item(name value) consumer, require nonnull
     */
    public void forEachFormItem(BiConsumer<String, String> consumer) {
        if (form != null) { form.forEach(consumer); }
    }

    /**
     * Get response data reader.
     *
     * @return response data reader
     */
    public ReadInputStreamAsDataMap getResponseDataReader() {
        return responseDataReader;
    }

    /**
     * Create and return a copy of {@link OAuthRequest}.
     *
     * @return copy of {@link OAuthRequest}
     */
    public OAuthRequest copy() {
        OAuthRequest copy = new OAuthRequest(method, url.copy(), responseDataReader);
        if (headers != null) { copy.headers = headers.copy(); }
        if (form != null) { copy.form = form.copy(); }
        return copy;
    }

    // -------------------- initialization --------------------------

    /**
     * Initialize oauth request(GET).
     *
     * @param url oauth request url, require nonnull
     * @return oauth request
     */
    public static OAuthRequest forGet(String url) {
        return init(Method.GET, url);
    }

    /**
     * Initialize oauth request(POST).
     *
     * @param url oauth request url, require nonnull
     * @return oauth request
     */
    public static OAuthRequest forPost(String url) {
        return init(Method.POST, url);
    }

    /**
     * Initialize oauth request.
     *
     * @param method oauth request method, require nonnull
     * @param url oauth request url, require nonnull
     * @return oauth request
     */
    public static OAuthRequest init(Method method, String url) {
        return init(method, url, ReadInputStreamAsDataMap.JSON);
    }

    /**
     * Initialize oauth request.
     *
     * @param method oauth request method, require nonnull
     * @param url oauth request url, require nonnull
     * @param responseDataReader response data reader, require nonnull
     * @return oauth request
     */
    public static OAuthRequest init(Method method, String url, ReadInputStreamAsDataMap responseDataReader) {
        return new OAuthRequest(method, new OAuthUrl(url), responseDataReader);
    }

    /**
     * Construct oauth request.
     *
     * @param method oauth request method, require nonnull
     * @param url oauth request url, require nonnull
     * @param responseDataReader response data reader, require nonnull
     */
    private OAuthRequest(Method method, OAuthUrl url, ReadInputStreamAsDataMap responseDataReader) {
        this.method = method;
        this.url = url;
        this.responseDataReader = responseDataReader;
    }

}
