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
import com.github.wautsns.okauth.core.client.builtin.dingtalk.DingTalkOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.builtin.dingtalk.DingTalkOAuth2Client;
import com.github.wautsns.okauth.core.client.builtin.elemeshopisv.ElemeShopIsvOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.builtin.elemeshopisv.ElemeShopIsvOAuth2Client;
import com.github.wautsns.okauth.core.client.builtin.gitee.GiteeOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.builtin.gitee.GiteeOAuth2Client;
import com.github.wautsns.okauth.core.client.builtin.github.GitHubOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.builtin.github.GitHubOAuth2Client;
import com.github.wautsns.okauth.core.client.builtin.oschina.OSChinaOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.builtin.oschina.OSChinaOAuth2Client;
import com.github.wautsns.okauth.core.client.builtin.tiktok.TikTokOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.builtin.tiktok.TikTokOAuth2Client;
import com.github.wautsns.okauth.core.client.builtin.wechatofficialaccount.WechatOfficialAccountOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.builtin.wechatofficialaccount.WechatOfficialAccountOAuth2Client;
import com.github.wautsns.okauth.core.client.builtin.wechatworkcorp.WechatWorkCorpOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.builtin.wechatworkcorp.WechatWorkCorpOAuth2Client;
import com.github.wautsns.okauth.core.client.builtin.wechatworkcorp.service.tokencache.WechatWorkCorpTokenCache;
import com.github.wautsns.okauth.core.client.builtin.wechatworkcorp.service.tokencache.builtin.WechatWorkCorpTokenLocalCache;
import com.github.wautsns.okauth.core.client.kernel.TokenRefreshableOAuth2Client;
import com.github.wautsns.okauth.spring.boot.autoconfigure.configuration.condition.ConditionalOnOkAuthEnabled;
import com.github.wautsns.okauth.spring.boot.autoconfigure.properties.OkAuthAppsInfoProperties;
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
 * OkAuth built-in oauth2 client auto-configuration.
 *
 * @author wautsns
 * @since May 25, 2020
 */
@Configuration
@ConditionalOnOkAuthEnabled
@EnableConfigurationProperties(OkAuthProperties.class)
@AutoConfigureAfter(OkAuthCommonComponentAutoConfiguration.class)
public class OkAuthBuiltInOAuth2ClientAutoConfiguration {

    // #################### Baidu #######################################################

    @Bean
    @ConditionalOnProperty("okauth.apps-info.baidu.enabled")
    @ConditionalOnMissingBean
    public BaiduOAuth2Client baiduOAuth2Client(
            OkAuthProperties okauthProps,
            TokenRefreshableOAuth2Client.TokenRefreshCallback tokenRefreshCallback) {
        OkAuthAppsInfoProperties.OkAuthBaiduAppInfo baidu = okauthProps.getAppsInfo().getBaidu();
        BaiduOAuth2AppInfo appInfo = baidu.getAppInfo();
        OAuth2HttpClient httpClient = OkAuthAutoConfigureUtils.initOAuth2HttpClient(okauthProps, baidu);
        return new BaiduOAuth2Client(appInfo, httpClient, tokenRefreshCallback);
    }

    // #################### DingTalk ####################################################

    @Bean
    @ConditionalOnProperty("okauth.apps-info.ding-talk.enabled")
    @ConditionalOnMissingBean
    public DingTalkOAuth2Client dingTalkOAuth2Client(OkAuthProperties okauthProps) {
        OkAuthAppsInfoProperties.OkAuthDingTalkAppInfo dingTalk = okauthProps.getAppsInfo().getDingTalk();
        DingTalkOAuth2AppInfo appInfo = dingTalk.getAppInfo();
        OAuth2HttpClient httpClient = OkAuthAutoConfigureUtils.initOAuth2HttpClient(okauthProps, dingTalk);
        return new DingTalkOAuth2Client(appInfo, httpClient);
    }

    // #################### ElemeShopIsv ################################################

    @Bean
    @ConditionalOnProperty("okauth.apps-info.eleme-shop-isv.enabled")
    @ConditionalOnMissingBean
    public ElemeShopIsvOAuth2Client elemeShopIsvOAuth2Client(
            OkAuthProperties okauthProps,
            TokenRefreshableOAuth2Client.TokenRefreshCallback tokenRefreshCallback) {
        OkAuthAppsInfoProperties.OkAuthElemeShopIsvAppInfo elemeShopIsv = okauthProps.getAppsInfo().getElemeShopIsv();
        ElemeShopIsvOAuth2AppInfo appInfo = elemeShopIsv.getAppInfo();
        OAuth2HttpClient httpClient = OkAuthAutoConfigureUtils.initOAuth2HttpClient(okauthProps, elemeShopIsv);
        return new ElemeShopIsvOAuth2Client(appInfo, httpClient, tokenRefreshCallback);
    }

    // #################### Gitee #######################################################

