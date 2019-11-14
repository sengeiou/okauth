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
package com.github.wautsns.okauth.core.client.util.http.builtin.okhttp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.github.wautsns.okauth.core.client.util.http.Request;
import com.github.wautsns.okauth.core.client.util.http.Response;
import com.github.wautsns.okauth.core.client.util.http.ResponseInputStreamReader;
import com.github.wautsns.okauth.core.exception.OkAuthIOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;

/**
 * Okhttp3 request.
 *
 * <p>Based on okhttp3.
 *
 * @author wautsns
 */
public class OkHttpRequest extends Request {

    /** okhttp3 client */
    private final OkHttpClient okHttpClient;
    /** okhttp3 request builder */
    private final Builder builder;
    /** form data map */
    private Map<String, String> form;

    /**
     * Construct an okhttp3 request.
     *
     * @param okHttpClient okhttp3 client, require nonnull
     * @param method request method, require nonnull
     * @param url request url, require nonnull
     */
    public OkHttpRequest(OkHttpClient okHttpClient, Method method, String url) {
        super(method, url);
        this.okHttpClient = okHttpClient;
        this.builder = new Builder();
    }

    /**
     * Construct an okhttp3 request.
     *
     * <p>Copy the ok http client, builder and form according to the given request.
     *
     * @param requester template, require nonnull
     */
    private OkHttpRequest(OkHttpRequest requester) {
        super(requester);
        this.okHttpClient = requester.okHttpClient;
        this.builder = requester.builder.url(requester.getUrl()).build().newBuilder();
        if (requester.form != null) { this.form = new HashMap<>(requester.form); }
    }

    @Override
    public Request addHeader(String name, String value) {
        if (value == null) { return this; }
        builder.addHeader(name, value);
        return this;
    }

    @Override
    public Request addFormItem(String name, String value) {
        if (value == null) { return this; }
        if (form == null) { form = new HashMap<>(8); }
        form.put(name, value);
        return this;
    }

    @Override
    public Request mutate() {
        return new OkHttpRequest(this);
    }

    @Override
    protected Response get(ResponseInputStreamReader reader) throws OkAuthIOException {
        builder.get().url(getUrl());
        return execute(reader);
    }

    @Override
    protected Response post(ResponseInputStreamReader reader) throws OkAuthIOException {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (form != null) { form.forEach(formBodyBuilder::add); }
        builder.post(formBodyBuilder.build()).url(getUrl());
        return execute(reader);
    }

    /**
     * Do execute request.
     *
     * @param reader response input stream reader, require nonnull
     * @return response
     * @throws OkAuthIOException if an IO exception occurs
     */
    private Response execute(ResponseInputStreamReader reader) throws OkAuthIOException {
        try {
            okhttp3.Response response = okHttpClient.newCall(builder.build()).execute();
            return new Response(
                response.code(),
                reader.read(response.body().byteStream()));
        } catch (IOException ioException) {
            throw new OkAuthIOException(ioException);
        }
    }

}
