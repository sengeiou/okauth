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
package com.github.wautsns.okauth.core.http.model.basic;

import java.io.Serializable;
import java.util.function.BiConsumer;

/**
 * OAuth url.
 *
 * @author wautsns
 * @since Mar 03, 2020
 */
public class OAuthUrl implements Serializable {

    private static final long serialVersionUID = 8502059624102763590L;

    /** pure url */
    private final String pureUrl;
    /** query */
    private OAuthParameters query;

    /**
     * Construct oauth url.
     *
     * <p><strong>Anchor is not supported</strong>.
     *
     * @param url url string(can only contain pure url and query), require nonnull
     */
    public OAuthUrl(String url) {
        url = url.trim();
        int index = url.indexOf('?');
        if (index == -1) {
            this.pureUrl = url;
        } else if (index == url.length() - 1) {
            this.pureUrl = url.substring(0, index);
        } else {
            this.pureUrl = url.substring(0, index);
            this.query = new OAuthParameters();
            String[] queryItems = url.substring(index + 1).split("&");
            for (String queryItem : queryItems) {
                String[] tmp = queryItem.split("=", 2);
                this.query.add(tmp[0], tmp[1]);
            }
        }
    }

    /**
     * Get url query.
     *
     * @return url query.
     * @see #forEachQueryItem(BiConsumer)
     */
    public OAuthParameters getQuery() {
        if (query != null) { return query; }
        query = new OAuthParameters();
        return query;
    }

    /**
     * For each query item.
     *
     * @param action action for query item, require nonnull
     */
    public void forEachQueryItem(BiConsumer<String, String> action) {
        if (query != null) { query.forEach(action); }
    }

    /**
     * Returns a string representation of the url.
     *
     * @return url string
     */
    public String toString() {
        if (query == null) { return pureUrl; }
        StringBuilder url = new StringBuilder(pureUrl);
        url.append('?');
        query.forEach((name, value) -> url.append(name).append('=').append(value).append('&'));
        url.deleteCharAt(url.length() - 1);
        return url.toString();
    }

    /**
     * Create and return a copy of {@link OAuthUrl}.
     *
     * @return copy of {@link OAuthUrl}
     */
    public OAuthUrl copy() {
        return new OAuthUrl(this);
    }

    /**
     * Construct oauth url.
     *
     * @param prototype prototype, require nonnull
     * @see #copy()
     */
    protected OAuthUrl(OAuthUrl prototype) {
        this.pureUrl = prototype.pureUrl;
        this.query = prototype.query.copy();
    }

}
