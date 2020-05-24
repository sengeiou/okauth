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
package com.github.wautsns.okauth.core.client.builtin.wechat.work.corp;

import com.github.wautsns.okauth.core.assist.http.builtin.httpclient4.HttpClient4OAuth2HttpClient;
import com.github.wautsns.okauth.core.assist.http.kernel.OAuth2HttpClient;
import com.github.wautsns.okauth.core.assist.http.kernel.model.OAuth2HttpRequest;
import com.github.wautsns.okauth.core.assist.http.kernel.model.OAuth2HttpResponse;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.OAuth2Url;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatformNames;
import com.github.wautsns.okauth.core.client.builtin.wechat.work.corp.model.WeChatWorkCorpOAuth2Token;
import com.github.wautsns.okauth.core.client.builtin.wechat.work.corp.model.WeChatWorkCorpOAuth2User;
import com.github.wautsns.okauth.core.client.builtin.wechat.work.corp.service.WeChatWorkCorpTokenCache;
import com.github.wautsns.okauth.core.client.kernel.OAuth2Client;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeRedirectUriQueryForOpenid;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeRedirectUriQueryForUser;
import com.github.wautsns.okauth.core.client.kernel.api.InitializeAuthorizeUrl;
import com.github.wautsns.okauth.core.client.kernel.api.basic.BiFunctionApi;
import com.github.wautsns.okauth.core.client.kernel.api.basic.SupplierApi;
import com.github.wautsns.okauth.core.exception.OAuth2ErrorException;
import com.github.wautsns.okauth.core.exception.OAuth2Exception;
import com.github.wautsns.okauth.core.exception.specific.token.ExpiredAccessTokenException;
import com.github.wautsns.okauth.core.exception.specific.user.InvalidUserAuthorizationException;

/**
 * WeChatWorkCorp OAuth2 client.
 *
 * @author wautsns
 * @see <a href="https://work.weixin.qq.com/api/doc/90000/90135/91039">WeChatWorkCorp OAuth2 doc</a>
 * @since May 23, 2020
 */
public class WeChatWorkCorpOAuth2Client extends OAuth2Client<WeChatWorkCorpOAuth2AppInfo, WeChatWorkCorpOAuth2User> {

    /** Token service. */
    protected final WeChatWorkCorpTokenCache tokenCache;

    /** API: get token. */
    protected final SupplierApi<WeChatWorkCorpOAuth2Token> apiGetToken;
    /** API: exchange access token and userid for user. */
    protected final BiFunctionApi<String, String, WeChatWorkCorpOAuth2User> apiExchangeAccessTokenAndUseridForUser;

    /**
     * Construct WeChatWorkCorp OAuth2 client.
     *
     * @param appInfo oauth2 app info
     * @param tokenCache token service
     */
    public WeChatWorkCorpOAuth2Client(
            WeChatWorkCorpOAuth2AppInfo appInfo,
            WeChatWorkCorpTokenCache tokenCache) {
        this(appInfo, HttpClient4OAuth2HttpClient.DEFAULT, tokenCache);
    }

    /**
     * Construct WeChatWorkCorp OAuth2 client.
     *
     * @param appInfo oauth2 app info
     * @param httpClient oauth2 http client
     * @param tokenCache token service
     */
    public WeChatWorkCorpOAuth2Client(
            WeChatWorkCorpOAuth2AppInfo appInfo, OAuth2HttpClient httpClient,
            WeChatWorkCorpTokenCache tokenCache) {
        super(appInfo, httpClient);
        this.tokenCache = tokenCache;
        this.apiGetToken = initApiGetToken();
        this.apiExchangeAccessTokenAndUseridForUser = initApiExchangeAccessTokenAndUseridForUser();
    }

    @Override
    public String getOpenPlatform() {
        return BuiltInOpenPlatformNames.WECHAT_WORK_CORP;
    }

    /**
     * Get oauth2 token.
     *
     * @return oauth2 token
     * @throws OAuth2Exception if oauth2 failed
     */
    public WeChatWorkCorpOAuth2Token getToken() throws OAuth2Exception {
        DataMap originalDataMap = tokenCache.get();
        if (originalDataMap != null) {
            return new WeChatWorkCorpOAuth2Token(originalDataMap);
        } else {
            WeChatWorkCorpOAuth2Token token = apiGetToken.execute();
            tokenCache.save(token.getOriginalDataMap(), token.getAccessTokenExpirationSeconds());
            return token;
        }
    }

