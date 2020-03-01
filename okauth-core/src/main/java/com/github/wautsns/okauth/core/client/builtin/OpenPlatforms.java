/**
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

import com.github.wautsns.okauth.core.client.OAuthClientInitializer;
import com.github.wautsns.okauth.core.client.OpenPlatform;
import com.github.wautsns.okauth.core.client.builtin.baidu.BaiduOAuthClient;
import com.github.wautsns.okauth.core.client.builtin.dingtalk.DingTalkOAuthClient;
import com.github.wautsns.okauth.core.client.builtin.gitee.GiteeOAuthClient;
import com.github.wautsns.okauth.core.client.builtin.github.GitHubOAuthClient;
import com.github.wautsns.okauth.core.client.builtin.microblog.MicroBlogOAuthClient;
import com.github.wautsns.okauth.core.client.builtin.oschina.OSChinaOAuthClient;
import com.github.wautsns.okauth.core.client.builtin.qq.QQOAuthClient;
import com.github.wautsns.okauth.core.client.kernel.OAuthClient;
import com.github.wautsns.okauth.core.client.kernel.http.OAuthRequestExecutor;
import com.github.wautsns.okauth.core.client.kernel.model.properties.OAuthAppProperties;

/**
 * Built-in open platforms.
 *
 * @since Feb 29, 2020
 * @author wautsns
 */
public enum OpenPlatforms implements OpenPlatform, OAuthClientInitializer {

    /** Baidu(百度) */
    BAIDU(BaiduOAuthClient::new),
    /** DingTalk(钉钉) */
    DINGTALK(DingTalkOAuthClient::new),
    /** Gitee(码云) */
    GITEE(GiteeOAuthClient::new),
    /** GitHub */
    GITHUB(GitHubOAuthClient::new),
    /** OSChina(开源中国) */
    OSCHINA(OSChinaOAuthClient::new),
    /** QQ(腾讯QQ) */
    QQ(QQOAuthClient::new),
    /** TencentCloud(腾讯云) */
    TENCENTCLOUD(null),
    /** MicroBlog(微博) */
    MICROBLOG(MicroBlogOAuthClient::new);

    private final OAuthClientInitializer initializer;

    /**
     * Construct open platform.
     *
     * @param initializer oauth client initializer, require nonnull
     */
    private OpenPlatforms(OAuthClientInitializer initializer) {
        this.initializer = initializer;
    }

    @Override
    public OAuthClient<?> initOAuthClient(OAuthAppProperties app, OAuthRequestExecutor executor) {
        return initializer.initOAuthClient(app, executor);
    }

}
