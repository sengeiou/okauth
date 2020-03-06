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
package com.github.wautsns.okauth.springbootstarter.properties;

import com.github.wautsns.okauth.core.client.kernel.OAuthClient;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * OAuth clients properties.
 *
 * @author wautsns
 * @since Mar 05, 2020
 */
@Data
@Accessors(chain = true)
@ConfigurationProperties("okauth")
public class OAuthClientsProperties {

    @NestedConfigurationProperty
    private final HttpClientImplementationProperties defaultHttpClient
            = HttpClientImplementationProperties.initDefault();

    @NestedConfigurationProperty
    private OAuthClientProperties baidu;
    // FIXME DingTalk is not tested.
    //@NestedConfigurationProperty
    //private OAuthClientProperties dingtalk;
    @NestedConfigurationProperty
    private OAuthClientProperties gitee;
    @NestedConfigurationProperty
    private OAuthClientProperties github;
    @NestedConfigurationProperty
    private OAuthClientProperties microblog;
    @NestedConfigurationProperty
    private OAuthClientProperties oschina;

    private Map<Class<? extends OAuthClient<?>>, OAuthClientProperties> customization = new HashMap<>();

}
