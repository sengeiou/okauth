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
package com.github.wautsns.okauth.spring.boot.autoconfigure.properties;

import com.github.wautsns.okauth.core.client.builtin.baidu.BaiduOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.builtin.dingtalk.DingTalkOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.builtin.gitee.GiteeOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.builtin.github.GitHubOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.builtin.oschina.OSChinaOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.builtin.tiktok.TikTokOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.builtin.wechatofficialaccount.WechatOfficialAccountOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.builtin.wechatworkcorp.WechatWorkCorpOAuth2AppInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * OAuth2 app info properties.
 *
 * @author wautsns
 * @since May 19, 2020
 */
@Data
@Accessors(chain = true)
public class OkAuthAppsInfoProperties {

    /** Baidu app info properties. */
    private OkAuthBaiduAppInfo baidu;
    /** DingTalk app info properties. */
    private OkAuthDingTalkAppInfo dingTalk;
    /** Gitee app info properties. */
    private OkAuthGiteeAppInfo gitee;
    /** GitHub app info properties. */
    private OkAuthGitHubAppInfo github;
    /** OSChina app info properties. */
    private OkAuthOSChinaAppInfo oschina;
    /** TikTok app info properties. */
    private OkAuthTikTokAppInfo tikTok;
    /** WechatOfficialAccount app info properties. */
    private OkAuthWechatOfficialAccountAppInfo wechatOfficialAccount;
    /** WechatWorkCorp app info properties. */
    private OkAuthWechatWorkCorpAppInfo wechatWorkCorp;

    // #################### built-in open platform app info #############################

    @Data
    @Accessors(chain = true)
    public abstract static class OkAuthAppInfo {

        /** Whether to enable the open platform. */
        private Boolean enabled;
        /** OAuth2 http client properties. */
        @NestedConfigurationProperty
        private OkAuthHttpClientProperties httpClient;

    }

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class OkAuthBaiduAppInfo extends OkAuthAppInfo {

        /** Baidu app info. */
        @NestedConfigurationProperty
        private BaiduOAuth2AppInfo appInfo;

    }

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class OkAuthDingTalkAppInfo extends OkAuthAppInfo {

        /** DingTalk app info. */
        @NestedConfigurationProperty
        private DingTalkOAuth2AppInfo appInfo;

    }

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class OkAuthGiteeAppInfo extends OkAuthAppInfo {

        /** Gitee app info. */
        @NestedConfigurationProperty
        private GiteeOAuth2AppInfo appInfo;

    }

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class OkAuthGitHubAppInfo extends OkAuthAppInfo {

        /** GitHub app info. */
        @NestedConfigurationProperty
        private GitHubOAuth2AppInfo appInfo;

    }

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class OkAuthOSChinaAppInfo extends OkAuthAppInfo {

        /** OSChina app info. */
        @NestedConfigurationProperty
        private OSChinaOAuth2AppInfo appInfo;

    }

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class OkAuthTikTokAppInfo extends OkAuthAppInfo {

        /** TikTok app info. */
        @NestedConfigurationProperty
        private TikTokOAuth2AppInfo appInfo;

    }

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class OkAuthWechatOfficialAccountAppInfo extends OkAuthAppInfo {

        /** WechatOfficialAccount app info. */
        @NestedConfigurationProperty
        private WechatOfficialAccountOAuth2AppInfo appInfo;

    }

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class OkAuthWechatWorkCorpAppInfo extends OkAuthAppInfo {

        /** WechatWorkCorp app info. */
        @NestedConfigurationProperty
        private WechatWorkCorpOAuth2AppInfo appInfo;

    }

}
