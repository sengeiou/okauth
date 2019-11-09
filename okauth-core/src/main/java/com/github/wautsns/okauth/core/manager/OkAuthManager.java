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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.github.wautsns.okauth.core.client.core.OkAuthClient;
import com.github.wautsns.okauth.core.client.core.OpenPlatform;
import com.github.wautsns.okauth.core.exception.UnsupportedOpenPlatformException;

/**
 * {@linkplain OkAuthClient okauth clients}' manager
 *
 * @author wautsns
 * @see OkAuthManagerBuilder
 */
public class OkAuthManager {

    /** registered clients */
    private Map<OpenPlatform, OkAuthClient> clients;
    /** cached clients for {@link #getClient(String)} */
    private Map<String, OkAuthClient> cache;

    /**
     * Construct an okauth manager.
     *
     * @param clients registered clients, require nonnull
     */
    OkAuthManager(Map<OpenPlatform, OkAuthClient> clients) {
        this.clients = new HashMap<>(clients);
        this.cache = new HashMap<>(this.clients.size());
    }

    /**
     * Get okauth client.
     *
     * @param openPlatform open platform, require nonnull
     * @return client assosiated with the param `openPlatform`
     * @throws UnsupportedOpenPlatformException if no client is assosiated with the param
     *         `openPlatform`
     */
    public OkAuthClient getClient(OpenPlatform openPlatform)
            throws UnsupportedOpenPlatformException {
        OkAuthClient client = clients.get(openPlatform);
        if (client != null) { return client; }
        throw new UnsupportedOpenPlatformException(openPlatform.getIdentifier());
    }

    /**
     * Get okauth client.
     *
     * @param caseInsensitiveIdentifier case insensitive identifier, require nonnull
     * @return client assosiated with the param `caseInsensitiveIdentifier`
     * @throws UnsupportedOpenPlatformException if no client is assosiated with the param
     *         `caseInsensitiveIdentifier`
     */
    public OkAuthClient getClient(String caseInsensitiveIdentifier)
            throws UnsupportedOpenPlatformException {
        OkAuthClient client = cache.get(caseInsensitiveIdentifier);
        if (client != null) { return client; }
        for (Entry<OpenPlatform, OkAuthClient> entry : clients.entrySet()) {
            if (entry.getKey().getIdentifier().equalsIgnoreCase(caseInsensitiveIdentifier)) {
                client = entry.getValue();
                synchronized (cache) {
                    cache.put(caseInsensitiveIdentifier, client);
                }
                return client;
            }
        }
        throw new UnsupportedOpenPlatformException(caseInsensitiveIdentifier);
    }

}
