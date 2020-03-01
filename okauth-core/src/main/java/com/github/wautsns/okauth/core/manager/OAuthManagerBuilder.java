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
package com.github.wautsns.okauth.core.manager;

import java.lang.reflect.Constructor;

import java.util.HashMap;
import java.util.Map;

import com.github.wautsns.okauth.core.client.OAuthClientInitializer;
import com.github.wautsns.okauth.core.client.OpenPlatform;
import com.github.wautsns.okauth.core.client.builtin.OpenPlatforms;
import com.github.wautsns.okauth.core.client.kernel.OAuthClient;
import com.github.wautsns.okauth.core.client.kernel.http.OAuthRequestExecutor;
import com.github.wautsns.okauth.core.client.kernel.http.model.properties.OAuthRequestExecutorProperties;
import com.github.wautsns.okauth.core.client.kernel.model.properties.OAuthAppProperties;

/**
 * OAuth manager builder.
 *
 * @since Feb 29, 2020
 * @author wautsns
 */
public class OAuthManagerBuilder {

    /** registered clients */
    private Map<OpenPlatform, OAuthClient<?>> clients = new HashMap<>();

    /**
     * Build oauth manager based on current configuration.
     *
     * @return oauth manager
     */
    public OAuthManager build() {
        return new OAuthManager(new HashMap<>(clients));
    }

    /**
     * Register oauth client.
     *
     * @param properties oauth manager properties, require nonnull
     * @return self reference
     */
    public OAuthManagerBuilder register(OAuthManagerProperties properties) {
        properties.check();
        OAuthRequestExecutorProperties defReqExe = properties.getDefaultRequestExecutor();
        properties.getClients().forEach(client -> {
            OAuthRequestExecutorProperties reqExe = client.getRequestExecutor();
            if (reqExe == null) {
                reqExe = new OAuthRequestExecutorProperties();
                client.setRequestExecutor(reqExe);
            }
            reqExe.fillNullPropertiesWith(defReqExe);
            String expr = client.getOpenPlatformExpr();
            OAuthAppProperties app = client.getApp();
            OAuthRequestExecutor executor = newRequestExecutor(reqExe);
            OAuthClientInitializer initializer = parseOpenPlatformExpr(expr);
            register(initializer.initOAuthClient(app, executor));
        });
        return this;
    }

    /**
     * Register oauth client.
     *
     * @param initializer oauth client initializer, require nonnull
     * @param app oauth app properties, require nonnull
     * @param executor oauth requeset executor, require nonnull
     * @return self reference
     */
    public OAuthManagerBuilder register(
            OAuthClientInitializer initializer,
            OAuthAppProperties app, OAuthRequestExecutor executor) {
        app.check();
        return register(initializer.initOAuthClient(app, executor));
    }

    /**
     * Register oauth client.
     *
     * @param client oauth client, require nonnull
     * @return self reference
     */
    public OAuthManagerBuilder register(OAuthClient<?> client) {
        clients.put(client.getOpenPlatform(), client);
        return this;
    }

    // -------------------- utils -----------------------------------

    /**
     * Parse open platform expr.
     *
     * @param expr open platform expr, require nonnull
     * @return oauth client initializer
     * @see OAuthManagerProperties#setClients(Map)
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static OAuthClientInitializer parseOpenPlatformExpr(String expr) {
        OpenPlatforms builtIn = getEnumByName(OpenPlatforms.class, expr.toUpperCase());
        if (builtIn != null) { return builtIn; }
        String[] indicator = expr.split(":", 2);
        Class<? extends OAuthClientInitializer> clazz = getInitializerClass(indicator[0]);
        if (indicator.length == 2) {
            Enum instance = Enum.valueOf((Class) clazz, indicator[1]);
            return (OAuthClientInitializer) instance;
        } else {
            OAuthClientInitializer[] tmp = clazz.getEnumConstants();
            if (tmp.length == 1) {
                return tmp[0];
            } else {
                throw new IllegalArgumentException(String.format(
                    "Please give name(like 'GitHub') to open platform expr: %s.",
                    expr));
            }
        }
    }

    /**
     * Get oauth client initializer class.
     *
     * @param className initializer class name, require nonnull
     * @return oauth client initializer class
     * @throws IllegalArgumentException if the class not found or the class is invalid
     */
    @SuppressWarnings("unchecked")
    private static Class<? extends OAuthClientInitializer> getInitializerClass(String className) {
        Class<?> tmp;
        try {
            tmp = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
        if (OAuthClientInitializer.class.isAssignableFrom(tmp) && tmp.isEnum()) {
            return (Class<? extends OAuthClientInitializer>) tmp;
        } else {
            throw new IllegalArgumentException(String.format(
                "%s is not assignable to %s or it is not enumeration",
                tmp, OAuthClientInitializer.class));
        }
    }

    /**
     * Get enum by name.
     *
     * @param <T> type of the enum
     * @param enumClass class of the enum, require nonnull
     * @param name name of the enum, require nonnull
     * @return enum of the specific name, or {@code null} if the name does not exist
     */
    private static <T extends Enum<T>> T getEnumByName(Class<T> enumClass, String name) {
        try {
            return Enum.valueOf(enumClass, name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * New oauth request executor.
     *
     * @param properties oauth request executor properties, require nonnull
     * @return oauth request executor
     */
    private static OAuthRequestExecutor newRequestExecutor(
            OAuthRequestExecutorProperties properties) {
        try {
            Constructor<? extends OAuthRequestExecutor> constructor = properties
                .getRequestExecutorClass()
                .getConstructor(OAuthRequestExecutorProperties.class);
            constructor.setAccessible(true);
            return constructor.newInstance(properties);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

}
