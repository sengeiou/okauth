/**
 * Copyright 2019 the original author or authors.
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatform;
import com.github.wautsns.okauth.core.client.core.OkAuthClient;
import com.github.wautsns.okauth.core.client.core.OkAuthClientInitializer;
import com.github.wautsns.okauth.core.client.core.OpenPlatform;
import com.github.wautsns.okauth.core.client.core.properties.OAuthAppInfo;
import com.github.wautsns.okauth.core.client.core.properties.OkAuthClientProperties;
import com.github.wautsns.okauth.core.client.util.http.Requester;
import com.github.wautsns.okauth.core.client.util.http.builtin.okhttp.OkHttpRequester;
import com.github.wautsns.okauth.core.client.util.http.properties.OkAuthRequesterProperties;
import com.github.wautsns.okauth.core.exception.OkAuthInitializeException;
import com.github.wautsns.okauth.core.manager.properties.OkAuthProperties;

/**
 * {@linkplain OkAuthManager okauth manager}'s builder
 *
 * @author wautsns
 */
public class OkAuthManagerBuilder {

    /** registered clients */
    private Map<OpenPlatform, OkAuthClient> clients = new HashMap<>();

    /**
     * Build an okauth manager.
     *
     * @return okauth manager
     */
    public OkAuthManager build() {
        return new OkAuthManager(clients);
    }

    /**
     * Register an okauth client.
     *
     * <p>Use default requester: {@linkplain OkHttpRequester ok http requester}.
     *
     * @param okauthClientInitializer okauth client initializer, require nonnull
     * @param oauthAppInfo oauth application info(e.g. clientId, clientSecret...), require nonnull
     * @param okauthHttpProperties oauth http properties, require nonnull
     * @return self reference
     * @throws OkAuthInitializeException if the `openPlatform` has been registered
     */
    public OkAuthManagerBuilder register(
            OkAuthClientInitializer okauthClientInitializer,
            OAuthAppInfo oauthAppInfo, OkAuthRequesterProperties okauthHttpProperties) {
        return register(
            okauthClientInitializer,
            oauthAppInfo, new OkHttpRequester(okauthHttpProperties));
    }

    /**
     * Register an okauth client.
     *
     * @param okauthClientInitializer okauth client initializer, require nonnull
     * @param oauthAppInfo oauth application info(e.g. clientId, clientSecret...), require nonnull
     * @param requester http requester, require nonnull
     * @return self reference
     * @throws OkAuthInitializeException if the `openPlatform` has been registered
     */
    public OkAuthManagerBuilder register(
            OkAuthClientInitializer okauthClientInitializer,
            OAuthAppInfo oauthAppInfo, Requester requester) {
        OkAuthClient old = clients.put(
            okauthClientInitializer,
            okauthClientInitializer.initOkAuthClient(oauthAppInfo, requester));
        if (old == null) { return this; }
        throw new OkAuthInitializeException(
            "Duplicate registered oauth client(open platform identifier:  "
                + okauthClientInitializer.getIdentifier() + ")");
    }

    /**
     * Register ok auth clients.
     *
     * @param properties okauth properties.
     * @return self reference
     */
    public OkAuthManagerBuilder register(OkAuthProperties properties) {
        OkAuthRequesterProperties defaultRequester = properties.getDefaultRequester();
        properties.getClients().forEach(client -> register(
            parseOpenPlatformExpr(client.getOpenPlatformExpr()),
            client.getOauthAppInfo(),
            initRequester(fillingRequesterProperties(defaultRequester, client.getRequester()))));
        return this;
    }

    // ------------------------- BEGIN -------------------------
    // ------------------------ assists ------------------------
    // ---------------------------------------------------------

