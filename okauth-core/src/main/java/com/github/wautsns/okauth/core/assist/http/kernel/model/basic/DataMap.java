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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
     * Get as {@code T} value.
     *
     * <ul>
     * <li>{@code value} =&gt; {@code (T)value}</li>
     * </ul>
     *
     * @param name name
     * @param <T> type of value
     * @return {@code T} value, or {@code null} if the dataMap contains no mapping for the name
     */
    @SuppressWarnings("unchecked")
    public <T> T getAs(String name) {
        return (T) get(name);
    }

    /**
     * Get as {@code String} value.
     *
     * <ul>
     * <li>{@code null} =&gt; {@code null}</li>
     * <li>others =&gt; {@code value.toString()}</li>
     * </ul>
     *
     * @param name name
     * @return {@code String} value, or {@code null} if the map contains no mapping for the name
     */
    public String getAsString(String name) {
        return Objects.toString(get(name), null);
    }

    /**
     * Get as {@code Boolean} value.
     *
     * <ul>
     * <li>{@code null} =&gt; {@code null}</li>
     * <li>{@code Boolean} =&gt; {@code value}</li>
     * <li>{@code String} =&gt; {@code Boolean.valueOf((String) value)}</li>
     * <li><strong>others =&gt; throw {@code UnsupportedOperationException}</strong></li>
     * </ul>
     *
     * @param name name
     * @return {@code Boolean} value, or {@code null} if the map contains no mapping for the name
     */
    public Boolean getAsBoolean(String name) {
        Serializable value = get(name);
        if (value == null) {
            return null;
        } else if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof String) {
            return Boolean.valueOf((String) value);
        }
        throw initExceptionForCannotConvert(Boolean.class, value);
    }

    /**
     * Get as {@code Integer} value.
     *
     * <ul>
     * <li>{@code null} =&gt; {@code null}</li>
     * <li>{@code Integer} =&gt; {@code value}</li>
     * <li>{@code String} =&gt; {@code Integer.parseInt(value)}</li>
     * <li>{@code Number} =&gt; {@code value.intValue()}</li>
     * <li><strong>others =&gt; throw {@code UnsupportedOperationException}</strong></li>
     * </ul>
     *
     * @param name name
     * @return {@code Integer} value, or {@code null} if the map contains no mapping for the name
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
        throw initExceptionForCannotConvert(Integer.class, value);
    }

    /**
     * Get as {@code Long} value.
     *
     * <ul>
     * <li>{@code null} =&gt; {@code null}</li>
     * <li>{@code Long} =&gt; {@code value}</li>
     * <li>{@code Number} =&gt; {@code value.longValue()}</li>
     * <li>{@code String} =&gt; {@code Long.parseLong(value)}</li>
     * <li><strong>others =&gt; throw {@code UnsupportedOperationException}</strong></li>
     * </ul>
     *
     * @param name name
     * @return {@code Long} value, or {@code null} if the map contains no mapping for the name
     */
    public Long getAsLong(String name) {
        Serializable value = get(name);
        if (value == null) {
            return null;
        } else if (value instanceof Long) {
            return (Long) value;
        } else if (value instanceof Number) {
            return ((Number) value).longValue();
        } else if (value instanceof String) {
            return Long.parseLong((String) value);
        }
        throw initExceptionForCannotConvert(Long.class, value);
    }

    /**
     * Get as {@code LocalDateTime} value.
     *
     * <ul>
     * <li>{@code null} =&gt; {@code null}</li>
     * <li>{@code LocalDateTime} =&gt; {@code value}</li>
     * <li>{@code String} =&gt; {@code LocalDateTime.parse(value, DateTimeFormatter.ISO_OFFSET_DATE_TIME)}</li>
     * <li><strong>others =&gt; throw {@code UnsupportedOperationException}</strong></li>
     * </ul>
     *
     * @param name name
     * @return {@code LocalDateTime} value, or {@code null} if the map contains no mapping for the name
     */
    public LocalDateTime getAsLocalDateTime(String name) {
        Serializable value = get(name);
        if (value == null) {
            return null;
        } else if (value instanceof String) {
            return LocalDateTime.parse((String) value, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        } else if (value instanceof LocalDateTime) {
            return (LocalDateTime) value;
        }
        throw initExceptionForCannotConvert(LocalDateTime.class, value);
    }

    /**
     * Get as {@code DataMap} value.
     *
     * <ul>
     * <li>{@code null} =&gt; {@code null}</li>
     * <li>{@code DataMap} =&gt; {@code value}</li>
     * <li>{@code Map} =&gt; {@code new DataMap(value)}</li>
     * <li><strong>others =&gt; throw {@code UnsupportedOperationException}</strong></li>
     * </ul>
     *
     * @param name names
     * @return {@code DataMap} value, or {@code null} if the map contains no mapping for the name
     */
    @SuppressWarnings("unchecked")
    public DataMap getAsDataMap(String name) {
        Serializable value = get(name);
        if (value == null) {
            return null;
        } else if (value instanceof DataMap) {
            return (DataMap) value;
        } else if (value instanceof Map) {
            return new DataMap((Map<String, Serializable>) value);
        }
        throw initExceptionForCannotConvert(DataMap.class, value);
    }

    /**
     * Get as {@code DataMap} value.
     *
     * <ul>
     * <li>{@code null} =&gt; {@code null}</li>
     * <li>{@code DataMap} =&gt; {@code value}</li>
     * <li>{@code Map} =&gt; {@code new DataMap(value)}</li>
     * <li><strong>others =&gt; throw {@code UnsupportedOperationException}</strong></li>
     * </ul>
     *
     * @param name names
     * @return {@code DataMap} value, or {@code null} if the map contains no mapping for the name
     */
    @SuppressWarnings("unchecked")
    public List<DataMap> getAsDataMapList(String name) {
        Serializable value = get(name);
        if (value == null) {
            return null;
        } else if (value instanceof Collection) {
            return ((Collection<Map<String, Serializable>>) value).stream()
                    .map(DataMap::new)
                    .collect(Collectors.toCollection(LinkedList::new));
        }
        throw initExceptionForCannotConvert(DataMap.class, value);
    }

    /**
     * Initialize exception for "cannot convert".
     *
     * @param targetClass target class
     * @param value value
     * @return exception
     */
    private static UnsupportedOperationException initExceptionForCannotConvert(Class<?> targetClass, Object value) {
        return new UnsupportedOperationException(String.format("Cannot convert [%s] to [%s].", value, targetClass));
    }

    // #################### others ######################################################

    /**
     * Put value with the specified name and return self reference.
     *
     * @param name name
     * @param value value
     * @return self reference
     */
    public DataMap with(String name, Serializable value) {
        put(name, value);
        return this;
    }

    // #################### constructors ################################################

    /** Construct an empty {@code DataMap} instance with the default initial capacity (16) and load factor (0.75). */
    public DataMap() {}

    /**
     * Construct an empty {@code DataMap} instance with the specified initial capacity and a default load factor
     * (0.75).
     *
     * @param initialCapacity the initial capacity
     */
    public DataMap(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Construct an empty {@code DataMap} instance with the specified initial capacity and load factor.
     *
     * @param initialCapacity the initial capacity
     * @param loadFactor the load factor
     */
    public DataMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    /**
     * Construct a {@code DataMap} instance with the same mappings as the specified map. The {@code DataMap} instance is
     * created with a default load factor (0.75) and an initial capacity sufficient to hold the mappings in the
     * specified map.
     *
     * @param m the map whose mappings are to be placed in this map
     */
    public DataMap(Map<? extends String, ? extends Serializable> m) {
        super(m);
    }

}
