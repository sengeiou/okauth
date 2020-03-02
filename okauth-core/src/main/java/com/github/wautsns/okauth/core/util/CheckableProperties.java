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
package com.github.wautsns.okauth.core.util;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Checkable properties.
 *
 * @author wautsns
 * @see CheckableProperties#check()
 * @since Feb 29, 2020
 */
public abstract class CheckableProperties {

    /**
     * Check the properties.
     *
     * @throws IllegalArgumentException if any property is illegal
     */
    public abstract void check();

    /**
     * Assert that the value is not empty after {@linkplain String#trim() trim}.
     *
     * @param property property name, require nonnull
     * @param value value
     * @return value after {n String#trim() trim}
     * @throws IllegalArgumentException if assertion is wrong
     */
    protected String trimAndNotEmpty(String property, String value) {
        notNull(property, value);
        value = value.trim();
        if (value.isEmpty()) {
            throw illegal(property, value, "not blank");
        } else {
            return value;
        }
    }

    /**
     * Assert that the value is not null.
     *
     * @param property property name, require nonnull
     * @param value property value
     * @param <T> value type
     * @return nonnull value
     * @throws IllegalArgumentException if assertion is wrong
     */
    protected <T> T notNull(String property, T value) {
        if (value != null) {
            return value;
        } else {
            throw illegal(property, null, "nonnull");
        }
    }

    /**
     * Assert that the class has constructor with the specific parameter types.
     *
     * @param property property name, require nonnull
     * @param clazz target clazz, require nonnull
     * @param parameterTypes constructor parameter types
     * @throws IllegalArgumentException if assertion is wrong
     */
    protected void requireConstructor(
        String property, Class<?> clazz, Class<?>... parameterTypes) {
        try {
            clazz.getDeclaredConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            throw illegal(property, clazz, String.format(
                "class with constructor <init>(%s)",
                Arrays.stream(parameterTypes)
                    .map(Class::getSimpleName)
                    .collect(Collectors.joining(", "))));
        }
    }

    /**
     * New {@link IllegalArgumentException}.
     *
     * <p>Message is like "Property[%s](%s) in %s is illegal(expect %s)."</p>
     *
     * @param property property name, require nonnull
     * @param actual actual value
     * @param expected expected value
     * @return {@link IllegalArgumentException}
     */
    protected IllegalArgumentException illegal(
        String property, Object actual, Object expected) {
        return new IllegalArgumentException(String.format(
            "Property[%s](%s) in %s is illegal(expect %s).",
            property, actual, getClass().getSimpleName(), expected));
    }

}
