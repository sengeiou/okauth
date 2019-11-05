package com.github.wautsns.okauth.core.manager;

import java.lang.reflect.Modifier;
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
            OpenPlatform openPlatform, OAuthAppInfo oAuthAppInfo, Requester requester) {
        clients.put(openPlatform, openPlatform.constructOkAuthClient(oAuthAppInfo, requester));
        return this;
    }

    public OkAuthManagerBuilder register(OkAuthClientProperties properties) {
        return register(
            parseOkAuthClient(properties.getOkAuthClient()),
            properties.getOauthAppInfo(),
            parseRequester(properties.getOkauthHttpProperties()));
    }

    // ------------------------- BEGIN -------------------------
    // -------------------- private methods --------------------
    // ---------------------------------------------------------

    @SuppressWarnings("unchecked")
    private OpenPlatform parseOkAuthClient(String okAuthClient) {
        OpenPlatform openPlatform = findOpenPlatform(BuiltInOpenPlatform.values(), okAuthClient);
        if (openPlatform != null) { return openPlatform; }
        String[] temp1 = okAuthClient.split(":", 2);
        String identifier = (temp1.length == 2) ? temp1[1] : null;
        Class<? extends OpenPlatform> openPlatformClass;
        try {
            Class<?> temp2 = Class.forName(temp1[0]);
            if (!OpenPlatform.class.isAssignableFrom(temp2)) {
                throw new RuntimeException(String.format(
                    "Open platform [%s] should implement %s",
                    temp2, OpenPlatform.class));
            }
            openPlatformClass = (Class<? extends OpenPlatform>) temp2;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (openPlatformClass.isEnum()) {
            openPlatform = findOpenPlatform(openPlatformClass.getEnumConstants(), identifier);
            if (openPlatform != null) { return openPlatform; }
            throw new RuntimeException((identifier == null)
                ? "Please give an identifier for " + openPlatformClass
                : String.format("There is no identifier named '%s' in %s",
                    identifier, openPlatformClass));
        } else if (!Modifier.isAbstract(openPlatformClass.getModifiers())) {
            try {
                return openPlatformClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(
                    openPlatformClass + " should contain a public no-arg constructor.");
            }
        }
        throw new RuntimeException("Illegal property value(ok-oauth-client): " + okAuthClient);
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
