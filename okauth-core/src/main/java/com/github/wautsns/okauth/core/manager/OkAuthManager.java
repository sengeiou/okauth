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

import java.util.Map;
import java.util.Map.Entry;

import com.github.wautsns.okauth.core.client.core.OkAuthClient;
import com.github.wautsns.okauth.core.client.core.OpenPlatform;
import com.github.wautsns.okauth.core.exception.OkAuthException;

/**
 *
 * @author wautsns
 */
public class OkAuthManager {

    private Map<OpenPlatform, OkAuthClient> clients;

    public OkAuthManager(Map<OpenPlatform, OkAuthClient> clients) {
        this.clients = clients;
    }

    public OkAuthClient getClient(String openPlatform) throws OkAuthException {
        for (Entry<OpenPlatform, OkAuthClient> entry : clients.entrySet()) {
            if (entry.getKey().getIdentifier().equalsIgnoreCase(openPlatform)) {
                return entry.getValue();
            }
        }
        return getClient((OpenPlatform) null);
    }

    public OkAuthClient getClient(OpenPlatform openPlatform) throws OkAuthException {
        OkAuthClient client = clients.get(openPlatform);
        if (client != null) { return client; }
        throw new OkAuthException(
            openPlatform,
            "UNREGISTERED_OPEN_PLATFORM",
            String.format("Client of %s is not registered.", openPlatform.getIdentifier()));
    }

}
