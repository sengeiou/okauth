package com.github.wautsns.okauth.core.client.core.dto;

import com.github.wautsns.okauth.core.client.util.http.Response;

/**
 *
 * @author wautsns
 */
public abstract class OAuthToken extends OAuthData {

    public OAuthToken(Response response) {
        super(response.getData());
    }

    public abstract String getAccessToken();

}
