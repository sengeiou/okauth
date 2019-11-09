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
package com.github.wautsns.okauth.core.client.builtin.gitee;

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
 * Gitee okauth client.
 *
 * @author wautsns
 * @see <a href="https://gitee.com/api/v5/oauth_doc">gitee oauth doc</a>
 */
public class GiteeOkAuthClient extends OkAuthClient {

    /** authorize url prefix */
    private final String authorizeUrlPrefix;
    /** token request template */
    private final Request tokenRequestTemplate;
    /** user request template */
    private final Request userRequestTemplate;

    /**
     * Construct a gitee okauth client.
     *
     * @param oauthAppInfo oauth application info, require nonnull
     * @param requester requester, require nonnull
     */
    public GiteeOkAuthClient(
            OAuthAppInfo oauthAppInfo, Requester requester) {
        super(oauthAppInfo, requester);
        authorizeUrlPrefix = "https://gitee.com/oauth/authorize"
            + "?response_type=code"
            + "&client_id=" + oauthAppInfo.getClientId()
            + "&redirect_uri=" + urlEncode(oauthAppInfo.getRedirectUri())
            + "&state=";
        tokenRequestTemplate = requester
            .post("https://gitee.com/oauth/token")
            .addFormItem("grant_type", "authorization_code")
            .addFormItem("client_id", oauthAppInfo.getClientId())
            .addFormItem("client_secret", oauthAppInfo.getClientSecret())
            .addFormItem("redirect_uri", oauthAppInfo.getRedirectUri());
        userRequestTemplate = requester
            .get("https://gitee.com/api/v5/user");
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatform.GITHUB;
    }

    @Override
    public String initAuthorizeUrl(String state) {
        return authorizeUrlPrefix + state;
    }

    @Override
    public GiteeToken exchangeForToken(OAuthRedirectUriQuery redirectUriQuery)
            throws OkAuthErrorException, OkAuthIOException {
        return new GiteeToken(checkResponse(tokenRequestTemplate.mutate()
            .addFormItem("code", redirectUriQuery.getCode())
            .exchangeForJson(), "error", "error_description"));
    }

    @Override
    public GiteeUser exchangeForUser(OAuthToken token)
            throws OkAuthErrorException, OkAuthIOException {
        return new GiteeUser(checkResponse(userRequestTemplate.mutate()
            .addQuery("access_token", token.getAccessToken())
            .exchangeForJson(), "error", "error_description"));
    }

}
