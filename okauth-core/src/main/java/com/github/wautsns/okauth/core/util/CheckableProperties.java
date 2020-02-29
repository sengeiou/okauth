/**
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
package com.github.wautsns.okauth.core.util;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Checkable properties.
 *
 * @since Feb 29, 2020
 * @author wautsns
 * @see CheckableProperties#check()
 */
public abstract class CheckableProperties {

    /**
     * Check the properties.
     *
     * @throws IllegalArgumentException if any property is illegal
     */
    public abstract void check();

    protected String trimAndNotEmpty(String property, String value) {
        notNull(property, value);
        value = value.trim();
        if (value.isEmpty()) {
            throw illegal(property, value, "not blank");
        } else {
            return value;
        }
    }

    protected <T> T notNull(String property, T value) {
        if (value != null) {
            return value;
        } else {
            throw illegal(property, value, "nonnull");
        }
    }

    protected <T> Class<T> requireConstructor(
            String property, Class<T> value, Class<?>... parameterTypes) {
        try {
            value.getDeclaredConstructor(parameterTypes);
            return value;
        } catch (NoSuchMethodException e) {
            throw illegal(property, value, String.format(
                "class with construct <init>(%s)",
                Arrays.stream(parameterTypes)
                    .map(Class::getSimpleName)
                    .collect(Collectors.joining(", "))));
        }
    }

    protected IllegalArgumentException illegal(String property, Object actual, Object expected) {
        return new IllegalArgumentException(String.format(
            "Property[%s](%s) in %s is illegal(expect %s).",
            property, actual, getClass().getSimpleName(), expected));
    }

}
