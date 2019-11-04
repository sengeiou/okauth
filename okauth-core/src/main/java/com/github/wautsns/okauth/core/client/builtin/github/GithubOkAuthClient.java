package com.github.wautsns.okauth.core.client.builtin.github;

import java.io.IOException;

import com.github.wautsns.okauth.core.client.OAuthAppInfo;
import com.github.wautsns.okauth.core.client.OkAuthClient;
import com.github.wautsns.okauth.core.client.dto.OAuthRedirectUriQuery;
import com.github.wautsns.okauth.core.client.dto.OAuthToken;
import com.github.wautsns.okauth.core.client.http.Requesters;
import com.github.wautsns.okauth.core.exception.OkAuthException;

/**
 *
 * @author wautsns
 */
public class GithubOkAuthClient extends OkAuthClient {

    private final String authorizeUrlPrefix;
    private final String tokenUrlPrefix;
    private final String userUrlPrefix;

    public GithubOkAuthClient(
            OAuthAppInfo oAuthAppInfo, Requesters requesters) {
        super(oAuthAppInfo, requesters);
        authorizeUrlPrefix = "https://github.com/login/oauth/authorize"
            + "?client_id=" + oauthAppInfo.getClientId()
            + "&state=";
        tokenUrlPrefix = "https://github.com/login/oauth/access_token"
            + "?client_id=" + oauthAppInfo.getClientId()
            + "&client_secret=" + oauthAppInfo.getClientSecret()
            + "&code=";
        userUrlPrefix = "https://api.github.com/user"
            + "?access_token=";
    }

    @Override
    public String initAuthorizeUrl(String state) {
        return authorizeUrlPrefix + state;
    }

    @Override
    public GithubToken exchangeForToken(OAuthRedirectUriQuery redirectUriQuery)
            throws OkAuthException, IOException {
        return new GithubToken(requesters
            .get(tokenUrlPrefix + redirectUriQuery.getCode())
            .acceptJson()
            .exchangeForJson()
            .check(getOpenPlatform(), "error", "error_description"));
    }

    @Override
    public GithubUser exchangeForUser(OAuthToken token) throws OkAuthException, IOException {
        return new GithubUser(requesters
            .get(userUrlPrefix + token.getAccessToken())
            .exchangeForJson()
            .check(getOpenPlatform(), "error", "error_description"));
    }

}
