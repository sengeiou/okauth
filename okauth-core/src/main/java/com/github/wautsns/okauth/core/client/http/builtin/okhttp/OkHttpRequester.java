package com.github.wautsns.okauth.core.client.http.builtin.okhttp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.github.wautsns.okauth.core.client.http.Requester;
import com.github.wautsns.okauth.core.client.http.Response;
import com.github.wautsns.okauth.core.client.http.ResponseReader;

import okhttp3.FormBody;
import okhttp3.FormBody.Builder;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 *
 * @author wautsns
 */
public class OkHttpRequester extends Requester {

    private final OkHttpClient okHttpClient;
    private final Request.Builder builder;
    private Map<String, String> formMap;

    public OkHttpRequester(HttpMethod httpMethod, String url, OkHttpClient okHttpClient) {
        super(httpMethod, url);
        this.okHttpClient = okHttpClient;
        this.builder = new Request.Builder();
    }

    private OkHttpRequester(OkHttpRequester requester) {
        super(requester);
        this.okHttpClient = requester.okHttpClient;
        this.builder = new Request.Builder(requester.builder.build());
        this.formMap = requester.formMap;
    }

    @Override
    public Requester addHeader(String name, String value) {
        builder.addHeader(name, value);
        return this;
    }

    @Override
    public Requester addFormItem(String name, String value) {
        if (formMap == null) { formMap = new HashMap<>(8); }
        formMap.put(name, value);
        return this;
    }

    @Override
    public Requester multate() {
        return new OkHttpRequester(this);
    }

    @Override
    protected Response get(ResponseReader responseReader) throws IOException {
        builder.get().url(url);
        return execute(responseReader);
    }

    @Override
    protected Response post(ResponseReader responseReader) throws IOException {
        Builder formBodyBuilder = new FormBody.Builder();
        formMap.forEach(formBodyBuilder::add);
        builder.post(formBodyBuilder.build()).url(url);
        return execute(responseReader);
    }

    private Response execute(ResponseReader responseReader) throws IOException {
        Request request = builder.build();
        okhttp3.Response response = okHttpClient.newCall(request).execute();
        return new Response(
            response.code(),
            responseReader.read(response.body().byteStream()));
    }

}
