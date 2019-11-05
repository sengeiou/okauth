package com.github.wautsns.okauth.core.client.builtin;

import java.util.function.BiFunction;

import com.github.wautsns.okauth.core.client.builtin.gitee.GiteeOkAuthClient;
import com.github.wautsns.okauth.core.client.builtin.github.GithubOkAuthClient;
import com.github.wautsns.okauth.core.client.core.OkAuthClient;
import com.github.wautsns.okauth.core.client.core.OpenPlatform;
import com.github.wautsns.okauth.core.client.core.properties.OAuthAppInfo;
import com.github.wautsns.okauth.core.client.util.http.Requester;

/**
 *
 * @author wautsns
 */
public enum BuiltInOpenPlatform implements OpenPlatform {

    GITEE("gitee", GiteeOkAuthClient::new),
    GITHUB("github", GithubOkAuthClient::new),
    ;

    private final String identifier;
    private final BiFunction<OAuthAppInfo, Requester, OkAuthClient> okAuthClientConstructor;

    private BuiltInOpenPlatform(
            String identifier, BiFunction<OAuthAppInfo, Requester, OkAuthClient> constructor) {
        this.identifier = identifier;
        this.okAuthClientConstructor = constructor;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public OkAuthClient constructOkAuthClient(OAuthAppInfo oauthAppInfo, Requester requester) {
        return okAuthClientConstructor.apply(oauthAppInfo, requester);
    }

}
