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
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatforms;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2User;
import com.github.wautsns.okauth.core.client.kernel.openplatform.OpenPlatform;

import java.time.LocalDateTime;

/**
 * GitHub oauth2 user.
 *
 * <pre>
 * {
 * 	"login": "wautsns",
 * 	"id": 39336604,
 * 	"node_id": "MDQ6VXNlcjM5MzM2NjA0",
 * 	"avatar_url": "https://avatars2.githubusercontent.com/u/39336604?v=4",
 * 	"gravatar_id": "",
 * 	"url": "https://api.github.com/users/wautsns",
 * 	"html_url": "https://github.com/wautsns",
 * 	"followers_url": "https://api.github.com/users/wautsns/followers",
 * 	"following_url": "https://api.github.com/users/wautsns/following{/other_user}",
 * 	"gists_url": "https://api.github.com/users/wautsns/gists{/gist_id}",
 * 	"starred_url": "https://api.github.com/users/wautsns/starred{/owner}{/repo}",
 * 	"subscriptions_url": "https://api.github.com/users/wautsns/subscriptions",
 * 	"organizations_url": "https://api.github.com/users/wautsns/orgs",
 * 	"repos_url": "https://api.github.com/users/wautsns/repos",
 * 	"events_url": "https://api.github.com/users/wautsns/events{/privacy}",
 * 	"received_events_url": "https://api.github.com/users/wautsns/received_events",
 * 	"type": "User",
 * 	"site_admin": false,
 * 	"name": "wautsns",
 * 	"company": null,
 * 	"blog": "",
 * 	"location": null,
 * 	"email": null,
 * 	"hireable": null,
 * 	"bio": null,
 * 	"public_repos": 8,
 * 	"public_gists": 0,
 * 	"followers": 0,
 * 	"following": 0,
 * 	"created_at": "2018-05-16T12:17:46Z",
 * 	"updated_at": "2020-05-22T10:44:23Z"
 * }
 * </pre>
 *
 * @author wautsns
 * @since May 17, 2020
 */
public class GitHubOAuth2User extends OAuth2User {

    private static final long serialVersionUID = 2546759827060779258L;

    /**
     * Construct a GitHub oauth2 user.
     *
     * @param originalDataMap original data map
     */
    public GitHubOAuth2User(DataMap originalDataMap) {
        super(originalDataMap);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatforms.GITHUB;
    }

    public String getLogin() {
        return getOriginalDataMap().getAsString("login");
    }

    public String getId() {
        return getOriginalDataMap().getAsString("id");
    }

    public String getNodeId() {
        return getOriginalDataMap().getAsString("node_id");
    }

    @Override
    public String getAvatarUrl() {
        return getOriginalDataMap().getAsString("avatar_url");
    }

    public String getGravatarId() {
        return getOriginalDataMap().getAsString("gravatar_id");
    }

    public String getUrl() {
        return getOriginalDataMap().getAsString("url");
    }

    public String getHtmlUrl() {
        return getOriginalDataMap().getAsString("html_url");
    }

    public String getFollowersUrl() {
        return getOriginalDataMap().getAsString("followers_url");
    }

    public String getFollowingUrl() {
        return getOriginalDataMap().getAsString("following_url");
    }

    public String getGistsUrl() {
        return getOriginalDataMap().getAsString("gists_url");
    }

    public String getStarredUrl() {
        return getOriginalDataMap().getAsString("starredUrl");
    }

    public String getSubscriptionsUrl() {
        return getOriginalDataMap().getAsString("subscriptions_url");
    }

    public String getOrganizationsUrl() {
        return getOriginalDataMap().getAsString("organizations_url");
    }

    public String getReposUrl() {
        return getOriginalDataMap().getAsString("repos_url");
    }

    public String getEventsUrl() {
        return getOriginalDataMap().getAsString("events_url");
    }

    public String getReceivedEventsUrl() {
        return getOriginalDataMap().getAsString("received_events_url");
    }

    public String getType() {
        return getOriginalDataMap().getAsString("type");
    }

    public Boolean getSiteAdmin() {
        return getOriginalDataMap().getAsBoolean("site_admin");
    }

    public String getName() {
        return getOriginalDataMap().getAsString("name");
    }

    public String getCompany() {
        return getOriginalDataMap().getAsString("company");
    }

    public String getBlog() {
        return getOriginalDataMap().getAsString("blog");
    }

    public String getLocation() {
        return getOriginalDataMap().getAsString("location");
    }

    @Override
    public String getEmail() {
        return getOriginalDataMap().getAsString("email");
    }

    public Boolean getHireable() {
        return getOriginalDataMap().getAsBoolean("hireable");
    }

    public String getBio() {
        return getOriginalDataMap().getAsString("bio");
    }

    public Integer getPublicRepos() {
        return getOriginalDataMap().getAsInteger("public_repos");
    }

    public Integer getPublicGists() {
        return getOriginalDataMap().getAsInteger("public_gists");
    }

    public Integer getFollowers() {
        return getOriginalDataMap().getAsInteger("followers");
    }

    public Integer getFollowings() {
        return getOriginalDataMap().getAsInteger("followings");
    }

    public LocalDateTime getCreatedAt() {
        return getOriginalDataMap().getAsLocalDateTime("created_at");
    }

    public LocalDateTime getUpdatedAt() {
        return getOriginalDataMap().getAsLocalDateTime("updated_at");
    }

    // #################### amendment ###################################################

    @Override
    public String getOpenid() {
        return getUid();
    }

    @Override
    public String getUid() {
        return getId();
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
