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
package com.github.wautsns.okauth.core.http.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Data map.
 *
 * @author wautsns
 * @since Mar 03, 2020
 */
public class DataMap extends HashMap<String, Serializable> {

    private static final long serialVersionUID = 8053655901684236188L;

    /**
     * Get as string.
     *
     * @param name name, require nonnull
     * @return string value, or {@code null} if the original data map contains no mapping for the name.
     */
    public String getAsString(String name) {
        return Objects.toString(get(name), null);
    }

    /**
     * Get integer.
     *
     * @param name name, require nonnull
     * @return int value, or {@code null} if the original data map contains no mapping for the name.
     */
    public Integer getInteger(String name) {
        return (Integer) get(name);
    }

    /**
     * Get boolean.
     *
     * @param name name, require nonnull
     * @return boolean value, or {@code null} if the original data map contains no mapping for the name.
     */
    public Boolean getBoolean(String name) {
        return (Boolean) get(name);
    }

    /**
     * Get data map.
     *
     * @param name name, require nonnull
     * @return data map value, or {@code null} if the original data map contains no mapping for the name.
     */
    @SuppressWarnings("unchecked")
    public DataMap getDataMap(String name) {
        Serializable dataMap = get(name);
        if (dataMap instanceof DataMap) {
            return (DataMap) dataMap;
        } else if (dataMap == null) {
            return null;
        } else {
            DataMap tmp = new DataMap();
            tmp.putAll((Map<String, Serializable>) dataMap);
            put(name, tmp);
            return tmp;
        }
    }

}
