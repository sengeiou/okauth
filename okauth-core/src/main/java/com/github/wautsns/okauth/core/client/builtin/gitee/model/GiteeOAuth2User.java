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
package com.github.wautsns.okauth.core.client.builtin.gitee.model;

import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatformNames;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2User;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * GitHub oauth2 token.
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
 * @since May 17, 2020
 */
@Data
public class GiteeOAuth2User implements OAuth2User {

    private static final long serialVersionUID = 7155633421437700449L;

    private final DataMap origin;

    @Override
    public String getOpenPlatform() {
        return BuiltInOpenPlatformNames.GITEE;
    }

    public Long getId() {
        return origin.getAsLong("id");
    }

    public String getLogin() {
        return origin.getAsString("login");
    }

    public String getName() {
        return origin.getAsString("name");
    }

    @Override
    public String getAvatarUrl() {
        return origin.getAsString("avatar_url");
    }

    public String getUrl() {
        return origin.getAsString("url");
    }

    public String getHtmlUrl() {
        return origin.getAsString("html_url");
    }

    public String getFollowersUrl() {
        return origin.getAsString("followers_url");
    }

    public String getFollowingUrl() {
        return origin.getAsString("following_url");
    }

    public String getGistsUrl() {
        return origin.getAsString("gists_url");
    }

    public String getStarredUrl() {
        return origin.getAsString("starredUrl");
    }

    public String getSubscriptionsUrl() {
        return origin.getAsString("subscriptions_url");
    }

    public String getOrganizationsUrl() {
        return origin.getAsString("organizations_url");
    }

    public String getReposUrl() {
        return origin.getAsString("repos_url");
    }

    public String getEventsUrl() {
        return origin.getAsString("events_url");
    }

    public String getReceivedEventsUrl() {
        return origin.getAsString("received_events_url");
    }

    public String getType() {
        return origin.getAsString("type");
    }

    public Boolean getSiteAdmin() {
        return origin.getAsBoolean("site_admin");
    }

    public String getBlog() {
        return origin.getAsString("blog");
    }

    public String getWeibo() {
        return origin.getAsString("weibo");
    }

    public String getBio() {
        return origin.getAsString("bio");
    }

    public Integer getPublicRepos() {
        return origin.getAsInteger("public_repos");
    }

    public Integer getPublicGists() {
        return origin.getAsInteger("public_gists");
    }

    public Integer getFollowers() {
        return origin.getAsInteger("followers");
    }

    public Integer getFollowings() {
        return origin.getAsInteger("followings");
    }

    public Integer getStared() {
        return origin.getAsInteger("stared");
    }

    public Integer getWatched() {
        return origin.getAsInteger("watched");
    }

    public LocalDateTime getCreatedAt() {
        return LocalDateTime.parse(origin.getAsString("created_at"));
    }

    public LocalDateTime getUpdatedAt() {
        return LocalDateTime.parse(origin.getAsString("updated_at"));
    }

    @Override
    public String getEmail() {
        return origin.getAsString("email");
    }

    // #################### - ###########################################################

    @Override
    public String getOpenid() {
        return getId().toString();
    }

    @Override
    public String getUid() {
        return getId().toString();
    }

    @Override
    public String getUsername() {
        return getLogin();
    }

    @Override
    public String getNickname() {
        return getName();
    }

}