    /**
     * Filling the okauth requester properties with default properties.
     *
     * @param defaultProperties default properties
     * @param properties target properties
     */
    private OkAuthRequesterProperties fillingRequesterProperties(
            OkAuthRequesterProperties defaultProperties,
            OkAuthRequesterProperties properties) {
        if (properties.getRequesterClass() == null) {
            properties.setRequesterClass(defaultProperties.getRequesterClass());
        }
        if (properties.getMaxConcurrentRequests() == null) {
            properties.setMaxConcurrentRequests(defaultProperties.getMaxConcurrentRequests());
        }
        if (properties.getMaxIdleConnections() == null) {
            properties.setMaxIdleConnections(defaultProperties.getMaxIdleConnections());
        }
        if (properties.getKeepAlive() == null || properties.getKeepAliveTimeUnit() == null) {
            properties.setKeepAlive(defaultProperties.getKeepAlive());
            properties.setKeepAliveTimeUnit(defaultProperties.getKeepAliveTimeUnit());
        }
        if (properties.getConnectTimeoutMilliseconds() == null) {
            properties.setConnectTimeoutMilliseconds(
                defaultProperties.getConnectTimeoutMilliseconds());
        }
        return properties;
    }

    /**
     * Parse open platform expression.
     *
     * @param openPlatformExpr {@linkplain OkAuthClientProperties#openPlatformExpr open platform
     *        expression}, require nonnull
     * @return okauth client initialization
     * @throws OkAuthInitializeException if the expression can not be parsed
     */
    @SuppressWarnings("unchecked")
    private OkAuthClientInitializer parseOpenPlatformExpr(String openPlatformExpr) {
        // First, try if the expr is a built-in simple open platform name.
        OkAuthClientInitializer[] initializers = BuiltInOpenPlatform.values();
        OkAuthClientInitializer initializer = Arrays.stream(initializers)
            .filter(openPlatform -> openPlatform.getIdentifier().equalsIgnoreCase(openPlatformExpr))
            .findFirst().orElse(null);
        if (initializer != null) { return initializer; }
        // The expr is extended okauth client initializer.
        String[] classAndIdentifier = openPlatformExpr.split(":", 2);
        // Parse open platform class
        Class<? extends OkAuthClientInitializer> initializerClass;
        try {
            Class<?> temp = Class.forName(classAndIdentifier[0]);
            if (!OkAuthClientInitializer.class.isAssignableFrom(temp) || !temp.isEnum()) {
                throw new OkAuthInitializeException(String.format(
                    "%s should be an enumeration that implements %s", temp, OpenPlatform.class));
            }
            initializerClass = (Class<? extends OkAuthClientInitializer>) temp;
        } catch (ClassNotFoundException e) {
            throw new OkAuthInitializeException(e);
        }
        initializers = initializerClass.getEnumConstants();
        String identifier = (classAndIdentifier.length == 2) ? classAndIdentifier[1] : null;
        if (identifier != null) {
            return Arrays.stream(initializers)
                .filter(openPlatform -> openPlatform.getIdentifier().equalsIgnoreCase(identifier))
                .findFirst().orElseThrow(() -> new OkAuthInitializeException(String.format(
                    "There is no identifier named '%s' in %s", identifier, initializerClass)));
        } else if (initializers.length == 1) {
            return initializers[0];
        } else {
            throw new OkAuthInitializeException(
                "Please give an identifier in " + initializerClass);
        }
    }

    /**
     * Initialize a requester.
     *
     * @param properties okauth requester properties, require nonnull
     * @return requester
     * @throws OkAuthInitializeException if the requester can not be initialized
     */
    private Requester initRequester(OkAuthRequesterProperties properties) {
        Class<? extends Requester> requesterClass = properties.getRequesterClass();
        try {
            return requesterClass
                .getConstructor(OkAuthRequesterProperties.class)
                .newInstance(properties);
        } catch (Exception e) {
            throw new OkAuthInitializeException(String.format(
                "%s should contain a public constructor with arg type of %s",
                requesterClass, OkAuthRequesterProperties.class));
        }
    }

}
