/**
 * Copyright 2019 the original author or authors.
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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.BiConsumer;

import com.github.wautsns.okauth.core.exception.OkAuthIOException;

/**
 * Abstract request for open platform interaction.
 *
 * @author wautsns
 * @see Requester
 */
public abstract class Request {

    /** request methods */
    public enum Method { GET, POST }

    /** request method */
    private Method method;
    /** request url */
    private Url url;
    /** request headers */
    private LinkedList<String> headers;
    /** request form(items are url encoded) */
    private LinkedList<String> form;

    /**
     * Construct a request.
     *
     * @param method request method, require nonnull
     * @param url request url, require nonnull
     */
    public Request(Method method, String url) {
        this.method = method;
        this.url = new Url(url);
    }

    /**
     * Construct a request.
     *
     * <p>Copy the method, url, headers and form according to the given request.
     *
     * @param request request prototype, require nonnull
     */
    protected Request(Request request) {
        this.method = request.method;
        this.url = request.url.mutate();
        if (request.headers != null) { this.headers = new LinkedList<>(request.headers); }
        if (request.form != null) { this.form = new LinkedList<>(request.form); }
    }

    /** Get {@link #url}. */
    public String getUrl() {
        return url.toString();
    }

    /**
     * Add header "Accept" with value "application/json"
     *
     * @return self reference
     */
    public Request addHeaderAcceptJson() {
        return addHeader("Accept", "application/json");
    }

    /**
     * Add header "Authorization".
     *
     * <p>If the value is {@code null}, it is not added.
     *
     * @param authorization header value
     * @return
     */
    public Request addHeaderAuthorization(String authorization) {
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
    public Request addHeader(String name, String value) {
        if (value == null) { return this; }
        if (headers == null) { headers = new LinkedList<>(); }
        headers.addLast(name);
        headers.addLast(value);
        return this;
    }

    /**
     * Add query param.
     *
     * <p>If the value is {@code null}, it is not added.
     *
     * @param name query param name, require nonnull
     * @param value query param value(will be url encoded if not null)
     * @return self reference
     */
    public Request addQueryParam(String name, String value) {
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
    public Request addUrlEncodedQueryParam(String name, String value) {
        url.addUrlEncodedQueryParam(name, value);
        return this;
    }

    /**
     * Add form item.
     *
     * <p>If the value is {@code null}, it is not added.
     *
     * @param name form item name, require nonnull
     * @param value form item value(will be url encoded if not null)
     * @return self reference
     */
    public Request addFormItem(String name, String value) {
        if (value == null) { return this; }
        try {
            return addUrlEncodedFormItem(name, URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("unreachable");
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
    public Request addUrlEncodedFormItem(String name, String value) {
        if (value == null) { return this; }
        if (form == null) { form = new LinkedList<>(); }
        form.addLast(name);
        form.addLast(value);
        return this;
    }

    /**
     * Mutate into a new identical request.
     *
     * @return a new request
     */
    public abstract Request mutate();

    /**
     * Exchange with json response reader.
     *
     * @return response
     * @throws OkAuthIOException if an IO exception occurs
     */
    public Response exchangeForJson() throws OkAuthIOException {
        return exchange(BuiltInResponseInputStreamReader.JSON);
    }

    /**
     * Exchange.
     *
     * @param reader response input stream reader, require nonnull
     * @return response
     * @throws OkAuthIOException if an IO exception occurs
     */
    public Response exchange(ResponseInputStreamReader reader) throws OkAuthIOException {
        if (method == Method.GET) {
            return doGet(reader);
        } else if (method == Method.POST) {
            return doPost(reader);
        } else {
            throw new RuntimeException("unreachable");
        }
    }

    /**
     * For each header.
     *
     * @param consumer header name-value consumer
     */
    protected void forEachHeader(BiConsumer<String, String> consumer) {
        if (headers == null) { return; }
        for (Iterator<String> i = headers.iterator(); i.hasNext();) {
            consumer.accept(i.next(), i.next());
        }
    }

    /**
     * For each url encoded form item.
     *
     * @param consumer form item name-value consumer
     */
    protected void forEachFormItem(BiConsumer<String, String> consumer) {
        if (form == null) { return; }
        for (Iterator<String> i = form.iterator(); i.hasNext();) {
            consumer.accept(i.next(), i.next());
        }
    }

    /**
     * Do GET request.
     *
     * <p>*** Remember to call {@link #forEachHeader(BiConsumer)}
     *
     * @param reader response input stream reader, require nonnull
     * @return response
     * @throws OkAuthIOException if an IO exception occurs
     */
    protected abstract Response doGet(ResponseInputStreamReader reader) throws OkAuthIOException;

    /**
     * Do POST request.
     *
     * <p>*** Remember to call {@link #forEachHeader(BiConsumer)} and
     * {@link #forEachFormItem(BiConsumer)}.
     *
     * @param reader response input stream reader, require nonnull
     * @return response
     * @throws OkAuthIOException if an IO exception occurs
     */
    protected abstract Response doPost(ResponseInputStreamReader reader) throws OkAuthIOException;

}
