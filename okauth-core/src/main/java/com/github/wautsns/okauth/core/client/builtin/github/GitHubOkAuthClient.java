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
import com.github.wautsns.okauth.core.client.core.OpenPlatform;
import com.github.wautsns.okauth.core.client.core.StandardOkAuthClient;
import com.github.wautsns.okauth.core.client.core.dto.OAuthToken;
import com.github.wautsns.okauth.core.client.core.dto.OAuthUser;
import com.github.wautsns.okauth.core.client.core.properties.OAuthAppInfo;
import com.github.wautsns.okauth.core.client.util.http.Request;
import com.github.wautsns.okauth.core.client.util.http.Requester;
import com.github.wautsns.okauth.core.client.util.http.Response;

/**
 * GitHub okauth client.
 *
 * @author wautsns
 * @see <a
 *      href="https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/">github
 *      oauth doc</a>
 */
public class GitHubOkAuthClient extends StandardOkAuthClient {

    /**
     * Construct a github okauth client.
     *
     * @param oauthAppInfo oauth application info, require nonnull
     * @param requester requester, require nonnull
     */
    public GitHubOkAuthClient(OAuthAppInfo oauthAppInfo, Requester requester) {
        super(oauthAppInfo, requester);
        tokenRequestPrototype.addHeaderAcceptJson();
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatform.GITHUB;
    }

    @Override
    protected String getAuthorizeUrl() {
        return "https://github.com/login/oauth/authorize";
    }

    @Override
    protected String getTokenUrl() {
        return "https://github.com/login/oauth/access_token";
    }

    @Override
    protected Request initUserRequestPrototype() {
        return Request.initGet("https://api.github.com/user");
    }

    @Override
    protected Request mutateUserRequest(OAuthToken token) {
        return userRequestPrototype.mutate()
            .addHeaderAuthorization("token " + token.getAccessToken());
    }

    @Override
    protected OAuthUser initOAuthUser(Response response) {
        return new GitHubUser(response);
    }

}
