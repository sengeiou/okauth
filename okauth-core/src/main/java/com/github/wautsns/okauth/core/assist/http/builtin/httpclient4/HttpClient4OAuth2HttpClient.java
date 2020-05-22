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
package com.github.wautsns.okauth.core.assist.http.builtin.httpclient4;

import com.github.wautsns.okauth.core.assist.http.kernel.OAuth2HttpClient;
import com.github.wautsns.okauth.core.assist.http.kernel.model.OAuth2HttpRequest;
import com.github.wautsns.okauth.core.assist.http.kernel.model.OAuth2HttpResponse;
import com.github.wautsns.okauth.core.assist.http.kernel.properties.OAuth2HttpClientProperties;
import com.github.wautsns.okauth.core.exception.OAuth2IOException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.IOException;
import java.time.Duration;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * HttpClient4 oauth2 http client.
 *
 * @author wautsns
 * @since May 21, 2020
 */
@Slf4j
@Getter
public class HttpClient4OAuth2HttpClient implements OAuth2HttpClient {

    /** Default httpClient4 oauth2 http client. */
    public static final HttpClient4OAuth2HttpClient DEFAULT
            = new HttpClient4OAuth2HttpClient(OAuth2HttpClientProperties.initDefault());

    /** Original http client. */
    private final HttpClient origin;
    /** Connection manager. */
    private final PoolingHttpClientConnectionManager connectionManager;

    /**
     * Construct httpClient4 oauth2 http client.
     *
     * @param props oauth2 http client properties
     */
    public HttpClient4OAuth2HttpClient(OAuth2HttpClientProperties props) {
        HttpClientBuilder builder = HttpClientBuilder.create();
        // #################### request config ##############################################
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout((int) props.getConnectTimeout().toMillis())
                .setSocketTimeout((int) props.getReadTimeout().toMillis())
                .build();
        builder.setDefaultRequestConfig(requestConfig);
        // #################### connect manager ##############################################
        this.connectionManager = new PoolingHttpClientConnectionManager();
        this.connectionManager.setMaxTotal(props.getMaxConcurrentRequests());
        this.connectionManager.setDefaultMaxPerRoute(props.getMaxConcurrentRequests());
        builder.setConnectionManager(this.connectionManager);
        // #################### max idle time ################################################
        Duration maxIdleTime = props.getMaxIdleTime();
        if (maxIdleTime != null) {
            long maxIdleTimeMillis = maxIdleTime.toMillis();
            builder.evictIdleConnections(maxIdleTimeMillis, TimeUnit.MILLISECONDS);
        }
        // #################### keep alive ################################################
        ConnectionKeepAliveStrategy keepAliveStrategy = DefaultConnectionKeepAliveStrategy.INSTANCE;
        Duration keepAliveTimeout = props.getKeepAliveTimeout();
        if (keepAliveTimeout != null) {
            long keepAliveTimeoutMillis = keepAliveTimeout.toMillis();
            keepAliveStrategy = (resp, ctx) -> keepAliveTimeoutMillis;
        }
        builder.setKeepAliveStrategy(keepAliveStrategy);
        // #################### retry handler ###############################################
        Integer retryTimes = props.getRetryTimes();
        if (retryTimes != null) {
            if (retryTimes == 3) {
                builder.setRetryHandler(OAuth2HttpRequestRetryHandler.THREE_TIMES);
            } else {
                builder.setRetryHandler(new OAuth2HttpRequestRetryHandler(retryTimes));
            }
        }
        // #################### http client #################################################
        this.origin = builder.build();
    }

    @Override
    public OAuth2HttpResponse execute(OAuth2HttpRequest request) throws OAuth2IOException {
        try {
            return executeOriginalRequest(initOriginalRequest(request));
        } catch (IOException e) {
            throw new OAuth2IOException(e);
        }
    }

    // #################### internal ####################################################

    /** Supported http request base initializers. */
    private static final EnumMap<OAuth2HttpRequest.Method, Function<String, HttpRequestBase>> HTTP_REQUEST_BASE_INITIALIZERS;

    static {
        HTTP_REQUEST_BASE_INITIALIZERS = new EnumMap<>(OAuth2HttpRequest.Method.class);
        HTTP_REQUEST_BASE_INITIALIZERS.put(OAuth2HttpRequest.Method.GET, HttpGet::new);
        HTTP_REQUEST_BASE_INITIALIZERS.put(OAuth2HttpRequest.Method.POST, HttpPost::new);
        HTTP_REQUEST_BASE_INITIALIZERS.put(OAuth2HttpRequest.Method.PUT, HttpPut::new);
        HTTP_REQUEST_BASE_INITIALIZERS.put(OAuth2HttpRequest.Method.PATCH, HttpPatch::new);
        HTTP_REQUEST_BASE_INITIALIZERS.put(OAuth2HttpRequest.Method.DELETE, HttpDelete::new);
        HTTP_REQUEST_BASE_INITIALIZERS.put(OAuth2HttpRequest.Method.HEAD, HttpHead::new);
        HTTP_REQUEST_BASE_INITIALIZERS.put(OAuth2HttpRequest.Method.OPTIONS, HttpOptions::new);
        HTTP_REQUEST_BASE_INITIALIZERS.put(OAuth2HttpRequest.Method.TRACE, HttpTrace::new);
    }

    /**
     * Initialize original http request.
     *
     * @param request oauth2 http request
     * @return original http request
     */
    private HttpRequestBase initOriginalRequest(OAuth2HttpRequest request) {
        Function<String, HttpRequestBase> initializer = HTTP_REQUEST_BASE_INITIALIZERS.get(request.getMethod());
        HttpRequestBase httpRequestBase = initializer.apply(request.getUrl().toString());
        request.forEachHeader(httpRequestBase::addHeader);
        if (httpRequestBase instanceof HttpEntityEnclosingRequestBase) {
            HttpEntityEnclosingRequestBase tmp = (HttpEntityEnclosingRequestBase) httpRequestBase;
            List<String> nameValuePairs = new LinkedList<>();
            request.forEachFormItem((name, value) -> nameValuePairs.add(name + "=" + value));
            String content = String.join("&", nameValuePairs);
            tmp.setEntity(new StringEntity(content, ContentType.APPLICATION_FORM_URLENCODED));
        }
        return httpRequestBase;
    }

    /**
     * Execute original request.
     *
     * @param request original request
     * @return oauth2 http response
     * @throws IOException if IO exception occurs
     */
    private OAuth2HttpResponse executeOriginalRequest(HttpRequestBase request) throws IOException {
        return new HttpClient4OAuth2HttpResponse(origin.execute(request));
    }

}
