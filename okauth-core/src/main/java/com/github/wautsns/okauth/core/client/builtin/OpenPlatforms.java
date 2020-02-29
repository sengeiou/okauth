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
import com.github.wautsns.okauth.core.client.builtin.dingtalk.DingtalkOAuthClient;
import com.github.wautsns.okauth.core.client.builtin.gitee.GiteeOAuthClient;
import com.github.wautsns.okauth.core.client.builtin.github.GithubOAuthClient;
import com.github.wautsns.okauth.core.client.builtin.weibo.WeiboOAuthClient;
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

    BAIDU(BaiduOAuthClient::new),
    DINGTALK(DingtalkOAuthClient::new),
    GITEE(GiteeOAuthClient::new),
    GITHUB(GithubOAuthClient::new),
    WEIBO(WeiboOAuthClient::new);

    private final OAuthClientInitializer initializer;

    /**
     * Construct an open platform.
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
