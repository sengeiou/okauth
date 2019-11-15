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
package com.github.wautsns.okauth.springbootstarter;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.wautsns.okauth.core.manager.OkAuthManager;
import com.github.wautsns.okauth.core.manager.OkAuthManagerBuilder;
import com.github.wautsns.okauth.core.manager.properties.OkAuthProperties;
import com.github.wautsns.okauth.springbootstarter.properties.SpringBootOkAuthProperties;

/**
 * Okauth auto-configuration.
 *
 * @author wautsns
 */
@Configuration
@EnableConfigurationProperties(SpringBootOkAuthProperties.class)
public class OkAuthAutoConfiguration {

    @Bean
    public OkAuthManager okauthManager(OkAuthProperties okauthProperties) {
        return new OkAuthManagerBuilder().register(okauthProperties).build();
    }

}
