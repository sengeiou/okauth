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
package com.github.wautsns.okauth.core.client;

import com.github.wautsns.okauth.core.client.kernel.OAuth2Client;
import com.github.wautsns.okauth.core.exception.specific.openplatform.UnsupportedOpenPlatformException;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * OAuth2 client manager.
 *
 * @author wautsns
 * @since May 19, 2020
 */
@RequiredArgsConstructor
public class OAuth2ClientManager {

    /** Registered clients. */
    private final Map<String, OAuth2Client<?, ?>> clients = new ConcurrentHashMap<>();

    /**
     * Get oauth2 client.
     *
     * @param name open platform name.
     * @param <C> type of oauth2 client.
     * @return oauth2 client.
     * @throws UnsupportedOpenPlatformException if there is no oauth2 client named the specified name.
     */
    @SuppressWarnings("unchecked")
    public <C extends OAuth2Client<?, ?>> C get(String name) throws UnsupportedOpenPlatformException {
        OAuth2Client<?, ?> client = clients.get(name);
        if (client != null) {
            return (C) client;
        } else {
            throw new UnsupportedOpenPlatformException(name);
        }
    }

    // #################### register ####################################################

    /**
     * Register oauth2 client.
     *
     * <ul>
     * <li>{@code client.getOpenPlatform()}</li>
     * <li>{@code client.getOpenPlatform().toLowerCase()}</li>
     * <li>{@code client.getOpenPlatform().toUpperCase()}</li>
     * </ul>
     *
     * @param client oauth2 client
     * @return self reference
     */
    public OAuth2ClientManager register(OAuth2Client<?, ?> client) {
        String openPlatform = client.getOpenPlatform();
        clients.put(openPlatform, client);
        clients.put(openPlatform.toLowerCase(), client);
        clients.put(openPlatform.toUpperCase(), client);
        return this;
    }

    /**
     * Register oauth2 client with specified name.
     *
     * @param name name
     * @param client oauth2 client
     * @return self reference
     */
    public OAuth2ClientManager register(String name, OAuth2Client<?, ?> client) {
        clients.put(name, client);
        return this;
    }

}
