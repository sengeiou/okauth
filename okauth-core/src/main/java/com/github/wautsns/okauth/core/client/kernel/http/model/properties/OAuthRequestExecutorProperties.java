/**
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

import java.util.concurrent.TimeUnit;

import com.github.wautsns.okauth.core.client.kernel.http.OAuthRequestExecutor;
import com.github.wautsns.okauth.core.util.CheckableProperties;

/**
 * OAuth request executor properties.
 *
 * @since Feb 29, 2020
 * @author wautsns
 * @see OAuthRequestExecutorProperties#check()
 */
public class OAuthRequestExecutorProperties extends CheckableProperties {

    /**
     * request executor class(Must have a constructor with parameter type of
     * {@linkplain OAuthRequestExecutorProperties})
     */
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
     * Get request executor class.
     *
     * @return request executor class.
     */
    public Class<? extends OAuthRequestExecutor> getRequestExecutorClass() {
        return requestExecutorClass;
    }

    /**
     * Set request executor class.
     *
     * <p>The request executor class must have a constructor with parameter type of
     * {@linkplain OAuthRequestExecutorProperties}
     *
     * @param requestExecutorClass request executor class
     * @return self reference
     */
    public OAuthRequestExecutorProperties setRequestExecutorClass(
            Class<? extends OAuthRequestExecutor> requestExecutorClass) {
        if (requestExecutorClass != null) {
            try {
                requestExecutorClass.getConstructor(OAuthRequestExecutorProperties.class);
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException(
                    "request executor must have a constructor with parameter type of"
                        + " OAuthRequestExecutorProperties",
                    e);
            }
        }
        this.requestExecutorClass = requestExecutorClass;
        return this;
    }

    /**
     * Get connect timeout milliseconds.
     *
     * @return connect timeout milliseconds
     */
    public Integer getConnectTimeoutMilliseconds() {
        return connectTimeoutMilliseconds;
    }

    /**
     * Set connect timeout milliseconds.
     *
     * @param connectTimeoutMilliseconds connect timeout milliseconds
     * @return self reference
     */
    public OAuthRequestExecutorProperties setConnectTimeoutMilliseconds(
            Integer connectTimeoutMilliseconds) {
        this.connectTimeoutMilliseconds = connectTimeoutMilliseconds;
        return this;
    }

    /**
     * Get max concurrent requests.
     *
     * @return max concurrent requests
     */
    public Integer getMaxConcurrentRequests() {
        return maxConcurrentRequests;
    }

    /**
     * Set max concurrent requests.
     *
     * @param maxConcurrentRequests max concurrent requests
     * @return self reference
     */
    public OAuthRequestExecutorProperties setMaxConcurrentRequests(Integer maxConcurrentRequests) {
        this.maxConcurrentRequests = maxConcurrentRequests;
        return this;
    }

    /**
     * Get max idle connections.
     *
     * @return max idle connections
     */
    public Integer getMaxIdleConnections() {
        return maxIdleConnections;
    }

    /**
     * Set max idle connections.
     *
     * @param maxIdleConnections max idle connections
     * @return max idle connections
     */
    public OAuthRequestExecutorProperties setMaxIdleConnections(Integer maxIdleConnections) {
        this.maxIdleConnections = maxIdleConnections;
        return this;
    }

    /**
     * Get keep alive.
     *
     * @return keep alive
     */
    public Long getKeepAlive() {
        return keepAlive;
    }

    /**
     * Set keep alive.
     *
     * @param keepAlive keep alive
     * @return self reference
     */
    public OAuthRequestExecutorProperties setKeepAlive(Long keepAlive) {
        this.keepAlive = keepAlive;
        return this;
    }

    /**
     * Get keep alive time unit.
     *
     * @return keep alive time unit
     */
    public TimeUnit getKeepAliveTimeUnit() {
        return keepAliveTimeUnit;
    }

    /**
     * Set keep alive time unit.
     *
     * @param keepAliveTimeUnit keep alive time unit
     * @return self reference
     */
    public OAuthRequestExecutorProperties setKeepAliveTimeUnit(TimeUnit keepAliveTimeUnit) {
        this.keepAliveTimeUnit = keepAliveTimeUnit;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * All the properties must not be {@code null} and the requestExecutorClass must have a
     * construct &lt;init>(OAuthRequestExecutorProperties)
     *
     * @throws IllegalArgumentException @{@link IncompatibleClassChangeError}
     */
    @Override
    public void check() {
        notNull("requestExecutorClass", requestExecutorClass);
        notNull("connectTimeoutMilliseconds", connectTimeoutMilliseconds);
        notNull("maxConcurrentRequests", maxConcurrentRequests);
        notNull("maxIdleConnections", maxIdleConnections);
        notNull("keepAlive", keepAlive);
        notNull("keepAliveTimeUnit", keepAliveTimeUnit);
        requireConstructor(
            "requestExecutorClass", requestExecutorClass,
            OAuthRequestExecutorProperties.class);
    }

    // ----------------------- utils ----------------------------------------------

    /**
     * Fill the null properties with ref.
     *
     * @param ref that requestProperties
     * @return self reference
     */
    public OAuthRequestExecutorProperties fillNullPropertiesWith(
            OAuthRequestExecutorProperties ref) {
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
