package com.github.wautsns.okauth.core.client.util.http.builtin.okhttp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.github.wautsns.okauth.core.client.util.http.Request;
import com.github.wautsns.okauth.core.client.util.http.Response;
import com.github.wautsns.okauth.core.client.util.http.ResponseReader;
import com.github.wautsns.okauth.core.exception.OkAuthIOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;

/**
 *
 * @author wautsns
 */
public class OkHttpRequest extends Request {

    private final OkHttpClient okHttpClient;
    private final Builder builder;
    private Map<String, String> formMap;

    public OkHttpRequest(HttpMethod httpMethod, String url, OkHttpClient okHttpClient) {
        super(httpMethod, url);
        this.okHttpClient = okHttpClient;
        this.builder = new Builder();
    }

    private OkHttpRequest(OkHttpRequest requester) {
        super(requester);
        this.okHttpClient = requester.okHttpClient;
        this.builder = new Builder(requester.builder.url(requester.getUrl()).build());
        if (requester.formMap != null) { this.formMap = new HashMap<>(requester.formMap); }
    }

    @Override
    public Request addHeader(String name, String value) {
        builder.addHeader(name, value);
        return this;
    }

    @Override
    public Request addFormItem(String name, String value) {
        if (formMap == null) { formMap = new HashMap<>(8); }
        formMap.put(name, value);
        return this;
    }

    @Override
    public Request mutate() {
        return new OkHttpRequest(this);
    }

    @Override
    protected Response get(ResponseReader responseReader) throws OkAuthIOException {
        builder.get().url(getUrl());
        return execute(responseReader);
    }

    @Override
    protected Response post(ResponseReader responseReader) throws OkAuthIOException {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        formMap.forEach(formBodyBuilder::add);
        builder.post(formBodyBuilder.build()).url(getUrl());
        return execute(responseReader);
    }

    private Response execute(ResponseReader responseReader) throws OkAuthIOException {
        try {
            okhttp3.Response response = okHttpClient.newCall(builder.build()).execute();
            return new Response(
                response.code(),
                responseReader.read(response.body().byteStream()));
        } catch (IOException ioException) {
            throw new OkAuthIOException(ioException);
        }
    }

}
