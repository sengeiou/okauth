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
package com.github.wautsns.okauth.core.client.kernel.http.model.properties;

import com.github.wautsns.okauth.core.client.kernel.http.OAuthRequestExecutor;
import com.github.wautsns.okauth.core.util.CheckableProperties;
import java.util.concurrent.TimeUnit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * OAuth request executor properties.
 *
 * @author wautsns
 * @see OAuthRequestExecutorProperties#check()
 * @since Feb 29, 2020
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class OAuthRequestExecutorProperties extends CheckableProperties {

    /** request executor class(Must have a constructor with parameter type {@link OAuthRequestExecutorProperties}) */
    private Class<? extends OAuthRequestExecutor> requestExecutorClass;
    /** connect time out milliseconds */
    private Integer connectTimeoutMilliseconds;
    /** max concurrent requests */
    private Integer maxConcurrentRequests;
    /** max idle connection */
    private Integer maxIdleConnections;
    /** keep alive */
    private Long keepAlive;
    /** keep alive time unit */
    private TimeUnit keepAliveTimeUnit;

    /**
     * {@inheritDoc}
     *
     * <p>All the properties must not be {@code null} and the requestExecutorClass must have a constructor with
     * parameter type {@link OAuthRequestExecutorProperties}.
     *
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Override
    public void check() {
        notNull("requestExecutorClass", requestExecutorClass);
        notNull("connectTimeoutMilliseconds", connectTimeoutMilliseconds);
        notNull("maxConcurrentRequests", maxConcurrentRequests);
        notNull("maxIdleConnections", maxIdleConnections);
        notNull("keepAlive", keepAlive);
        notNull("keepAliveTimeUnit", keepAliveTimeUnit);
        requireConstructor("requestExecutorClass", requestExecutorClass, OAuthRequestExecutorProperties.class);
    }

    // ----------------------- utils ----------------------------------------------

    /**
     * Fill the null properties with ref.
     *
     * @param ref ref requestProperties
     * @return self reference
     */
    public OAuthRequestExecutorProperties fillNullPropertiesWith(OAuthRequestExecutorProperties ref) {
        if (this.requestExecutorClass == null) {
            this.requestExecutorClass = ref.requestExecutorClass;
        }
        if (this.connectTimeoutMilliseconds == null) {
            this.connectTimeoutMilliseconds = ref.connectTimeoutMilliseconds;
        }
        if (this.maxConcurrentRequests == null) {
            this.maxConcurrentRequests = ref.maxConcurrentRequests;
        }
        if (this.maxIdleConnections == null) {
            this.maxIdleConnections = ref.maxIdleConnections;
        }
        if (this.keepAliveTimeUnit == null
            && (ref.keepAliveTimeUnit != null && ref.keepAlive != null)) {
            this.keepAliveTimeUnit = ref.keepAliveTimeUnit;
        }
        if (this.keepAlive == null) {
            this.keepAlive = ref.keepAlive;
        }
        return this;
    }

}
