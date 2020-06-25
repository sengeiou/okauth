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
package com.github.wautsns.okauth.spring.boot.autoconfigure.util;

import com.github.wautsns.okauth.core.assist.http.kernel.OAuth2HttpClient;
import com.github.wautsns.okauth.core.assist.http.kernel.properties.OAuth2HttpClientProperties;
import com.github.wautsns.okauth.spring.boot.autoconfigure.properties.OkAuthAppsInfoProperties;
import com.github.wautsns.okauth.spring.boot.autoconfigure.properties.OkAuthHttpClientProperties;
import com.github.wautsns.okauth.spring.boot.autoconfigure.properties.OkAuthProperties;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * OkAuth auto-configure utils.
 *
 * @author wautsns
 * @since May 25, 2020
 */
@UtilityClass
public class OkAuthAutoConfigureUtils {

    /**
     * Initialize oauth2 http client.
     *
     * @param okauthProps okauth properties
     * @param okauthAppInfoProps okauth app info properties
     * @return oauth2 http client
     */
    public static OAuth2HttpClient initOAuth2HttpClient(
            OkAuthProperties okauthProps, OkAuthAppsInfoProperties.OkAuthAppInfo okauthAppInfoProps) {
        OkAuthHttpClientProperties okauthHttpClientProps = fillNullProperties(
                okauthAppInfoProps.getHttpClient(), okauthProps.getDefaultHttpClient());
        try {
            Constructor<? extends OAuth2HttpClient> constructor = okauthHttpClientProps
                    .getImplementation()
                    .getConstructor(OAuth2HttpClientProperties.class);
            constructor.setAccessible(true);
            return constructor.newInstance(okauthHttpClientProps.getProperties());
        } catch (NoSuchMethodException e) {
            throw new UnsupportedOperationException(String.format(
                    "%s need a constructor with argument of type: %s",
                    okauthHttpClientProps.getImplementation(), OAuth2HttpClientProperties.class));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Fill null properties.
     *
     * @param target target value
     * @param source source value
     * @param <T> type of value
     * @return value after filling
     */
    private static <T> T fillNullProperties(T target, T source) {
        if (target == null) { return source; }
        Arrays.stream(target.getClass().getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .forEach(field -> {
                    try {
                        field.setAccessible(true);
                        Class<?> type = field.getType();
                        Object value;
                        if (type.isPrimitive() || Modifier.isFinal(type.getModifiers())) {
                            value = field.get(source);
                        } else {
                            value = fillNullProperties(field.get(target), field.get(source));
                        }
                        field.set(target, value);
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    }
                });
        return target;
    }

}
