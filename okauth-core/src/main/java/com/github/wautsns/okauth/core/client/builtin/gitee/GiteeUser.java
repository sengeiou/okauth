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
package com.github.wautsns.okauth.core.client.builtin.gitee;

import com.github.wautsns.okauth.core.client.OpenPlatform;
import com.github.wautsns.okauth.core.client.builtin.OpenPlatforms;
import com.github.wautsns.okauth.core.client.kernel.http.model.dto.OAuthResponse;
import com.github.wautsns.okauth.core.client.kernel.model.dto.OpenPlatformUser;

/**
 * Gitee user.
 *
 * <p>response data map:
 *
 * <pre>
 * {
 *     "id": 19xxxxx,
 *     "login": "wautsns",
 *     "name": "独自漫步〃寂静の夜空下",
 *     "avatar_url": "https://portrait.gitee.com/uploads/avatars/user/645/19xxxxx_wautsns_1578962737.png",
 *     "url": "https://gitee.com/api/v5/users/wautsns",
 *     "html_url": "https://gitee.com/wautsns",
 *     "followers_url": "https://gitee.com/api/v5/users/wautsns/followers",
 *     "following_url": "https://gitee.com/api/v5/users/wautsns/following_url{/other_user}",
 *     "gists_url": "https://gitee.com/api/v5/users/wautsns/gists{/gist_id}",
 *     "starred_url": "https://gitee.com/api/v5/users/wautsns/starred{/owner}{/repo}",
 *     "subscriptions_url": "https://gitee.com/api/v5/users/wautsns/subscriptions",
 *     "organizations_url": "https://gitee.com/api/v5/users/wautsns/orgs",
 *     "repos_url": "https://gitee.com/api/v5/users/wautsns/repos",
 *     "events_url": "https://gitee.com/api/v5/users/wautsns/events{/privacy}",
 *     "received_events_url": "https://gitee.com/api/v5/users/wautsns/received_events",
 *     "type": "User",
 *     "site_admin": false,
 *     "blog": "",
 *     "weibo": "",
 *     "bio": "",
 *     "public_repos": 0,
 *     "public_gists": 0,
 *     "followers": 0,
 *     "following": 0,
 *     "stared": 0,
 *     "watched": 2,
 *     "created_at": "2018-05-15T21:27:41+08:00",
 *     "updated_at": "2020-03-01T00:58:49+08:00",
 *     "email": null
 * }
 * </pre>
 *
 * @author wautsns
 * @since Feb 29, 2020
 */
public class GiteeUser extends OpenPlatformUser {

    private static final long serialVersionUID = -6198419705522461920L;

    /**
     * Construct Gitee user.
     *
     * @param response okauth response, require nonnull
     */
    public GiteeUser(OAuthResponse response) {
        super(response);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return OpenPlatforms.GITEE;
    }

    @Override
    public String getOpenid() {
        return getAsString("id");
    }

    @Override
    public String getUsername() {
        return getAsString("login");
    }

    @Override
    public String getNickname() {
        return getAsString("name");
    }

    @Override
    public String getAvatarUrl() {
        return getAsString("avatar_url");
    }

}
