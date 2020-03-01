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
package com.github.wautsns.okauth.core.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.Map;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Reader.
 *
 * @since Feb 28, 2020
 * @author wautsns
 */
public class Reader {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final JavaType MAP = OBJECT_MAPPER.getTypeFactory()
        .constructMapType(Map.class, String.class, Serializable.class);

    public static String readAsString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString();
    }

    /**
     * Read json input stream as map.
     *
     * @param inputStream json input stream, require nonnull
     * @return map
     * @throws IOException if IO exception occurs
     */
    public static Map<String, Serializable> readeJsonAsMap(InputStream inputStream)
            throws IOException {
        return OBJECT_MAPPER.readValue(inputStream, MAP);
    }

    /**
     * Read json string as map.
     *
     * @param string json string, require nonnull
     * @return map
     * @throws IOException if IO exception occurs
     */
    public static Map<String, Serializable> readeJsonAsMap(String string) throws IOException {
        return OBJECT_MAPPER.readValue(string, MAP);
    }

    // -------------------- ignored ---------------------------------

    private Reader() {}

}
