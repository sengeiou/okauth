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
package com.github.wautsns.okauth.core.client.util.http;

import java.util.concurrent.TimeUnit;

/**
 * Requester properties.
 *
 * @since Feb 18, 2020
 * @author wautsns
 */
public class RequesterProperties {

    /** requester class */
    private Class<? extends Requester> requesterClass;
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

    /** Get {@link #requesterClass}. */
    public Class<? extends Requester> getRequesterClass() {
        return requesterClass;
    }

    /** Set {@link #requesterClass}. */
    public RequesterProperties setRequesterClass(Class<? extends Requester> requesterClass) {
        this.requesterClass = requesterClass;
        return this;
    }

    /** Get {@link #connectTimeoutMilliseconds}. */
    public Integer getConnectTimeoutMilliseconds() {
        return connectTimeoutMilliseconds;
    }

    /** Set {@link #connectTimeoutMilliseconds}. */
    public RequesterProperties setConnectTimeoutMilliseconds(
            Integer connectTimeoutMilliseconds) {
        this.connectTimeoutMilliseconds = connectTimeoutMilliseconds;
        return this;
    }

    /** Get {@link #maxConcurrentRequests}. */
    public Integer getMaxConcurrentRequests() {
        return maxConcurrentRequests;
    }

    /** Set {@link #maxConcurrentRequests}. */
    public RequesterProperties setMaxConcurrentRequests(Integer maxConcurrentRequests) {
        this.maxConcurrentRequests = maxConcurrentRequests;
        return this;
    }

    /** Get {@link #maxIdleConnections}. */
    public Integer getMaxIdleConnections() {
        return maxIdleConnections;
    }

    /** Set {@link #maxIdleConnections}. */
    public RequesterProperties setMaxIdleConnections(Integer maxIdleConnections) {
        this.maxIdleConnections = maxIdleConnections;
        return this;
    }

    /** Get {@link #keepAlive}. */
    public Long getKeepAlive() {
        return keepAlive;
    }

    /** Set {@link #keepAlive}. */
    public RequesterProperties setKeepAlive(Long keepAlive) {
        this.keepAlive = keepAlive;
        return this;
    }

    /** Get {@link #keepAliveTimeUnit}. */
    public TimeUnit getKeepAliveTimeUnit() {
        return keepAliveTimeUnit;
    }

    /** Set {@link #keepAliveTimeUnit}. */
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
