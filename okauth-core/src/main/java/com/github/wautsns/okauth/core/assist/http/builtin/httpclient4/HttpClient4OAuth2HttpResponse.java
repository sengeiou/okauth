/*
 * Copyright 2020 the original author or authors.
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
package com.github.wautsns.okauth.core.assist.http.builtin.httpclient4;

import com.github.wautsns.okauth.core.assist.http.kernel.model.OAuth2HttpResponse;
import com.github.wautsns.okauth.core.exception.OAuth2IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpResponse;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * HttpClient4 oauth2 http response.
 *
 * @author wautsns
 * @since May 22, 2020
 */
@Slf4j
@RequiredArgsConstructor
public class HttpClient4OAuth2HttpResponse extends OAuth2HttpResponse {

    /** Original http response. */
    private final HttpResponse origin;

    @Override
    public int getStatus() {
        return origin.getStatusLine().getStatusCode();
    }

    @Override
    public String getHeader(String name) {
        return origin.getFirstHeader(name).getValue();
    }

    @Override
    public List<String> getHeaders(String name) {
        return Arrays.stream(origin.getHeaders(name))
                .map(Header::getValue)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Entity getEntity() {
        return new Entity() {
            @Override
            public InputStream getInputStream() throws IOException {
                return origin.getEntity().getContent();
            }
        };
    }

    @Override
    public void close() throws OAuth2IOException {
        if (origin instanceof Closeable) {
            try {
                ((Closeable) origin).close();
            } catch (IOException e) {
                throw new OAuth2IOException(e);
            }
        }
    }

}
