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

import java.util.Map;

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
     * <p>Copy the method and url according to the given request.
     *
     * @param request request prototype, require nonnull
     */
    protected Request(Request request) {
        this.method = request.method;
        this.url = request.url.mutate();
    }

    /**
     * Add header "Accept" with value "application/json"
     *
     * @return self reference
     */
    public Request acceptJson() {
        return addHeader("Accept", "application/json");
    }

    /**
     * Add header.
     *
     * @param name header name, require nonnull
     * @param value header value, require nonnull
     * @return self reference
     */
    public abstract Request addHeader(String name, String value);

    /** Get {@link #url}. */
    public String getUrl() {
        return url.toString();
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
     * @param name form item name, require nonnull
     * @param value form item value, require nonnull
     * @return self reference
     */
    public abstract Request addFormItem(String name, String value);

    /**
     * Add form items.
     *
     * @param form form, require nonnull
     * @return self reference
     */
    public Request addForm(Map<String, String> form) {
        form.forEach(this::addFormItem);
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
            return get(reader);
        } else if (method == Method.POST) {
            return post(reader);
        } else {
            throw new RuntimeException("unreachable");
        }
    }

    /**
     * GET request.
     *
     * @param reader response input stream reader, require nonnull
     * @return response
     * @throws OkAuthIOException if an IO exception occurs
     */
    protected abstract Response get(ResponseInputStreamReader reader) throws OkAuthIOException;

    /**
     * POST request.
     *
     * @param reader response input stream reader, require nonnull
     * @return response
     * @throws OkAuthIOException if an IO exception occurs
     */
    protected abstract Response post(ResponseInputStreamReader reader) throws OkAuthIOException;

}
