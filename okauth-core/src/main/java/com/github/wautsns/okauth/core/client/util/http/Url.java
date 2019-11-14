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

/**
 * Url.
 *
 * @author wautsns
 */
public class Url {

    /** url */
    private String url;
    /** if the url has query */
    private boolean hasQuery;

    /**
     * Construct a url.
     *
     * @param url url, require nonnull
     */
    public Url(String url) {
        this.url = url;
        this.hasQuery = (url.lastIndexOf('?') >= 0);
    }

    /**
     * Construct a url.
     *
     * @param url url prototype, require nonnull
     */
    private Url(Url url) {
        this.url = url.url;
        this.hasQuery = url.hasQuery;
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
    public Url addQueryParam(String name, String value) {
        if (value == null) { return this; }
        try {
            return addUrlEncodedQueryParam(name, URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("unreachable");
        }
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
    public Url addUrlEncodedQueryParam(String name, String value) {
        if (value == null) { return this; }
        if (hasQuery) {
            url += '&';
        } else {
            url += '?';
            hasQuery = true;
        }
        url += name + '=' + value;
        return this;
    }

    /**
     * Mutate into a new identical url prototype.
     *
     * @return a new prototype
     */
    public Url mutate() {
        return new Url(this);
    }

    @Override
    public String toString() {
        return url;
    }

}
