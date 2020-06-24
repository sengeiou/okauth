/*
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
package com.github.wautsns.okauth.core.client.builtin.tiktok;

import com.github.wautsns.okauth.core.assist.http.kernel.OAuth2HttpClient;
import com.github.wautsns.okauth.core.assist.http.kernel.model.OAuth2HttpRequest;
import com.github.wautsns.okauth.core.assist.http.kernel.model.OAuth2HttpResponse;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.OAuth2Url;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatformNames;
import com.github.wautsns.okauth.core.client.builtin.tiktok.model.TikTokOAuth2Token;
import com.github.wautsns.okauth.core.client.builtin.tiktok.model.TikTokOAuth2User;
import com.github.wautsns.okauth.core.client.kernel.TokenRefreshableOAuth2Client;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeRedirectUriQueryForToken;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeTokenForOpenid;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeTokenForUser;
import com.github.wautsns.okauth.core.client.kernel.api.RefreshToken;
import com.github.wautsns.okauth.core.exception.OAuth2ErrorException;
import com.github.wautsns.okauth.core.exception.OAuth2Exception;
import com.github.wautsns.okauth.core.exception.specific.token.ExpiredAccessTokenException;
import com.github.wautsns.okauth.core.exception.specific.token.ExpiredRefreshTokenException;
import com.github.wautsns.okauth.core.exception.specific.token.InvalidAccessTokenException;

/**
 * TikTok oauth2 client.
 *
 * @author wautsns
 * @see <a href="https://open.douyin.com/platform/doc/OpenAPI-oauth2">TikTok OAuth2 doc</a>
 * @since Jun 23, 2020
 */
public class TikTokOAuth2Client
        extends TokenRefreshableOAuth2Client<TikTokOAuth2AppInfo, TikTokOAuth2Token, TikTokOAuth2User> {

    /**
     * Construct a TikTok oauth2 client.
     *
     * @param appInfo oauth2 app info
     * @param httpClient oauth2 http client
     * @param tokenRefreshCallback token refresh call back
     */
    public TikTokOAuth2Client(
            TikTokOAuth2AppInfo appInfo, OAuth2HttpClient httpClient,
            TokenRefreshCallback tokenRefreshCallback) {
        super(appInfo, httpClient, tokenRefreshCallback);
    }

    @Override
    public String getOpenPlatform() {
        return BuiltInOpenPlatformNames.TIK_TOK;
    }

    // #################### initialize api ##############################################

    @Override
    protected InitializeAuthorizeUrl initApiInitializeAuthorizeUrl() {
        String url = "https://open.douyin.com/platform/oauth/connect/";
        OAuth2Url basic = new OAuth2Url(url);
        basic.getQuery()
                .add("clientKey", appInfo.getClientKey())
                .addResponseTypeWithValueCode()
                .addScope(TikTokOAuth2AppInfo.Scope.joinWith(appInfo.getScopes(), ","))
                .addRedirectUri(appInfo.getRedirectUri());
        return state -> {
            OAuth2Url authorizeUrl = basic.copy();
            authorizeUrl.getQuery().addState(state);
            return authorizeUrl;
        };
    }

    @Override
    protected ExchangeRedirectUriQueryForToken<TikTokOAuth2Token> initApiExchangeRedirectUriQueryForToken() {
        String url = "https://open.douyin.com/oauth/access_token/";
        OAuth2HttpRequest basic = OAuth2HttpRequest.initGet(url);
        basic.getUrl().getQuery()
                .add("clientKey", appInfo.getClientKey())
                .addClientSecret(appInfo.getClientSecret())
                .addGrantTypeWithValueAuthorizationCode();
        return redirectUriQuery -> {
            OAuth2HttpRequest request = basic.copy();
            request.getUrl().getQuery().addCode(redirectUriQuery.getCode());
            return new TikTokOAuth2Token(executeAndCheck(request));
        };
    }

    @Override
    protected RefreshToken<TikTokOAuth2Token> initApiRefreshToken() {
        String url = "https://open.douyin.com/oauth/refresh_token/";
        OAuth2HttpRequest basic = OAuth2HttpRequest.initGet(url);
        basic.getUrl().getQuery()
                .add("clientKey", appInfo.getClientKey())
                .addGrantTypeWithValueRefreshToken();
        return token -> {
            OAuth2HttpRequest request = basic.copy();
            request.getUrl().getQuery().addRefreshToken(token.getRefreshToken());
            return new TikTokOAuth2Token(executeAndCheck(request));
        };
    }

    @Override
    protected ExchangeTokenForOpenid<TikTokOAuth2Token> initApiExchangeTokenForOpenid() {
        return TikTokOAuth2Token::getOpenid;
    }

    @Override
    protected ExchangeTokenForUser<TikTokOAuth2Token, TikTokOAuth2User> initApiExchangeTokenForUser() {
        String url = "https://open.douyin.com/oauth/userinfo/";
        OAuth2HttpRequest basic = OAuth2HttpRequest.initGet(url);
        return token -> {
            OAuth2HttpRequest request = basic.copy();
            request.getUrl().getQuery()
                    .addAccessToken(token.getAccessToken())
                    .add("open_id", token.getOpenid());
            return new TikTokOAuth2User(executeAndCheck(request));
        };
    }

    // #################### execute request and check response ##########################

    /**
     * Execute request and check response.
     *
     * @param request request
     * @return correct data map
     * @throws OAuth2Exception if oauth2 failed
     */
    protected DataMap executeAndCheck(OAuth2HttpRequest request) throws OAuth2Exception {
        OAuth2HttpResponse response = httpClient.execute(request);
        DataMap dataMap = response.readJsonAsDataMap().getAsDataMap("data");
        String errcode = dataMap.getAsString("error_code");
        String errmsg = dataMap.getAsString("description");
        switch (errcode) {
            case "0":
                dataMap.remove("error_code");
                dataMap.remove("description");
                return dataMap;
            case "2190008":
                throw new ExpiredAccessTokenException(getOpenPlatform(), errcode, errmsg);
            case "2190002":
                throw new InvalidAccessTokenException(getOpenPlatform(), errcode, errmsg);
            case "10010":
                throw new ExpiredRefreshTokenException(getOpenPlatform(), errcode, errmsg);
            default:
                throw new OAuth2ErrorException(getOpenPlatform(), errcode, errmsg);
        }
    }

}
