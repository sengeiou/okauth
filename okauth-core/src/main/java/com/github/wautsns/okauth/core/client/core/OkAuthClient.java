package com.github.wautsns.okauth.core.client.core;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.github.wautsns.okauth.core.client.core.dto.OAuthRedirectUriQuery;
import com.github.wautsns.okauth.core.client.core.dto.OAuthToken;
import com.github.wautsns.okauth.core.client.core.dto.OAuthUser;
import com.github.wautsns.okauth.core.client.core.properties.OAuthAppInfo;
import com.github.wautsns.okauth.core.client.util.http.Requester;
import com.github.wautsns.okauth.core.exception.OkAuthException;
import com.github.wautsns.okauth.core.exception.OkAuthIOException;

/**
 *
 * @author wautsns
 */
public abstract class OkAuthClient {

    protected final OAuthAppInfo oauthAppInfo;
    protected final Requester requester;

    protected OkAuthClient(OAuthAppInfo oAuthAppInfo, Requester requester) {
        this.oauthAppInfo = oAuthAppInfo;
        this.requester = requester;
    }

    public abstract OpenPlatform getOpenPlatform();

    public abstract String initAuthorizeUrl(String state);

    public abstract OAuthToken exchangeForToken(OAuthRedirectUriQuery redirectUriQuery)
            throws OkAuthException, OkAuthIOException;

    public abstract OAuthUser exchangeForUser(OAuthToken token)
            throws OkAuthException, OkAuthIOException;

    public OAuthUser exchangeForUser(OAuthRedirectUriQuery redirectUriQuery)
            throws OkAuthException, OkAuthIOException {
        return exchangeForUser(exchangeForToken(redirectUriQuery));
    }

    protected static String urlEncode(String text) {
        try {
            return URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
