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
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * OAuth2 http client properties.
 *
 * @author wautsns
 * @since May 17, 2020
 */
@Data
@Accessors(chain = true)
public class OAuth2HttpClientProperties {

    /** Connect timeout. */
    private Duration connectTimeout;
    /** Read timeout. */
    private Duration readTimeout;
    /** Max concurrent requests. */
    private Integer maxConcurrentRequests;
    /** Max idle time of connection. */
    private Duration maxIdleTime;
    /** Keep alive timeout of connection. */
    private Duration keepAliveTimeout;
    /** Retry times. */
    private Integer retryTimes;
    /** Proxy([SCHEME://]IP[:PORT]). */
    private String proxy;
    /** Custom properties. */
    private Map<String, Object> customProperties;

    /**
     * Create and return a copy of this object.
     *
     * @return a copy of this properties
     */
    public OAuth2HttpClientProperties copy() {
        return new OAuth2HttpClientProperties()
                .setConnectTimeout(connectTimeout)
                .setReadTimeout(readTimeout)
                .setMaxConcurrentRequests(maxConcurrentRequests)
                .setMaxIdleTime(maxIdleTime)
                .setKeepAliveTimeout(keepAliveTimeout)
                .setReadTimeout(readTimeout)
                .setProxy(proxy)
                .setCustomProperties((customProperties == null) ? null : new LinkedHashMap<>(customProperties));
    }

    // #################### utils #######################################################

    /**
     * Initialize default http client properties.
     *
     * <ul>
     * <li>connectTimeout: {@code 3s}</li>
     * <li>readTimeout: {@code 7s}</li>
     * <li>maxConcurrentRequests: {@code 64}</li>
     * <li>maxIdleTime: {@code 5m}</li>
     * <li>keepAliveTimeout: {@code 3m}</li>
     * <li>retryTimes: {@code 2}</li>
     * </ul>
     *
     * @return http client properties with default properties
     */
    public static OAuth2HttpClientProperties initDefault() {
        return new OAuth2HttpClientProperties()
                .setConnectTimeout(Duration.parse("PT3S"))
                .setReadTimeout(Duration.parse("PT7S"))
                .setMaxConcurrentRequests(64)
                .setMaxIdleTime(Duration.parse("PT5M"))
                .setKeepAliveTimeout(Duration.parse("PT3M"))
                .setRetryTimes(1);
    }

    /**
     * Fill null properties.
     *
     * @param target target properties
     * @param source source properties
     * @return target properties
     */
    public static OAuth2HttpClientProperties fillNullProperties(
            OAuth2HttpClientProperties target, OAuth2HttpClientProperties source) {
        if (source == null) { return target; }
        if (target == null) { return source.copy(); }
        if (target.connectTimeout == null) { target.connectTimeout = source.connectTimeout; }
        if (target.readTimeout == null) { target.readTimeout = source.readTimeout; }
        if (target.maxConcurrentRequests == null) { target.maxConcurrentRequests = source.maxConcurrentRequests; }
        if (target.maxIdleTime == null) { target.maxIdleTime = source.maxIdleTime; }
        if (target.keepAliveTimeout == null) { target.keepAliveTimeout = source.keepAliveTimeout; }
        if (target.retryTimes == null) { target.retryTimes = source.retryTimes; }
        if (target.proxy == null) { target.proxy = source.proxy; }
        if (source.customProperties != null) {
            Map<String, Object> targetCustomProperties = new LinkedHashMap<>(source.customProperties);
            if (target.customProperties != null) {
                targetCustomProperties.putAll(target.customProperties);
            }
            target.customProperties = targetCustomProperties;
        }
        return target;
    }

}
