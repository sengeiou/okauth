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

import com.github.wautsns.okauth.core.client.kernel.OAuth2AppInfo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Gitee oauth2 app info.
 *
 * @author wautsns
 * @since May 17, 2020
 */
@Data
@Accessors(chain = true)
public class GiteeOAuth2AppInfo implements OAuth2AppInfo {

    /** Client id. */
    private String clientId;
    /** Client secret. */
    private String clientSecret;
    /** Redirect uri. */
    private String redirectUri;
    /** The list of permissions. */
    private List<Scope> scopes;

    /**
     * Scope.
     *
     * @see <a href="https://gitee.com/api/v5/oauth_doc#/">Scope list.</a>
     */
    @RequiredArgsConstructor
    public enum Scope {

        /** Access user's personal information, latest news, etc. */
        USER_INFO("user_info"),
        /** View, create, and update user's projects. */
        PROJECTS("projects"),
        /** View, publish, and update user's Pull Request. */
        PULL_REQUESTS("pull_requests"),
        /** View, publish, and update user issues. */
        ISSUES("issues"),
        /** View, post, and manage user comments in projects and code snippets. */
        NOTES("notes"),
        /** View, deploy, and delete a user's public key. */
        KEYS("keys"),
        /** View, deploy, and update users' webhooks. */
        HOOK("hook"),
        /** View and manage user organizations and members. */
        GROUPS("groups"),
        /** View, delete, and update user code snippets. */
        GISTS("gists"),
        /** View user's personal mailbox information. */
        ENTERPRISES("enterprises");

        /** Value. */
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
