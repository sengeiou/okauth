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

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * OAuth data.
 *
 * @author wautsns
 */
@JsonNaming(SnakeCaseStrategy.class)
class OAuthData {

    /** data map */
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
     * @return value assosiated with the param `name`, or {@code null} if no value assosiated with
     *         the param
     * @throws ClassCastException if the actual value is not instance of the type
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String name) {
        return (T) originalDataMap.get(name);
    }

    /**
     * Get string value.
     *
     * @param name value name, require nonnull
     * @return value assosiated with the param `name`, or {@code null} if no value assosiated with
     *         the param
     */
    public String getString(String name) {
        Object value = originalDataMap.get(name);
        return (value == null) ? null : value.toString();
    }

    /**
     * Get map value.
     *
     * @param name value name, require nonnull
     * @return value assosiated with the param `name`, or {@code null} if no value assosiated with
     *         the param or the value is not instance of {@code Map}
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
