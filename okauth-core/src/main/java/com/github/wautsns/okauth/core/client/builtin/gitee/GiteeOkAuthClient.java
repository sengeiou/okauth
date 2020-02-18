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
import com.github.wautsns.okauth.core.client.core.OpenPlatform;
import com.github.wautsns.okauth.core.client.core.StandardOkAuthClient;
import com.github.wautsns.okauth.core.client.core.dto.OAuthToken;
import com.github.wautsns.okauth.core.client.core.dto.OAuthUser;
import com.github.wautsns.okauth.core.client.core.properties.OAuthAppInfo;
import com.github.wautsns.okauth.core.client.util.http.Request;
import com.github.wautsns.okauth.core.client.util.http.Requester;
import com.github.wautsns.okauth.core.client.util.http.Response;

/**
 * Gitee okauth client.
 *
 * @since Feb 18, 2020
 * @author wautsns
 * @see <a href="https://gitee.com/api/v5/oauth_doc">gitee oauth doc</a>
 */
public class GiteeOkAuthClient extends StandardOkAuthClient {

    /**
     * Construct a gitee okauth client.
     *
     * @param oauthAppInfo oauth application info, require nonnull
     * @param requester requester, require nonnull
     */
    public GiteeOkAuthClient(OAuthAppInfo oauthAppInfo, Requester requester) {
        super(oauthAppInfo, requester);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatform.GITEE;
    }

    @Override
    protected String getAuthorizeUrl() {
        return "https://gitee.com/oauth/authorize";
    }

    @Override
    protected String getTokenUrl() {
        return "https://gitee.com/oauth/token";
    }

    @Override
    protected Request initUserRequestPrototype() {
        return Request.initGet("https://gitee.com/api/v5/user");
    }

    @Override
    protected Request mutateUserRequest(OAuthToken token) {
        return userRequestPrototype.mutate()
            .addQueryParam("access_token", token.getAccessToken());
    }

    @Override
    protected OAuthUser initOAuthUser(Response response) {
        return new GiteeUser(response);
    }

}
