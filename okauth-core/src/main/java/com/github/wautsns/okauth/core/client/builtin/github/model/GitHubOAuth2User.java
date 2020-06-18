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

/**
 * GitHub oauth2 token.
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
@Data
public class GitHubOAuth2User implements OAuth2User {

    private static final long serialVersionUID = 7155633421437700449L;

    /** Original data map. */
    private final DataMap originalDataMap;

    @Override
    public String getOpenPlatform() {
        return BuiltInOpenPlatformNames.GITHUB;
    }

    public String getLogin() {
        return originalDataMap.getAsString("login");
    }

    public String getId() {
        return originalDataMap.getAsString("id");
    }

    public String getNodeId() {
        return originalDataMap.getAsString("node_id");
    }

    @Override
    public String getAvatarUrl() {
        return originalDataMap.getAsString("avatar_url");
    }

    public String getGravatarId() {
        return originalDataMap.getAsString("gravatar_id");
    }

    public String getUrl() {
        return originalDataMap.getAsString("url");
    }

    public String getHtmlUrl() {
        return originalDataMap.getAsString("html_url");
    }

    public String getFollowersUrl() {
        return originalDataMap.getAsString("followers_url");
    }

    public String getFollowingUrl() {
        return originalDataMap.getAsString("following_url");
    }

    public String getGistsUrl() {
        return originalDataMap.getAsString("gists_url");
    }

    public String getStarredUrl() {
        return originalDataMap.getAsString("starredUrl");
    }

    public String getSubscriptionsUrl() {
        return originalDataMap.getAsString("subscriptions_url");
    }

    public String getOrganizationsUrl() {
        return originalDataMap.getAsString("organizations_url");
    }

    public String getReposUrl() {
        return originalDataMap.getAsString("repos_url");
    }

    public String getEventsUrl() {
        return originalDataMap.getAsString("events_url");
    }

    public String getReceivedEventsUrl() {
        return originalDataMap.getAsString("received_events_url");
    }

    public String getType() {
        return originalDataMap.getAsString("type");
    }

    public Boolean getSiteAdmin() {
        return originalDataMap.getAsBoolean("site_admin");
    }

    public String getName() {
        return originalDataMap.getAsString("name");
    }

    public String getCompany() {
        return originalDataMap.getAsString("company");
    }

    public String getBlog() {
        return originalDataMap.getAsString("blog");
    }

    public String getLocation() {
        return originalDataMap.getAsString("location");
    }

    @Override
    public String getEmail() {
        return originalDataMap.getAsString("email");
    }

    public Boolean getHireable() {
        return originalDataMap.getAsBoolean("hireable");
    }

    public String getBio() {
        return originalDataMap.getAsString("bio");
    }

    public Integer getPublicRepos() {
        return originalDataMap.getAsInteger("public_repos");
    }

    public Integer getPublicGists() {
        return originalDataMap.getAsInteger("public_gists");
    }

    public Integer getFollowers() {
        return originalDataMap.getAsInteger("followers");
    }

    public Integer getFollowings() {
        return originalDataMap.getAsInteger("followings");
    }

    public LocalDateTime getCreatedAt() {
        return originalDataMap.getAsLocalDateTime("created_at");
    }

    public LocalDateTime getUpdatedAt() {
        return originalDataMap.getAsLocalDateTime("updated_at");
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
