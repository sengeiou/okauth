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
package com.github.wautsns.okauth.springbootstarter;

import com.github.wautsns.okauth.core.client.OAuthClients;
import com.github.wautsns.okauth.core.client.OAuthClientsBuilder;
import com.github.wautsns.okauth.core.client.builtin.baidu.BaiduOAuthClient;
import com.github.wautsns.okauth.core.client.builtin.gitee.GiteeOAuthClient;
import com.github.wautsns.okauth.core.client.builtin.github.GitHubOAuthClient;
import com.github.wautsns.okauth.core.client.builtin.microblog.MicroBlogOAuthClient;
import com.github.wautsns.okauth.core.client.builtin.oschina.OSChinaOAuthClient;
import com.github.wautsns.okauth.core.client.kernel.OAuthAppProperties;
import com.github.wautsns.okauth.core.client.kernel.OAuthClient;
import com.github.wautsns.okauth.core.http.HttpClient;
import com.github.wautsns.okauth.core.http.HttpClientProperties;
import com.github.wautsns.okauth.springbootstarter.properties.HttpClientImplementationProperties;
import com.github.wautsns.okauth.springbootstarter.properties.OAuthClientProperties;
import com.github.wautsns.okauth.springbootstarter.properties.OAuthClientsProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OkAuth auto configuration.
 *
 * @author wautsns
 * @since Feb 27, 2020
 */
@Configuration
@EnableConfigurationProperties(OAuthClientsProperties.class)
public class OkAuthAutoConfiguration {

    @Bean
    public OAuthClients oauthClients(OAuthClientsProperties oauthClientsProperties) {
        OAuthClientsBuilder builder = new OAuthClientsBuilder();
        HttpClientImplementationProperties defaultHttpClient = oauthClientsProperties.getDefaultHttpClient();
        // built-in
        builder.register(initOAuthClient(
            defaultHttpClient, BaiduOAuthClient.class, oauthClientsProperties.getBaidu()));
        builder.register(initOAuthClient(
            defaultHttpClient, GiteeOAuthClient.class, oauthClientsProperties.getGitee()));
        builder.register(initOAuthClient(
            defaultHttpClient, GitHubOAuthClient.class, oauthClientsProperties.getGithub()));
        builder.register(initOAuthClient(
            defaultHttpClient, MicroBlogOAuthClient.class, oauthClientsProperties.getMicroblog()));
        builder.register(initOAuthClient(
            defaultHttpClient, OSChinaOAuthClient.class, oauthClientsProperties.getOschina()));
        // customization
        if (oauthClientsProperties.getCustomization() != null) {
            oauthClientsProperties.getCustomization()
                .forEach((oauthClientClass, oauthClientProperties) -> builder.register(
                    initOAuthClient(defaultHttpClient, oauthClientClass, oauthClientProperties)));
        }
        return builder.build();
    }

    private static OAuthClient<?> initOAuthClient(
        HttpClientImplementationProperties defaultHttpClient,
        Class<? extends OAuthClient<?>> oauthClientClass, OAuthClientProperties oauthClientProperties) {
        if (oauthClientProperties == null || !Boolean.TRUE.equals(oauthClientProperties.getEnabled())) { return null; }
        HttpClientImplementationProperties httpClientProperties = oauthClientProperties.getHttpClient();
        if (httpClientProperties == null) { httpClientProperties = new HttpClientImplementationProperties(); }
        BeanUtils.copyProperties(defaultHttpClient, httpClientProperties);
        try {
            OAuthAppProperties app = oauthClientProperties.getOauthApp();
            HttpClient httpClient = httpClientProperties.getImplementation()
                .getConstructor(HttpClientProperties.class)
                .newInstance(httpClientProperties);
            return oauthClientClass
                .getConstructor(OAuthAppProperties.class, HttpClient.class)
                .newInstance(app, httpClient);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

}
