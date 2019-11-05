package com.github.wautsns.okauth.core.client.util.http.properties;

import java.util.concurrent.TimeUnit;

import com.github.wautsns.okauth.core.client.util.http.Requester;
import com.github.wautsns.okauth.core.client.util.http.builtin.okhttp.OkHttpRequester;

/**
 *
 * @author wautsns
 */
public class OkAuthHttpProperties {

    private Class<? extends Requester> requesterClass = OkHttpRequester.class;
    private Integer maxIdleConnections = 5;
    private Long keepAlive = 5L * 60_000;
    private TimeUnit keepAliveTimeUnit = TimeUnit.MILLISECONDS;
    private Integer connectTimeoutMilliseconds = 5_000;

    /** Get {@link #requesterClass}. */
    public Class<? extends Requester> getRequesterClass() {
        return requesterClass;
    }

    /** Set {@link #requesterClass}. */
    public OkAuthHttpProperties setRequesterClass(Class<? extends Requester> requesterClass) {
        this.requesterClass = requesterClass;
        return this;
    }

    /** Get {@link #maxIdleConnections}. */
    public Integer getMaxIdleConnections() {
        return maxIdleConnections;
    }

    /** Set {@link #maxIdleConnections}. */
    public OkAuthHttpProperties setMaxIdleConnections(Integer maxIdleConnections) {
        this.maxIdleConnections = maxIdleConnections;
        return this;
    }

    /** Get {@link #keepAlive}. */
    public Long getKeepAlive() {
        return keepAlive;
    }

    /** Set {@link #keepAlive}. */
    public OkAuthHttpProperties setKeepAlive(Long keepAlive) {
        this.keepAlive = keepAlive;
        return this;
    }

    /** Get {@link #keepAliveTimeUnit}. */
    public TimeUnit getKeepAliveTimeUnit() {
        return keepAliveTimeUnit;
    }

    /** Set {@link #keepAliveTimeUnit}. */
    public OkAuthHttpProperties setKeepAliveTimeUnit(TimeUnit keepAliveTimeUnit) {
        this.keepAliveTimeUnit = keepAliveTimeUnit;
        return this;
    }

    /** Get {@link #connectTimeoutMilliSeconds}. */
    public Integer getConnectTimeoutMilliseconds() {
        return connectTimeoutMilliseconds;
    }

    /** Set {@link #connectTimeoutMilliSeconds}. */
    public OkAuthHttpProperties setConnectTimeoutMilliseconds(Integer connectTimeoutMilliseconds) {
        this.connectTimeoutMilliseconds = connectTimeoutMilliseconds;
        return this;
    }

}
