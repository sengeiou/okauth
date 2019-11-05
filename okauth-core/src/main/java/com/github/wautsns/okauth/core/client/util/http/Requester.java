package com.github.wautsns.okauth.core.client.util.http;

import com.github.wautsns.okauth.core.client.util.http.Request.HttpMethod;

/**
 *
 * @author wautsns
 */
public abstract class Requester {

    protected abstract Request create(HttpMethod httpMethod, String url);

    public Request get(String url) {
        return create(HttpMethod.GET, url);
    }

    public Request post(String url) {
        return create(HttpMethod.POST, url);
    }

}
