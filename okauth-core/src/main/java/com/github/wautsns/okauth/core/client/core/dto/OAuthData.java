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
package com.github.wautsns.okauth.core.client.core.dto;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * OAuth data.
 *
 * @since Feb 18, 2020
 * @author wautsns
 */
@JsonNaming(SnakeCaseStrategy.class)
class OAuthData {

    private static final ObjectMapper OM = new ObjectMapper();

    /** original data map */
    private final Map<String, Object> originalDataMap;

    /**
     * Construct an oauth data.
     *
     * @param originalDataMap original data map, require nonnull
     */
    protected OAuthData(Map<String, Object> originalDataMap) {
        this.originalDataMap = originalDataMap;
    }

    /**
     * Get value.
     *
     * @param <T> type of the value
     * @param name value name, require nonnull
     * @return value assosiated with the name, or {@code null} if no value assosiated with the name
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String name) {
        return (T) originalDataMap.get(name);
    }

    /**
     * Get string value.
     *
     * <ul>
     * <li>If the value is {@code null}, {@code null} will be returned.</li>
     * <li>If the value is instance of {@code String} or {@code Number} or {@code Boolean},
     * {@code value.toString()} will be returned.</li>
     * <li>Otherwise, the json string of the value will be returned.</li>
     * </ul>
     *
     * @param name value name, require nonnull
     * @return value string assosiated with the name, or {@code null} if no value assosiated with
     *         the name
     */
    public String getString(String name) {
        Object value = originalDataMap.get(name);
        if (value instanceof String) { return (String) value; }
        if (value == null) { return null; }
        if (value instanceof Number || value instanceof Boolean) { return value.toString(); }
        try {
            return OM.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Get map value.
     *
     * @param name value name, require nonnull
     * @return value assosiated with the name, or {@code null} if no value assosiated with the name
     *         or the value is not instance of {@code Map}
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getMap(String name) {
        Object value = originalDataMap.get(name);
        return (value instanceof Map) ? (Map<String, Object>) value : null;
    }

    /** Get {@link #originalDataMap}. */
    public Map<String, Object> getOriginalDataMap() {
        return originalDataMap;
    }

}
