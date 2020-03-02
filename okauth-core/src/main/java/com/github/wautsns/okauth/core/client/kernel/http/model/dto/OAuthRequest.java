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
package com.github.wautsns.okauth.core.client.kernel.http.model.dto;

import com.github.wautsns.okauth.core.client.kernel.http.model.basic.OAuthHeaders;
import com.github.wautsns.okauth.core.client.kernel.http.model.basic.OAuthParams;
import com.github.wautsns.okauth.core.client.kernel.http.model.basic.OAuthUrl;
import com.github.wautsns.okauth.core.client.kernel.http.util.OAuthResponseInputStreamReader;
import java.io.Serializable;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * OAuth request.
 *
 * @author wautsns
 * @since Feb 28, 2020
 */
@Getter
@RequiredArgsConstructor
public class OAuthRequest implements Serializable {

    public enum Method {GET, POST, PUT}

    private static final long serialVersionUID = 311353763232467771L;

    /** method of the request */
    private final @NonNull Method method;
    /** url of the request */
    private final @NonNull OAuthUrl url;
    /** response input stream reader */
    private final @NonNull OAuthResponseInputStreamReader responseInputStreamReader;
    /** headers of the request */
    private OAuthHeaders headers;
    /** form of the request */
    private OAuthParams form;

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
        if (headers == null) { headers = new OAuthHeaders(); }
        return headers;
    }

    /**
     * Get form of the request.
     *
     * @return form of the request
     */
    public OAuthParams getForm() {
        if (form == null) { form = new OAuthParams(); }
        return form;
    }

    /**
     * Return query if the method is GET, otherwise form.
     *
     * @return query(GET) or form(POST).
     */
    public OAuthParams getParamsByMethod() {
        return (method == Method.GET) ? getQuery() : getForm();
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
     * Initialize oauth request(GET) and the response input stream reader is {@link
     * OAuthResponseInputStreamReader#JSON}).
     *
     * @param url request url, require nonnull
     * @return request
     */
    public static OAuthRequest forGet(String url) {
        return init(Method.GET, url);
    }

    /**
     * Initialize oauth request(POST) and the response input stream reader is {@link
     * OAuthResponseInputStreamReader#JSON}).
     *
     * @param url request url, require nonnull
     * @return request
     */
    public static OAuthRequest forPost(String url) {
        return init(Method.POST, url);
    }

    /**
     * Initialize oauth request(the response input stream reader is {@link OAuthResponseInputStreamReader#JSON}).
     *
     * @param method request method, require nonnull
     * @param url request url, require nonnull
     * @return oauth request
     */
    public static OAuthRequest init(Method method, String url) {
        return new OAuthRequest(method, new OAuthUrl(url), OAuthResponseInputStreamReader.JSON);
    }

    /**
     * Initialize oauth request.
     *
     * @param method request method, require nonnull
     * @param url request url, require nonnull
     * @param responseInputStreamReader response input stream reader, require nonnull
     * @return oauth request
     */
    public static OAuthRequest init(
        Method method, String url, OAuthResponseInputStreamReader responseInputStreamReader) {
        return new OAuthRequest(method, new OAuthUrl(url), responseInputStreamReader);
    }

    /**
     * Initialize oauth request.
     *
     * @param method request method, require nonnull
     * @param url request url, require nonnull
     * @param responseInputStreamReader response input stream reader, require nonnull
     * @return oauth request
     */
    public static OAuthRequest init(
        Method method, OAuthUrl url, OAuthResponseInputStreamReader responseInputStreamReader) {
        return new OAuthRequest(method, url, responseInputStreamReader);
    }

}
