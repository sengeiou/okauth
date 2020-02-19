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
package com.github.wautsns.okauth.core.manager;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.github.wautsns.okauth.core.client.core.properties.OkAuthClientProperties;
import com.github.wautsns.okauth.core.client.util.http.RequesterProperties;
import com.github.wautsns.okauth.core.client.util.http.builtin.okhttp.OkHttpRequester;

/**
 * Okauth properties.
 *
 * @author wautsns
 */
public class OkAuthProperties {

    /**
     * default requester and the default values are as follows<br/>
     * <ul>
     * <li>requestClass: {@linkplain OkHttpRequester}.class</li>
     * <li>connectTimeoutMilliseconds: 7_000</li>
     * <li>maxConcurrentRequests: 64</li>
     * <li>maxIdleConnections: 8</li>
     * <li>keepAlive: 5 * 60_000</li>
     * <li>keepAliveTimeUnit: {@linkplain TimeUnit#MILLISECONDS}</li>
     * </ul>
     */
    private RequesterProperties defaultRequester = new RequesterProperties()
        .setRequesterClass(OkHttpRequester.class)
        .setConnectTimeoutMilliseconds(7_000)
        .setMaxConcurrentRequests(32)
        .setMaxIdleConnections(8)
        .setKeepAlive(5L * 60_000)
        .setKeepAliveTimeUnit(TimeUnit.MILLISECONDS);
    /** okauth clients */
    private List<OkAuthClientProperties> clients;

    /** Get {@link #defaultRequester}. */
    public RequesterProperties getDefaultRequester() {
        return defaultRequester;
    }

    /** Set {@link #defaultRequester}(The null value will be filled with default value). */
    public OkAuthProperties setDefaultRequester(RequesterProperties defaultRequester) {
        this.defaultRequester = defaultRequester.fillNullPropertiesWithThat(this.defaultRequester);
        return this;
    }

    /** Get {@link #clients}. */
    public List<OkAuthClientProperties> getClients() {
        return clients;
    }

    /** Set {@link #clients}. */
    public OkAuthProperties setClients(List<OkAuthClientProperties> clients) {
        this.clients = clients;
        return this;
    }

}
