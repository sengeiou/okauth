package com.github.wautsns.okauth.core.client.builtin.gitee;

import java.io.IOException;

import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatform;
import com.github.wautsns.okauth.core.client.core.OkAuthClient;
import com.github.wautsns.okauth.core.client.core.OpenPlatform;
import com.github.wautsns.okauth.core.client.core.dto.OAuthRedirectUriQuery;
import com.github.wautsns.okauth.core.client.core.dto.OAuthToken;
import com.github.wautsns.okauth.core.client.core.properties.OAuthAppInfo;
import com.github.wautsns.okauth.core.client.util.http.Request;
import com.github.wautsns.okauth.core.client.util.http.Requester;
import com.github.wautsns.okauth.core.exception.OkAuthException;

/**
 *
 * @author wautsns
 */
public class GiteeOkAuthClient extends OkAuthClient {

    private final String authorizeUrlPrefix;
    private final Request tokenRequest;
    private final Request userRequest;

    public GiteeOkAuthClient(
            OAuthAppInfo oAuthAppInfo, Requester requester) {
        super(oAuthAppInfo, requester);
        authorizeUrlPrefix = "https://gitee.com/oauth/authorize"
            + "?response_type=code"
            + "&client_id=" + oauthAppInfo.getClientId()
            + "&redirect_uri=" + urlEncode(oauthAppInfo.getRedirectUri())
            + "&state=";
        tokenRequest = requester
            .post("https://gitee.com/oauth/token")
            .addFormItem("grant_type", "authorization_code")
            .addFormItem("client_id", oauthAppInfo.getClientId())
            .addFormItem("client_secret", oauthAppInfo.getClientSecret())
            .addFormItem("redirect_uri", oauthAppInfo.getRedirectUri());
        userRequest = requester
            .get("https://gitee.com/api/v5/user");
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatform.GITHUB;
    }

    @Override
    public String initAuthorizeUrl(String state) {
        return authorizeUrlPrefix + state;
    }

    @Override
    public GiteeToken exchangeForToken(OAuthRedirectUriQuery redirectUriQuery)
            throws OkAuthException, IOException {
        return new GiteeToken(tokenRequest.mutate()
            .addFormItem("code", redirectUriQuery.getCode())
            .exchangeForJson()
            .check(getOpenPlatform(), "error", "error_description"));
    }

    @Override
    public GiteeUser exchangeForUser(OAuthToken token) throws OkAuthException, IOException {
        return new GiteeUser(userRequest.mutate()
            .addQuery("access_token", token.getAccessToken())
            .exchangeForJson()
            .check(getOpenPlatform(), "error", "error_description"));
    }

}
