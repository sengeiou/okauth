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
package com.github.wautsns.okauth.core.assist.http.kernel.properties;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Duration;

/**
 * OAuth2 http client properties.
 *
 * @author wautsns
 * @since May 17, 2020
 */
@Data
@Accessors(chain = true)
public class OAuth2HttpClientProperties {

    /** connect timeout */
    private Duration connectTimeout;
    /** read timeout */
    private Duration readTimeout;
    /** max concurrent requests */
    private Integer maxConcurrentRequests;
    /** max idle connection */
    private Integer maxIdleConnections;
    /** max idle time of connection */
    private Duration maxIdleTime;
    /** keep alive timeout of connection */
    private Duration keepAliveTimeout;

    // #################### utils #######################################################

    /**
     * Initialize default http client properties.
     *
     * <ul>
     * default properties are as followers:
     * <li>connectTimeout: {@code 3s}</li>
     * <li>readTimeout: {@code 7s}</li>
     * <li>maxConcurrentRequests: {@code 64}</li>
     * <li>maxIdleConnections: {@code 16}</li>
     * <li>maxIdleTime: {@code 5m}</li>
     * <li>keepAliveTimeout: {@code 3m}</li>
     * </ul>
     *
     * @return http client properties with default properties
     */
    public static OAuth2HttpClientProperties initDefault() {
        return new OAuth2HttpClientProperties()
                .setConnectTimeout(Duration.parse("PT3S"))
                .setReadTimeout(Duration.parse("PT7S"))
                .setMaxConcurrentRequests(64)
                .setMaxIdleConnections(16)
                .setMaxIdleTime(Duration.parse("PT5M"))
                .setKeepAliveTimeout(Duration.parse("PT3M"));
    }

}
