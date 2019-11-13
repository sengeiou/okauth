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
     * @param openPlatform open platform, require nonnull
     * @param oauthAppInfo oauth application info(e.g. clientId, clientSecret...), require nonnull
     * @param okauthHttpProperties oauth http properties, require nonnull
     * @return self reference
     * @throws OkAuthInitializeException if the `openPlatform` has been registered
     */
    public OkAuthManagerBuilder register(
            OpenPlatform openPlatform, OAuthAppInfo oauthAppInfo,
            OkAuthRequesterProperties okauthHttpProperties)
            throws OkAuthInitializeException {
        return register(openPlatform, oauthAppInfo, new OkHttpRequester(okauthHttpProperties));
    }

    /**
     * Register an okauth client.
     *
     * @param openPlatform open platform, require nonnull
     * @param oauthAppInfo oauth application info(e.g. clientId, clientSecret...), require nonnull
     * @param requester http requester, require nonnull
     * @return self reference
     * @throws OkAuthInitializeException if the `openPlatform` has been registered
     */
    public OkAuthManagerBuilder register(
            OpenPlatform openPlatform, OAuthAppInfo oauthAppInfo, Requester requester)
            throws OkAuthInitializeException {
        OkAuthClient old = clients.put(
            openPlatform,
            openPlatform.initOkAuthClient(oauthAppInfo, requester));
        if (old == null) { return this; }
        throw new OkAuthInitializeException(
            "Duplicate registered oauth client(open platform identifier:  "
                + openPlatform.getIdentifier() + ")");
    }

    /**
     * Register ok auth clients.
     *
     * @param properties okauth properties.
     * @return self reference
     */
    public OkAuthManagerBuilder register(OkAuthProperties properties) {
        OkAuthRequesterProperties defaultRequester = properties.getDefaultRequester();
        for (OkAuthClientProperties client : properties.getClients()) {
            OkAuthRequesterProperties requester = client.getRequester();
            if (requester.getRequesterClass() == null) {
                requester.setRequesterClass(defaultRequester.getRequesterClass());
            }
            if (requester.getMaxConcurrentRequests() == null) {
                requester.setMaxConcurrentRequests(defaultRequester.getMaxConcurrentRequests());
            }
            if (requester.getMaxIdleConnections() == null) {
                requester.setMaxIdleConnections(defaultRequester.getMaxIdleConnections());
            }
            if (requester.getKeepAlive() == null || requester.getKeepAliveTimeUnit() == null) {
                requester.setKeepAlive(defaultRequester.getKeepAlive());
                requester.setKeepAliveTimeUnit(defaultRequester.getKeepAliveTimeUnit());
            }
            if (requester.getConnectTimeoutMilliseconds() == null) {
                requester.setConnectTimeoutMilliseconds(
                    defaultRequester.getConnectTimeoutMilliseconds());
            }
            register(
                parseOpenPlatformExpr(client.getOpenPlatformExpr()),
                client.getOauthAppInfo(),
                initRequester(requester));
        }
        return this;
    }

    // ------------------------- BEGIN -------------------------
    // ------------------------ assists ------------------------
    // ---------------------------------------------------------

    /**
     * Parse open platform expression.
     *
     * @param openPlatformExpr {@linkplain OkAuthClientProperties#openPlatformExpr open platform
     *        expression}, require nonnull
     * @return open platform
     * @throws OkAuthInitializeException if the expression can not be parsed
     */
    @SuppressWarnings("unchecked")
    private OpenPlatform parseOpenPlatformExpr(String openPlatformExpr)
            throws OkAuthInitializeException {
        // First, try if the expr is a built-in simple open platform name.
        OpenPlatform[] openPlatforms = BuiltInOpenPlatform.values();
        OpenPlatform openPlatform = Arrays.stream(openPlatforms)
            .filter(op -> op.getIdentifier().equalsIgnoreCase(openPlatformExpr))
            .findFirst().orElse(null);
        if (openPlatform != null) { return openPlatform; }
        // The expr is extended open platform.
        String[] classAndIdentifier = openPlatformExpr.split(":", 2);
        // Parse open platform class
        Class<? extends OpenPlatform> openPlatformClass;
        try {
            Class<?> temp1 = Class.forName(classAndIdentifier[0]);
            if (!OpenPlatform.class.isAssignableFrom(temp1) || !temp1.isEnum()) {
                throw new OkAuthInitializeException(String.format(
                    "%s should be an enumeration that implements %s", temp1, OpenPlatform.class));
            }
            openPlatformClass = (Class<? extends OpenPlatform>) temp1;
        } catch (ClassNotFoundException e) {
            throw new OkAuthInitializeException(e);
        }
        openPlatforms = openPlatformClass.getEnumConstants();
        String identifier = (classAndIdentifier.length == 2) ? classAndIdentifier[1] : null;
        if (identifier != null) {
            return Arrays.stream(openPlatforms)
                .filter(op -> op.getIdentifier().equalsIgnoreCase(identifier))
                .findFirst().orElseThrow(() -> new OkAuthInitializeException(String.format(
                    "There is no identifier named '%s' in %s", identifier, openPlatformClass)));
        } else if (openPlatforms.length == 1) {
            return openPlatforms[0];
        } else {
            throw new OkAuthInitializeException(
                "Please give an identifier for " + openPlatformClass);
        }
    }

    /**
     * Initialize a requester.
     *
     * @param properties okauth requester properties, require nonnull
     * @return requester
     * @throws OkAuthInitializeException if the requester can not be initialized
     */
    private Requester initRequester(OkAuthRequesterProperties properties)
            throws OkAuthInitializeException {
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
