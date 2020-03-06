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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * Parameters.
 *
 * @author wautsns
 * @since Mar 03, 2020
 */
public class Parameters implements Serializable {

    private static final long serialVersionUID = 613574422404538886L;

    /** parameters: name, value, name, value... */
    private final List<String> nameValuePairs = new LinkedList<>();

    /**
     * For each parameter.
     *
     * @param action action for parameter, require nonnull
     */
    public void forEach(BiConsumer<String, String> action) {
        for (Iterator<String> i = nameValuePairs.iterator(); i.hasNext(); ) {
            action.accept(i.next(), i.next());
        }
    }

    /**
     * Add parameter.
     *
     * <p>If the value is {@code null}, the parameter will not be added.
     *
     * @param name name of the parameter, require nonnull
     * @param value value of the parameter
     * @return self reference
     */
    public Parameters add(String name, String value) {
        if (value == null) { return this; }
        nameValuePairs.add(name);
        nameValuePairs.add(value);
        return this;
    }

    /**
     * Get name value pairs.(eg. name, value, name, value...)
     *
     * @return name value pairs
     */
    public List<String> getNameValuePairs() {
        return nameValuePairs;
    }

    /**
     * Create and return a copy of {@link Parameters}.
     *
     * @return copy of {@link Parameters}
     */
    public Parameters copy() {
        Parameters copy = new Parameters();
        copy.nameValuePairs.addAll(nameValuePairs);
        return copy;
    }

}
