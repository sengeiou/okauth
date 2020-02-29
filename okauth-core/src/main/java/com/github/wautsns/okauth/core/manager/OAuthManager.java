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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.github.wautsns.okauth.core.client.OpenPlatform;
import com.github.wautsns.okauth.core.client.kernel.OAuthClient;
import com.github.wautsns.okauth.core.exception.UnsupportedOpenPlatformException;

/**
 * OAuth manager.
 *
 * @since Feb 29, 2020
 * @author wautsns
 * @see OAuthManagerBuilder
 */
public class OAuthManager {

    /** registered clients */
    private Map<OpenPlatform, OAuthClient<?>> clients;
    /** named clients */
    private Map<String, OAuthClient<?>> namedClients;

    /**
     * Get oauth client.
     *
     * @param openPlatform open platform, require nonnull
     * @return client assosiated with the openPlatform
     * @throws UnsupportedOpenPlatformException if no client is assosiated with the openPlatform
     */
    public <T extends OAuthClient<?>> T getClient(OpenPlatform openPlatform)
            throws UnsupportedOpenPlatformException {
        @SuppressWarnings("unchecked")
        T client = (T) clients.get(openPlatform);
        if (client != null) { return client; }
        throw new UnsupportedOpenPlatformException(openPlatform.name());
    }

    /**
     * Get oauth client.
     *
     * @param name open platform name(case insensitive), require nonnull
     * @return client assosiated with the open platform name
     * @throws UnsupportedOpenPlatformException if no client is assosiated with the openPlatform
     */
    @SuppressWarnings("unchecked")
    public <T extends OAuthClient<?>> T getClient(String name)
            throws UnsupportedOpenPlatformException {
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
     * Construct an oauth manager.
     *
     * @param clients registered clients, require nonnull
     */
    OAuthManager(Map<OpenPlatform, OAuthClient<?>> clients) {
        this.clients = new HashMap<>(clients);
        // GITHUB, GitHub, github....
        this.namedClients = new HashMap<>(clients.size() << 1);
        for (Entry<OpenPlatform, OAuthClient<?>> entry : clients.entrySet()) {
            namedClients.put(entry.getKey().name(), entry.getValue());
        }
    }

}
