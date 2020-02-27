/**
 * Copyright 2020 the original author or authors.
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
package com.github.wautsns.okauth.core.client.builtin.wechat;

import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatform;
import com.github.wautsns.okauth.core.client.core.OkAuthClient;
import com.github.wautsns.okauth.core.client.core.OpenPlatform;
import com.github.wautsns.okauth.core.client.core.dto.OAuthRedirectUriQuery;
import com.github.wautsns.okauth.core.client.core.dto.OAuthToken;
import com.github.wautsns.okauth.core.client.core.dto.OAuthUser;
import com.github.wautsns.okauth.core.client.core.properties.OAuthAppInfo;
import com.github.wautsns.okauth.core.client.util.http.OkAuthRequest;
import com.github.wautsns.okauth.core.client.util.http.OkAuthRequester;
import com.github.wautsns.okauth.core.client.util.http.OkAuthResponse;
import com.github.wautsns.okauth.core.client.util.http.OkAuthUrl;
import com.github.wautsns.okauth.core.exception.OkAuthErrorException;
import com.github.wautsns.okauth.core.exception.OkAuthIOException;

/**
 * WeChat okauth client.
 *
 * @deprecated not tested
 * @since Feb 27, 2020
 * @author wautsns
 * @see <a
 *      href="https://developers.weixin.qq.com/doc/oplatform/Website_App/WeChat_Login/Wechat_Login.html">WeChat
 *      oauth doc</a>
 */
@Deprecated
public class WeChatOkAuthClient extends OkAuthClient {

    /**
     * Construct a WeChat okauth client.
     *
     * @param oauthAppInfo oauth application info, require nonnull
     * @param requester requester, require nonnull
     */
    public WeChatOkAuthClient(OAuthAppInfo oauthAppInfo, OkAuthRequester requester) {
        super(oauthAppInfo, requester);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatform.WECHAT;
    }

    @Override
    protected OkAuthUrl initAuthorizeUrlPrototype() {
        return new OkAuthUrl("https://open.weixin.qq.com/connect/qrconnect")
            .addQueryParam("appid", oauthAppInfo.getClientId())
            .addQueryParam("redirect_uri", oauthAppInfo.getRedirectUri())
            .addQueryParam("response_type", "code")
            .addQueryParam("scope", "snsapi_login");
    }

    @Override
    protected OkAuthRequest initTokenRequestPrototype() {
        return OkAuthRequest.forGet("https://api.weixin.qq.com/sns/oauth2/access_token")
            .addQueryParam("appid", oauthAppInfo.getClientId())
            .addQueryParam("secret", oauthAppInfo.getClientSecret())
            .addQueryParam("grant_type", "authorization_code");
    }

    @Override
    protected OkAuthRequest mutateTokenRequest(OAuthRedirectUriQuery query) {
        return tokenRequestPrototype.mutate()
            .addQueryParam("code", query.getCode());
    }

    @Override
    public OAuthToken requestToken(OAuthRedirectUriQuery query)
            throws OkAuthErrorException, OkAuthIOException {
        if (query.getCode() == null) {
            throw new OkAuthErrorException(getOpenPlatform(),
                "USER_REFUSES_AUTHORIZATION", "用户拒绝授权");
        }
        return new OAuthToken(requestAndCheck(mutateTokenRequest(query)));
    }

    @Override
    protected OkAuthRequest initUserRequestPrototype() {
        return OkAuthRequest.forGet("https://api.weixin.qq.com/sns/userinfo");
    }

    @Override
    protected OkAuthRequest mutateUserRequest(OAuthToken token) {
        return userRequestPrototype.mutate()
            .addQueryParam("access_token", token.getAccessToken())
            .addQueryParam("openid", token.getString("openid"));
    }

    @Override
    public OAuthUser requestUser(OAuthToken token)
            throws OkAuthErrorException, OkAuthIOException {
        return new WeChatUser(requestAndCheck(mutateUserRequest(token)));
    }

    /**
     * Exchange request and check response.
     *
     * @param request request, require nonnull
     * @return response
     * @throws OkAuthErrorException if an oauth exception occurs
     */
    private OkAuthResponse requestAndCheck(OkAuthRequest request)
            throws OkAuthErrorException, OkAuthIOException {
        return checkResponse(requester.exchange(request), "errcode", "errmsg");
    }

}
