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
package com.github.wautsns.okauth.core.client.builtin.baidu;

import com.github.wautsns.okauth.core.client.kernel.OAuth2AppInfo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Baidu oauth2 app info.
 *
 * @author wautsns
 * @since May 17, 2020
 */
@Data
@Accessors(chain = true)
public class BaiduOAuth2AppInfo implements OAuth2AppInfo {

    /** Api key. */
    private String apiKey;
    /** Secret key. */
    private String secretKey;
    /** Redirect uri. */
    private String redirectUri;
    /** See {@link Scope} for details. */
    private List<Scope> scope;
    /** Extra authorize url query. */
    private final ExtraAuthorizeUrlQuery extraAuthorizeUrlQuery = new ExtraAuthorizeUrlQuery();

    // #################### enum ########################################################

    /** The space-separated list of permissions, if null, represents the default permissions of the requesting user. */
    @RequiredArgsConstructor
    public enum Scope {

        /** Basic user rights, you can get the user's basic information. */
        BASIC("basic"),
        /**
         * Send a message reminder to the user's Baidu homepage, any application related to the API can be used, but to
         * display the message reminder on the Baidu homepage, a third party needs to fill in additional relevant
         * information when registering the application.
         */
        SUPER_MSG("super_msg"),
        /** Obtain user data stored in personal cloud storage. */
        NETDISK("netdisk"),
        /** Have access to public open APIs. */
        PUBLIC("public"),
        /**
         * You can access the open API interface provided by Hao123. This permission needs to be applied for activation.
         * Please send specific reasons and uses to tuuangou@baidu.com.
         */
        HAO123("hao123");

        /** Value. */
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

    // #################### extraAuthorizeUrlQuery ######################################

    /** Extra authorize url query. */
    @Data
    @Accessors(chain = true)
    public static class ExtraAuthorizeUrlQuery {

        /** See {@link Display} for details. */
        private Display display = Display.DEFAULT;
        /** See {@link ForceLogin} for details. */
        private ForceLogin forceLogin = ForceLogin.DEFAULT;
        /** See {@link ConfirmLogin} for details. */
        private ConfirmLogin confirmLogin = ConfirmLogin.DEFAULT;
        /** See {@link LoginType} for details. */
        private LoginType loginType = LoginType.DEFAULT;

        // #################### enum ########################################################

        /**
         * The display style of the login and authorization pages.
         *
         * <p>If you need to get `display` <strong>dynamically</strong>, please use {@linkplain DisplaySupplier
         * DisplaySupplier}.
         */
        @RequiredArgsConstructor
        public enum Display {

            /** Use default value(null). */
            DEFAULT(null),
            /** Full screen authorization page (default), suitable for web applications. */
            PAGE("page"),
            /**
             * The authorization page in the form of a pop-up box is suitable for desktop software applications and web
             * applications.
             */
            POPUP("popup"),
            /**
             * The authorization page in the form of a floating layer can only be used for web applications within the
             * station.
             */
            DIALOG("dialog"),
            /**
             * The authorization page used on smart mobile terminals such as IPhone/Android is suitable for applications
             * on smart mobile terminals such as IPhone/Android.
             */
            MOBILE("mobile"),
            /**
             * +The authorization page used on tablets such as IPad/Android is suitable for applications on smart mobile
             * terminals such as IPad/Android.
             */
            PAD("pad"),
            /** Authorization page for large screens such as TVs. */
            TV("tv");

            /** Value. */
            public final String value;

        }

        /**
         * Force login.
         * <ul>
         * optional values:
         * <li>{@link ForceLogin#DEFAULT}</li>
         * <li>{@link ForceLogin#ENABLED}</li>
         * <li>{@link ForceLogin#DISABLED}</li>
         * </ul>
         */
        @RequiredArgsConstructor
        public enum ForceLogin {

            /** Use default value(null). */
            DEFAULT(null),
            /**
             * The user is forced to enter the user name and password when loading the login page, and the Baidu user's
             * login status will not be read from the cookie.
             */
            ENABLED("1"),
            /** The value is equal to {@linkplain ConfirmLogin#DEFAULT}. */
            DISABLED(null);

            /** Value. */
            public final String value;

        }

        /**
         * Confirm login.
         * <ul>
         * optional values:
         * <li>{@link ConfirmLogin#DEFAULT}</li>
         * <li>{@link ConfirmLogin#ENABLED}</li>
         * <li>{@link ConfirmLogin#DISABLED}</li>
         * </ul>
         */
        @RequiredArgsConstructor
        public enum ConfirmLogin {

            /** Use default value(null). */
            DEFAULT(null),
            /**
             * If the Baidu user is already logged in, it will prompt whether to use the currently logged in user to
             * authorize the application.
             */
            ENABLED("1"),
            /** The value is equal to {@linkplain ConfirmLogin#DEFAULT}. */
            DISABLED(null);

            /** Value. */
            public final String value;

        }

        /**
         * Login type.
         * <ul>
         * optional values:
         * <li>{@link LoginType#DEFAULT}</li>
         * <li>{@link LoginType#SMS}</li>
         * </ul>
         */
        @RequiredArgsConstructor
        public enum LoginType {

            /** Use default value(null). */
            DEFAULT(null),
            /** The authorization page will use the SMS dynamic password to register the login method by default. */
            SMS("sms");

            /** Value. */
            public final String value;

        }

        // #################### service #####################################################

        /** Display supplier. */
        public interface DisplaySupplier {

            /**
             * Get display.
             *
             * @return display
             * @see #getDisplay()
             */
            Display get(String state);

            // #################### default #####################################################

            /** Default display supplier. */
            DisplaySupplier DEFAULT = state -> Display.DEFAULT;

        }

    }

}
