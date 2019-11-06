package com.github.wautsns.okauth.core.client.builtin.github;

import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatform;
import com.github.wautsns.okauth.core.client.core.OkAuthClient;
import com.github.wautsns.okauth.core.client.core.OpenPlatform;
import com.github.wautsns.okauth.core.client.core.dto.OAuthRedirectUriQuery;
import com.github.wautsns.okauth.core.client.core.dto.OAuthToken;
import com.github.wautsns.okauth.core.client.core.properties.OAuthAppInfo;
import com.github.wautsns.okauth.core.client.util.http.Request;
import com.github.wautsns.okauth.core.client.util.http.Requester;
import com.github.wautsns.okauth.core.exception.OkAuthException;
import com.github.wautsns.okauth.core.exception.OkAuthIOException;

/**
 *
 * @author wautsns
 */
public class GithubOkAuthClient extends OkAuthClient {

    private final String authorizeUrlPrefix;
    private final Request tokenRequest;
    private final Request userRequest;

    public GithubOkAuthClient(
            OAuthAppInfo oAuthAppInfo, Requester requester) {
        super(oAuthAppInfo, requester);
        authorizeUrlPrefix = "https://github.com/login/oauth/authorize"
            + "?client_id=" + oauthAppInfo.getClientId()
            + "&state=";
        tokenRequest = requester
            .get("https://github.com/login/oauth/access_token")
            .acceptJson()
            .addQuery("client_id", oauthAppInfo.getClientId())
            .addQuery("client_secret", oauthAppInfo.getClientSecret());
        userRequest = requester
            .get("https://api.github.com/user");
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatform.GITEE;
    }

    @Override
    public String initAuthorizeUrl(String state) {
        return authorizeUrlPrefix + state;
    }

    @Override
    public GithubToken exchangeForToken(OAuthRedirectUriQuery redirectUriQuery)
            throws OkAuthException, OkAuthIOException {
        return new GithubToken(tokenRequest.mutate()
            .addQuery("code", redirectUriQuery.getCode())
            .exchangeForJson()
            .check(getOpenPlatform(), "error", "error_description"));
    }

    @Override
    public GithubUser exchangeForUser(OAuthToken token) throws OkAuthException, OkAuthIOException {
        return new GithubUser(userRequest.mutate()
            .addQuery("access_token", token.getAccessToken())
            .exchangeForJson()
            .check(getOpenPlatform(), "error", "error_description"));
    }

}
