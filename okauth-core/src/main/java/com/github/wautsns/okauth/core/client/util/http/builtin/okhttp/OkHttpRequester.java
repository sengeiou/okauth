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
package com.github.wautsns.okauth.core.client.util.http.builtin.okhttp;

import java.io.IOException;

import java.util.concurrent.TimeUnit;

import com.github.wautsns.okauth.core.client.util.http.OkAuthRequest;
import com.github.wautsns.okauth.core.client.util.http.OkAuthRequester;
import com.github.wautsns.okauth.core.client.util.http.OkAuthResponse;
import com.github.wautsns.okauth.core.client.util.http.OkAuthResponse.MapDateInputStreamReader;
import com.github.wautsns.okauth.core.client.util.http.RequesterProperties;
import com.github.wautsns.okauth.core.exception.OkAuthIOException;

import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Okhttp3 requester.
 *
 * <p>Based on okhttp3.
 *
 * @since Feb 27, 2020
 * @author wautsns
 */
public class OkHttpRequester extends OkAuthRequester {

    /** okhttp3 client */
    private final OkHttpClient okhttpClient;

    /**
     * Construct an okhttp requester.
     *
     * @param okhttpClient okhttp client, require nonnull
     */
    public OkHttpRequester(OkHttpClient okhttpClient) {
        this.okhttpClient = okhttpClient;
    }

    /**
     * Construct an okhttp requester.
     *
     * @param properties okauth http properties, require nonnull and all properties require nonnull
     */
    public OkHttpRequester(RequesterProperties properties) {
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
    protected OkAuthResponse doGet(OkAuthRequest request) throws OkAuthIOException {
        Request.Builder builder = new Request.Builder();
        builder.get().url(request.getUrl());
        request.forEachHeader(builder::addHeader);
        return doExecute(builder.build(), request.getResponseInputStreamReader());
    }

    @Override
    protected OkAuthResponse doPost(OkAuthRequest request) throws OkAuthIOException {
        Request.Builder builder = new Request.Builder();
        request.forEachHeader(builder::addHeader);
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        request.forEachUrlEncodedFormItem(formBodyBuilder::addEncoded);
        builder.post(formBodyBuilder.build()).url(request.getUrl());
        return doExecute(builder.build(), request.getResponseInputStreamReader());
    }

    /**
     * Do execute request.
     *
     * @param request okhttp3 request, require nonnull
     * @param reader response input stream reader, require nonnull
     * @return response
     * @throws OkAuthIOException if an IO exception occurs
     */
    private OkAuthResponse doExecute(Request request, MapDateInputStreamReader reader)
            throws OkAuthIOException {
        try {
            Response response = okhttpClient.newCall(request).execute();
            return new OkAuthResponse(response.code(), reader.read(response.body().byteStream()));
        } catch (IOException ioException) {
            throw new OkAuthIOException(ioException);
        }
    }

}
