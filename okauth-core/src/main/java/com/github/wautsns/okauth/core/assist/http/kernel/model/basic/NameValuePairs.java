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

import lombok.Getter;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Name value pairs.
 *
 * @author wautsns
 * @since May 16, 2020
 */
@Getter
public class NameValuePairs implements Serializable {

    private static final long serialVersionUID = -2963874324351686069L;

    /** original data */
    private final LinkedList<String> origin = new LinkedList<>();

    /**
     * For each name value pair.
     *
     * @param action action for name value pair
     */
    public void forEach(BiConsumer<String, String> action) {
        for (Iterator<String> iterator = origin.iterator(); iterator.hasNext(); ) {
            action.accept(iterator.next(), iterator.next());
        }
    }

    /**
     * Add name value pair.
     *
     * <p>If the value is {@code null}, the value will not be added.
     *
     * @param name name
     * @param value value
     * @return self reference
     */
    public NameValuePairs add(String name, String value) {
        if (value == null) { return this; }
        origin.add(name);
        origin.add(value);
        return this;
    }

    /**
     * Add name value pair.
     *
     * <p>If the value is {@code null}, default value will not be added.
     *
     * @param name name
     * @param value value
     * @param defaultValue default value
     * @return self reference
     */
    public NameValuePairs add(String name, String value, String defaultValue) {
        if (value == null) {
            return add(name, defaultValue);
        } else {
            origin.add(name);
            origin.add(value);
            return this;
        }
    }

    /**
     * Add all values.
     *
     * <p>If nameValueMap is {@code null}, no values will be added.
     * <p>If value is {@code null}, the value will not be added.
     *
     * @param nameValueMap name value map
     * @return self reference
     */
    public NameValuePairs addAll(Map<String, String> nameValueMap) {
        if (nameValueMap != null) { nameValueMap.forEach(this::add); }
        return this;
    }

    /**
     * Set value.
     *
     * @param name name
     * @param value value
     */
    public NameValuePairs set(String name, String value) {
        remove(name);
        return add(name, value);
    }

    /**
     * Set value.
     *
     * @param name name
     */
    public void remove(String name) {
        for (Iterator<String> iterator = origin.iterator(); iterator.hasNext(); ) {
            if (iterator.next().equals(name)) {
                iterator.remove();
                iterator.next();
                iterator.remove();
            }
        }
    }

    /**
     * Create and return a copy of this object.
     *
     * @return a copy of this object
     */
    public NameValuePairs copy() {
        NameValuePairs copy = new NameValuePairs();
        copy.origin.addAll(this.origin);
        return copy;
    }

}
