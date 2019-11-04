package com.github.wautsns.okauth.core.client.dto;

import com.github.wautsns.okauth.core.client.http.Response;

/**
 *
 * @author wautsns
 */
public abstract class OAuthUser extends OAuthData {

    public OAuthUser(Response response) {
        super(response.getData());
    }

    public abstract String getOpenId();

    public abstract String getNickName();

    public abstract String getAvatarUrl();

}
