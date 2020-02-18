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

import java.io.IOException;
import java.io.InputStream;

import java.util.Map;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Universal response.
 *
 * @since Feb 18, 2020
 * @author wautsns
 */
public class Response {

    /** response status code */
    private final int statusCode;
    /** response data map */
    private final Map<String, Object> data;

    /**
     * Construct a response.
     *
     * @param statusCode response status code
     * @param data response data map, require nonnull
     */
    public Response(int statusCode, Map<String, Object> data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    /** Get {@link #statusCode}. */
    public int getStatusCode() {
        return statusCode;
    }

    /** Get {@link #data}. */
    public Map<String, Object> getData() {
        return data;
    }

    // --------------- map data input stream reader ------------------------------

    public static final MapDateInputStreamReader INPUT_STREAM_READER_JSON =
        new MapDateInputStreamReader() {

            private final ObjectMapper objectMapper = new ObjectMapper();
            private final JavaType mapType =
                objectMapper.getTypeFactory().constructMapType(Map.class, String.class,
                    Object.class);

            @Override
            public Map<String, Object> read(InputStream inputStream) throws IOException {
                return objectMapper.readValue(inputStream, mapType);
            }

        };

    public interface MapDateInputStreamReader {

        Map<String, Object> read(InputStream inputStream) throws IOException;

    }

}
