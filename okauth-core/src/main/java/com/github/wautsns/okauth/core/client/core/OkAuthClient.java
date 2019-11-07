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
import com.github.wautsns.okauth.core.exception.OkAuthException;
import com.github.wautsns.okauth.core.exception.OkAuthIOException;

/**
 *
 * @author wautsns
 */
public abstract class OkAuthClient {

    protected final OAuthAppInfo oauthAppInfo;
    protected final Requester requester;

    protected OkAuthClient(OAuthAppInfo oAuthAppInfo, Requester requester) {
        this.oauthAppInfo = oAuthAppInfo;
        this.requester = requester;
    }

    public abstract OpenPlatform getOpenPlatform();

    public abstract String initAuthorizeUrl(String state);

    public abstract OAuthToken exchangeForToken(OAuthRedirectUriQuery redirectUriQuery)
            throws OkAuthException, OkAuthIOException;

    public abstract OAuthUser exchangeForUser(OAuthToken token)
            throws OkAuthException, OkAuthIOException;

    public OAuthUser exchangeForUser(OAuthRedirectUriQuery redirectUriQuery)
            throws OkAuthException, OkAuthIOException {
        return exchangeForUser(exchangeForToken(redirectUriQuery));
    }

    protected static String urlEncode(String text) {
        try {
            return URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
