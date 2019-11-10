/**
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.wautsns.okauth.core.client.builtin.baidu;

import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatform;
import com.github.wautsns.okauth.core.client.core.OpenPlatform;
import com.github.wautsns.okauth.core.client.core.dto.OAuthToken;
import com.github.wautsns.okauth.core.client.core.dto.OAuthUser;
import com.github.wautsns.okauth.core.client.core.properties.OAuthAppInfo;
import com.github.wautsns.okauth.core.client.core.standard.oauth2.StandardOAuth2Client;
import com.github.wautsns.okauth.core.client.util.http.Request;
import com.github.wautsns.okauth.core.client.util.http.Requester;
import com.github.wautsns.okauth.core.client.util.http.Response;

/**
 * Baidu okauth client.
 *
 * @author wautsns
 * @see <a href="http://developer.baidu.com/wiki/index.php?title=docs/oauth">baidu oauth doc</a>
 */
public class BaiduOkAuthClient extends StandardOAuth2Client {

    /**
     * Construct a baidu okauth client.
     *
     * @param oauthAppInfo oauth application info, require nonnull
     * @param requester requester, require nonnull
     */
    public BaiduOkAuthClient(OAuthAppInfo oauthAppInfo, Requester requester) {
        super(oauthAppInfo, requester);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatform.BAIDU;
    }

    @Override
    protected String getAuthorizeUrl() {
        return "http://openapi.baidu.com/oauth/2.0/authorize";
    }

    @Override
    protected String getTokenUrl() {
        return "https://openapi.baidu.com/oauth/2.0/token";
    }

    @Override
    protected OAuthToken initOAuthToken(Response response) {
        return new BaiduToken(response);
    }

    @Override
    protected String getUserUrl() {
        return "http://openapi.baidu.com/rest/2.0/passport/users/getLoggedInUser";
    }

    @Override
    protected Request mutateUserRequest(OAuthToken token) {
        BaiduToken baiduToken = (BaiduToken) token;
        return super.mutateUserRequest(token)
            .addQueryParam("session_key", baiduToken.getSessionKey())
            .addQueryParam("session_secret", baiduToken.getSessionSecret());
    }

    @Override
    protected OAuthUser initOAuthUser(Response response) {
        return new BaiduUser(response);
    }

}
