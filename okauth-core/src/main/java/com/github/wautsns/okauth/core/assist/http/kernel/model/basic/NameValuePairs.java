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
import java.util.ListIterator;
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

    /** Original data. */
    private final LinkedList<String> origin = new LinkedList<>();

    /**
     * Iterate over each name value pair.
     *
     * @param action the action to be performed for each name value pair
     */
    public void forEach(BiConsumer<String, String> action) {
        for (Iterator<String> iterator = origin.iterator(); iterator.hasNext(); ) {
            action.accept(iterator.next(), iterator.next());
        }
    }

    /**
     * Add value with the specified name.
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
     * Add value with the specified name.
     *
     * <p>If the value is {@code null}, the default value will be added.
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
     * Add name value pairs.
     *
     * <p>If nameValuePairs is {@code null}, no values will be added.
     * <p>If value is {@code null}, the value will not be added.
     *
     * @param nameValuePairs name value map
     * @return self reference
     */
    public NameValuePairs addAll(Map<String, String> nameValuePairs) {
        if (nameValuePairs != null) { nameValuePairs.forEach(this::add); }
        return this;
    }

    /**
     * Associates value with the specified name(Old value will be replaced).
     *
     * @param name name
     * @param value value
     */
    public NameValuePairs set(String name, String value) {
        for (ListIterator<String> iterator = origin.listIterator(); iterator.hasNext(); ) {
            String target = iterator.next();
            iterator.next();
            if (target.equals(name)) { iterator.set(value); }
        }
        return this;
    }

    /**
     * Remove value associated with the specified name.
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
