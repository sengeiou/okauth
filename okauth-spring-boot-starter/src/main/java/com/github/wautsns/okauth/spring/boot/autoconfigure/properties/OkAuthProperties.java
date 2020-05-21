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

import com.github.wautsns.okauth.core.assist.http.builtin.okhttp3.OkHttp3OAuth2HttpClient;
import com.github.wautsns.okauth.core.assist.http.kernel.properties.OAuth2HttpClientProperties;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * OAuth clients properties.
 *
 * @author wautsns
 * @since May 19, 2020
 */
@Data
@Accessors(chain = true)
@ConfigurationProperties("okauth")
public class OkAuthProperties {

    /** Whether to enable the okauth. */
    private Boolean enabled;
    /** Default http client properties. */
    @NestedConfigurationProperty
    private final OkAuthHttpClientProperties defaultHttpClient = new OkAuthHttpClientProperties()
            .setImplementation(OkHttp3OAuth2HttpClient.class)
            .setProperties(OAuth2HttpClientProperties.initDefault());
    /** Apps info properties. */
    @NestedConfigurationProperty
    private final OkAuthAppsInfoProperties appsInfo = new OkAuthAppsInfoProperties();

}