    /**
     * Exchange token and userid for user.
     *
     * @param accessToken access token
     * @param userid user id
     * @return user
     * @throws OAuth2Exception
     */
    public WeChatWorkCorpOAuth2User exchangeForUser(String accessToken, String userid)
            throws OAuth2Exception {
        return refreshIfAccessTokenExpired(apiExchangeAccessTokenAndUseridForUser, accessToken, userid);
    }

    /**
     * Auto-refresh access token if the api throws {@code ExpiredAccessTokenException}.
     *
     * @param accessTokenAndUseridRelatedApi token and userid related api
     * @param accessToken access token
     * @param userid userid
     * @param <R> type of result
     * @return result of the api
     * @throws OAuth2Exception if oauth2 failed
     */
    protected <R> R refreshIfAccessTokenExpired(
            BiFunctionApi<String, String, R> accessTokenAndUseridRelatedApi,
            String accessToken, String userid) throws OAuth2Exception {
        try {
            return accessTokenAndUseridRelatedApi.execute(accessToken, userid);
        } catch (ExpiredAccessTokenException e) {
            return accessTokenAndUseridRelatedApi.execute(getToken().getAccessToken(), userid);
        }
    }

    // #################### initialize api ##############################################

    @Override
    protected InitializeAuthorizeUrl initApiInitializeAuthorizeUrl() {
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize";
        OAuth2Url basic = new OAuth2Url(url);
        basic.getQuery()
                .addAppid(appInfo.getCorpId())
                .addRedirectUri(appInfo.getRedirectUri())
                .addResponseTypeWithValueCode()
                .addScope("snsapi_base");
        basic.setAnchor("wechat_redirect");
        return state -> {
            OAuth2Url oauth2Url = basic.copy();
            oauth2Url.getQuery().addState(state);
            return oauth2Url;
        };
    }

    /**
     * Initialize API: get token.
     *
     * @return API: get token
     */
    protected SupplierApi<WeChatWorkCorpOAuth2Token> initApiGetToken() {
        String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
        OAuth2HttpRequest request = OAuth2HttpRequest.initGet(url);
        request.getUrl().getQuery()
                .add("corpid", appInfo.getCorpId())
                .add("corpsecret", appInfo.getCorpSecret());
        return () -> new WeChatWorkCorpOAuth2Token(executeAndCheck(request));
    }

    /**
     * Initialize API: exchange token and userid for user.
     *
     * @return API: exchange token and userid for user
     */
    protected BiFunctionApi<String, String, WeChatWorkCorpOAuth2User> initApiExchangeAccessTokenAndUseridForUser() {
        return (token, userid) -> {
            String url = "https://qyapi.weixin.qq.com/cgi-bin/user/get";
            OAuth2HttpRequest request = OAuth2HttpRequest.initGet(url);
            request.getUrl().getQuery()
                    .addAccessToken(token)
                    .add("userid", userid);
            return new WeChatWorkCorpOAuth2User(executeAndCheck(request));
        };
    }

    @Override
    protected ExchangeRedirectUriQueryForOpenid initApiExchangeRedirectUriQueryForOpenid() {
        return redirectUriQuery -> {
            String url = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo";
            OAuth2HttpRequest request = OAuth2HttpRequest.initGet(url);
            request.getUrl().getQuery()
                    .addAccessToken(getToken().getAccessToken())
                    .addCode(redirectUriQuery.getCode());
            String userId = executeAndCheck(request).getAsString("UserId");
            if (userId != null) { return userId; }
            throw new InvalidUserAuthorizationException(getOpenPlatform());
        };
    }

    @Override
    protected ExchangeRedirectUriQueryForUser<WeChatWorkCorpOAuth2User> initApiExchangeRedirectUriQueryForUser() {
        return redirectUriQuery -> {
            String accessToken = getToken().getAccessToken();
            String userid = exchangeForOpenid(redirectUriQuery);
            return exchangeForUser(accessToken, userid);
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
    private DataMap executeAndCheck(OAuth2HttpRequest request) throws OAuth2Exception {
        OAuth2HttpResponse response = httpClient.execute(request);
        DataMap dataMap = response.readJsonAsDataMap();
        String errcode = dataMap.getAsString("errcode");
        String errmsg = (String) dataMap.remove("errmsg");
        dataMap.remove("errcode");
        if ("0".equals(errcode)) { return dataMap; }
        if ("42001".equals(errcode)) {
            throw new ExpiredAccessTokenException(getOpenPlatform(), errcode, errmsg);
        } else {
            throw new OAuth2ErrorException(getOpenPlatform(), errcode, errmsg);
        }
    }

}
