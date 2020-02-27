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
package com.github.wautsns.okauth.core.client.core.properties;

import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatform;
import com.github.wautsns.okauth.core.client.core.OpenPlatform;
import com.github.wautsns.okauth.core.client.util.http.RequesterProperties;

/**
 * Okauth client properties.
 *
 * @since Feb 27, 2020
 * @author wautsns
 */
public class OkAuthClientProperties {

    /**
     * open platform expression
     *
     * <p>There are two types of expressions:
     * <ul>
     * <li>
     * built-in open platform<br/>
     * For built-in open platform clients, use the short name (case insensitive, but to match the
     * identifier of the specified client), the specific configurable identifier can be found in
     * {@link BuiltInOpenPlatform}
     * </li>
     * <li>
     * extended open platform<br>
     * Need to implement the interface {@linkplain OpenPlatform}, the specific implementation can
     * refer to {@link BuiltInOpenPlatform}.<br/>
     * Assume that there is an enumeration `a.b.c.ExtendedOpenPlatform` that meets the requirements
     * and that the enumeration class has an enumeration value of `XYZ("xyz",...)`, and the
     * expression is `a.b.c.ExtendedOpenPlatform:xyz` , `xyz` after `:` is case insensitive.
     * <strong>Special, when the enumeration class contains only one enumeration value, it can be
     * omitted.</strong>
     * </li>
     * </ul>
     */
    private String openPlatformExpr;
    /** oauth application info */
    private OAuthAppInfo oauthAppInfo;
    /** okauth requester properties */
    private RequesterProperties requester;

    /**
     * Get open platform expr.
     *
     * <p>There are two types of expressions:
     * <ul>
     * <li>
     * built-in open platform<br/>
     * For built-in open platform clients, use the short name (case insensitive, but to match the
     * identifier of the specified client), the specific configurable identifier can be found in
     * {@link BuiltInOpenPlatform}
     * </li>
     * <li>
     * extended open platform<br>
     * Need to implement the interface {@linkplain OpenPlatform}, the specific implementation can
     * refer to {@link BuiltInOpenPlatform}.<br/>
     * Assume that there is an enumeration `a.b.c.ExtendedOpenPlatform` that meets the requirements
     * and that the enumeration class has an enumeration value of `XYZ("xyz",...)`, and the
     * expression is `a.b.c.ExtendedOpenPlatform:xyz` , `xyz` after `:` is case insensitive.
     * <strong>Special, when the enumeration class contains only one enumeration value, it can be
     * omitted.</strong>
     * </li>
     * </ul>
     *
     * @return open platform expr
     */
    public String getOpenPlatformExpr() {
        return openPlatformExpr;
    }

    /**
     * Set open platform expr.
     *
     * <p>There are two types of expressions:
     * <ul>
     * <li>
     * built-in open platform<br/>
     * For built-in open platform clients, use the short name (case insensitive, but to match the
     * identifier of the specified client), the specific configurable identifier can be found in
     * {@link BuiltInOpenPlatform}
     * </li>
     * <li>
     * extended open platform<br>
     * Need to implement the interface {@linkplain OpenPlatform}, the specific implementation can
     * refer to {@link BuiltInOpenPlatform}.<br/>
     * Assume that there is an enumeration `a.b.c.ExtendedOpenPlatform` that meets the requirements
     * and that the enumeration class has an enumeration value of `XYZ("xyz",...)`, and the
     * expression is `a.b.c.ExtendedOpenPlatform:xyz` , `xyz` after `:` is case insensitive.
     * <strong>Special, when the enumeration class contains only one enumeration value, it can be
     * omitted.</strong>
     * </li>
     * </ul>
     *
     * @param openPlatformExpr open platform expr
     * @return self reference
     */
    public OkAuthClientProperties setOpenPlatformExpr(String openPlatformExpr) {
        this.openPlatformExpr = openPlatformExpr;
        return this;
    }

    /**
     * Get OAuth app info.
     *
     * @return oauth app info
     */
    public OAuthAppInfo getOauthAppInfo() {
        return oauthAppInfo;
    }

    /**
     * Set oauth app info.
     *
     * @param oauthAppInfo oauth app info
     * @return self reference
     */
    public OkAuthClientProperties setOauthAppInfo(OAuthAppInfo oauthAppInfo) {
        this.oauthAppInfo = oauthAppInfo;
        return this;
    }

    /**
     * Get requester.
     *
     * @return requester
     */
    public RequesterProperties getRequester() {
        return requester;
    }

    /**
     * Set requester.
     *
     * @param requester requester
     * @return self reference
     */
    public OkAuthClientProperties setRequester(RequesterProperties requester) {
        this.requester = requester;
        return this;
    }

}
