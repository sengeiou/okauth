package com.github.wautsns.okauth.core.client;

/**
 *
 * @author wautsns
 */
public class OAuthAppInfo {

    private String openPlatform;
    private String clientId;
    private String clientSecret;
    private String redirectUri;

    /** Get {@link #openPlatform}. */
    public String getOpenPlatform() {
        return openPlatform;
    }

    /** Set {@link #openPlatform}. */
    public OAuthAppInfo setOpenPlatform(String openPlatform) {
        this.openPlatform = openPlatform;
        return this;
    }

    /** Get {@link #clientId}. */
    public String getClientId() {
        return clientId;
    }

    /** Set {@link #clientId}.
     * @return */
    public OAuthAppInfo setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    /** Get {@link #clientSecret}. */
    public String getClientSecret() {
        return clientSecret;
    }

    /** Set {@link #clientSecret}.
     * @return */
    public OAuthAppInfo setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    /** Get {@link #redirectUri}. */
    public String getRedirectUri() {
        return redirectUri;
    }

    /** Set {@link #redirectUri}.
     * @return */
    public OAuthAppInfo setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }

}
