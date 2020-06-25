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
package com.github.wautsns.okauth.core.assist.http.kernel.model.basic.entity.builtin;

import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.entity.OAuth2HttpEntity;
import com.github.wautsns.okauth.core.assist.http.kernel.util.WriteUtils;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

/**
 * OAuth2 http json entity.
 *
 * @author wautsns
 * @since Jun 25, 2020
 */
public class OAuth2HttpJsonEntity implements OAuth2HttpEntity {

    private static final long serialVersionUID = -5283149053170420932L;

    /** Changeable dataMap names. */
    private Set<String> changeableDataMapNames;
    /** Original data map. */
    private DataMap origin;

    /**
     * Put unchanged value.
     *
     * @param name name
     * @param value unchanged value
     * @return self reference
     */
    public OAuth2HttpJsonEntity putUnchangedValue(String name, Serializable value) {
        if (origin == null) { origin = new DataMap(8); }
        origin.put(name, value);
        return this;
    }

    /**
     * Put changeable data map.
     *
     * @param name name
     * @param value changeable data map
     * @return self reference
     */
    public OAuth2HttpJsonEntity putChangeableDataMap(String name, DataMap value) {
        origin.put(name, value);
        if (changeableDataMapNames == null) { changeableDataMapNames = new HashSet<>(); }
        changeableDataMapNames.add(name);
        return this;
    }

    /**
     * Get as {@code T} value.
     *
     * <ul>
     * <li>{@code value} =&gt; {@code (T)value}</li>
     * </ul>
     *
     * @param name name
     * @param <T> type of value
     * @return {@code T} value, or {@code null} if the json entity contains no mapping for the name
     */
    public <T> T getAs(String name) {
        return origin.getAs(name);
    }

    /**
     * Get data map with specified name.
     *
     * @param name name
     * @return data map with specified name
     */
    public DataMap getDataMap(String name) {
        return origin.getAsDataMap(name);
    }

    @Override
    public byte[] toBytes() {
        return WriteUtils.writeObjectAsJsonString(origin).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public OAuth2HttpJsonEntity copy() {
        OAuth2HttpJsonEntity copy = new OAuth2HttpJsonEntity();
        copy.changeableDataMapNames = this.changeableDataMapNames;
        if (changeableDataMapNames == null) {
            copy.origin = new DataMap(this.origin);
        } else {
            copy.origin = new DataMap(this.origin.size() + 2);
            this.origin.forEach((name, value) -> {
                if (value instanceof DataMap && changeableDataMapNames.contains(name)) {
                    copy.origin.put(name, copyDataMap((DataMap) value));
                } else {
                    copy.origin.put(name, value);
                }
            });
        }
        return copy;
    }

    /**
     * Create and return a copy of data map.
     *
     * @param source source
     * @return a copy of data map
     */
    private DataMap copyDataMap(DataMap source) {
        DataMap copy = new DataMap(source.size());
        source.forEach((name, value) -> {
            if (value instanceof DataMap) {
                value = copyDataMap((DataMap) value);
            }
            copy.put(name, value);
        });
        return copy;
    }

}
