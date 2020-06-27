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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.OAuth2UrlEncodedEntries;
import lombok.experimental.UtilityClass;

import java.nio.charset.StandardCharsets;

/**
 * Read utils.
 *
 * @author wautsns
 * @since Jun 24, 2020
 */
@UtilityClass
public class WriteUtils {

    /** Jackson ObjectMapper. */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

    /**
     * Write urlEncodedEntries as query like text(eg. a=3&b=qwe).
     *
     * @param urlEncodedEntries url encoded entries
     * @return query like text
     */
    public static String writeUrlEncodedEntriesAsQueryLikeText(OAuth2UrlEncodedEntries urlEncodedEntries) {
        StringBuilder queryLikeText = new StringBuilder();
        urlEncodedEntries.forEach((name, value) -> queryLikeText.append(name).append('=').append(value).append('&'));
        queryLikeText.deleteCharAt(queryLikeText.length() - 1);
        return queryLikeText.toString();
    }

    /**
     * Write urlEncodedEntries as query like text(eg. a=3&b=qwe) bytes.
     *
     * @param urlEncodedEntries url encoded entries
     * @return query like text bytes
     */
    public static byte[] writeUrlEncodedEntriesAsQueryLikeTextBytes(OAuth2UrlEncodedEntries urlEncodedEntries) {
        return writeUrlEncodedEntriesAsQueryLikeText(urlEncodedEntries).getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Write object as json string.
     *
     * @param object object
     * @return json string
     */
    public static String writeObjectAsJsonString(Object object) {
        return new String(writeObjectAsJsonBytes(object), StandardCharsets.UTF_8);
    }

    /**
     * Write object as json bytes.
     *
     * @param object object
     * @return json bytes
     */
    public static byte[] writeObjectAsJsonBytes(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

}
