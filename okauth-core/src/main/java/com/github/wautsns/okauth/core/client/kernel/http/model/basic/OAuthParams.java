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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.BiConsumer;

/**
 * OAuth params.
 *
 * @since Feb 28, 2020
 * @author wautsns
 */
public class OAuthParams implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = -1266336327459515005L;

    /** oauth params data */
    private final LinkedList<String> data = new LinkedList<>();

    /**
     * Return {@code true} if the oauth params have no data, otherwise {@code false}
     *
     * @return {@code true} if the oauth params have no data, otherwise {@code false}
     */
    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * For each param.
     *
     * @param action action for params
     */
    public void forEach(BiConsumer<String, String> action) {
        for (Iterator<String> i = data.iterator(); i.hasNext();) {
            action.accept(i.next(), i.next());
        }
    }

    /**
     * Create and return a copy of this oauth params.
     *
     * @return a copy of this oauth params
     */
    public OAuthParams copy() {
        OAuthParams copy = new OAuthParams();
        copy.data.addAll(this.data);
        return copy;
    }

    // -------------------- add -------------------------------------

    /**
     * Add param `client_id`.
     *
     * @param clientId client id
     * @return self reference
     * @see #add(String, String)
     */
    public OAuthParams addClientId(String clientId) {
        return add("client_id", clientId);
    }

    /**
     * Add param `appid`.
     *
     * @param appid appid
     * @return self reference
     * @see #add(String, String)
     */
    public OAuthParams addAppid(String appid) {
        return add("appid", appid);
    }

    /**
     * Add param `client_secret`.
     *
     * @param clientSecret client secret
     * @return self reference
     * @see #add(String, String)
     */
    public OAuthParams addClientSecret(String clientSecret) {
        return add("client_secret", clientSecret);
    }

    /**
     * Add param `secret`.
     *
     * @param secret secret
     * @return self reference
     * @see #add(String, String)
     */
    public OAuthParams addSecret(String secret) {
        return add("secret", secret);
    }

    /**
     * Add param `redirect_uri`.
     *
     * @param redirectUri redirect uri
     * @return self reference
     * @see #add(String, String)
     */
    public OAuthParams addRedirectUri(String redirectUri) {
        return add("redirect_uri", redirectUri);
    }

    /**
     * Add param `response_type` with value {@code "code"}.
     *
     * @return self reference
     */
    public OAuthParams addResponseTypeWithValueCode() {
        return add("response_type", "code");
    }

    /**
     * Add param `grant_type` with value {@code "authorization_code"}.
     *
     * @return self reference
     */
    public OAuthParams addGrantTypeWithValueAuthorizationCode() {
        return add("grant_type", "authorization_code");
    }

    /**
     * Add param `grant_type` with value {@code "refresh_token"}.
     *
     * @return self reference
     */
    public OAuthParams addGrantTypeWithValueRefreshToken() {
        return add("grant_type", "refresh_token");
    }

    /**
     * Add param `code`.
     *
     * @param value code
     * @return self reference
     * @see #add(String, String)
     */
    public OAuthParams addCode(String value) {
        return add("code", value);
    }

    /**
     * Add param `state`.
     *
     * @param value state
     * @return self reference
     * @see #add(String, String)
     */
    public OAuthParams addState(String value) {
        return add("state", value);
    }

    /**
     * Add param `access_token`.
     *
     * @param value access token
     * @return self reference
     * @see #add(String, String)
     */
    public OAuthParams addAccessToken(String value) {
        return add("access_token", value);
    }

    /**
     * Add param `refresh_token`.
     *
     * @param value refresh token
     * @return self reference
     * @see #add(String, String)
     */
    public OAuthParams addRefreshToken(String value) {
        return add("refresh_token", value);
    }

    /**
     * Add param.
     *
     * @param name name of the oauth param, require nonnull
     * @param value value of the oauth param
     * @return self reference
     */
    public OAuthParams add(String name, String value) {
        if (value == null) { return null; }
        try {
            value = URLEncoder.encode(value, "UTF-8");
            data.add(name);
            data.add(value);
            return this;
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Add url encoded oauth param.
     *
     * @param name name of the oauth param, require nonnull
     * @param value url encoded value of the oauth param
     * @return self reference
     */
    public OAuthParams addUrlEncoded(String name, String value) {
        if (value != null) {
            data.add(name);
            data.add(value);
        }
        return this;
    }

}
