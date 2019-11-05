package com.github.wautsns.okauth.core.client.core.dto;

import com.github.wautsns.okauth.core.client.util.http.Response;

/**
 *
 * @author wautsns
 */
public abstract class OAuthUser extends OAuthData {

    public OAuthUser(Response response) {
        super(response.getData());
    }

    public abstract String getOpenId();

    public abstract String getNickname();

    public abstract String getAvatarUrl();

}
