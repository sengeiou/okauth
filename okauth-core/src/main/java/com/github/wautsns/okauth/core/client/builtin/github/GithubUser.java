package com.github.wautsns.okauth.core.client.builtin.github;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.wautsns.okauth.core.client.dto.OAuthUser;
import com.github.wautsns.okauth.core.client.http.Response;

/**
 *
 * @author wautsns
 */
@JsonNaming(SnakeCaseStrategy.class)
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
