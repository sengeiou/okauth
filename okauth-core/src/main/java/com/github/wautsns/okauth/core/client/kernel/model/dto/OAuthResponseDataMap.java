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
package com.github.wautsns.okauth.core.client.kernel.model.dto;

import com.github.wautsns.okauth.core.client.kernel.http.model.dto.OAuthResponse;
import com.github.wautsns.okauth.core.util.Reader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;

/**
 * OAuth response data map.
 *
 * @author wautsns
 * @since Feb 28, 2020
 */
@Data
@Accessors(chain = true)
public class OAuthResponseDataMap implements Serializable {

    private static final long serialVersionUID = -5429452841745088452L;

    /** original data map */
    private final @NonNull Map<String, Serializable> originalDataMap;

    /**
     * Construct oauth response data map.
     *
     * @param response okauth response, require nonnull
     */
    public OAuthResponseDataMap(OAuthResponse response) {
        this(response.getData());
    }

    /**
     * Get value of the specific name as string.
     *
     * <ul>
     * <li>If the value is {@code null}, {@code null} will be returned.</li>
     * <li>If the value is instance of String, the value will be returned.</li>
     * <li>If the value is instance of Number or Boolean, {@code value.toString()} will be returned.</li>
     * <li>Otherwise, the json string of the value will be returned.</li>
     * </ul>
     *
     * @param name the specific name, require nonnull
     * @return value string associated with the name, or {@code null} if no value associated with the name
     */
    public String getAsString(String name) {
        Serializable value = originalDataMap.get(name);
        if (value instanceof String) { return (String) value; }
        if (value == null) { return null; }
        if (value instanceof Number
            || value instanceof Boolean) { return value.toString(); }
        try {
            return Reader.readAsJson(value);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Get map value of the specific name.
     *
     * @param name the specific name, require nonnull
     * @return value associated with the name, or {@code null} if no value associated with the name or the value is not
     * instance of Map
     */
    @SuppressWarnings("unchecked")
    public Map<String, Serializable> getMap(String name) {
        Object value = originalDataMap.get(name);
        return (value instanceof Map) ? (Map<String, Serializable>) value : null;
    }

    /**
     * Get value of the specific name.
     *
     * @param name the specific name, require nonnull
     * @return value associated with the name, or {@code null} if no value associated with the name
     */
    public Object get(String name) {
        return originalDataMap.get(name);
    }

}
