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
import com.github.wautsns.okauth.core.client.builtin.gitee.GiteeOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.builtin.github.GitHubOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.builtin.oschina.OSChinaOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.builtin.wechat.officialaccount.WechatOfficialAccountOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.builtin.wechat.work.corp.WechatWorkCorpOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.kernel.OAuth2AppInfo;
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
    /** Gitee app info properties. */
    private OkAuthGiteeAppInfo gitee;
    /** Baidu app info properties. */
    private OkAuthGitHubAppInfo github;
    /** Baidu app info properties. */
    private OkAuthOSChinaAppInfo oschina;
    /** Baidu app info properties. */
    private OkAuthWechatOfficialAccountAppInfo wechatOfficialAccount;
    /** Baidu app info properties. */
    private OkAuthWechatWorkCorpAppInfo wechatWorkCorp;

    // #################### built-in open platform app info #############################

    @Data
    @Accessors(chain = true)
    public abstract static class OkAuthAppInfo<I extends OAuth2AppInfo> {

        /** Whether to enable the open platform. */
        private Boolean enabled;
        /** OAuth2 http client properties. */
        @NestedConfigurationProperty
        private OkAuthHttpClientProperties httpClient;

    }

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class OkAuthBaiduAppInfo extends OkAuthAppInfo<BaiduOAuth2AppInfo> {

        /** Baidu app info. */
        @NestedConfigurationProperty
        private BaiduOAuth2AppInfo appInfo;

    }

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class OkAuthGiteeAppInfo extends OkAuthAppInfo<GiteeOAuth2AppInfo> {

        /** Gitee app info. */
        @NestedConfigurationProperty
        private GiteeOAuth2AppInfo appInfo;

    }

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class OkAuthGitHubAppInfo extends OkAuthAppInfo<GitHubOAuth2AppInfo> {

        /** GitHub app info. */
        @NestedConfigurationProperty
        private GitHubOAuth2AppInfo appInfo;

    }

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class OkAuthOSChinaAppInfo extends OkAuthAppInfo<OSChinaOAuth2AppInfo> {

        /** OSChina app info. */
        @NestedConfigurationProperty
        private OSChinaOAuth2AppInfo appInfo;

    }

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class OkAuthWechatOfficialAccountAppInfo extends OkAuthAppInfo<WechatOfficialAccountOAuth2AppInfo> {

        /** WechatOfficialAccount app info. */
        @NestedConfigurationProperty
        private WechatOfficialAccountOAuth2AppInfo appInfo;

    }

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class OkAuthWechatWorkCorpAppInfo extends OkAuthAppInfo<WechatWorkCorpOAuth2AppInfo> {

        /** WechatWorkCorp app info. */
        @NestedConfigurationProperty
        private WechatWorkCorpOAuth2AppInfo appInfo;

    }

}
