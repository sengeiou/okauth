package com.github.wautsns.okauth.core.client.core.properties;

import com.github.wautsns.okauth.core.client.util.http.properties.OkAuthHttpProperties;

/**
 *
 * @author wautsns
 */
public class OkAuthClientProperties {

    private String okAuthClient;
    private OAuthAppInfo oauthAppInfo;
    private OkAuthHttpProperties okauthHttpProperties = new OkAuthHttpProperties();

    /** Get {@link #okAuthClient}. */
    public String getOkAuthClient() {
        return okAuthClient;
    }

    /** Set {@link #okAuthClient}. */
    public OkAuthClientProperties setOkAuthClient(String okAuthClient) {
        this.okAuthClient = okAuthClient;
        return this;
    }

    /** Get {@link #oauthAppInfo}. */
    public OAuthAppInfo getOauthAppInfo() {
        return oauthAppInfo;
    }

    /** Set {@link #oauthAppInfo}. */
    public OkAuthClientProperties setOauthAppInfo(OAuthAppInfo oauthAppInfo) {
        this.oauthAppInfo = oauthAppInfo;
        return this;
    }

    /** Get {@link #okauthHttpProperties}. */
    public OkAuthHttpProperties getOkauthHttpProperties() {
        return okauthHttpProperties;
    }

    /** Set {@link #okauthHttpProperties}. */
    public OkAuthClientProperties setOkauthHttpProperties(
            OkAuthHttpProperties okauthHttpProperties) {
        this.okauthHttpProperties = okauthHttpProperties;
        return this;
    }

}
