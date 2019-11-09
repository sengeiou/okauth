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

/**
 * OAuth data.
 *
 * @author wautsns
 */
class OAuthData {

    /** data map */
    private final Map<String, Object> data;

    /**
     * Construct an oauth data.
     *
     * @param data data map, require nonnull
     */
    protected OAuthData(Map<String, Object> data) {
        this.data = data;
    }

    /**
     * Get string value.
     *
     * @param name value name, require nonnull
     * @return value assosiated with the param `name`, or {@code null} if no value assosiated with
     *         the param
     */
    public String getString(String name) {
        Object value = data.get(name);
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
        Object value = data.get(name);
        return (value instanceof Map) ? (Map<String, Object>) value : null;
    }

    /** Get {@link #data}. */
    public Map<String, Object> getData() {
        return data;
    }

}
