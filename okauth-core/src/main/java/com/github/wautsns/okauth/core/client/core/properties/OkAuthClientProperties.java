package com.github.wautsns.okauth.core.client.core.properties;

import com.github.wautsns.okauth.core.client.util.http.properties.OkAuthHttpProperties;

/**
 *
 * @author wautsns
 */
public class OkAuthClientProperties {

    private String openPlatform;
    private OAuthAppInfo oauthAppInfo;
    private OkAuthHttpProperties http = new OkAuthHttpProperties();

    /** Get {@link #openPlatform}. */
    public String getOpenPlatform() {
        return openPlatform;
    }

    /** Set {@link #openPlatform}. */
    public OkAuthClientProperties setOpenPlatform(String openPlatform) {
        this.openPlatform = openPlatform;
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

    /** Get {@link #http}. */
    public OkAuthHttpProperties getHttp() {
        return http;
    }

    /** Set {@link #http}. */
    public OkAuthClientProperties setHttp(OkAuthHttpProperties http) {
        this.http = http;
        return this;
    }

}
