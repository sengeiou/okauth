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
package com.github.wautsns.okauth.core.assist.http.kernel.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import lombok.experimental.UtilityClass;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Read utils.
 *
 * @author wautsns
 * @since Mar 03, 2020
 */
@UtilityClass
public class ReadUtils {

    /** Jackson ObjectMapper. */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);
    /** Java type: {@code DataMap}. */
    private static final JavaType JAVA_TYPE_DATA_MAP = OBJECT_MAPPER.getTypeFactory().constructType(DataMap.class);

    /**
     * Read input stream as {@code String} value.
     *
     * <p><strong>The method will not close the input stream.</strong>
     *
     * @param inputStream input stream
     * @return {@code String} value
     * @throws IOException if IO exception occurs
     */
    public static String readInputStreamAsString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString();
    }

    /**
     * Read query like input stream(eg. a=3&b=4) as {@code DataMap} value.
     *
     * <p><strong>The method will not close the input stream.</strong>
     *
     * @param inputStream query like input stream
     * @return {@code DataMap} value
     */
    public static DataMap readQueryLikeTextAsDataMap(InputStream inputStream) throws IOException {
        return readQueryLikeTextAsDataMap(readInputStreamAsString(inputStream));
    }

    /**
     * Read query like text(eg. a=3&b=4) as {@code DataMap} value.
     *
     * @param string query like string
     * @return {@code DataMap} value
     */
    public static DataMap readQueryLikeTextAsDataMap(String string) {
        DataMap dataMap = new DataMap();
        for (String item : string.split("&")) {
            String[] nameAndValue = item.split("=", 2);
            dataMap.put(nameAndValue[0], nameAndValue[1]);
        }
        return dataMap;
    }

    /**
     * Read json string as {@code DataMap} value.
     *
     * @param string json string
     * @return {@code DataMap} value
     * @throws IOException if IO exception occurs
     */
    public static DataMap readJsonAsDataMap(String string) throws IOException {
        return OBJECT_MAPPER.readValue(string, JAVA_TYPE_DATA_MAP);
    }

    /**
     * Read json input stream as {@code DataMap} value.
     *
     * <p><strong>The method will not close the input stream.</strong>
     *
     * @param inputStream json input stream
     * @return {@code DataMap} value
     * @throws IOException if IO exception occurs
     */
    public static DataMap readJsonAsDataMap(InputStream inputStream) throws IOException {
        return OBJECT_MAPPER.readValue(inputStream, JAVA_TYPE_DATA_MAP);
    }

}
