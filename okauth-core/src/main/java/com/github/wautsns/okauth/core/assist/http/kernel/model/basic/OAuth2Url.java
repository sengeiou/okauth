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
package com.github.wautsns.okauth.core.assist.http.kernel.model.basic;

import java.io.Serializable;
import java.util.function.BiConsumer;

/**
 * OAuth2 url.
 *
 * @author wautsns
 * @since May 16, 2020
 */
public class OAuth2Url implements Serializable {

    private static final long serialVersionUID = 8502059624102763590L;

    /** Pure url(without query, anchor...). */
    private final String pureUrl;
    /** Url query. */
    private OAuth2UrlEncodedEntries query;
    /** Url anchor. */
    private String anchor;

    /**
     * Construct an oauth2 url.
     *
     * @param url url string
     */
    public OAuth2Url(String url) {
        url = url.trim();
        int indexOfAnchor = url.indexOf('#');
        if (indexOfAnchor >= 0) {
            anchor = url.substring(indexOfAnchor + 1);
            url = url.substring(0, indexOfAnchor);
        }
        int indexOfQuery = url.indexOf('?');
        if (indexOfQuery == -1) {
            this.pureUrl = url;
        } else if (indexOfQuery == url.length() - 1) {
            this.pureUrl = url.substring(0, indexOfQuery);
        } else {
            this.pureUrl = url.substring(0, indexOfQuery);
            this.query = new OAuth2UrlEncodedEntries();
            String[] queryItems = url.substring(indexOfQuery + 1).split("&");
            for (String queryItem : queryItems) {
                String[] nameAndUrlEncodedValue = queryItem.split("=", 2);
                this.query.addUrlEncoded(nameAndUrlEncodedValue[0], nameAndUrlEncodedValue[1]);
            }
        }
    }

    /**
     * Get pure url(without query, anchor...).
     *
     * @return pure url
     */
    public String getPureUrl() {
        return pureUrl;
    }

    /**
     * Get url query.
     *
     * @return url query.
     */
    public OAuth2UrlEncodedEntries getQuery() {
        if (query != null) { return query; }
        query = new OAuth2UrlEncodedEntries();
        return query;
    }

    /**
     * Iterate over each query item.
     *
     * @param action the action to be performed for each query item
     */
    public void forEachQueryItem(BiConsumer<String, String> action) {
        if (query != null) { query.forEach(action); }
    }

    /**
     * Get url anchor.
     *
     * @return url anchor
     */
    public String getAnchor() {
        return anchor;
    }

    /**
     * Set url anchor.
     *
     * @return self reference
     */
    public OAuth2Url setAnchor(String anchor) {
        this.anchor = anchor;
        return this;
    }

    @Override
    public String toString() {
        if (query == null) { return (anchor == null) ? pureUrl : (pureUrl + '#' + anchor); }
        StringBuilder url = new StringBuilder(pureUrl);
        url.append('?');
        query.forEach((name, value) -> url.append(name).append('=').append(value).append('&'));
        url.deleteCharAt(url.length() - 1);
        if (anchor != null) { url.append('#').append(anchor); }
        return url.toString();
    }

    /**
     * Create and return a copy of this object.
     *
     * @return a copy of this object
     */
    public OAuth2Url copy() {
        return new OAuth2Url(this);
    }

    /**
     * Construct an oauth2 url.
     *
     * @param prototype prototype
     */
    protected OAuth2Url(OAuth2Url prototype) {
        this.pureUrl = prototype.pureUrl;
        this.query = (prototype.query == null) ? null : prototype.query.copy();
        this.anchor = prototype.anchor;
    }

}
