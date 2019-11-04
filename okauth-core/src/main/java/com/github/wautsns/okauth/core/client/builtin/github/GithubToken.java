package com.github.wautsns.okauth.core.client.builtin.github;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.wautsns.okauth.core.client.dto.OAuthToken;
import com.github.wautsns.okauth.core.client.http.Response;

/**
 *
 * @author wautsns
 */
@JsonNaming(SnakeCaseStrategy.class)
public class GithubToken extends OAuthToken {

    public GithubToken(Response response) {
        super(response);
    }

    @Override
    public String getAccessToken() {
        return getString("access_token");
    }

}
