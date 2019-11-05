package com.github.wautsns.okauth.core.client.util.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 *
 * @author wautsns
 */
public abstract class Request {

    public enum HttpMethod { GET, POST }

    private HttpMethod httpMethod;
    private String url;
    private boolean containsQuery;

    public Request(HttpMethod httpMethod, String url) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.containsQuery = (url.lastIndexOf('?') >= 0);
    }

    protected Request(Request requester) {
        this.httpMethod = requester.httpMethod;
        this.url = requester.url;
        this.containsQuery = requester.containsQuery;
    }

    public Request acceptJson() {
        return addHeader("Accept", "application/json");
    }

    public abstract Request addHeader(String name, String value);

    public String getUrl() {
        return url;
    }

    public Request addQuery(String name, String value) {
        try {
            return addUrlEncodedQuery(name, URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("unreachable");
        }
    }

    public Request addUrlEncodedQuery(String name, String value) {
        if (containsQuery) {
            url += '&';
        } else {
            url += '?';
            containsQuery = true;
        }
        url += name + '=' + value;
        return this;
    }

    public abstract Request addFormItem(String name, String value);

    public Request addFormItems(Map<String, String> formItems) {
        formItems.forEach(this::addFormItem);
        return this;
    }

    public abstract Request mutate();

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
