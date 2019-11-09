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
package com.github.wautsns.okauth.core.client.core;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.github.wautsns.okauth.core.client.core.dto.OAuthRedirectUriQuery;
import com.github.wautsns.okauth.core.client.core.dto.OAuthToken;
import com.github.wautsns.okauth.core.client.core.dto.OAuthUser;
import com.github.wautsns.okauth.core.client.core.properties.OAuthAppInfo;
import com.github.wautsns.okauth.core.client.util.http.Requester;
import com.github.wautsns.okauth.core.client.util.http.Response;
import com.github.wautsns.okauth.core.exception.OkAuthErrorException;
import com.github.wautsns.okauth.core.exception.OkAuthIOException;

/**
 * Abstract okauth client.
 *
 * @author wautsns
 */
public abstract class OkAuthClient {

    /** oauth application info */
    protected final OAuthAppInfo oauthAppInfo;
    /** requester */
    protected final Requester requester;

    /**
     * Construct an okauth client.
     *
     * @param oauthAppInfo oauth app info, require nonnull
     * @param requester requester, require nonnull
     */
    protected OkAuthClient(OAuthAppInfo oauthAppInfo, Requester requester) {
        this.oauthAppInfo = oauthAppInfo;
        this.requester = requester;
    }

    /** Get open platform. */
    public abstract OpenPlatform getOpenPlatform();

    /**
     * Initialize an authorize url.
     *
     * @param state url query(Do not underestimate it, because it can use to prevent CSRF)
     * @return authorize url
     */
    public abstract String initAuthorizeUrl(String state);

    /**
     * Exchange for oauth token.
     *
     * @param redirectUriQuery redirect uri query, require nonnull
     * @return oauth token
     * @throws OkAuthErrorException if an oauth exception occurs
     * @throws OkAuthIOException if an IO exception occurs
     */
    public abstract OAuthToken exchangeForToken(OAuthRedirectUriQuery redirectUriQuery)
            throws OkAuthErrorException, OkAuthIOException;

    /**
     * Exchange for oauth user.
     *
     * @param token oauth token, require nonnull
     * @return oauth user
     * @throws OkAuthErrorException if an oauth exception occurs
     * @throws OkAuthIOException if an IO exception occurs
     */
    public abstract OAuthUser exchangeForUser(OAuthToken token)
            throws OkAuthErrorException, OkAuthIOException;

    /**
     * Exchange for oauth token.
     *
     * @param redirectUriQuery, require nonnull
     * @return oauth user
     * @throws OkAuthErrorException if an oauth exception occurs
     * @throws OkAuthIOException if an IO exception occurs
     */
    public OAuthUser exchangeForUser(OAuthRedirectUriQuery redirectUriQuery)
            throws OkAuthErrorException, OkAuthIOException {
        return exchangeForUser(exchangeForToken(redirectUriQuery));
    }

    // ------------------------- BEGIN -------------------------
    // ------------------------ assists ------------------------
    // ---------------------------------------------------------

    /**
     * Url encode.
     *
     * @param text any text, require nonnull
     * @return url encoded text
     */
    protected String urlEncode(String text) {
        try {
            return URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Check response.
     *
     * @param response response
     * @param errorName error name
     * @param errorDescriptionName error description name
     * @return response
     * @throws OkAuthErrorException if an oauth exception occurs
     */
    protected Response checkResponse(
            Response response, String errorName, String errorDescriptionName)
            throws OkAuthErrorException {
        Object temp = response.getData().get(errorName);
        String error;
        String errorDescription = null;
        if (temp != null) {
            error = temp.toString();
            temp = response.getData().get(errorDescriptionName);
            if (temp != null) { errorDescription = temp.toString(); }
        } else {
            int statusCode = response.getStatusCode();
            if (statusCode < 300 && statusCode >= 200) {
                return response;
            } else {
                error = String.valueOf(statusCode);
            }
        }
        if (errorDescription == null) { errorDescription = error; }
        throw new OkAuthErrorException(getOpenPlatform(), error, errorDescription);
    }

}
