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

import com.github.wautsns.okauth.core.client.OpenPlatform;
import com.github.wautsns.okauth.core.client.builtin.OpenPlatforms;
import com.github.wautsns.okauth.core.client.kernel.http.model.dto.OAuthResponse;
import com.github.wautsns.okauth.core.client.kernel.model.dto.OpenPlatformUser;

/**
 * GitHub user.
 *
 * <p>response data map:
 *
 * <pre>
 * {
 *     "login": "wautsns",
 *     "id": 393xxxxx,
 *     "node_id": "MDQ6VXNlcjM5MzM2NjA0",
 *     "avatar_url": "https://avatars2.githubusercontent.com/u/393xxxxx?v=4",
 *     "gravatar_id": "",
 *     "url": "https://api.github.com/users/wautsns",
 *     "html_url": "https://github.com/wautsns",
 *     "followers_url": "https://api.github.com/users/wautsns/followers",
 *     "following_url": "https://api.github.com/users/wautsns/following{/other_user}",
 *     "gists_url": "https://api.github.com/users/wautsns/gists{/gist_id}",
 *     "starred_url": "https://api.github.com/users/wautsns/starred{/owner}{/repo}",
 *     "subscriptions_url": "https://api.github.com/users/wautsns/subscriptions",
 *     "organizations_url": "https://api.github.com/users/wautsns/orgs",
 *     "repos_url": "https://api.github.com/users/wautsns/repos",
 *     "events_url": "https://api.github.com/users/wautsns/events{/privacy}",
 *     "received_events_url": "https://api.github.com/users/wautsns/received_events",
 *     "type": "User",
 *     "site_admin": false,
 *     "name": "wautsns",
 *     "company": null,
 *     "blog": "",
 *     "location": null,
 *     "email": null,
 *     "hireable": null,
 *     "bio": null,
 *     "public_repos": 10,
 *     "public_gists": 0,
 *     "followers": 0,
 *     "following": 0,
 *     "created_at": "2018-05-16T12:17:46Z",
 *     "updated_at": "2020-02-28T02:55:07Z"
 * }
 * </pre>
 *
 * @since Feb 29, 2020
 * @author wautsns
 */
public class GitHubUser extends OpenPlatformUser {

    /** serialVersionUID */
    private static final long serialVersionUID = 1325181221533596732L;

    /**
     * Construct GitHub user.
     *
     * @param response okauth response, require nonnull
     */
    public GitHubUser(OAuthResponse response) {
        super(response);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return OpenPlatforms.GITHUB;
    }

    @Override
    public String getOpenid() {
        return getString("id");
    }

    @Override
    public String getUsername() {
        return getString("login");
    }

    @Override
    public String getNickname() {
        return getString("name");
    }

    @Override
    public String getAvatarUrl() {
        return getString("avatar_url");
    }

}
