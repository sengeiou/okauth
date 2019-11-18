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

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatform;
import com.github.wautsns.okauth.core.client.core.OpenPlatform;
import com.github.wautsns.okauth.core.client.core.dto.OAuthUser;
import com.github.wautsns.okauth.core.client.util.http.Response;

/**
 * Gitee user.
 *
 * <p>Take the author as an example:
 *
 * <pre>
 * {
 *     "id":1937041,
 *     "login":"wautsns",
 *     "name":"独自漫步〃寂静の夜空下",
 *     "avatar_url":"https://avatar.gitee.com/uploads/41/1937041_wautsns.png?1526391243",
 *     "url":"https://gitee.com/api/v5/users/wautsns",
 *     "html_url":"https://gitee.com/wautsns",
 *     "followers_url":"https://gitee.com/api/v5/users/wautsns/followers",
 *     "following_url":"https://gitee.com/api/v5/users/wautsns/following_url{/other_user}",
 *     "gists_url":"https://gitee.com/api/v5/users/wautsns/gists{/gist_id}",
 *     "starred_url":"https://gitee.com/api/v5/users/wautsns/starred{/owner}{/repo}",
 *     "subscriptions_url":"https://gitee.com/api/v5/users/wautsns/subscriptions",
 *     "organizations_url":"https://gitee.com/api/v5/users/wautsns/orgs",
 *     "repos_url":"https://gitee.com/api/v5/users/wautsns/repos",
 *     "events_url":"https://gitee.com/api/v5/users/wautsns/events{/privacy}",
 *     "received_events_url":"https://gitee.com/api/v5/users/wautsns/received_events",
 *     "type":"User",
 *     "site_admin":false,
 *     "blog":"",
 *     "weibo":"",
 *     "bio":"",
 *     "public_repos":0,
 *     "public_gists":0,
 *     "followers":0,
 *     "following":0,
 *     "stared":0,
 *     "watched":1,
 *     "created_at":"2018-05-15T21:27:41+08:00",
 *     "updated_at":"2019-11-18T23:01:11+08:00",
 *     "email":null
 * }
 * </pre>
 *
 * @author wautsns
 */
@JsonNaming(SnakeCaseStrategy.class)
public class GiteeUser extends OAuthUser {

    /**
     * Construct a Gitee user.
     *
     * @param response response, require nonnull
     */
    public GiteeUser(Response response) {
        super(response);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatform.GITEE;
    }

    @Override
    public String getOpenId() {
        return getString("id");
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
