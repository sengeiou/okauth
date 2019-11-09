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
package com.github.wautsns.okauth.core.client.util.http.builtin.okhttp;

import java.util.concurrent.TimeUnit;

import com.github.wautsns.okauth.core.client.util.http.Request;
import com.github.wautsns.okauth.core.client.util.http.Request.Method;
import com.github.wautsns.okauth.core.client.util.http.Requester;
import com.github.wautsns.okauth.core.client.util.http.properties.OkAuthRequesterProperties;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

/**
 * Okhttp3 requester.
 *
 * <p>Based on okhttp3.
 *
 * @author wautsns
 */
public class OkHttpRequester extends Requester {

    /** okhttp3 client */
    private final OkHttpClient okHttpClient;

    /**
     * Construct okhttp requester.
     *
     * @param okHttpClient okhttp client, require nonnull
     */
    public OkHttpRequester(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    /**
     * Construct okhttp requester.
     *
     * @param properties okauth http properties, require nonnull
     */
    public OkHttpRequester(OkAuthRequesterProperties properties) {
        this.okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(properties.getConnectTimeoutMilliseconds(), TimeUnit.MILLISECONDS)
            .readTimeout(3, TimeUnit.SECONDS)
            .writeTimeout(3, TimeUnit.SECONDS)
            .connectionPool(new ConnectionPool(
                properties.getMaxIdleConnections(),
                properties.getKeepAlive(),
                properties.getKeepAliveTimeUnit()))
            .build();
    }

    @Override
    protected Request create(Method httpMethod, String url) {
        return new OkHttpRequest(okHttpClient, httpMethod, url);
    }

}
