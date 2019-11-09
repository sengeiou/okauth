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
package com.github.wautsns.okauth.core.client.builtin.github;

import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatform;
import com.github.wautsns.okauth.core.client.core.OkAuthClient;
import com.github.wautsns.okauth.core.client.core.OpenPlatform;
import com.github.wautsns.okauth.core.client.core.dto.OAuthRedirectUriQuery;
import com.github.wautsns.okauth.core.client.core.dto.OAuthToken;
import com.github.wautsns.okauth.core.client.core.properties.OAuthAppInfo;
import com.github.wautsns.okauth.core.client.util.http.Request;
import com.github.wautsns.okauth.core.client.util.http.Requester;
import com.github.wautsns.okauth.core.exception.OkAuthErrorException;
import com.github.wautsns.okauth.core.exception.OkAuthIOException;

/**
 * Github okauth client.
 *
 * @author wautsns
 * @see <a
 *      href="https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/">github
 *      oauth doc</a>
 */
public class GithubOkAuthClient extends OkAuthClient {

    /** authorize url prefix */
    private final String authorizeUrlPrefix;
    /** token request template */
    private final Request tokenRequest;
    /** user request template */
    private final Request userRequest;

    /**
     * Construct a github okauth client.
     *
     * @param oauthAppInfo oauth application info, require nonnull
     * @param requester requester, require nonnull
     */
    public GithubOkAuthClient(
            OAuthAppInfo oAuthAppInfo, Requester requester) {
        super(oAuthAppInfo, requester);
        authorizeUrlPrefix = "https://github.com/login/oauth/authorize"
            + "?client_id=" + oauthAppInfo.getClientId()
            + "&state=";
        tokenRequest = requester
            .get("https://github.com/login/oauth/access_token")
            .acceptJson()
            .addQuery("client_id", oauthAppInfo.getClientId())
            .addQuery("client_secret", oauthAppInfo.getClientSecret());
        userRequest = requester
            .get("https://api.github.com/user");
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatform.GITEE;
    }

    @Override
    public String initAuthorizeUrl(String state) {
        return authorizeUrlPrefix + state;
    }

    @Override
    public GithubToken exchangeForToken(OAuthRedirectUriQuery redirectUriQuery)
            throws OkAuthErrorException, OkAuthIOException {
        return new GithubToken(checkResponse(tokenRequest.mutate()
            .addQuery("code", redirectUriQuery.getCode())
            .exchangeForJson(), "error", "error_description"));
    }

    @Override
    public GithubUser exchangeForUser(OAuthToken token)
            throws OkAuthErrorException, OkAuthIOException {
        return new GithubUser(checkResponse(userRequest.mutate()
            .addQuery("access_token", token.getAccessToken())
            .exchangeForJson(), "error", "error_description"));
    }

}
