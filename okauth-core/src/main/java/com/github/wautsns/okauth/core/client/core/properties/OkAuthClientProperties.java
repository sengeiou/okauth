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
package com.github.wautsns.okauth.core.client.core.properties;

import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatform;
import com.github.wautsns.okauth.core.client.core.OpenPlatform;
import com.github.wautsns.okauth.core.client.util.http.properties.OkAuthRequesterProperties;

/**
 * Okauth client properties.
 *
 * @author wautsns
 */
public class OkAuthClientProperties {

    /**
     * open platform expression
     *
     * <p>There are three types of expressions:
     * <ol>
     * <li>built-in open platform name(case insensitive) like "github"...(optional values see
     * {@linkplain BuiltInOpenPlatform})</li>
     * <li>simple extended open platform like "a.b.c.Github"(`a.b.c.Github` can only have one
     * enumeration constant)</li>
     * <li>composite extended open platform like "a.b.c.ExtendedOpenPlatform:xyz"(string after ':'
     * is a case insensitive identifier)</li>
     * </ol>
     * *** All the classes mentioned above(like `a.b.c.Github`, `a.b.c.ExtendedOpenPlatform`)
     * <strong>must be enumeration that implements {@linkplain OpenPlatform}</strong> ***
     */
    private String openPlatformExpr;
    /** oauth application info */
    private OAuthAppInfo oauthAppInfo;
    /** okauth requester properties */
    private OkAuthRequesterProperties requester = new OkAuthRequesterProperties();

    /** Get {@link #openPlatformExpr}. */
    public String getOpenPlatformExpr() {
        return openPlatformExpr;
    }

    /** Set {@link #openPlatformExpr}. */
    public OkAuthClientProperties setOpenPlatformExpr(String openPlatformExpr) {
        this.openPlatformExpr = openPlatformExpr;
        return this;
    }

    /** Get {@link #oauthAppInfo}. */
    public OAuthAppInfo getOauthAppInfo() {
        return oauthAppInfo;
    }

    /** Set {@link #oauthAppInfo}. */
    public OkAuthClientProperties setOauthAppInfo(OAuthAppInfo oauthAppInfo) {
        this.oauthAppInfo = oauthAppInfo;
        return this;
    }

    /** Get {@link #requester}. */
    public OkAuthRequesterProperties getRequester() {
        return requester;
    }

    /** Set {@link #requester}. */
    public void setRequester(OkAuthRequesterProperties requester) {
        this.requester = requester;
    }

}
