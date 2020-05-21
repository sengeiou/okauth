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
package com.github.wautsns.okauth.core.assist.http.kernel.model.basic;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Data map.
 *
 * @author wautsns
 * @since May 16, 2020
 */
public class DataMap extends LinkedHashMap<String, Serializable> {

    private static final long serialVersionUID = 8053655901684236188L;

    // #################### getAsXxx ####################################################

    /**
     * Get as {@code String} value.
     *
     * <pre>
     * example:
     * null => null
     * others => value.toString()
     * </pre>
     *
     * @param name name
     * @return {@code String} value, or {@code null} if the origin contains no mapping for the name.
     */
    public String getAsString(String name) {
        Serializable value = get(name);
        if (value == null) {
            return null;
        } else {
            return value.toString();
        }
    }

    /**
     * Get as {@code Long} value.
     *
     * <pre>
     * example:
     * null => null
     * Long => value
     * String => Long.parseLong(value)
     * Number => value.longValue()
     * others => UnsupportedOperationException
     * </pre>
     *
     * @param name name
     * @return {@code Long} value, or {@code null} if the origin contains no mapping for the name.
     */
    public Long getAsLong(String name) {
        Serializable value = get(name);
        if (value == null) {
            return null;
        } else if (value instanceof Long) {
            return (Long) value;
        } else if (value instanceof String) {
            return Long.parseLong((String) value);
        } else if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        throw new UnsupportedOperationException(String.format("Cannot convert [%s] to Long.", value));
    }

    /**
     * Get as {@code Integer} value.
     *
     * <pre>
     * example:
     * null => null
     * Integer => value
     * String => Integer.parseInt(value)
     * Number => value.intValue()
     * others => UnsupportedOperationException
     * </pre>
     *
     * @param name name
     * @return {@code Integer} value, or {@code null} if the origin contains no mapping for the name.
     */
    public Integer getAsInteger(String name) {
        Serializable value = get(name);
        if (value == null) {
            return null;
        } else if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof String) {
            return Integer.parseInt((String) value);
        } else if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        throw new UnsupportedOperationException(String.format("Cannot convert [%s] to Integer.", value));
    }

    /**
     * Get as {@code Boolean} value.
     *
     * <pre>
     * example:
     * null => null
     * Boolean => value
     * String => Boolean.valueOf(value)
     * Number => (value == 0) ? True : False
     * others => UnsupportedOperationException
     * </pre>
     *
     * @param name name
     * @return {@code Boolean} value, or {@code null} if the origin contains no mapping for the name.
     */
    public Boolean getAsBoolean(String name) {
        Serializable value = get(name);
        if (value == null) {
            return null;
        } else if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof String) {
            return Boolean.valueOf((String) value);
        } else if (value instanceof Number) {
            return (((Number) value).doubleValue() == 0 && "0".equals(value.toString()));
        }
        throw new UnsupportedOperationException(String.format("Cannot convert [%s] to Boolean.", value));
    }

    /**
     * Get as data map.
     *
     * <pre>
     * example:
     * null => null
     * DataMap => DataMap
     * Map => new DateMap(value) and overwrite original value
     * others => UnsupportedOperationException
     * </pre>
     *
     * @param name name
     * @return {@code DataMap} value, or {@code null} if the origin contains no mapping for the name.
     */
    @SuppressWarnings("unchecked")
    public DataMap getAsDataMap(String name) {
        Serializable value = get(name);
        if (value == null) {
            return null;
        } else if (value instanceof DataMap) {
            return (DataMap) value;
        } else if (value instanceof Map) {
            DataMap tmp = new DataMap((Map<String, Serializable>) value);
            put(name, tmp);
            return tmp;
        }
        throw new UnsupportedOperationException(String.format("Cannot convert [%s] to DataMap.", value));
    }

    // #################### constructors ################################################

    public DataMap() {}

    public DataMap(int initialCapacity) {
        super(initialCapacity);
    }

    public DataMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public DataMap(Map<? extends String, ? extends Serializable> map) {
        super(map);
    }

}
