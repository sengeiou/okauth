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
package com.github.wautsns.okauth.core.client.builtin;

import com.github.wautsns.okauth.core.OpenPlatform;
import com.github.wautsns.okauth.core.client.builtin.baidu.BaiduOAuthClient;
import com.github.wautsns.okauth.core.client.builtin.dingtalk.DingTalkOAuthClient;
import com.github.wautsns.okauth.core.client.builtin.gitee.GiteeOAuthClient;
import com.github.wautsns.okauth.core.client.builtin.github.GitHubOAuthClient;
import com.github.wautsns.okauth.core.client.builtin.microblog.MicroBlogOAuthClient;
import com.github.wautsns.okauth.core.client.builtin.oschina.OSChinaOAuthClient;
import com.github.wautsns.okauth.core.client.kernel.OAuthAppProperties;
import com.github.wautsns.okauth.core.client.kernel.OAuthClient;
import com.github.wautsns.okauth.core.http.HttpClient;
import com.github.wautsns.okauth.core.http.HttpClientProperties;
import com.github.wautsns.okauth.core.http.builtin.okhttp.OkHttp3HttpClient;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;

/**
 * Built-in open platform.
 *
 * @author wautsns
 * @since Mar 04, 2020
 */
@RequiredArgsConstructor
public enum BuiltInOpenPlatform implements OpenPlatform {

    /** Baidu(百度) */
    BAIDU(BaiduOAuthClient::new),
    /** Gitee(码云) */
    GITEE(GiteeOAuthClient::new),
    /** GitHub */
    GITHUB(GitHubOAuthClient::new),
    /** MicroBlog(微博) */
    MICROBLOG(MicroBlogOAuthClient::new),
    /** OSChina(开源中国) */
    OSCHINA(OSChinaOAuthClient::new),

    // -------------------- Not tested ------------------------------

    /**
     * DingTalk(钉钉)
     *
     * FIXME DingTalk is not tested.
     *
     * @deprecated Not tested.
     */
    @Deprecated
    DINGTALK(DingTalkOAuthClient::new),

    ;

    private final BiFunction<OAuthAppProperties, HttpClient, OAuthClient<?>> oauthClientConstructor;

    /**
     * Initialize oauth client with default http client.
     *
     * @param oauthAppProperties oauth app properties
     * @return oauth client
     */
    public OAuthClient<?> initOAuthClient(OAuthAppProperties oauthAppProperties) {
        HttpClient httpClient = new OkHttp3HttpClient(HttpClientProperties.initDefault());
        return initOAuthClient(oauthAppProperties, httpClient);
    }

    /**
     * Initialize oauth client.
     *
     * @param oauthAppProperties oauth app properties
     * @param httpClient http client
     * @return oauth client
     */
    public OAuthClient<?> initOAuthClient(OAuthAppProperties oauthAppProperties, HttpClient httpClient) {
        return oauthClientConstructor.apply(oauthAppProperties, httpClient);
    }

}
