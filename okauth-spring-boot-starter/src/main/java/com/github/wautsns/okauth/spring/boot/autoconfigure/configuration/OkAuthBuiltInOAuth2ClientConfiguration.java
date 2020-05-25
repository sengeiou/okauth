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
package com.github.wautsns.okauth.spring.boot.autoconfigure.configuration;

import com.github.wautsns.okauth.core.assist.http.kernel.OAuth2HttpClient;
import com.github.wautsns.okauth.core.client.builtin.baidu.BaiduOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.builtin.baidu.BaiduOAuth2Client;
import com.github.wautsns.okauth.core.client.builtin.gitee.GiteeOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.builtin.gitee.GiteeOAuth2Client;
import com.github.wautsns.okauth.core.client.builtin.github.GitHubOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.builtin.github.GitHubOAuth2Client;
import com.github.wautsns.okauth.core.client.builtin.oschina.OSChinaOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.builtin.oschina.OSChinaOAuth2Client;
import com.github.wautsns.okauth.core.client.builtin.wechat.work.corp.WeChatWorkCorpOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.builtin.wechat.work.corp.WeChatWorkCorpOAuth2Client;
import com.github.wautsns.okauth.core.client.builtin.wechat.work.corp.service.WeChatWorkCorpTokenCache;
import com.github.wautsns.okauth.core.client.kernel.TokenRefreshableOAuth2Client;
import com.github.wautsns.okauth.spring.boot.autoconfigure.properties.OkAuthAppInfoProperties;
import com.github.wautsns.okauth.spring.boot.autoconfigure.properties.OkAuthProperties;
import com.github.wautsns.okauth.spring.boot.autoconfigure.util.OkAuthAutoConfigureUtils;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OkAuth built-in open platform client configuration.
 *
 * @author wautsns
 * @since May 25, 2020
 */
@Configuration
@AutoConfigureAfter(OkAuthCommonServiceAutoConfiguration.class)
@EnableConfigurationProperties(OkAuthProperties.class)
public class OkAuthBuiltInOAuth2ClientConfiguration {

    // #################### Baidu #######################################################

    @Bean
    @ConditionalOnProperty("okauth.apps-info.baidu.enabled")
    public BaiduOAuth2Client baiduOAuth2Client(
            OkAuthProperties okauthProps,
            TokenRefreshableOAuth2Client.TokenRefreshCallback tokenRefreshCallback,
            BaiduOAuth2AppInfo.ExtraAuthorizeUrlQuery.DisplaySupplier displaySupplier) {
        OkAuthAppInfoProperties<BaiduOAuth2AppInfo> baidu = okauthProps.getAppsInfo().getBaidu();
        BaiduOAuth2AppInfo appInfo = baidu.getAppInfo();
        OAuth2HttpClient httpClient = OkAuthAutoConfigureUtils.initOAuth2HttpClient(okauthProps, baidu);
        return new BaiduOAuth2Client(appInfo, httpClient, tokenRefreshCallback, displaySupplier);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(BaiduOAuth2Client.class)
    public BaiduOAuth2AppInfo.ExtraAuthorizeUrlQuery.DisplaySupplier displaySupplier() {
        return BaiduOAuth2AppInfo.ExtraAuthorizeUrlQuery.DisplaySupplier.DEFAULT;
    }

    // #################### Gitee #######################################################

    @Bean
    @ConditionalOnProperty("okauth.apps-info.gitee.enabled")
    public GiteeOAuth2Client giteeOAuth2Client(
            OkAuthProperties okauthProps,
            TokenRefreshableOAuth2Client.TokenRefreshCallback tokenRefreshCallback) {
        OkAuthAppInfoProperties<GiteeOAuth2AppInfo> gitee = okauthProps.getAppsInfo().getGitee();
        GiteeOAuth2AppInfo appInfo = gitee.getAppInfo();
        OAuth2HttpClient httpClient = OkAuthAutoConfigureUtils.initOAuth2HttpClient(okauthProps, gitee);
        return new GiteeOAuth2Client(appInfo, httpClient, tokenRefreshCallback);
    }

    // #################### GitHub ######################################################

    @Bean
    @ConditionalOnProperty("okauth.apps-info.github.enabled")
    public GitHubOAuth2Client gitHubOAuth2Client(OkAuthProperties okauthProps) {
        OkAuthAppInfoProperties<GitHubOAuth2AppInfo> github = okauthProps.getAppsInfo().getGithub();
        GitHubOAuth2AppInfo appInfo = github.getAppInfo();
        OAuth2HttpClient httpClient = OkAuthAutoConfigureUtils.initOAuth2HttpClient(okauthProps, github);
        return new GitHubOAuth2Client(appInfo, httpClient);
    }

    // #################### OSChina #####################################################

    @Bean
    @ConditionalOnProperty("okauth.apps-info.oschina.enabled")
    public OSChinaOAuth2Client oschinaOAuth2Client(
            OkAuthProperties okauthProps,
            TokenRefreshableOAuth2Client.TokenRefreshCallback tokenRefreshCallback) {
        OkAuthAppInfoProperties<OSChinaOAuth2AppInfo> oschina = okauthProps.getAppsInfo().getOschina();
        OSChinaOAuth2AppInfo appInfo = oschina.getAppInfo();
        OAuth2HttpClient httpClient = OkAuthAutoConfigureUtils.initOAuth2HttpClient(okauthProps, oschina);
        return new OSChinaOAuth2Client(appInfo, httpClient, tokenRefreshCallback);
    }

    // #################### WechatWorkCorp ##############################################

    @Bean
    @ConditionalOnProperty("okauth.apps-info.wechat-work-corp.enabled")
    public WeChatWorkCorpOAuth2Client wechatWorkCorpOAuth2Client(
            OkAuthProperties okauthProps,
            WeChatWorkCorpTokenCache tokenCache) {
        OkAuthAppInfoProperties<WeChatWorkCorpOAuth2AppInfo> wechatWorkCorp
                = okauthProps.getAppsInfo().getWechatWorkCorp();
        WeChatWorkCorpOAuth2AppInfo appInfo = wechatWorkCorp.getAppInfo();
        OAuth2HttpClient httpClient = OkAuthAutoConfigureUtils.initOAuth2HttpClient(okauthProps, wechatWorkCorp);
        return new WeChatWorkCorpOAuth2Client(appInfo, httpClient, tokenCache);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(WeChatWorkCorpOAuth2Client.class)
    public WeChatWorkCorpTokenCache wechatWorkCorpTokenCache() {
        return WeChatWorkCorpTokenCache.LOCAL_CACHE;
    }

}
