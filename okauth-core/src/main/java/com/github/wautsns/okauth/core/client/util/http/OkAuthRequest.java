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
package com.github.wautsns.okauth.core.client.util.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import java.util.Base64;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

import com.github.wautsns.okauth.core.client.util.http.OkAuthResponse.MapDateInputStreamReader;

/**
 * Request for open platform interaction.
 *
 * @since Feb 27, 2020
 * @author wautsns
 * @see OkAuthRequester
 */
public class OkAuthRequest {

    /** request methods */
    public enum Method { GET, POST }

    /** request method */
    private Method method;
    /** request url */
    private OkAuthUrl url;
    /** request headers(list? -> name, value, name, value....) */
    private List<String> headers = Collections.emptyList();
    /** request form(items have been url encoded, list? -> name, value, name, value...) */
    private List<String> form = Collections.emptyList();
    /** response input stream reader */
    private MapDateInputStreamReader responseInputStreamReader;

    /**
     * Construct an okauth request.
     *
     * @param method request method, require nonnull
     * @param url request url, require nonnull
     * @param responseInputStreamReader response input stream reader, require nonnull
     */
    private OkAuthRequest(
            Method method, String url, MapDateInputStreamReader responseInputStreamReader) {
        this.method = Objects.requireNonNull(method);
        this.url = new OkAuthUrl(url);
        this.responseInputStreamReader = Objects.requireNonNull(responseInputStreamReader);
    }

    /**
     * Construct(Copy) an okauth request.
     *
     * @param prototype request prototype, require nonnull
     */
    private OkAuthRequest(OkAuthRequest prototype) {
        method = prototype.method;
        url = prototype.url.mutate();
        if (!prototype.headers.isEmpty()) { headers = new LinkedList<>(prototype.headers); }
        if (!prototype.form.isEmpty()) { form = new LinkedList<>(prototype.form); }
        responseInputStreamReader = prototype.responseInputStreamReader;
    }

    /**
     * Get method.
     *
     * @return method
     */
    public Method getMethod() {
        return method;
    }

    /**
     * Get url.
     *
     * @return url
     */
    public String getUrl() {
        return url.toString();
    }

    /**
     * Get response input stream reader.
     *
     * @return response input stream reader
     */
    public MapDateInputStreamReader getResponseInputStreamReader() {
        return responseInputStreamReader;
    }

    /**
     * Mutate a new identical request.
     *
     * @return a new identical request
     */
    public OkAuthRequest mutate() {
        return new OkAuthRequest(this);
    }

    // -------------------- header ----------------------------------------

    /**
     * Add header "Accept" with value "application/json"
     *
     * @return self reference
     */
    public OkAuthRequest addHeaderAcceptJson() {
        return addHeader("Accept", "application/json");
    }

    /**
     * Add header "Authorization"(Basic Auth).
     *
     * @param username username, require nonnull
     * @param password password, require nonnull
     * @return self reference
     */
    public OkAuthRequest addHeaderAuthorizationBasic(String username, String password) {
        byte[] src = (username + ':' + password).getBytes();
        String base64 = Base64.getEncoder().encodeToString(src);
        return addHeaderAuthorization(base64);
    }

    /**
     * Add header "Authorization"(Bearer Auth).
     *
     * @param token access token, require nonnull
     * @return self reference
     */
    public OkAuthRequest addHeaderAuthorizationBearer(String token) {
        return addHeaderAuthorization("Bearer " + token);
    }

    /**
     * Add header "Authorization".
     *
     * <p>If the value is {@code null}, it is not added.
     *
     * @param authorization header value, require nonnull
     * @return self reference
     */
    public OkAuthRequest addHeaderAuthorization(String authorization) {
        return addHeader("Authorization", authorization);
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
    public OkAuthRequest addHeader(String name, String value) {
        if (value == null) { return this; }
        if (headers.isEmpty()) { headers = new LinkedList<>(); }
        headers.add(name);
        headers.add(value);
        return this;
    }

    /**
     * For each header.
     *
     * @param action header name-value consumer
     */
    public void forEachHeader(BiConsumer<String, String> action) {
        for (Iterator<String> i = headers.iterator(); i.hasNext();) {
            action.accept(i.next(), i.next());
        }
    }

    // -------------------- query param ----------------------------------------

    /**
     * Add query param.
     *
     * <p>If the value is {@code null}, it is not added.
     *
     * @param name query param name, require nonnull
     * @param value query param value(will be url encoded)
     * @return self reference
     */
    public OkAuthRequest addQueryParam(String name, String value) {
        url.addQueryParam(name, value);
        return this;
    }

    /**
     * Add url encoded query param.
     *
     * <p>If the value is {@code null}, it is not added.
     *
     * @param name query param name, require nonnull
     * @param value url encoded query param value
     * @return self reference
     */
    public OkAuthRequest addUrlEncodedQueryParam(String name, String value) {
        url.addUrlEncodedQueryParam(name, value);
        return this;
    }

    // -------------------- form item ----------------------------------------

    /**
     * Add form item.
     *
     * <p>If the value is {@code null}, it is not added.
     *
     * @param name form item name, require nonnull
     * @param value form item value(will be url encoded)
     * @return self reference
     */
    public OkAuthRequest addFormItem(String name, String value) {
        if (value == null) { return this; }
        try {
            return addUrlEncodedFormItem(name, URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Add url encoded form item.
     *
     * <p>If the value is {@code null}, it is not added.
     *
     * @param name form item name, require nonnull
     * @param value form item value
     * @return self reference
     */
    public OkAuthRequest addUrlEncodedFormItem(String name, String value) {
        if (value == null) { return this; }
        if (form.isEmpty()) { form = new LinkedList<>(); }
        form.add(name);
        form.add(value);
        return this;
    }

    /**
     * For each url encoded form item.
     *
     * @param action form item name-value consumer
     */
    public void forEachUrlEncodedFormItem(BiConsumer<String, String> action) {
        for (Iterator<String> i = form.iterator(); i.hasNext();) {
            action.accept(i.next(), i.next());
        }
    }

    // -------------------- initialization ----------------------------------------

    /**
     * Initialize a GET request and the response input stream reader is
     * {@linkplain OkAuthResponse#INPUT_STREAM_READER_JSON JSON}.
     *
     * @param url request url, require nonnull
     * @return request
     */
    public static OkAuthRequest forGet(String url) {
        return init(Method.GET, url);
    }

    /**
     * Initialize a POST request and the response input stream reader is
     * {@linkplain OkAuthResponse#INPUT_STREAM_READER_JSON JSON}.
     *
     * @param url request url, require nonnull
     * @return request
     */
    public static OkAuthRequest forPost(String url) {
        return init(Method.POST, url);
    }

    /**
     * Initialize a request and the response input stream reader is
     * {@linkplain OkAuthResponse#INPUT_STREAM_READER_JSON JSON}.
     *
     * @param method request method, require nonnull
     * @param url request url, require nonnull
     * @return request
     */
    public static OkAuthRequest init(Method method, String url) {
        return new OkAuthRequest(method, url, OkAuthResponse.INPUT_STREAM_READER_JSON);
    }

    /**
     * Initialize a request.
     *
     * @param method request method, require nonnull
     * @param url request url, require nonnull
     * @param reader response input stream reader, require nonnull
     * @return request
     */
    public static OkAuthRequest init(Method method, String url, MapDateInputStreamReader reader) {
        return new OkAuthRequest(method, url, reader);
    }

}
