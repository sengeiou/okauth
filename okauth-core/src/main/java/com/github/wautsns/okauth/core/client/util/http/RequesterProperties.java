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
package com.github.wautsns.okauth.core.client.util.http;

import java.util.concurrent.TimeUnit;

/**
 * Requester properties.
 *
 * @since Feb 27, 2020
 * @author wautsns
 */
public class RequesterProperties {

    /** requester class */
    private Class<? extends OkAuthRequester> requesterClass;
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
     * Get requester class.
     *
     * @return requester class.
     */
    public Class<? extends OkAuthRequester> getRequesterClass() {
        return requesterClass;
    }

    /**
     * Set requester class.
     *
     * @param requesterClass requester class
     * @return self reference
     */
    public RequesterProperties setRequesterClass(Class<? extends OkAuthRequester> requesterClass) {
        this.requesterClass = requesterClass;
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
    public RequesterProperties setConnectTimeoutMilliseconds(
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
    public RequesterProperties setMaxConcurrentRequests(Integer maxConcurrentRequests) {
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
    public RequesterProperties setMaxIdleConnections(Integer maxIdleConnections) {
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
    public RequesterProperties setKeepAlive(Long keepAlive) {
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
    public RequesterProperties setKeepAliveTimeUnit(TimeUnit keepAliveTimeUnit) {
        this.keepAliveTimeUnit = keepAliveTimeUnit;
        return this;
    }

    // ----------------------- utils ----------------------------------------------

    /**
     * Fill the null properties with that.
     *
     * @param that that requestProperties
     * @return self reference
     */
    public RequesterProperties fillNullPropertiesWithThat(RequesterProperties that) {
        if (this.requesterClass == null) {
            this.requesterClass = that.requesterClass;
        }
        if (this.connectTimeoutMilliseconds == null) {
            this.connectTimeoutMilliseconds = that.connectTimeoutMilliseconds;
        }
        if (this.maxConcurrentRequests == null) {
            this.maxConcurrentRequests = that.maxConcurrentRequests;
        }
        if (this.maxIdleConnections == null) {
            this.maxIdleConnections = that.maxIdleConnections;
        }
        if (this.keepAliveTimeUnit == null
            && (that.keepAliveTimeUnit != null && that.keepAlive != null)) {
            this.keepAliveTimeUnit = that.keepAliveTimeUnit;
        }
        if (this.keepAlive == null) {
            this.keepAlive = that.keepAlive;
        }
        return this;
    }

}
