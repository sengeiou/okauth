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

import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatform;
import com.github.wautsns.okauth.core.client.core.OkAuthClient;
import com.github.wautsns.okauth.core.client.core.OpenPlatform;
import com.github.wautsns.okauth.core.client.core.properties.OAuthAppInfo;
import com.github.wautsns.okauth.core.client.core.properties.OkAuthClientProperties;
import com.github.wautsns.okauth.core.client.util.http.Requester;
import com.github.wautsns.okauth.core.client.util.http.properties.OkAuthHttpProperties;

/**
 *
 * @author wautsns
 */
public class OkAuthManagerBuilder {

    private Map<OpenPlatform, OkAuthClient> clients = new HashMap<>();

    public OkAuthManager build() {
        return new OkAuthManager(clients);
    }

    public OkAuthManagerBuilder register(
            OpenPlatform openPlatform, OAuthAppInfo oauthAppInfo, Requester requester) {
        OkAuthClient old = clients.put(
            openPlatform,
            openPlatform.constructOkAuthClient(oauthAppInfo, requester));
        if (old == null) { return this; }
        throw new RuntimeException(
            "There are two open platform with the same identifier(or duplicate registration): "
                + "old: " + old.getOpenPlatform()
                + "new: " + openPlatform);
    }

    public OkAuthManagerBuilder register(OkAuthClientProperties properties) {
        return register(
            parseOpenPlatform(properties.getOpenPlatform()),
            properties.getOauthAppInfo(),
            parseRequester(properties.getHttp()));
    }

    // ------------------------- BEGIN -------------------------
    // -------------------- private methods --------------------
    // ---------------------------------------------------------

    @SuppressWarnings("unchecked")
    private OpenPlatform parseOpenPlatform(String openPlatform) {
        OpenPlatform temp1 = findOpenPlatform(BuiltInOpenPlatform.values(), openPlatform);
        if (temp1 != null) { return temp1; }
        String[] temp2 = openPlatform.split(":", 2);
        String identifier = (temp2.length == 2) ? temp2[1] : null;
        Class<? extends OpenPlatform> openPlatformClass;
        try {
            Class<?> temp3 = Class.forName(temp2[0]);
            if (!OpenPlatform.class.isAssignableFrom(temp3)) {
                throw new RuntimeException(String.format(
                    "Open platform [%s] should be an enumeration that implements %s",
                    temp3, OpenPlatform.class));
            }
            openPlatformClass = (Class<? extends OpenPlatform>) temp3;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (!openPlatformClass.isEnum()) {
            throw new RuntimeException(String.format(
                "Open platform [%s] should be an enumeration.", openPlatformClass));
        }
        OpenPlatform[] openPlatforms = openPlatformClass.getEnumConstants();
        temp1 = findOpenPlatform(openPlatforms, identifier);
        if (temp1 != null) { return temp1; }
        if (identifier != null) {
            throw new RuntimeException(String.format(
                "There is no identifier named '%s' in %s",
                identifier, openPlatformClass));
        } else if (openPlatforms.length == 1) {
            return openPlatforms[0];
        } else {
            throw new RuntimeException("Please give an identifier for " + openPlatformClass);
        }
    }

    private <T extends OpenPlatform> OpenPlatform findOpenPlatform(
            T[] openPlatforms, String identifier) {
        if (identifier == null) { return null; }
        for (T openPlatform : openPlatforms) {
            if (openPlatform.getIdentifier().equalsIgnoreCase(identifier)) {
                return openPlatform;
            }
        }
        return null;
    }

    private Requester parseRequester(OkAuthHttpProperties properties) {
        Class<? extends Requester> requesterClass = properties.getRequesterClass();
        try {
            return requesterClass
                .getConstructor(OkAuthHttpProperties.class)
                .newInstance(properties);
        } catch (Exception e) {
            throw new RuntimeException(
                requesterClass
                    + " should contain a public constructor with arg type of"
                    + OkAuthHttpProperties.class);
        }
    }

}
