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
package com.github.wautsns.okauth.core.client.builtin.github;

import com.github.wautsns.okauth.core.client.kernel.OAuth2AppInfo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Collection;
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
public class GitHubOAuth2AppInfo implements OAuth2AppInfo {

    /** Client id. */
    private String clientId;
    /** Client secret. */
    private String clientSecret;
    /** Redirect uri. */
    private String redirectUri;
    /**
     * A list of scopes. If not provided, scope defaults to an empty list for users that have not authorized any scopes
     * for the application. For users who have authorized scopes for the application, the user won't be shown the OAuth
     * authorization page with the list of scopes. Instead, this step of the flow will automatically complete with the
     * set of scopes the user has authorized for the application. For example, if a user has already performed the web
     * flow twice and has authorized one token with user scope and another token with repo scope, a third web flow that
     * does not provide a scope will receive a token with user and repo scope.
     */
    private List<Scope> scopes;
    /** Extra authorize url query. */
    private final ExtraAuthorizeUrlQuery extraAuthorizeUrlQuery = new ExtraAuthorizeUrlQuery();

    /**
     * Scope.
     *
     * @see <a href="https://developer.github.com/apps/building-oauth-apps/understanding-scopes-for-oauth-apps/">Scope
     * list.</a>
     */
    @RequiredArgsConstructor
    public enum Scope {

        /**
         * Grants full access to private and public repositories. That includes read/write access to code, commit
         * statuses, repository and organization projects, invitations, collaborators, adding team memberships,
         * deployment statuses, and repository webhooks for public and private repositories and organizations. Also
         * grants ability to manage user projects.
         */
        REPO("repo"),
        /**
         * Grants read/write access to public and private repository commit statuses. This scope is only necessary to
         * grant other users or services access to private repository commit statuses without granting access to the
         * code.
         */
        REPO_STATUS("repo:status"),
        /**
         * Grants access to deployment statuses for public and private repositories. This scope is only necessary to
         * grant other users or services access to deployment statuses, without granting access to the code.
         */
        REPO_DEPLOYMENT("repo_deployment"),
        /**
         * Limits access to public repositories. That includes read/write access to code, commit statuses, repository
         * projects, collaborators, and deployment statuses for public repositories and organizations. Also required for
         * starring public repositories.
         */
        PUBLIC_REPO("public_repo"),
        /**
         * Grants accept/decline abilities for invitations to collaborate on a repository. This scope is only necessary
         * to grant other users or services access to invites without granting access to the code.
         */
        REPO_INVITE("repo:invite"),
        /** Grants read and write access to security events in the code scanning API. */
        SECURITY_EVENTS("security_events"),
        /**
         * Grants read, write, ping, and delete access to repository hooks in public and private repositories. The repo
         * and public_repo scopes grants full access to repositories, including repository hooks. Use the
         * admin:repo_hook scope to limit access to only repository hooks.
         */
        ADMIN_REPO_HOOK("admin:repo_hook"),
        /** Grants read, write, and ping access to hooks in public or private repositories. */
        WRITE_REPO_HOOK("write:repo_hook"),
        /** Grants read and ping access to hooks in public or private repositories. */
        READ_REPO_HOOK("read:repo_hook"),
        /** Fully manage the organization and its teams, projects, and memberships. */
        ADMIN_ORG("admin:org"),
        /** Read and write access to organization membership, organization projects, and team membership. */
        WRITE_ORG("write:org"),
        /** Read-only access to organization membership, organization projects, and team membership. */
        READ_ORG("read:org"),
        /** Fully manage public keys. */
        ADMIN_PUBLIC_KEY("admin:public_key"),
        /** Create, list, and view details for public keys. */
        WRITE_PUBLIC_KEY("write:public_key"),
        /** List and view details for public keys. */
        READ_PUBLIC_KEY("read:public_key"),
        /**
         * Grants read, write, ping, and delete access to organization hooks. Note: OAuth tokens will only be able to
         * perform these actions on organization hooks which were created by the OAuth App. Personal access tokens will
         * only be able to perform these actions on organization hooks created by a user.
         */
        ADMIN_ORG_HOOK("admin:org_hook"),
        /** Grants write access to gists. */
        GIST("gist"),
        /**
         * Grants: Read access to a user's notifications; Mark as read access to threads; Watch and unwatch access to a
         * repository; Read, write, and delete access to thread subscriptions.
         */
        NOTIFICATIONS("notifications"),
        /** Grants read/write access to profile info only. Note that this scope includes user:email and user:follow. */
        USER("user"),
        /** Grants access to read a user's profile data. */
        READ_USER("read:user"),
        /** Grants read access to a user's email addresses. */
        USER_EMAIL("user:email"),
        /** Grants access to follow or unfollow other users. */
        USER_FOLLOW("user:follow"),
        /** Grants access to delete adminable repositories. */
        DELETE_REPO("delete_repo"),
        /** Allows read and write access for team discussions. */
        WRITE_DISCUSSION("write:discussion"),
        /** Allows read access for team discussions. */
        READ_DISCUSSION("read:discussion"),
        /**
         * Grants access to upload or publish a package in GitHub Packages. For more information, see "Publishing a
         * package" in the GitHub Help documentation.
         */
        WRITE_PACKAGES("write:packages"),
        /**
         * Grants access to download or install packages from GitHub Packages. For more information, see "Installing a
         * package" in the GitHub Help documentation.
         */
        READ_PACKAGES("read:packages"),
        /**
         * Grants access to delete packages from GitHub Packages. For more information, see "Deleting packages" in the
         * GitHub Help documentation.
         */
        DELETE_PACKAGES("delete:packages"),
        /** Fully manage GPG keys. */
        ADMIN_GPG_KEY("admin:gpg_key"),
        /** Create, list, and view details for GPG keys. */
        WRITE_GPG_KEY("write:gpg_key"),
        /** List and view details for GPG keys. */
        READ_GPG_KEY("read:gpg_key"),
        /**
         * Grants the ability to add and update GitHub Actions workflow files. Workflow files can be committed without
         * this scope if the same file (with both the same path and contents) exists on another branch in the same
         * repository.
         */
        WORK_FLOW("workflow");

        /** Value. */
        public final String value;

        /**
         * Join scopes with specified delimiter.
         *
         * @param scopes scopes
         * @param delimiter delimiter
         * @return space-separated list of scopes
         */
        public static String joinWith(Collection<Scope> scopes, String delimiter) {
            if (scopes == null || scopes.isEmpty()) { return null; }
            return scopes.stream()
                    .distinct()
                    .map(s -> s.value)
                    .collect(Collectors.joining(delimiter));
        }

    }

    /** Extra authorize url query. */
    @Data
    @Accessors(chain = true)
    public static class ExtraAuthorizeUrlQuery {

        /** Suggests a specific account to use for signing in and authorizing the app. */
        private String login;
        /** See {@link AllowSignup} for details. */
        private AllowSignup allowSignup = AllowSignup.DEFAULT;

        /**
         * Allow signup.
         *
         * <ul>
         * <li>{@link AllowSignup#DEFAULT}</li>
         * <li>{@link AllowSignup#ENABLED}</li>
         * <li>{@link AllowSignup#DISABLED}</li>
         * </ul>
         */
        @RequiredArgsConstructor
        public enum AllowSignup {

            /** Use default value(null). */
            DEFAULT(null),
            /** The value is equal to {@linkplain AllowSignup#DEFAULT}. */
            ENABLED(null),
            /** Unauthenticated users will not be offered an option to sign up for GitHub during the OAuth flow. */
            DISABLED("false");

            /** Value. */
            public final String value;

        }

    }

}
