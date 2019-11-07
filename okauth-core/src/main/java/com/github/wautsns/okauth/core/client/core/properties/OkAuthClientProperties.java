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

import com.github.wautsns.okauth.core.client.util.http.properties.OkAuthHttpProperties;

/**
 *
 * @author wautsns
 */
public class OkAuthClientProperties {

    private String openPlatform;
    private OAuthAppInfo oauthAppInfo;
    private OkAuthHttpProperties http = new OkAuthHttpProperties();

    /** Get {@link #openPlatform}. */
    public String getOpenPlatform() {
        return openPlatform;
    }

    /** Set {@link #openPlatform}. */
    public OkAuthClientProperties setOpenPlatform(String openPlatform) {
        this.openPlatform = openPlatform;
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

    /** Get {@link #http}. */
    public OkAuthHttpProperties getHttp() {
        return http;
    }

    /** Set {@link #http}. */
    public OkAuthClientProperties setHttp(OkAuthHttpProperties http) {
        this.http = http;
        return this;
    }

}
