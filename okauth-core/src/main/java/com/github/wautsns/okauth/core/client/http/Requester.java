package com.github.wautsns.okauth.core.client.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 *
 * @author wautsns
 */
public abstract class Requester {

    public enum HttpMethod { GET, POST }

    private HttpMethod httpMethod;
    protected String url;
    private boolean containsQuery;

    protected Requester(HttpMethod httpMethod, String url) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.containsQuery = (url.lastIndexOf('?') == -1);
    }

    protected Requester(Requester requester) {
        this.httpMethod = requester.httpMethod;
        this.url = requester.url;
        this.containsQuery = requester.containsQuery;
    }

    public Requester acceptJson() {
        return addHeader("Accept", "application/json");
    }

    public abstract Requester addHeader(String name, String value);

    public Requester addQuery(String name, String value) {
        try {
            return addQueryWithUrlEncodedValue(name, URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("unreachable");
        }
    }

    public Requester addQueryWithUrlEncodedValue(String name, String value) {
        if (containsQuery) {
            url += '&';
        } else {
            url += '?';
            containsQuery = true;
        }
        url += name + '=' + value;
        return this;
    }

    public abstract Requester addFormItem(String name, String value);

    public abstract Requester multate();

    public Response exchangeForJson() throws IOException {
        return exchange(ResponseReader.JSON);
    }

    public Response exchange(ResponseReader responseReader) throws IOException {
        if (httpMethod == HttpMethod.GET) {
            return get(responseReader);
        } else if (httpMethod == HttpMethod.POST) {
            return post(responseReader);
        } else {
            return get(responseReader);
        }
    }

    protected abstract Response get(ResponseReader responseReader) throws IOException;

    protected abstract Response post(ResponseReader responseReader) throws IOException;

}
