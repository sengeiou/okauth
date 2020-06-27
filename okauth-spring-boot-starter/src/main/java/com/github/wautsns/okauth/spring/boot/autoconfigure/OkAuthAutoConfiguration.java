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

import com.github.wautsns.okauth.core.client.OAuth2ClientManager;
import com.github.wautsns.okauth.core.client.kernel.OAuth2Client;
import com.github.wautsns.okauth.spring.boot.autoconfigure.configuration.OkAuthBuiltInOAuth2ClientAutoConfiguration;
import com.github.wautsns.okauth.spring.boot.autoconfigure.configuration.condition.ConditionalOnOkAuthEnabled;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OkAuth auto configuration.
 *
 * @author wautsns
 * @since Feb 27, 2020
 */
@ComponentScan
@Configuration
@ConditionalOnOkAuthEnabled
@AutoConfigureAfter(OkAuthBuiltInOAuth2ClientAutoConfiguration.class)
public class OkAuthAutoConfiguration {

    @Bean
    public OAuth2ClientManager oauth2ClientManager(List<OAuth2Client<?, ?>> oauth2Clients) {
        OAuth2ClientManager oauth2ClientManager = new OAuth2ClientManager();
        oauth2Clients.forEach(oauth2ClientManager::register);
        return oauth2ClientManager;
    }

}
