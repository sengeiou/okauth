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
package com.github.wautsns.okauth.core.client.kernel.http.model.dto;

import java.io.Serializable;

import java.util.Objects;

import com.github.wautsns.okauth.core.client.kernel.http.model.basic.OAuthHeaders;
import com.github.wautsns.okauth.core.client.kernel.http.model.basic.OAuthParams;
import com.github.wautsns.okauth.core.client.kernel.http.model.basic.OAuthUrl;
import com.github.wautsns.okauth.core.client.kernel.http.util.OAuthResponseInputStreamReader;

/**
 * OAuth request.
 *
 * @since Feb 28, 2020
 * @author wautsns
 */
public class OAuthRequest implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 311353763232467771L;

    public enum Method { GET, POST, PUT }

    /** method of the request */
    private final Method method;
    /** url of the request */
    private final OAuthUrl url;
    /** headers of the request */
    private OAuthHeaders headers;
    /** form of the request */
    private OAuthParams form;
    /** response input stream reader */
    private final OAuthResponseInputStreamReader responseInputStreamReader;

    /**
     * Get method of the request.
     *
     * @return method of the request
     */
    public Method getMethod() {
        return method;
    }

    /**
     * Get url of the request.
     *
     * @return url of the request
     */
    public OAuthUrl getUrl() {
        return url;
    }

    /**
     * Get url query of the request.
     *
     * @return url query of the request
     */
    public OAuthParams getQuery() {
        return url.getQuery();
    }

    /**
     * Get headers of the request.
     *
     * @return headers of the request
     */
    public OAuthHeaders getHeaders() {
        return (headers != null) ? headers : (headers = new OAuthHeaders());
    }

    /**
     * Get form of the request.
     *
     * @return form of the request
     */
    public OAuthParams getForm() {
        return (form != null) ? form : (form = new OAuthParams());
    }

    /**
     * Get params by method.
     *
     * @return query if method is GET, otherwise form.
     */
    public OAuthParams getParamsByMethod() {
        return (method == Method.GET) ? getQuery() : getForm();
    }

    /**
     * Get response input stream reader.
     *
     * @return response input stream reader
     */
    public OAuthResponseInputStreamReader getResponseInputStreamReader() {
        return responseInputStreamReader;
    }

    // -------------------- initialization --------------------------

    /**
     * Create and return a copy of the oauth request.
     *
     * @return a copy of the oauth request
     */
    public OAuthRequest copy() {
        OAuthRequest copy = new OAuthRequest(method, url, responseInputStreamReader);
        if (headers != null) { copy.headers = headers.copy(); }
        if (form != null) { copy.form = form.copy(); }
        return copy;
    }

    /**
     * Initialize an oauth request(GET) and the response input stream reader is
     * {@linkplain OAuthResponseInputStreamReader#JSON}).
     *
     * @param url request url, require nonnull
     * @return request
     */
    public static final OAuthRequest forGet(String url) {
        return init(Method.GET, url);
    }

    /**
     * Initialize an oauth request(POST) and the response input stream reader is
     * {@linkplain OAuthResponseInputStreamReader#JSON}).
     *
     * @param url request url, require nonnull
     * @return request
     */
    public static final OAuthRequest forPost(String url) {
        return init(Method.POST, url);
    }

    /**
     * Initialize an oauth request(the response input stream reader is
     * {@linkplain OAuthResponseInputStreamReader#JSON}).
     *
     * @param method request method, require nonnull
     * @param url request url, require nonnull
     * @return oauth request
     */
    public static final OAuthRequest init(Method method, String url) {
        return new OAuthRequest(method, new OAuthUrl(url), OAuthResponseInputStreamReader.JSON);
    }

    /**
     * Initialize an oauth request.
     *
     * @param method request method, require nonnull
     * @param url request url, require nonnull
     * @param responseInputStreamReader response input stream reader, require nonnull
     * @return oauth request
     */
    public static final OAuthRequest init(
            Method method, OAuthUrl url,
            OAuthResponseInputStreamReader responseInputStreamReader) {
        return new OAuthRequest(method, url, responseInputStreamReader);
    }

    /**
     * Construct an oauth request.
     *
     * @param method request method, require nonnull
     * @param url request url, require nonnull
     * @param responseInputStreamReader response input stream reader, require nonnull
     */
    private OAuthRequest(
            Method method, OAuthUrl url,
            OAuthResponseInputStreamReader responseInputStreamReader) {
        this.method = Objects.requireNonNull(method);
        this.url = Objects.requireNonNull(url);
        this.responseInputStreamReader = Objects.requireNonNull(responseInputStreamReader);
    }

}
