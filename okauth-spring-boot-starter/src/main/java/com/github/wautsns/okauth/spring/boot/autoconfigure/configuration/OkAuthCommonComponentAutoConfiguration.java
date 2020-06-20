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
package com.github.wautsns.okauth.spring.boot.autoconfigure.configuration;

import com.github.wautsns.okauth.core.client.kernel.TokenRefreshableOAuth2Client;
import com.github.wautsns.okauth.spring.boot.autoconfigure.configuration.condition.ConditionalOnOkAuthEnabled;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OkAuth common component auto configuration.
 *
 * @author wautsns
 * @since Jun 15, 2020
 */
@Configuration
@ConditionalOnOkAuthEnabled
public class OkAuthCommonComponentAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public TokenRefreshableOAuth2Client.TokenRefreshCallback tokenRefreshCallback() {
        return TokenRefreshableOAuth2Client.TokenRefreshCallback.IGNORE;
    }

}
