package com.github.wautsns.okauth.core.client;

import java.io.IOException;

import com.github.wautsns.okauth.core.client.dto.OAuthRedirectUriQuery;
import com.github.wautsns.okauth.core.client.dto.OAuthToken;
import com.github.wautsns.okauth.core.client.dto.OAuthUser;
import com.github.wautsns.okauth.core.client.http.Requesters;
import com.github.wautsns.okauth.core.exception.OkAuthException;

/**
 *
 * @author wautsns
 */
public abstract class OkAuthClient {

    protected final OAuthAppInfo oauthAppInfo;
    protected final Requesters requesters;

    protected OkAuthClient(OAuthAppInfo oAuthAppInfo, Requesters requesters) {
        this.oauthAppInfo = oAuthAppInfo;
        this.requesters = requesters;
    }

    public String getOpenPlatform() {
        return oauthAppInfo.getOpenPlatform();
    }

    public abstract String initAuthorizeUrl(String state);

    public abstract OAuthToken exchangeForToken(OAuthRedirectUriQuery redirectUriQuery)
            throws OkAuthException, IOException;

    public abstract OAuthUser exchangeForUser(OAuthToken token) throws OkAuthException, IOException;

    public OAuthUser exchangeForUser(OAuthRedirectUriQuery redirectUriQuery)
            throws OkAuthException, IOException {
        return exchangeForUser(exchangeForToken(redirectUriQuery));
    }

}
