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
package com.github.wautsns.okauth.core.http;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Duration;

/**
 * Http client properties.
 *
 * @author wautsns
 * @since Mar 04, 2020
 */
@Data
@Accessors(chain = true)
public class HttpClientProperties {

    /** connect time out milliseconds */
    private Integer connectTimeoutMilliseconds;
    /** max concurrent requests */
    private Integer maxConcurrentRequests;
    /** max idle connection */
    private Integer maxIdleConnections;
    /** keep alive */
    private Duration keepAlive;

    /**
     * Initialize default http client properties.
     *
     * <ul>
     * default properties are as followers:
     * <li>connectTimeoutMilliseconds: {@code 7_000}</li>
     * <li>maxConcurrentRequests: {@code 64}</li>
     * <li>maxIdleConnections: {@code 8}</li>
     * <li>keepAlive: {@code Duration.ofMinutes(5)}</li>
     * </ul>
     *
     * @return http client properties with default properties
     */
    public static HttpClientProperties initDefault() {
        return new HttpClientProperties()
                .setConnectTimeoutMilliseconds(7_000)
                .setMaxConcurrentRequests(64)
                .setMaxIdleConnections(8)
                .setKeepAlive(Duration.ofMinutes(5));
    }

}
