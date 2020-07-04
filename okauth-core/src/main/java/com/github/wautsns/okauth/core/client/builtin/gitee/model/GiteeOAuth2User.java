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
import com.github.wautsns.okauth.core.client.kernel.openplatform.OpenPlatform;

import java.time.LocalDateTime;

/**
 * Gitee oauth2 user.
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
public class GiteeOAuth2User extends OAuth2User {

    private static final long serialVersionUID = -2925874836823140573L;

    /**
     * Construct a Gitee oauth2 user.
     *
     * @param originalDataMap original data map
     */
    public GiteeOAuth2User(DataMap originalDataMap) {
        super(originalDataMap);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatformNames.GITEE;
    }

    public String getId() {
        return getOriginalDataMap().getAsString("id");
    }

    public String getLogin() {
        return getOriginalDataMap().getAsString("login");
    }

    public String getName() {
        return getOriginalDataMap().getAsString("name");
    }

    @Override
    public String getAvatarUrl() {
        return getOriginalDataMap().getAsString("avatar_url");
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

    public String getBlog() {
        return getOriginalDataMap().getAsString("blog");
    }

    public String getWeibo() {
        return getOriginalDataMap().getAsString("weibo");
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

    public Integer getStared() {
        return getOriginalDataMap().getAsInteger("stared");
    }

    public Integer getWatched() {
        return getOriginalDataMap().getAsInteger("watched");
    }

    public LocalDateTime getCreatedAt() {
        return getOriginalDataMap().getAsLocalDateTime("created_at");
    }

    public LocalDateTime getUpdatedAt() {
        return getOriginalDataMap().getAsLocalDateTime("updated_at");
    }

    @Override
    public String getEmail() {
        return getOriginalDataMap().getAsString("email");
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
