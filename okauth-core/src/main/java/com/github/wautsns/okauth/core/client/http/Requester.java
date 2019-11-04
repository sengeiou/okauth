package com.github.wautsns.okauth.core.client.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 *
 * @author wautsns
 */
public abstract class Requester {

    public enum HttpMethod { GET, POST }

    private HttpMethod httpMethod;
    private String url;
    private boolean containsQuery;

    public Requester(HttpMethod httpMethod, String url) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.containsQuery = (url.lastIndexOf('?') >= 0);
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

    public String getUrl() {
        return url;
    }

    public Requester addQuery(String name, String value) {
        try {
            return addUrlEncodedQuery(name, URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("unreachable");
        }
    }

    public Requester addUrlEncodedQuery(String name, String value) {
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

    public Requester addFormItems(Map<String, String> formItems) {
        formItems.forEach(this::addFormItem);
        return this;
    }

    public abstract Requester mutate();

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
