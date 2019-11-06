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

    public OkAuthClient getClient(String openPlatform) {
        for (Entry<OpenPlatform, OkAuthClient> entry : clients.entrySet()) {
            if (entry.getKey().getIdentifier().equalsIgnoreCase(openPlatform)) {
                return entry.getValue();
            }
        }
        return getClient((OpenPlatform) null);
    }

    public OkAuthClient getClient(OpenPlatform openPlatform) {
        OkAuthClient client = clients.get(openPlatform);
        if (client != null) { return client; }
        throw new OkAuthException(
            openPlatform,
            "UNREGISTERED_OPEN_PLATFORM",
            String.format("Client of %s is not registered.", openPlatform.getIdentifier()));
    }

}
