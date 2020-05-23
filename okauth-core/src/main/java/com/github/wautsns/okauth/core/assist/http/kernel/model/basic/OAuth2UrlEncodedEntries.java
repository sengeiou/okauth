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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * OAuth2 url encoded entries.
 *
 * @author wautsns
 * @since May 16, 2020
 */
public class OAuth2UrlEncodedEntries extends NameValuePairs {

    private static final long serialVersionUID = -6684282712581355622L;

    // #################### oauth app properties ########################################

    /**
     * Add `client_id`.
     *
     * @param value client id
     * @return self reference
     * @see #add(String, String)
     */
    public OAuth2UrlEncodedEntries addClientId(String value) {
        return add("client_id", value);
    }

    /**
     * Add `appid`.
     *
     * @param value appid
     * @return self reference
     * @see #add(String, String)
     */
    public OAuth2UrlEncodedEntries addAppid(String value) {
        return add("appid", value);
    }

    /**
     * Add `client_secret`.
     *
     * @param value client secret
     * @return self reference
     * @see #add(String, String)
     */
    public OAuth2UrlEncodedEntries addClientSecret(String value) {
        return add("client_secret", value);
    }

    /**
     * Add `secret`.
     *
     * @param value secret
     * @return self reference
     * @see #add(String, String)
     */
    public OAuth2UrlEncodedEntries addSecret(String value) {
        return add("secret", value);
    }

    /**
     * Add `redirect_uri`.
     *
     * @param value redirect uri
     * @return self reference
     * @see #add(String, String)
     */
    public OAuth2UrlEncodedEntries addRedirectUri(String value) {
        return add("redirect_uri", value);
    }

    // #################### authorize url ###############################################

    /**
     * Add `response_type` with value {@code "code"}.
     *
     * @return self reference
     */
    public OAuth2UrlEncodedEntries addResponseTypeWithValueCode() {
        return add("response_type", "code");
    }

    /**
     * Add `scope`.
     *
     * @param value scope
     * @return self reference
     * @see #add(String, String)
     */
    public OAuth2UrlEncodedEntries addScope(String value) {
        return add("scope", value);
    }

    /**
     * Add `state`.
     *
     * @param value state
     * @return self reference
     * @see #add(String, String)
     */
    public OAuth2UrlEncodedEntries addState(String value) {
        return add("state", value);
    }

    // #################### oauth token #################################################

    /**
     * Add `grant_type` with value {@code "authorization_code"}.
     *
     * @return self reference
     */
    public OAuth2UrlEncodedEntries addGrantTypeWithValueAuthorizationCode() {
        return add("grant_type", "authorization_code");
    }

    /**
     * Add `grant_type` with value {@code "refresh_token"}.
     *
     * @return self reference
     */
    public OAuth2UrlEncodedEntries addGrantTypeWithValueRefreshToken() {
        return add("grant_type", "refresh_token");
    }

    /**
     * Add `code`.
     *
     * @param value code
     * @return self reference
     * @see #add(String, String)
     */
    public OAuth2UrlEncodedEntries addCode(String value) {
        return add("code", value);
    }

    /**
     * Add `access_token`.
     *
     * @param value access token
     * @return self reference
     * @see #add(String, String)
     */
    public OAuth2UrlEncodedEntries addAccessToken(String value) {
        return add("access_token", value);
    }

    /**
     * Add `refresh_token`.
     *
     * @param value refresh token
     * @return self reference
     * @see #add(String, String)
     */
    public OAuth2UrlEncodedEntries addRefreshToken(String value) {
        return add("refresh_token", value);
    }

    // #################### common #####################################################

    /**
     * {@inheritDoc}
     *
     * <p>The value will be url encoded.
     *
     * @param name {@inheritDoc}
     * @param value {@inheritDoc}
     * @return self reference
     */
    @Override
    public OAuth2UrlEncodedEntries add(String name, String value) {
        if (value == null) { return this; }
        try {
            return addUrlEncoded(name, URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>Values will be url encoded.
     *
     * @param nameValuePairs {@inheritDoc}
     * @return self reference
     */
    @Override
    public OAuth2UrlEncodedEntries addAll(Map<String, String> nameValuePairs) {
        if (nameValuePairs == null) { return this; }
        nameValuePairs.forEach(this::add);
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The value will be url encoded.
     *
     * @param name {@inheritDoc}
     * @param value {@inheritDoc}
     * @return self reference
     */
    @Override
    public OAuth2UrlEncodedEntries set(String name, String value) {
        if (value == null) { return this; }
        try {
            return setUrlEncoded(name, URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Add url encoded value with the specified name.
     *
     * @param name name
     * @param value url encoded value
     * @return self reference
     */
    public OAuth2UrlEncodedEntries addUrlEncoded(String name, String value) {
        return (OAuth2UrlEncodedEntries) super.add(name, value);
    }

    /**
     * Add all url encoded values.
     *
     * @param nameUrlEncodedValueMap url encoded map
     * @return self reference
     */
    public OAuth2UrlEncodedEntries addAllUrlEncoded(Map<String, String> nameUrlEncodedValueMap) {
        return (OAuth2UrlEncodedEntries) super.addAll(nameUrlEncodedValueMap);
    }

    /**
     * Associated url encoded value with the specified name(Old value will be replaced).
     *
     * @param name name
     * @param value url encoded value
     * @return self reference
     */
    public OAuth2UrlEncodedEntries setUrlEncoded(String name, String value) {
        return (OAuth2UrlEncodedEntries) super.set(name, value);
    }

    @Override
    public OAuth2UrlEncodedEntries copy() {
        OAuth2UrlEncodedEntries copy = new OAuth2UrlEncodedEntries();
        copy.getOrigin().addAll(this.getOrigin());
        return copy;
    }

}
