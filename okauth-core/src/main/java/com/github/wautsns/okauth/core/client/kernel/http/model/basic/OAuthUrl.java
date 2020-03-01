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
package com.github.wautsns.okauth.core.client.kernel.http.model.basic;

import java.io.Serializable;

/**
 * OAuth url.
 *
 * @since Feb 28, 2020
 * @author wautsns
 */
public class OAuthUrl implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 2900878644645922887L;

    /** url string */
    private final String url;
    /** query */
    private final OAuthParams query;

    /**
     * Construct oauth url.
     *
     * @param url url string, require nonnull
     */
    public OAuthUrl(String url) {
        url = url.trim();
        int index = url.indexOf('?');
        this.query = new OAuthParams();
        if (index == -1) {
            this.url = url;
        } else if (index == url.length() - 1) {
            this.url = url.substring(0, index);
        } else {
            this.url = url.substring(0, index);
            String[] queryItems = url.substring(index + 1).split("&");
            for (String queryItem : queryItems) {
                String[] tmp = queryItem.split("=", 2);
                this.query.add(tmp[0], tmp[1]);
            }
        }
    }

    /**
     * Get query of the url.
     *
     * @return query of the url
     */
    public OAuthParams getQuery() {
        return query;
    }

    @Override
    public String toString() {
        if (query.isEmpty()) {
            return url;
        } else {
            StringBuilder bder = new StringBuilder(url).append('?');
            query.forEach((name, value) -> bder.append(name).append('=').append(value).append('&'));
            bder.deleteCharAt(bder.length() - 1);
            return bder.toString();
        }
    }

    /**
     * Create and return a copy of the oauth url.
     *
     * @return a copy of the oauth url
     */
    public OAuthUrl copy() {
        return new OAuthUrl(this);
    }

    /**
     * Construct oauth url with prototype.
     *
     * <p>Used for {@linkplain #copy()}
     *
     * @param prototype prototype, require nonnull
     */
    private OAuthUrl(OAuthUrl prototype) {
        this.url = prototype.url;
        this.query = prototype.query.copy();
    }

}
