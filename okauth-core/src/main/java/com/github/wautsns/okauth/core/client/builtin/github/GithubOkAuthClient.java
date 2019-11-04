package com.github.wautsns.okauth.core.client.builtin.github;

import java.io.IOException;

import com.github.wautsns.okauth.core.client.OAuthAppInfo;
import com.github.wautsns.okauth.core.client.OkAuthClient;
import com.github.wautsns.okauth.core.client.dto.OAuthRedirectUriQuery;
import com.github.wautsns.okauth.core.client.dto.OAuthToken;
import com.github.wautsns.okauth.core.client.http.Requester;
import com.github.wautsns.okauth.core.client.http.Requesters;
import com.github.wautsns.okauth.core.exception.OkAuthException;

/**
 *
 * @author wautsns
 */
public class GithubOkAuthClient extends OkAuthClient {

    private final String authorizeUrlPrefix;
    private final Requester tokenRequester;
    private final Requester userRequester;

    public GithubOkAuthClient(
            OAuthAppInfo oAuthAppInfo, Requesters requesters) {
        super(oAuthAppInfo, requesters);
        authorizeUrlPrefix = "https://github.com/login/oauth/authorize"
            + "?client_id=" + oauthAppInfo.getClientId()
            + "&state=";
        tokenRequester = requesters
            .get("https://github.com/login/oauth/access_token")
            .acceptJson()
            .addQuery("client_id", oauthAppInfo.getClientId())
            .addQuery("client_secret", oauthAppInfo.getClientSecret());
        userRequester = requesters
            .get("https://api.github.com/user");
    }

    @Override
    public String initAuthorizeUrl(String state) {
        return authorizeUrlPrefix + state;
    }

    @Override
    public GithubToken exchangeForToken(OAuthRedirectUriQuery redirectUriQuery)
            throws OkAuthException, IOException {
        return new GithubToken(tokenRequester.mutate()
            .addQuery("code", redirectUriQuery.getCode())
            .exchangeForJson()
            .check(getOpenPlatform(), "error", "error_description"));
    }

    @Override
    public GithubUser exchangeForUser(OAuthToken token) throws OkAuthException, IOException {
        return new GithubUser(userRequester.mutate()
            .addQuery("access_token", token.getAccessToken())
            .exchangeForJson()
            .check(getOpenPlatform(), "error", "error_description"));
    }

}
