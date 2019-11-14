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
    private final OkHttpClient okhttpClient;

    /**
     * Construct an okhttp3 request.
     *
     * @param okhttpClient okhttp3 client, require nonnull
     * @param method request method, require nonnull
     * @param url request url, require nonnull
     */
    public OkHttpRequest(OkHttpClient okhttpClient, Method method, String url) {
        super(method, url);
        this.okhttpClient = okhttpClient;
    }

    /**
     * Construct an okhttp3 request.
     *
     * <p>Copy the okhttp client according to the given request.
     *
     * @param requester template, require nonnull
     */
    private OkHttpRequest(OkHttpRequest requester) {
        super(requester);
        this.okhttpClient = requester.okhttpClient;
    }

    @Override
    public Request mutate() {
        return new OkHttpRequest(this);
    }

    @Override
    protected Response doGet(ResponseInputStreamReader reader) throws OkAuthIOException {
        Builder builder = new Builder().get().url(getUrl());
        forEachHeader(builder::addHeader);
        return execute(builder.build(), reader);
    }

    @Override
    protected Response doPost(ResponseInputStreamReader reader) throws OkAuthIOException {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        forEachFormItem(formBodyBuilder::addEncoded);
        Builder builder = new Builder().post(formBodyBuilder.build()).url(getUrl());
        forEachHeader(builder::addHeader);
        return execute(builder.build(), reader);
    }

    /**
     * Execute request.
     *
     * @param request okhttp3 request
     * @param reader response input stream reader, require nonnull
     * @return response
     * @throws OkAuthIOException if an IO exception occurs
     */
    private Response execute(okhttp3.Request request, ResponseInputStreamReader reader)
            throws OkAuthIOException {
        try {
            okhttp3.Response response = okhttpClient.newCall(request).execute();
            return new Response(
                response.code(),
                reader.read(response.body().byteStream()));
        } catch (IOException ioException) {
            throw new OkAuthIOException(ioException);
        }
    }

}
