package com.github.wautsns.okauth.core.client.core.properties;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 *
 * @author wautsns
 */
public class OAuthAppInfo {

    private String clientId;
    private String clientSecret;
    private String redirectUri;

    public String getUrlEncodedRedirectUri() {
        try {
            return URLEncoder.encode(redirectUri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("unreachable");
        }
    }

    /** Get {@link #clientId}. */
    public String getClientId() {
        return clientId;
    }

    /** Set {@link #clientId}. */
    public OAuthAppInfo setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    /** Get {@link #clientSecret}. */
    public String getClientSecret() {
        return clientSecret;
    }

    /** Set {@link #clientSecret}. */
    public OAuthAppInfo setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    /** Get {@link #redirectUri}. */
    public String getRedirectUri() {
        return redirectUri;
    }

    /** Set {@link #redirectUri}. */
    public OAuthAppInfo setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }

}
