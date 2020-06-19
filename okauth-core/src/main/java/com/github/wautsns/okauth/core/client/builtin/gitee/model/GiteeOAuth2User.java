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
 * GitHub oauth2 user.
 *
 * <pre>
 * {
 * 	"id": 1937041,
 * 	"login": "wautsns",
 * 	"name": "独自漫步〃寂静の夜空下",
 * 	"avatar_url": "https://portrait.gitee.com/uploads/avatars/user/645/1937041_wautsns_1578962737.png",
 * 	"url": "https://gitee.com/api/v5/users/wautsns",
 * 	"html_url": "https://gitee.com/wautsns",
 * 	"followers_url": "https://gitee.com/api/v5/users/wautsns/followers",
 * 	"following_url": "https://gitee.com/api/v5/users/wautsns/following_url{/other_user}",
 * 	"gists_url": "https://gitee.com/api/v5/users/wautsns/gists{/gist_id}",
 * 	"starred_url": "https://gitee.com/api/v5/users/wautsns/starred{/owner}{/repo}",
 * 	"subscriptions_url": "https://gitee.com/api/v5/users/wautsns/subscriptions",
 * 	"organizations_url": "https://gitee.com/api/v5/users/wautsns/orgs",
 * 	"repos_url": "https://gitee.com/api/v5/users/wautsns/repos",
 * 	"events_url": "https://gitee.com/api/v5/users/wautsns/events{/privacy}",
 * 	"received_events_url": "https://gitee.com/api/v5/users/wautsns/received_events",
 * 	"type": "User",
 * 	"site_admin": false,
 * 	"blog": null,
 * 	"weibo": null,
 * 	"bio": "",
 * 	"public_repos": 0,
 * 	"public_gists": 0,
 * 	"followers": 0,
 * 	"following": 0,
 * 	"stared": 0,
 * 	"watched": 3,
 * 	"created_at": "2018-05-15T21:27:41+08:00",
 * 	"updated_at": "2020-05-23T18:33:25+08:00",
 * 	"email": null
 * }
 * </pre>
 *
 * @author wautsns
 * @since May 17, 2020
 */
@Data
public class GiteeOAuth2User implements OAuth2User {

    private static final long serialVersionUID = -2925874836823140573L;

    /** Original data map. */
    private final DataMap originalDataMap;

    @Override
    public String getOpenPlatform() {
        return BuiltInOpenPlatformNames.GITEE;
    }

    public String getId() {
        return originalDataMap.getAsString("id");
    }

    public String getLogin() {
        return originalDataMap.getAsString("login");
    }

    public String getName() {
        return originalDataMap.getAsString("name");
    }

    @Override
    public String getAvatarUrl() {
        return originalDataMap.getAsString("avatar_url");
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

    public String getBlog() {
        return originalDataMap.getAsString("blog");
    }

    public String getWeibo() {
        return originalDataMap.getAsString("weibo");
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

    public Integer getStared() {
        return originalDataMap.getAsInteger("stared");
    }

    public Integer getWatched() {
        return originalDataMap.getAsInteger("watched");
    }

    public LocalDateTime getCreatedAt() {
        return originalDataMap.getAsLocalDateTime("created_at");
    }

    public LocalDateTime getUpdatedAt() {
        return originalDataMap.getAsLocalDateTime("updated_at");
    }

    @Override
    public String getEmail() {
        return originalDataMap.getAsString("email");
    }

    // #################### amendment ###################################################

    @Override
    public String getOpenid() {
        return getId();
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
