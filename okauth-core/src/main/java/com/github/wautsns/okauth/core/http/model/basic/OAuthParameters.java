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

import com.github.wautsns.okauth.core.http.util.Parameters;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Oauth parameters.
 *
 * @author wautsns
 * @since Mar 03, 2020
 */
public class OAuthParameters extends Parameters {

    private static final long serialVersionUID = 5788318807823100634L;

    // -------------------- oauth app properties ---------------------

    /**
     * Add parameter `client_id`.
     *
     * @param value client id
     * @return self reference
     * @see #add(String, String)
     */
    public OAuthParameters addClientId(String value) {
        return add("client_id", value);
    }

    /**
     * Add parameter `appid`.
     *
     * @param value appid
     * @return self reference
     * @see #add(String, String)
     */
    public OAuthParameters addAppid(String value) {
        return add("appid", value);
    }

    /**
     * Add parameter `client_secret`.
     *
     * @param value client secret
     * @return self reference
     * @see #add(String, String)
     */
    public OAuthParameters addClientSecret(String value) {
        return add("client_secret", value);
    }

    /**
     * Add parameter `secret`.
     *
     * @param value secret
     * @return self reference
     * @see #add(String, String)
     */
    public OAuthParameters addSecret(String value) {
        return add("secret", value);
    }

    /**
     * Add parameter `redirect_uri`.
     *
     * @param value redirect uri
     * @return self reference
     * @see #add(String, String)
     */
    public OAuthParameters addRedirectUri(String value) {
        return add("redirect_uri", value);
    }

    // -------------------- authorize url ---------------------------

    /**
     * Add parameter `response_type` with value {@code "code"}.
     *
     * @return self reference
     */
    public OAuthParameters addResponseTypeWithValueCode() {
        return add("response_type", "code");
    }

    /**
     * Add parameter `scope`.
     *
     * @param value scope
     * @return self reference
     * @see #add(String, String)
     */
    public OAuthParameters addScope(String value) {
        return add("scope", value);
    }

    /**
     * Add parameter `state`.
     *
     * @param value state
     * @return self reference
     * @see #add(String, String)
     */
    public OAuthParameters addState(String value) {
        return add("state", value);
    }

    // -------------------- oauth token -----------------------------

    /**
     * Add parameter `grant_type` with value {@code "authorization_code"}.
     *
     * @return self reference
     */
    public OAuthParameters addGrantTypeWithValueAuthorizationCode() {
        return add("grant_type", "authorization_code");
    }

    /**
     * Add parameter `grant_type` with value {@code "refresh_token"}.
     *
     * @return self reference
     */
    public OAuthParameters addGrantTypeWithValueRefreshToken() {
        return add("grant_type", "refresh_token");
    }

    /**
     * Add parameter `code`.
     *
     * @param value code
     * @return self reference
     * @see #add(String, String)
     */
    public OAuthParameters addCode(String value) {
        return add("code", value);
    }

    /**
     * Add parameter `access_token`.
     *
     * @param value access token
     * @return self reference
     * @see #add(String, String)
     */
    public OAuthParameters addAccessToken(String value) {
        return add("access_token", value);
    }

    /**
     * Add parameter `refresh_token`.
     *
     * @param value refresh token
     * @return self reference
     * @see #add(String, String)
     */
    public OAuthParameters addRefreshToken(String value) {
        return add("refresh_token", value);
    }

    /**
     * Add parameter.
     *
     * <p>If the value is {@code null}, the parameter will not be added.
     * <p>The value will be url encoded.
     *
     * @param name {@inheritDoc}
     * @param value {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public OAuthParameters add(String name, String value) {
        if (value == null) { return this; }
        try {
            value = URLEncoder.encode(value, "UTF-8");
            return (OAuthParameters) super.add(name, value);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Create and return a copy of {@link OAuthParameters}.
     *
     * @return copy of {@link OAuthParameters}
     */
    @Override
    public OAuthParameters copy() {
        OAuthParameters copy = new OAuthParameters();
        copy.getNameValuePairs().addAll(getNameValuePairs());
        return copy;
    }

}
