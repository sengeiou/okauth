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

/**
 * OAuth application info.
 *
 * @author wautsns
 */
public class OAuthAppInfo {

    /** client id */
    private String clientId;
    /** client secrect */
    private String clientSecret;
    /** redirect uri */
    private String redirectUri;

    /** Get {@link #clientId}. */
    public String getClientId() {
        return clientId;
    }

    /** Set {@link #clientId}. */
    public OAuthAppInfo setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    /** Get {@link #clientSecret}. */
    public String getClientSecret() {
        return clientSecret;
    }

    /** Set {@link #clientSecret}. */
    public OAuthAppInfo setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    /** Get {@link #redirectUri}. */
    public String getRedirectUri() {
        return redirectUri;
    }

    /** Set {@link #redirectUri}. */
    public OAuthAppInfo setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }

}
