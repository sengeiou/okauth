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
package com.github.wautsns.okauth.core.manager;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.github.wautsns.okauth.core.client.kernel.http.builtin.okhttp.OkHttpOAuthRequestExecutor;
import com.github.wautsns.okauth.core.client.kernel.http.model.properties.OAuthRequestExecutorProperties;
import com.github.wautsns.okauth.core.client.kernel.model.properties.OAuthClientProperties;
import com.github.wautsns.okauth.core.util.CheckableProperties;

/**
 * OAuth client manager properties.
 *
 * @since Feb 29, 2020
 * @author wautsns
 * @see OAuthManagerProperties#check()
 */
public class OAuthManagerProperties extends CheckableProperties {

    /**
     * default request executor properties
     *
     * <p>Default values are as follows:
     * <ul>
     * <li>requestExecutorClass: {@linkplain OkHttpOAuthRequestExecutor}.class</li>
     * <li>connectTimeoutMilliseconds: 7_000</li>
     * <li>maxConcurrentRequests: 64</li>
     * <li>maxIdleConnections: 8</li>
     * <li>keepAlive: 5 * 60_000</li>
     * <li>keepAliveTimeUnit: {@linkplain TimeUnit#MILLISECONDS}</li>
     * </ul>
     */
    private OAuthRequestExecutorProperties defaultRequestExecutor =
        new OAuthRequestExecutorProperties()
            .setRequestExecutorClass(OkHttpOAuthRequestExecutor.class)
            .setConnectTimeoutMilliseconds(7_000)
            .setMaxConcurrentRequests(64)
            .setMaxIdleConnections(8)
            .setKeepAlive(5L * 60_000)
            .setKeepAliveTimeUnit(TimeUnit.MILLISECONDS);
    /** oauth clients */
    private List<OAuthClientProperties> clients;

    /**
     * Get default request executor.
     *
     * <p>Default values are as follows:
     * <ul>
     * <li>requestExecutorClass: {@linkplain OkHttpOAuthRequestExecutor}.class</li>
     * <li>connectTimeoutMilliseconds: 7_000</li>
     * <li>maxConcurrentRequests: 64</li>
     * <li>maxIdleConnections: 8</li>
     * <li>keepAlive: 5 * 60_000</li>
     * <li>keepAliveTimeUnit: {@linkplain TimeUnit#MILLISECONDS}</li>
     * </ul>
     *
     * @return default request executor
     */
    public OAuthRequestExecutorProperties getDefaultRequestExecutor() {
        return defaultRequestExecutor;
    }

    /**
     * Set default request executor.
     *
     * @param defaultRequestExecutor default request executor
     * @return self reference
     */
    public OAuthManagerProperties setDefaultRequestExecutor(
            OAuthRequestExecutorProperties defaultRequestExecutor) {
        this.defaultRequestExecutor = defaultRequestExecutor;
        return this;
    }

    /**
     * Get clients.
     *
     * @return clients
     */
    public List<OAuthClientProperties> getClients() {
        return clients;
    }

    /**
     * Set clients.
     *
     * @param clients clients
     * @return self reference
     */
    public OAuthManagerProperties setClients(List<OAuthClientProperties> clients) {
        this.clients = clients;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Just check if the clients(include keys and values) is {@code null}.
     *
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Override
    public void check() {
        notNull("clients", clients);
        clients.forEach(properties -> notNull("clients@element", properties));
    }

}