    @Bean
    @ConditionalOnProperty("okauth.apps-info.gitee.enabled")
    @ConditionalOnMissingBean
    public GiteeOAuth2Client giteeOAuth2Client(
            OkAuthProperties okauthProps,
            TokenRefreshableOAuth2Client.TokenRefreshCallback tokenRefreshCallback) {
        OkAuthAppsInfoProperties.OkAuthGiteeAppInfo gitee = okauthProps.getAppsInfo().getGitee();
        GiteeOAuth2AppInfo appInfo = gitee.getAppInfo();
        OAuth2HttpClient httpClient = OkAuthAutoConfigureUtils.initOAuth2HttpClient(okauthProps, gitee);
        return new GiteeOAuth2Client(appInfo, httpClient, tokenRefreshCallback);
    }

    // #################### GitHub ######################################################

    @Bean
    @ConditionalOnProperty("okauth.apps-info.github.enabled")
    @ConditionalOnMissingBean
    public GitHubOAuth2Client gitHubOAuth2Client(OkAuthProperties okauthProps) {
        OkAuthAppsInfoProperties.OkAuthGitHubAppInfo github = okauthProps.getAppsInfo().getGithub();
        GitHubOAuth2AppInfo appInfo = github.getAppInfo();
        OAuth2HttpClient httpClient = OkAuthAutoConfigureUtils.initOAuth2HttpClient(okauthProps, github);
        return new GitHubOAuth2Client(appInfo, httpClient);
    }

    // #################### OSChina #####################################################

    @Bean
    @ConditionalOnProperty("okauth.apps-info.oschina.enabled")
    @ConditionalOnMissingBean
    public OSChinaOAuth2Client oschinaOAuth2Client(
            OkAuthProperties okauthProps,
            TokenRefreshableOAuth2Client.TokenRefreshCallback tokenRefreshCallback) {
        OkAuthAppsInfoProperties.OkAuthOSChinaAppInfo oschina = okauthProps.getAppsInfo().getOschina();
        OSChinaOAuth2AppInfo appInfo = oschina.getAppInfo();
        OAuth2HttpClient httpClient = OkAuthAutoConfigureUtils.initOAuth2HttpClient(okauthProps, oschina);
        return new OSChinaOAuth2Client(appInfo, httpClient, tokenRefreshCallback);
    }

    // #################### TikTok ######################################################

    @Bean
    @ConditionalOnProperty("okauth.apps-info.tik-tok.enabled")
    @ConditionalOnMissingBean
    public TikTokOAuth2Client tikTokOAuth2Client(
            OkAuthProperties okauthProps,
            TokenRefreshableOAuth2Client.TokenRefreshCallback tokenRefreshCallback) {
        OkAuthAppsInfoProperties.OkAuthTikTokAppInfo tikTok = okauthProps.getAppsInfo().getTikTok();
        TikTokOAuth2AppInfo appInfo = tikTok.getAppInfo();
        OAuth2HttpClient httpClient = OkAuthAutoConfigureUtils.initOAuth2HttpClient(okauthProps, tikTok);
        return new TikTokOAuth2Client(appInfo, httpClient, tokenRefreshCallback);
    }

    // #################### WechatOfficialAccount #######################################

    @Bean
    @ConditionalOnProperty("okauth.apps-info.oschina.enabled")
    @ConditionalOnMissingBean
    public WechatOfficialAccountOAuth2Client weChatOfficialAccountOAuth2Client(
            OkAuthProperties okauthProps,
            TokenRefreshableOAuth2Client.TokenRefreshCallback tokenRefreshCallback) {
        OkAuthAppsInfoProperties.OkAuthWechatOfficialAccountAppInfo wechatOfficialAccount
                = okauthProps.getAppsInfo().getWechatOfficialAccount();
        WechatOfficialAccountOAuth2AppInfo appInfo = wechatOfficialAccount.getAppInfo();
        OAuth2HttpClient httpClient = OkAuthAutoConfigureUtils.initOAuth2HttpClient(okauthProps, wechatOfficialAccount);
        return new WechatOfficialAccountOAuth2Client(appInfo, httpClient, tokenRefreshCallback);
    }

    // #################### WechatWorkCorp ##############################################

    @Bean
    @ConditionalOnProperty("okauth.apps-info.wechat-work-corp.enabled")
    @ConditionalOnMissingBean
    public WechatWorkCorpOAuth2Client wechatWorkCorpOAuth2Client(
            OkAuthProperties okauthProps,
            WechatWorkCorpTokenCache tokenCache) {
        OkAuthAppsInfoProperties.OkAuthWechatWorkCorpAppInfo wechatWorkCorp
                = okauthProps.getAppsInfo().getWechatWorkCorp();
        WechatWorkCorpOAuth2AppInfo appInfo = wechatWorkCorp.getAppInfo();
        OAuth2HttpClient httpClient = OkAuthAutoConfigureUtils.initOAuth2HttpClient(okauthProps, wechatWorkCorp);
        return new WechatWorkCorpOAuth2Client(appInfo, httpClient, tokenCache);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(WechatWorkCorpOAuth2Client.class)
    public WechatWorkCorpTokenCache wechatWorkCorpTokenCache() {
        return WechatWorkCorpTokenLocalCache.INSTANCE;
    }

}
