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
package com.github.wautsns.okauth.core.manager;

import com.github.wautsns.okauth.core.client.OpenPlatform;
import com.github.wautsns.okauth.core.client.kernel.OAuthClient;
import com.github.wautsns.okauth.core.exception.UnsupportedOpenPlatformException;
import java.util.HashMap;
import java.util.Map;

/**
 * OAuth manager.
 *
 * @author wautsns
 * @see OAuthManagerBuilder
 * @since Feb 29, 2020
 */
public class OAuthManager {

    /** open platform -> registered clients map */
    private final Map<OpenPlatform, OAuthClient<?>> clients;
    /**
     * open platform name -> registered clients map
     *
     * <p>An open platform may have multiple names. eg. Github, GITHUB...</p>
     */
    private final Map<String, OAuthClient<?>> namedClients;

    /**
     * Get oauth client.
     *
     * @param openPlatform open platform, require nonnull
     * @return client associated with the open platform
     * @throws UnsupportedOpenPlatformException if no client is associated with the open platform
     */
    @SuppressWarnings("unchecked")
    public <T extends OAuthClient<?>> T getClient(OpenPlatform openPlatform) throws UnsupportedOpenPlatformException {
        T client = (T) clients.get(openPlatform);
        if (client != null) {
            return client;
        } else {
            throw new UnsupportedOpenPlatformException(openPlatform);
        }
    }

    /**
     * Get oauth client.
     *
     * @param name open platform name(case insensitive), require nonnull
     * @return client associated with the open platform name
     * @throws UnsupportedOpenPlatformException if no client is associated with the openPlatform
     */
    @SuppressWarnings("unchecked")
    public <T extends OAuthClient<?>> T getClient(String name) throws UnsupportedOpenPlatformException {
        T client = (T) namedClients.get(name);
        if (client != null) { return client; }
        client = (T) namedClients.get(name.toUpperCase());
        if (client == null) {
            throw new UnsupportedOpenPlatformException(name);
        } else {
            synchronized (namedClients) {
                namedClients.putIfAbsent(name, client);
            }
            return client;
        }
    }

    /**
     * Construct oauth manager.
     *
     * @param clients registered clients, require nonnull
     */
    OAuthManager(Map<OpenPlatform, OAuthClient<?>> clients) {
        this.clients = new HashMap<>(clients);
        this.namedClients = new HashMap<>(clients.size() << 1);
        clients.forEach((openPlatform, oauthClient) ->
            namedClients.put(openPlatform.name(), oauthClient));
    }

}
