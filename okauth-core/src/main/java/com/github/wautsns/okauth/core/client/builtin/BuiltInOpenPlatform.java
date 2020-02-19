/**
 * Copyright 2019 the original author or authors.
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

import java.util.function.BiFunction;

import com.github.wautsns.okauth.core.client.builtin.baidu.BaiduOkAuthClient;
import com.github.wautsns.okauth.core.client.builtin.gitee.GiteeOkAuthClient;
import com.github.wautsns.okauth.core.client.builtin.github.GitHubOkAuthClient;
import com.github.wautsns.okauth.core.client.builtin.microblog.MicroBlogOkAuthClient;
import com.github.wautsns.okauth.core.client.builtin.wechat.WeChatOkAuthClient;
import com.github.wautsns.okauth.core.client.core.OkAuthClient;
import com.github.wautsns.okauth.core.client.core.OkAuthClientInitializer;
import com.github.wautsns.okauth.core.client.core.properties.OAuthAppInfo;
import com.github.wautsns.okauth.core.client.util.http.Requester;

/**
 * Built-in open platform.
 *
 * @since Feb 18, 2020
 * @author wautsns
 */
public enum BuiltInOpenPlatform implements OkAuthClientInitializer {

    BAIDU("Baidu", BaiduOkAuthClient::new),
    GITEE("Gitee", GiteeOkAuthClient::new),
    GITHUB("GitHub", GitHubOkAuthClient::new),
    MICROBLOG("MicroBlog", MicroBlogOkAuthClient::new),
    /** @deprecated not tested */
    @Deprecated
    WECHAT("WeChat", WeChatOkAuthClient::new),
    ;

    /** identifier */
    private final String identifier;
    /** function to initialize an okauth client */
    private final BiFunction<OAuthAppInfo, Requester, OkAuthClient> okauthClientInitializer;

    /**
     * Construct a built-in open platform.
     *
     * @param identifier open platform identifier
     * @param okauthClientInitializer okauth client initializer
     */
    private BuiltInOpenPlatform(
            String identifier,
            BiFunction<OAuthAppInfo, Requester, OkAuthClient> okauthClientInitializer) {
        this.identifier = identifier;
        this.okauthClientInitializer = okauthClientInitializer;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public OkAuthClient initOkAuthClient(OAuthAppInfo oauthAppInfo, Requester requester) {
        return okauthClientInitializer.apply(oauthAppInfo, requester);
    }

}
