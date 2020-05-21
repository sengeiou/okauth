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
package com.github.wautsns.okauth.core.assist.http.builtin.okhttp3;

import com.github.wautsns.okauth.core.assist.http.kernel.OAuth2HttpClient;
import com.github.wautsns.okauth.core.assist.http.kernel.model.OAuth2HttpRequest;
import com.github.wautsns.okauth.core.assist.http.kernel.model.OAuth2HttpResponse;
import com.github.wautsns.okauth.core.assist.http.kernel.properties.OAuth2HttpClientProperties;
import com.github.wautsns.okauth.core.exception.OAuth2IOException;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpMethod;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * OkHttp3 oauth2 http client.
 *
 * @author wautsns
 * @since May 17, 2020
 */
public class OkHttp3OAuth2HttpClient implements OAuth2HttpClient {

    /** default okhttp3 oauth2 http client */
    public static final OkHttp3OAuth2HttpClient DEFAULT = new OkHttp3OAuth2HttpClient(
            OAuth2HttpClientProperties.initDefault());

    /** okhttp3 http client */
    private final OkHttpClient httpClient;

    /**
     * Construct okhttp3 oauth http client.
     *
     * @param okHttpClient okhttp client
     */
    public OkHttp3OAuth2HttpClient(OkHttpClient okHttpClient) {
        this.httpClient = Objects.requireNonNull(okHttpClient);
    }

    /**
     * Construct okhttp3 oauth http client.
     *
     * @param properties oauth http client properties
     */
    public OkHttp3OAuth2HttpClient(OAuth2HttpClientProperties properties) {
        // connection pool
        ConnectionPool connectionPool = new ConnectionPool(
                properties.getMaxIdleConnections(),
                properties.getKeepAliveTimeout().toMillis(), TimeUnit.MILLISECONDS);
        // http client
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(properties.getConnectTimeout())
                .readTimeout(properties.getReadTimeout())
                .connectionPool(connectionPool);
        httpClient = builder.build();
        Dispatcher dispatcher = httpClient.dispatcher();
        dispatcher.setMaxRequests(properties.getMaxConcurrentRequests());
        dispatcher.setMaxRequestsPerHost(properties.getMaxConcurrentRequests());
    }

    @Override
    public OAuth2HttpResponse execute(OAuth2HttpRequest request) throws OAuth2IOException {
        try {
            return execute(initOkHttp3Request(request));
        } catch (IOException e) {
            throw new OAuth2IOException(e);
        }
    }

    /**
     * Initialize okhttp3 request.
     *
     * @param request oauth http request
     * @return okhttp3 request
     */
    private Request initOkHttp3Request(OAuth2HttpRequest request) {
        Request.Builder builder = new Request.Builder();
        builder.url(request.getUrl().toString());
        request.forEachHeader(builder::addHeader);
        FormBody formBody = null;
        String method = request.getMethod();
        if (HttpMethod.requiresRequestBody(method)) {
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            request.forEachFormItem(formBodyBuilder::addEncoded);
            formBody = formBodyBuilder.build();
        }
        builder.method(request.getMethod(), formBody);
        return builder.build();
    }

    /**
     * Execute request.
     *
     * @param request request
     * @return oauth response
     * @throws IOException if IO exception occurs
     */
    private OAuth2HttpResponse execute(Request request) throws IOException {
        Response response = httpClient.newCall(request).execute();
        return new OAuth2HttpResponse() {
            @Override
            public int getStatus() {
                return response.code();
            }

            @Override
            public String getHeader(String name) {
                return response.header(name);
            }

            @Override
            public List<String> getHeaders(String name) {
                return response.headers(name);
            }

            @Override
            public Entity getEntity() {
                ResponseBody body = Objects.requireNonNull(response.body());
                return body::byteStream;
            }
        };
    }

}
