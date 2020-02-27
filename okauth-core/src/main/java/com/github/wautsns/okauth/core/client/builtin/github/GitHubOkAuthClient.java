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
package com.github.wautsns.okauth.core.client.builtin.github;

import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatform;
import com.github.wautsns.okauth.core.client.core.OpenPlatform;
import com.github.wautsns.okauth.core.client.core.StandardOkAuthClient;
import com.github.wautsns.okauth.core.client.core.dto.OAuthUser;
import com.github.wautsns.okauth.core.client.core.properties.OAuthAppInfo;
import com.github.wautsns.okauth.core.client.util.http.OkAuthRequest;
import com.github.wautsns.okauth.core.client.util.http.OkAuthRequester;
import com.github.wautsns.okauth.core.client.util.http.OkAuthResponse;

/**
 * GitHub okauth client.
 *
 * @since Feb 27, 2020
 * @author wautsns
 * @see <a
 *      href="https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/">github
 *      oauth doc</a>
 */
public class GitHubOkAuthClient extends StandardOkAuthClient {

    /**
     * Construct a GitHub okauth client.
     *
     * @param oauthAppInfo oauth application info, require nonnull
     * @param requester requester, require nonnull
     */
    public GitHubOkAuthClient(OAuthAppInfo oauthAppInfo, OkAuthRequester requester) {
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
    protected String getTokenRequestUrl() {
        return "https://github.com/login/oauth/access_token";
    }

    @Override
    protected OkAuthRequest initUserRequestPrototype() {
        return OkAuthRequest.forGet("https://api.github.com/user");
    }

    @Override
    protected OAuthUser newOAuthUser(OkAuthResponse response) {
        return new GitHubUser(response);
    }

}
