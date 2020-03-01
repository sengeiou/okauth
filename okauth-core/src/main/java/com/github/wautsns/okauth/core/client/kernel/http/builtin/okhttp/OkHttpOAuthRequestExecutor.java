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
package com.github.wautsns.okauth.core.client.kernel.http.builtin.okhttp;

import java.io.IOException;
import java.io.Serializable;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import com.github.wautsns.okauth.core.client.kernel.http.OAuthRequestExecutor;
import com.github.wautsns.okauth.core.client.kernel.http.model.dto.OAuthRequest;
import com.github.wautsns.okauth.core.client.kernel.http.model.dto.OAuthRequest.Method;
import com.github.wautsns.okauth.core.client.kernel.http.model.dto.OAuthResponse;
import com.github.wautsns.okauth.core.client.kernel.http.model.properties.OAuthRequestExecutorProperties;
import com.github.wautsns.okauth.core.client.kernel.http.util.OAuthResponseInputStreamReader;
import com.github.wautsns.okauth.core.exception.OAuthIOException;

import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OkHttp3 oauth request executor.
 *
 * @since Feb 29, 2020
 * @author wautsns
 */
public class OkHttpOAuthRequestExecutor implements OAuthRequestExecutor {

    /** okhttp3 client */
    private final OkHttpClient client;

    /**
     * Construct okhttp3 oauth request executor.
     *
     * @param okhttpClient okhttp client, require nonnull
     */
    public OkHttpOAuthRequestExecutor(OkHttpClient okhttpClient) {
        this.client = Objects.requireNonNull(okhttpClient);
    }

    /**
     * Construct okhttp3 oauth request executor.
     *
     * @param properties oauth request executor properties, require nonnull
     */
    public OkHttpOAuthRequestExecutor(OAuthRequestExecutorProperties properties) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
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
        this.client = client;
    }

    @Override
    public OAuthResponse execute(OAuthRequest request) throws IOException {
        if (request.getMethod() == Method.GET) {
            return doExecuteGet(request);
        } else {
            return doExecuteNonGet(request);
        }
    }

    /**
     * Do execute request(GET).
     *
     * @param request oauth request, require nonnull
     * @return oauth response
     * @throws OAuthIOException if IO exception occurs
     */
    private OAuthResponse doExecuteGet(OAuthRequest request) throws IOException {
        Request.Builder builder = new Request.Builder();
        builder.url(request.getUrl().toString());
        request.getHeaders().forEach(builder::addHeader);
        builder.get();
        return doExecute(request, builder.build());
    }

    /**
     * Do execute request(non GET).
     *
     * @param request oauth request, require nonnull
     * @return oauth response
     * @throws OAuthIOException if IO exception occurs
     */
    private OAuthResponse doExecuteNonGet(OAuthRequest request) throws IOException {
        Request.Builder builder = new Request.Builder();
        builder.url(request.getUrl().toString());
        request.getHeaders().forEach(builder::addHeader);
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        request.getForm().forEach(formBodyBuilder::addEncoded);
        builder.method(request.getMethod().name(), formBodyBuilder.build());
        return doExecute(request, builder.build());
    }

    /**
     * Do execute request.
     *
     * @param oauthRequest oauth request, require nonnull
     * @param okhttp3Request okhttp3 request, require nonnull
     * @return oauth response
     * @throws IOException if IO exception occurs
     */
    private OAuthResponse doExecute(OAuthRequest oauthRequest, Request okhttp3Request)
            throws IOException {
        Response okHttp3Response = client.newCall(okhttp3Request).execute();
        int status = okHttp3Response.code();
        OAuthResponseInputStreamReader reader = oauthRequest.getResponseInputStreamReader();
        Map<String, Serializable> data = reader.readAsMap(okHttp3Response.body().byteStream());
        return new OAuthResponse(oauthRequest, status, data);
    }

}
