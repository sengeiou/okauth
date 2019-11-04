package com.github.wautsns.okauth.core.client.builtin.gitee;

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
public class GiteeOkAuthClient extends OkAuthClient {

    private final String authorizeUrlPrefix;
    private final Requester tokenRequester;
    private final Requester userRequester;

    public GiteeOkAuthClient(
            OAuthAppInfo oAuthAppInfo, Requesters requesters) {
        super(oAuthAppInfo, requesters);
        authorizeUrlPrefix = "https://gitee.com/oauth/authorize"
            + "?response_type=code"
            + "&client_id=" + oauthAppInfo.getClientId()
            + "&redirect_uri=" + oauthAppInfo.getUrlEncodedRedirectUri()
            + "&state=";
        tokenRequester = requesters
            .post("https://gitee.com/oauth/token")
            .addFormItem("grant_type", "authorization_code")
            .addFormItem("client_id", oauthAppInfo.getClientId())
            .addFormItem("client_secret", oauthAppInfo.getClientSecret())
            .addFormItem("redirect_uri", oauthAppInfo.getRedirectUri());
        userRequester = requesters
            .get("https://gitee.com/api/v5/user");
    }

    @Override
    public String initAuthorizeUrl(String state) {
        return authorizeUrlPrefix + state;
    }

    @Override
    public GiteeToken exchangeForToken(OAuthRedirectUriQuery redirectUriQuery)
            throws OkAuthException, IOException {
        return new GiteeToken(tokenRequester.mutate()
            .addFormItem("code", redirectUriQuery.getCode())
            .exchangeForJson()
            .check(getOpenPlatform(), "error", "error_description"));
    }

    @Override
    public GiteeUser exchangeForUser(OAuthToken token) throws OkAuthException, IOException {
        return new GiteeUser(userRequester.mutate()
            .addQuery("access_token", token.getAccessToken())
            .exchangeForJson()
            .check(getOpenPlatform(), "error", "error_description"));
    }

}
