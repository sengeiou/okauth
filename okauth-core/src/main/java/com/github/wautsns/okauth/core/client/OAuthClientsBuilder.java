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

import com.github.wautsns.okauth.core.OpenPlatform;
import com.github.wautsns.okauth.core.client.kernel.OAuthClient;

import java.util.HashMap;
import java.util.Map;

/**
 * OAuth clients builder.
 *
 * @author wautsns
 * @since Mar 04, 2020
 */
public class OAuthClientsBuilder {

    /** registered clients */
    private final Map<OpenPlatform, OAuthClient<?>> clients = new HashMap<>();

    /**
     * Build oauth clients based on current configuration.
     *
     * @return oauth clients
     */
    public OAuthClients build() {
        return new OAuthClients(new HashMap<>(clients));
    }

    /**
     * Register oauth client.
     *
     * <p>If the client is {@code null}, it will not be registered.
     *
     * @param client oauth client
     * @return self reference
     */
    public OAuthClientsBuilder register(OAuthClient<?> client) {
        if (client != null) { clients.put(client.getOpenPlatform(), client); }
        return this;
    }

}
