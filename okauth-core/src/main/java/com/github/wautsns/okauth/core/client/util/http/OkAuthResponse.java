/**
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
package com.github.wautsns.okauth.core.client.util.http;

import java.io.IOException;
import java.io.InputStream;

import java.util.Map;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * OkAuth response.
 *
 * @since Feb 27, 2020
 * @author wautsns
 */
public class OkAuthResponse {

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
    public OkAuthResponse(int statusCode, Map<String, Object> data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    /**
     * Get status code.
     *
     * @return status code
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Get data.
     *
     * @return data
     */
    public Map<String, Object> getData() {
        return data;
    }

    // --------------- input stream reader ------------------------------

    /** reader for json input stream */
    public static final MapDateInputStreamReader INPUT_STREAM_READER_JSON =
        new MapDateInputStreamReader() {

            /** json util */
            private final ObjectMapper json = new ObjectMapper();
            /** javaType of Map&lt;String, Object&gt; */
            private final JavaType mapType = json.getTypeFactory()
                .constructMapType(Map.class, String.class, Object.class);

            @Override
            public Map<String, Object> read(InputStream inputStream) throws IOException {
                return json.readValue(inputStream, mapType);
            }

        };

    /** Map data input stream reader. */
    public interface MapDateInputStreamReader {

        /**
         * Read input stream as map.
         *
         * @param inputStream input stream, require nonnull
         * @return data map
         * @throws IOException if an IO exception occurs
         */
        Map<String, Object> read(InputStream inputStream) throws IOException;

    }

}
