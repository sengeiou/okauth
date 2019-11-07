/**
 * Copyright 2019 the original author or authors.
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
package com.github.wautsns.okauth.core.client.util.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import com.github.wautsns.okauth.core.exception.OkAuthIOException;

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

    public Response exchangeForJson() throws OkAuthIOException {
        return exchange(ResponseReader.JSON);
    }

    public Response exchange(ResponseReader responseReader) throws OkAuthIOException {
        if (httpMethod == HttpMethod.GET) {
            return get(responseReader);
        } else if (httpMethod == HttpMethod.POST) {
            return post(responseReader);
        } else {
            return get(responseReader);
        }
    }

    protected abstract Response get(ResponseReader responseReader) throws OkAuthIOException;

    protected abstract Response post(ResponseReader responseReader) throws OkAuthIOException;

}
