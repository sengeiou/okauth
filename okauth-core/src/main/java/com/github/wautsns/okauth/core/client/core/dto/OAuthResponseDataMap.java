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
package com.github.wautsns.okauth.core.client.core.dto;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * OAuth response data map.
 *
 * @since Feb 27, 2020
 * @author wautsns
 */
@JsonNaming(SnakeCaseStrategy.class)
class OAuthResponseDataMap {

    /** json util */
    private static final ObjectMapper JSON = new ObjectMapper();

    /** original data map */
    private final Map<String, Object> originalDataMap;

    /**
     * Construct an oauth response data map.
     *
     * @param originalDataMap original data map, require nonnull
     */
    protected OAuthResponseDataMap(Map<String, Object> originalDataMap) {
        this.originalDataMap = originalDataMap;
    }

    /**
     * Get value of the specific name.
     *
     * @param name the specific name, require nonnull
     * @return value assosiated with the name, or {@code null} if no value assosiated with the name
     */
    public Object get(String name) {
        return originalDataMap.get(name);
    }

    /**
     * Get string value of the specific name.
     *
     * <ul>
     * <li>If the value is {@code null}, {@code null} will be returned.</li>
     * <li>If the value is instance of String, Number or Boolean, {@code value.toString()} will be
     * returned.</li>
     * <li>Otherwise, the json string of the value will be returned.</li>
     * </ul>
     *
     * @param name the specific name, require nonnull
     * @return value string assosiated with the name, or {@code null} if no value assosiated with
     *         the name
     */
    public String getString(String name) {
        Object value = originalDataMap.get(name);
        if (value instanceof String) { return (String) value; }
        if (value == null) { return null; }
        if (value instanceof Number
            || value instanceof Boolean) { return value.toString(); }
        try {
            return JSON.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Get map value.
     *
     * @param name the specific name, require nonnull
     * @return value assosiated with the name, or {@code null} if no value assosiated with the name
     *         or the value is not instance of Map
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getMap(String name) {
        Object value = originalDataMap.get(name);
        return (value instanceof Map) ? (Map<String, Object>) value : null;
    }

    /**
     * Get the original data map.
     *
     * @return the original data map
     */
    public Map<String, Object> getOriginalDataMap() {
        return originalDataMap;
    }

}
