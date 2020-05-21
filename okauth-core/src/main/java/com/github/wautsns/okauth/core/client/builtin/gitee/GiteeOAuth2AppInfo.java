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

import java.util.List;
import java.util.stream.Collectors;

/**
 * GitHub oauth2 app info.
 *
 * @author wautsns
 * @since May 17, 2020
 */
@Data
@Accessors(chain = true)
public class GiteeOAuth2AppInfo implements OAuth2AppInfo {

    private String clientId;
    private String clientSecret;
    private String redirectUri;
    /** See {@link Scope} for details. */
    private List<Scope> scope;

    /** The scope indicates the scope of permissions, separated by spaces when requesting. */
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

        /** value */
        public final String value;

        /**
         * Join scope with space.
         *
         * @param scope scope set
         * @return space-separated list of scopes
         */
        public static String join(List<Scope> scope) {
            if (scope == null || scope.isEmpty()) { return null; }
            return scope.stream()
                    .map(s -> s.value)
                    .collect(Collectors.joining(" "));
        }

    }

}
