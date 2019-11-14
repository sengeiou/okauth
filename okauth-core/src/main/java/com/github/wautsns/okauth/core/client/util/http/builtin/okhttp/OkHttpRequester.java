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
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;

/**
 * Okhttp3 requester.
 *
 * <p>Based on okhttp3.
 *
 * @author wautsns
 */
public class OkHttpRequester extends Requester {

    /** okhttp3 client */
    private final OkHttpClient okhttpClient;

    /**
     * Construct okhttp requester.
     *
     * @param okhttpClient okhttp client, require nonnull
     */
    public OkHttpRequester(OkHttpClient okhttpClient) {
        this.okhttpClient = okhttpClient;
    }

    /**
     * Construct okhttp requester.
     *
     * @param properties okauth http properties, require nonnull
     */
    public OkHttpRequester(OkAuthRequesterProperties properties) {
        Builder builder = new OkHttpClient.Builder()
            .connectTimeout(properties.getConnectTimeoutMilliseconds(), TimeUnit.MILLISECONDS)
            .readTimeout(3, TimeUnit.SECONDS)
            .writeTimeout(3, TimeUnit.SECONDS)
            .connectionPool(new ConnectionPool(
                properties.getMaxIdleConnections(),
                properties.getKeepAlive(),
                properties.getKeepAliveTimeUnit()));
        OkHttpClient client = builder.build();
        Dispatcher dispatcher = client.dispatcher();
        dispatcher.setMaxRequests(properties.getMaxConcurrentRequests());
        // a requester is recommended to be used for only one open platform,
        // so max requests equals to max requests per host
        dispatcher.setMaxRequestsPerHost(properties.getMaxConcurrentRequests());
        this.okhttpClient = client;
    }

    @Override
    protected Request create(Method httpMethod, String url) {
        return new OkHttpRequest(okhttpClient, httpMethod, url);
    }

}
