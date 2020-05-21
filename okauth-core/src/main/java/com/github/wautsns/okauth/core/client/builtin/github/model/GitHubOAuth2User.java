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
package com.github.wautsns.okauth.core.client.builtin.github.model;

import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatformNames;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * GitHub oauth2 token.
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
 *     "followin`g_url": "https://api.github.com/users/wautsns/following{/other_user}",
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
 * @author wautsns
 * @since May 17, 2020
 */
@Data
public class GitHubOAuth2User implements OAuth2User {

    private static final long serialVersionUID = 7155633421437700449L;

    private final DataMap origin;

    @Override
    public String getOpenPlatform() {
        return BuiltInOpenPlatformNames.GIT_HUB;
    }

    public String getLogin() {
        return origin.getAsString("login");
    }

    public Long getId() {
        return origin.getAsLong("id");
    }

    public String getNodeId() {
        return origin.getAsString("node_id");
    }

    @Override
    public String getAvatarUrl() {
        return origin.getAsString("avatar_url");
    }

    public String getGravatarId() {
        return origin.getAsString("gravatar_id");
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

    public String getName() {
        return origin.getAsString("name");
    }

    public String getCompany() {
        return origin.getAsString("company");
    }

    public String getBlog() {
        return origin.getAsString("blog");
    }

    public String getLocation() {
        return origin.getAsString("location");
    }

    @Override
    public String getEmail() {
        return origin.getAsString("email");
    }

    public Boolean getHireable() {
        return origin.getAsBoolean("hireable");
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

    public LocalDateTime getCreatedAt() {
        return LocalDateTime.parse(origin.getAsString("created_at"));
    }

    public LocalDateTime getUpdatedAt() {
        return LocalDateTime.parse(origin.getAsString("updated_at"));
    }

    // #################### - ###########################################################

    @Override
    public String getOpenid() {
        return getUid();
    }

    @Override
    public String getUid() {
        return Objects.toString(getId(), null);
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
