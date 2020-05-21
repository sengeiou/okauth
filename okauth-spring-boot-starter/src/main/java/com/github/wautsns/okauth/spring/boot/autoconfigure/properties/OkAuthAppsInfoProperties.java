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
import lombok.Data;
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

    @NestedConfigurationProperty
    private final OkAuthAppInfoProperties<BaiduOAuth2AppInfo> baidu
            = new OkAuthAppInfoProperties<>(new BaiduOAuth2AppInfo());
    @NestedConfigurationProperty
    private final OkAuthAppInfoProperties<GiteeOAuth2AppInfo> gitee
            = new OkAuthAppInfoProperties<>(new GiteeOAuth2AppInfo());
    @NestedConfigurationProperty
    private final OkAuthAppInfoProperties<GitHubOAuth2AppInfo> github
            = new OkAuthAppInfoProperties<>(new GitHubOAuth2AppInfo());

}
