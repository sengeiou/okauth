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
package com.github.wautsns.okauth.core.client.builtin.microblog;

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
 * MicroBlog okauth client.
 *
 * @since Feb 18, 2020
 * @author wautsns
 * @see <a
 *      href="https://open.weibo.com/wiki/%E6%8E%88%E6%9D%83%E6%9C%BA%E5%88%B6%E8%AF%B4%E6%98%8E">MicroBlog
 *      oauth doc</a>
 */
public class MicroBlogOkAuthClient extends StandardOkAuthClient {

    public MicroBlogOkAuthClient(OAuthAppInfo oauthAppInfo, Requester requester) {
        super(oauthAppInfo, requester);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatform.MICROBLOG;
    }

    @Override
    protected String getAuthorizeUrl() {
        return "https://api.weibo.com/oauth2/authorize";
    }

    @Override
    protected String getTokenUrl() {
        return "https://api.weibo.com/oauth2/access_token";
    }

    @Override
    protected Request initUserRequestPrototype() {
        return Request.initGet("https://api.weibo.com/2/users/show.json");
    }

    /**
     * Mutate a user request with query param `access_token` and `uid`.
     *
     * @param token oauth token, require nonnull
     * @return a new user request
     */
    @Override
    protected Request mutateUserRequest(OAuthToken token) {
        return super.mutateUserRequest(token)
            .addQueryParam("uid", token.getString("uid"));
    }

    @Override
    protected OAuthUser initOAuthUser(Response response) {
        return new MicroBlogUser(response);
    }

}
