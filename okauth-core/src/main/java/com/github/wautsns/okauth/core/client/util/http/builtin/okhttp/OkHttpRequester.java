package com.github.wautsns.okauth.core.client.util.http.builtin.okhttp;

import java.util.concurrent.TimeUnit;

import com.github.wautsns.okauth.core.client.util.http.Request;
import com.github.wautsns.okauth.core.client.util.http.Request.HttpMethod;
import com.github.wautsns.okauth.core.client.util.http.Requester;
import com.github.wautsns.okauth.core.client.util.http.properties.OkAuthHttpProperties;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

/**
 *
 * @author wautsns
 */
public class OkHttpRequester extends Requester {

    private final OkHttpClient okHttpClient;

    public OkHttpRequester(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public OkHttpRequester(OkAuthHttpProperties okAuthHttpConfiguration) {
        this.okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(
                okAuthHttpConfiguration.getConnectTimeoutMilliseconds(),
                TimeUnit.MILLISECONDS)
            .readTimeout(3, TimeUnit.SECONDS)
            .writeTimeout(3, TimeUnit.SECONDS)
            .connectionPool(new ConnectionPool(
                okAuthHttpConfiguration.getMaxIdleConnections(),
                okAuthHttpConfiguration.getKeepAlive(),
                okAuthHttpConfiguration.getKeepAliveTimeUnit()))
            .build();
    }

    @Override
    protected Request create(HttpMethod httpMethod, String url) {
        return new OkHttpRequest(httpMethod, url, okHttpClient);
    }

}
