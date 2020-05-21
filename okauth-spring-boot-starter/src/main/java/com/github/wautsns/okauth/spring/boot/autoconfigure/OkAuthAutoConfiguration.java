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
package com.github.wautsns.okauth.spring.boot.autoconfigure;

import com.github.wautsns.okauth.core.assist.http.kernel.OAuth2HttpClient;
import com.github.wautsns.okauth.core.assist.http.kernel.properties.OAuth2HttpClientProperties;
import com.github.wautsns.okauth.core.client.OAuth2ClientManager;
import com.github.wautsns.okauth.core.client.builtin.baidu.BaiduOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.builtin.baidu.BaiduOAuth2Client;
import com.github.wautsns.okauth.core.client.builtin.gitee.GiteeOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.builtin.gitee.GiteeOAuth2Client;
import com.github.wautsns.okauth.core.client.builtin.github.GitHubOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.builtin.github.GitHubOAuth2Client;
import com.github.wautsns.okauth.core.client.kernel.OAuth2Client;
import com.github.wautsns.okauth.core.client.kernel.TokenRefreshableOAuth2Client;
import com.github.wautsns.okauth.spring.boot.autoconfigure.properties.OkAuthHttpClientProperties;
import com.github.wautsns.okauth.spring.boot.autoconfigure.properties.OkAuthAppInfoProperties;
import com.github.wautsns.okauth.spring.boot.autoconfigure.properties.OkAuthProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

/**
 * OkAuth auto configuration.
 *
 * @author wautsns
 * @since Feb 27, 2020
 */
@Configuration
@ConditionalOnProperty(name = "okauth.enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(OkAuthProperties.class)
public class OkAuthAutoConfiguration {

    @Bean
    public OAuth2ClientManager oauth2ClientManager(List<OAuth2Client<?, ?>> oauth2Clients) {
        OAuth2ClientManager oauth2ClientManager = new OAuth2ClientManager();
        oauth2Clients.forEach(oauth2ClientManager::register);
        return oauth2ClientManager;
    }

    // #################### common component ############################################

    @Bean
    @ConditionalOnMissingBean
    public TokenRefreshableOAuth2Client.TokenRefreshCallback tokenRefreshCallback() {
        return TokenRefreshableOAuth2Client.TokenRefreshCallback.DEFAULT;
    }

    // #################### builtInOAuth2Client #########################################

    // ==================== baidu =======================================================

    @Bean
    @ConditionalOnProperty("okauth.apps-info.baidu.enabled")
    public BaiduOAuth2Client baiduOAuth2Client(
            OkAuthProperties okauthProps,
            TokenRefreshableOAuth2Client.TokenRefreshCallback tokenRefreshCallback,
            BaiduOAuth2AppInfo.ExtraAuthorizeUrlQuery.DisplaySupplier displaySupplier) {
        OkAuthAppInfoProperties<BaiduOAuth2AppInfo> baidu = okauthProps.getAppsInfo().getBaidu();
        BaiduOAuth2AppInfo appInfo = baidu.getAppInfo();
        OAuth2HttpClient httpClient = initOAuth2HttpClient(okauthProps, baidu);
        return new BaiduOAuth2Client(appInfo, httpClient, tokenRefreshCallback, displaySupplier);
    }

    @Bean
    @ConditionalOnMissingBean
    public BaiduOAuth2AppInfo.ExtraAuthorizeUrlQuery.DisplaySupplier displaySupplier() {
        return BaiduOAuth2AppInfo.ExtraAuthorizeUrlQuery.DisplaySupplier.DEFAULT;
    }

    // ==================== gitee =======================================================

    @Bean
    @ConditionalOnProperty("okauth.apps-info.gitee.enabled")
    public GiteeOAuth2Client giteeOAuth2Client(
            OkAuthProperties okauthProps,
            TokenRefreshableOAuth2Client.TokenRefreshCallback tokenRefreshCallback) {
        OkAuthAppInfoProperties<GiteeOAuth2AppInfo> gitee = okauthProps.getAppsInfo().getGitee();
        GiteeOAuth2AppInfo appInfo = gitee.getAppInfo();
        OAuth2HttpClient httpClient = initOAuth2HttpClient(okauthProps, gitee);
        return new GiteeOAuth2Client(appInfo, httpClient, tokenRefreshCallback);
    }

    // ==================== github ======================================================

    @Bean
    @ConditionalOnProperty("okauth.apps-info.github.enabled")
    public GitHubOAuth2Client gitHubOAuth2Client(OkAuthProperties okauthProps) {
        OkAuthAppInfoProperties<GitHubOAuth2AppInfo> github = okauthProps.getAppsInfo().getGithub();
        GitHubOAuth2AppInfo appInfo = github.getAppInfo();
        OAuth2HttpClient httpClient = initOAuth2HttpClient(okauthProps, github);
        return new GitHubOAuth2Client(appInfo, httpClient);
    }

    // #################### utils #######################################################

    private static OAuth2HttpClient initOAuth2HttpClient(
            OkAuthProperties okauthProps, OkAuthAppInfoProperties<?> okauthAppInfoProps) {
        OkAuthHttpClientProperties okauthHttpClientProps = fillNullProperties(
                okauthAppInfoProps.getHttpClient(), okauthProps.getDefaultHttpClient());
        try {
            Constructor<? extends OAuth2HttpClient> constructor = okauthHttpClientProps
                    .getImplementation()
                    .getConstructor(OAuth2HttpClientProperties.class);
            constructor.setAccessible(true);
            return constructor.newInstance(okauthHttpClientProps.getProperties());
        } catch (NoSuchMethodException e) {
            throw new UnsupportedOperationException(String.format(
                    "%s need a constructor with argument of type: %s",
                    okauthHttpClientProps.getImplementation(), OAuth2HttpClientProperties.class));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static <T> T fillNullProperties(T target, T source) {
        if (target == null) { return source; }
        Arrays.stream(target.getClass().getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .forEach(field -> {
                    try {
                        field.setAccessible(true);
                        Class<?> type = field.getType();
                        Object value;
                        if (type.isPrimitive() || Modifier.isFinal(type.getModifiers())) {
                            value = field.get(source);
                        } else {
                            value = fillNullProperties(field.get(target), field.get(source));
                        }
                        field.set(target, value);
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    }
                });
        return target;
    }

}
