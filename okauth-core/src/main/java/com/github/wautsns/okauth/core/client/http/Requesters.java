package com.github.wautsns.okauth.core.client.http;

import com.github.wautsns.okauth.core.client.http.Requester.HttpMethod;

/**
 *
 * @author wautsns
 */
public interface Requesters {

    Requester construct(HttpMethod httpMethod, String url);

    default Requester get(String url) {
        return construct(HttpMethod.GET, url);
    }

    default Requester post(String url) {
        return construct(HttpMethod.POST, url);
    }

}
