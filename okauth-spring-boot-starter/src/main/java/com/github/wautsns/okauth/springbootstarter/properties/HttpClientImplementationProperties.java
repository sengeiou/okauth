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
package com.github.wautsns.okauth.springbootstarter.properties;

import com.github.wautsns.okauth.core.http.HttpClient;
import com.github.wautsns.okauth.core.http.HttpClientProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.Duration;

/**
 * Http client implementation properties.
 *
 * @author wautsns
 * @since Mar 05, 2020
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class HttpClientImplementationProperties extends HttpClientProperties {

    private Class<? extends HttpClient> implementation;

    @Override
    public HttpClientImplementationProperties setConnectTimeoutMilliseconds(Integer connectTimeoutMilliseconds) {
        super.setConnectTimeoutMilliseconds(connectTimeoutMilliseconds);
        return this;
    }

    @Override
    public HttpClientImplementationProperties setMaxConcurrentRequests(Integer maxConcurrentRequests) {
        super.setMaxConcurrentRequests(maxConcurrentRequests);
        return this;
    }

    @Override
    public HttpClientImplementationProperties setMaxIdleConnections(Integer maxIdleConnections) {
        super.setMaxIdleConnections(maxIdleConnections);
        return this;
    }

    @Override
    public HttpClientImplementationProperties setKeepAlive(Duration keepAlive) {
        super.setKeepAlive(keepAlive);
        return this;
    }

}
