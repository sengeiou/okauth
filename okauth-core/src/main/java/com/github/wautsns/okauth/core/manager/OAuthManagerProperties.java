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
package com.github.wautsns.okauth.core.manager;

import com.github.wautsns.okauth.core.client.kernel.http.builtin.okhttp.OkHttpOAuthRequestExecutor;
import com.github.wautsns.okauth.core.client.kernel.http.model.properties.OAuthRequestExecutorProperties;
import com.github.wautsns.okauth.core.client.kernel.model.properties.OAuthClientProperties;
import com.github.wautsns.okauth.core.util.CheckableProperties;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * OAuth client manager properties.
 *
 * @author wautsns
 * @see #check()
 * @since Feb 29, 2020
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class OAuthManagerProperties extends CheckableProperties {

    /**
     * default request executor properties
     *
     * <ul>
     * Default properties are as follows:
     * <li>requestExecutorClass: {@linkplain OkHttpOAuthRequestExecutor}.class</li>
     * <li>connectTimeoutMilliseconds: 7_000</li>
     * <li>maxConcurrentRequests: 64</li>
     * <li>maxIdleConnections: 8</li>
     * <li>keepAlive: 5 * 60_000</li>
     * <li>keepAliveTimeUnit: {@link TimeUnit#MILLISECONDS}</li>
     * </ul>
     */
    private OAuthRequestExecutorProperties defaultRequestExecutor = new OAuthRequestExecutorProperties()
        .setRequestExecutorClass(OkHttpOAuthRequestExecutor.class)
        .setConnectTimeoutMilliseconds(7_000)
        .setMaxConcurrentRequests(64)
        .setMaxIdleConnections(8)
        .setKeepAlive(5L * 60_000)
        .setKeepAliveTimeUnit(TimeUnit.MILLISECONDS);
    /** oauth clients */
    private List<OAuthClientProperties> clients;

    /**
     * {@inheritDoc}
     *
     * <p>Just check if the clients contain {@code null}.</p>
     *
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Override
    public void check() {
        notNull("clients", clients);
        clients.forEach(properties -> notNull("clients@element", properties));
    }

}
