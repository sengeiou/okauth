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
package com.github.wautsns.okauth.core.http.builtin.okhttp;

import com.github.wautsns.okauth.core.http.HttpClient;
import com.github.wautsns.okauth.core.http.HttpClientProperties;
import com.github.wautsns.okauth.core.http.model.OAuthRequest;
import com.github.wautsns.okauth.core.http.model.OAuthResponse;
import com.github.wautsns.okauth.core.http.util.DataMap;
import com.github.wautsns.okauth.core.http.util.ReadInputStreamAsDataMap;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Http client based on okhttp3.
 *
 * @author wautsns
 * @since Mar 04, 2020
 */
public class OkHttp3HttpClient implements HttpClient {

    /** okhttp3 client */
    private final OkHttpClient client;

    /**
     * Construct okhttp3 http client.
     *
     * @param client okhttp client, require nonnull
     */
    public OkHttp3HttpClient(OkHttpClient client) {
        this.client = Objects.requireNonNull(client);
    }

    /**
     * Construct okhttp3 http client.
     *
     * @param properties http client properties, require nonnull
     */
    public OkHttp3HttpClient(HttpClientProperties properties) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(properties.getConnectTimeoutMilliseconds(), TimeUnit.MILLISECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(
                        properties.getMaxIdleConnections(),
                        properties.getKeepAlive().getSeconds(), TimeUnit.SECONDS));
        client = builder.build();
        Dispatcher dispatcher = client.dispatcher();
        dispatcher.setMaxRequests(properties.getMaxConcurrentRequests());
        dispatcher.setMaxRequestsPerHost(properties.getMaxConcurrentRequests());
    }

    @Override
    public OAuthResponse execute(OAuthRequest request) throws IOException {
        if (request.getMethod() == OAuthRequest.Method.GET) {
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
     * @throws IOException if IO exception occurs
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
     * @throws IOException if IO exception occurs
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
    private OAuthResponse doExecute(OAuthRequest oauthRequest, Request okhttp3Request) throws IOException {
        Response okHttp3Response = client.newCall(okhttp3Request).execute();
        int status = okHttp3Response.code();
        ReadInputStreamAsDataMap reader = oauthRequest.getResponseDataReader();
        DataMap data = reader.read(Objects.requireNonNull(okHttp3Response.body()).byteStream());
        return new OAuthResponse(status, data);
    }

}
