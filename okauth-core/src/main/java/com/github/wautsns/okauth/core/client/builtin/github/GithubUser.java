package com.github.wautsns.okauth.core.client.builtin.github;

import com.github.wautsns.okauth.core.client.dto.OAuthUser;
import com.github.wautsns.okauth.core.client.http.Response;

/**
 *
 * @author wautsns
 */
public class GithubUser extends OAuthUser {

    public GithubUser(Response response) {
        super(response);
    }

    @Override
    public String getOpenId() {
        return getString("id");
    }

    @Override
    public String getNickName() {
        return getString("name");
    }

    @Override
    public String getAvatarUrl() {
        return getString("avatar_url");
    }

}
