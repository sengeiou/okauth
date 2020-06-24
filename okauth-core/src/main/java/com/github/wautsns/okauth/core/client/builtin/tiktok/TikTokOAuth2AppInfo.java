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
package com.github.wautsns.okauth.core.client.builtin.tiktok;

import com.github.wautsns.okauth.core.client.kernel.OAuth2AppInfo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TikTok oauth2 app info.
 *
 * @author wautsns
 * @since Jun 23, 2020
 */
@Data
@Accessors(chain = true)
public class TikTokOAuth2AppInfo implements OAuth2AppInfo {

    /** Client key. */
    private String clientKey;
    /** Client secret. */
    private String clientSecret;
    /** Redirect uri. */
    private String redirectUri;
    /** The list of permissions. */
    private List<Scope> scopes;

    /** Scope. */
    @RequiredArgsConstructor
    public enum Scope {

        LOGIN_ID("login_id"),
        USER_INFO("user_info"),
        FANS_LIST("fans.list"),
        FANS_DATA("fans.data"),
        FOLLOWING_LIST("following.list"),
        IM("im"),
        HOT_SEARCH("hotsearch"),
        AWEME_SHARE("aweme.share"),
        DISCOVERY_ENT("discovery.ent"),
        VIDEO_CREATE("video.create"),
        VIDEO_LIST("video.list"),
        VIDEO_DATE("video.data"),
        VIDEO_DELETE("video.delete"),
        VIDEO_COMMENT("video.comment"),
        POI_BASE("poi.base"),
        POI_SEARCH("poi.search"),
        POI_PRODUCT("poi.product"),
        STAR_TOPS("star_tops"),
        STAR_TOP_SCORE_DISPLAY("star_top_score_display"),
        STAR_AUTHOR_SCORE_DISPLAY("star_author_score_display"),
        ENTERPRISE_IM("enterprise.im"),
        ENTERPRISE_DATA("enterprise.data"),
        ENTERPRISE_GROUPON("enterprise.groupon"),
        DATA_EXTERNAL_USER("data.external.user"),
        DATA_EXTERNAL_POI("data.external.poi"),
        DATA_EXTERNAL_ITEM("data.external.item"),
        DATA_EXTERNAL_SDK_SHARE("data.external.sdk_share");

        public final String value;

        /**
         * Join scopes with specified delimiter.
         *
         * @param scopes scopes
         * @param delimiter delimiter
         * @return string of scopes with specified delimiter
         */
        public static String joinWith(Collection<Scope> scopes, String delimiter) {
            if (scopes == null || scopes.isEmpty()) { return null; }
            return scopes.stream()
                    .distinct()
                    .map(scope -> scope.value)
                    .collect(Collectors.joining(delimiter));
        }

    }

}
