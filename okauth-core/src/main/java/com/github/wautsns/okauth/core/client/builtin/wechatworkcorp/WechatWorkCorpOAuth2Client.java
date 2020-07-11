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
package com.github.wautsns.okauth.core.client.builtin.wechatworkcorp;

import com.github.wautsns.okauth.core.assist.http.builtin.httpclient4.HttpClient4OAuth2HttpClient;
import com.github.wautsns.okauth.core.assist.http.kernel.OAuth2HttpClient;
import com.github.wautsns.okauth.core.assist.http.kernel.model.OAuth2HttpRequest;
import com.github.wautsns.okauth.core.assist.http.kernel.model.OAuth2HttpResponse;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.OAuth2Url;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatforms;
import com.github.wautsns.okauth.core.client.builtin.wechatworkcorp.model.WechatWorkCorpOAuth2Token;
import com.github.wautsns.okauth.core.client.builtin.wechatworkcorp.model.WechatWorkCorpOAuth2User;
import com.github.wautsns.okauth.core.client.builtin.wechatworkcorp.service.tokencache.WechatWorkCorpTokenCache;
import com.github.wautsns.okauth.core.client.builtin.wechatworkcorp.service.tokencache.builtin.WechatWorkCorpTokenLocalCache;
import com.github.wautsns.okauth.core.client.kernel.OAuth2Client;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeRedirectUriQueryForOpenid;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeRedirectUriQueryForUser;
import com.github.wautsns.okauth.core.client.kernel.api.basic.OAuth2FunctionApi;
import com.github.wautsns.okauth.core.client.kernel.api.basic.OAuth2SupplierApi;
import com.github.wautsns.okauth.core.client.kernel.openplatform.OpenPlatform;
import com.github.wautsns.okauth.core.exception.OAuth2ErrorException;
import com.github.wautsns.okauth.core.exception.OAuth2Exception;
import com.github.wautsns.okauth.core.exception.specific.token.ExpiredAccessTokenException;
import com.github.wautsns.okauth.core.exception.specific.token.InvalidAccessTokenException;
import com.github.wautsns.okauth.core.exception.specific.user.InvalidUserAuthorizationException;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

/**
 * WechatWorkCorp oauth2 client.
 *
 * @author wautsns
 * @see <a href="https://work.weixin.qq.com/api/doc/90000/90135/91022">WechatWorkCorp OAuth2 doc</a>
 * @since May 23, 2020
 */
public class WechatWorkCorpOAuth2Client extends OAuth2Client<WechatWorkCorpOAuth2AppInfo, WechatWorkCorpOAuth2User> {

    /** Token service. */
    protected final WechatWorkCorpTokenCache tokenCache;

    /** API: get token. */
    protected final OAuth2SupplierApi<WechatWorkCorpOAuth2Token> apiGetToken;
    /** API: exchange userid for user. */
    protected final OAuth2FunctionApi<String, WechatWorkCorpOAuth2User> apiExchangeUseridForUser;

    /**
     * Construct a WechatWorkCorp oauth2 client.
     *
     * @param appInfo oauth2 app info
     */
    public WechatWorkCorpOAuth2Client(WechatWorkCorpOAuth2AppInfo appInfo) {
        this(appInfo, new HttpClient4OAuth2HttpClient(), WechatWorkCorpTokenLocalCache.INSTANCE);
    }

    /**
     * Construct a WechatWorkCorp oauth2 client.
     *
     * @param appInfo oauth2 app info
     * @param httpClient oauth2 http client
     * @param tokenCache token service
     */
    public WechatWorkCorpOAuth2Client(
            WechatWorkCorpOAuth2AppInfo appInfo, OAuth2HttpClient httpClient,
            WechatWorkCorpTokenCache tokenCache) {
        super(appInfo, httpClient);
        this.tokenCache = tokenCache;
        this.apiGetToken = initApiGetToken();
        this.apiExchangeUseridForUser = initApiExchangeUseridForUser();
        this.tokenCache.injectApiGetToken(this.apiGetToken);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatforms.WECHAT_WORK_CORP;
    }

    /** Semaphore for getting token. */
    private final Semaphore semaphoreForGettingToken = new Semaphore(1);

    /**
     * Get oauth2 token.
     *
     * <p><strong>If the cached token has expired, in the case of concurrency, only one request will actually perform
     * the fetch operation.</strong>
     *
     * @return oauth2 token
     * @throws OAuth2Exception if oauth2 failed
     */
    public WechatWorkCorpOAuth2Token getToken() throws OAuth2Exception {
        DataMap originalDataMap = tokenCache.get();
        if (originalDataMap != null) {
            return new WechatWorkCorpOAuth2Token(originalDataMap);
        } else if (semaphoreForGettingToken.tryAcquire()) {
            try {
                WechatWorkCorpOAuth2Token token = apiGetToken.execute();
                tokenCache.save(token.getOriginalDataMap(), token.getAccessTokenExpirationSeconds());
                return token;
            } finally {
                semaphoreForGettingToken.release();
            }
        } else {
            // Not directly recursive is to prevent stack overflow.
            do {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                }
                originalDataMap = tokenCache.get();
                if (originalDataMap != null) { return new WechatWorkCorpOAuth2Token(originalDataMap); }
            } while (semaphoreForGettingToken.availablePermits() == 0);
            // Do not use `tokenCache.get()` directly, because the semaphore may be released due to an exception.
            return getToken();
        }
    }

    /**
     * Exchange token and userid for user.
     *
     * @param userid user id
     * @return user
     * @throws OAuth2Exception if oauth2 failed
     */
    public WechatWorkCorpOAuth2User exchangeForUser(String userid) throws OAuth2Exception {
        return refreshIfAccessTokenExpired(apiExchangeUseridForUser, userid);
    }

    /**
     * Auto-refresh access token if the api throws {@code ExpiredAccessTokenException}.
     *
     * @param useridRelatedApi userid related api
     * @param userid userid
     * @param <R> type of result
     * @return result of the api
     * @throws OAuth2Exception if oauth2 failed
     */
    protected <R> R refreshIfAccessTokenExpired(OAuth2FunctionApi<String, R> useridRelatedApi, String userid)
            throws OAuth2Exception {
        try {
            return useridRelatedApi.execute(userid);
        } catch (ExpiredAccessTokenException e) {
            tokenCache.delete();
            return useridRelatedApi.execute(userid);
        }
    }

    // #################### initialize api ##############################################

    @Override
    protected InitializeAuthorizeUrl initApiInitializeAuthorizeUrl() {
        OAuth2Url basic = initBasicAuthorizeUrl(appInfo.getAuthorizeType());
        return state -> {
            OAuth2Url authorizeUrl = basic.copy();
            authorizeUrl.getQuery().addState(state);
            return authorizeUrl;
        };
    }

    /**
     * Initialize basic authorize url.
     *
     * @return basic authorize url
     */
    private OAuth2Url initBasicAuthorizeUrl(WechatWorkCorpOAuth2AppInfo.AuthorizeType authorizeType) {
        switch (authorizeType) {
            case WEB:
                return initBasicAuthorizeUrlForWeb();
            case QR_CODE:
                return initBasicAuthorizeUrlForQRCode();
            default:
                throw new IllegalStateException("Unsupported authorizeType: " + authorizeType);
        }
    }

    /**
     * Initialize basic authorize url for {@linkplain WechatWorkCorpOAuth2AppInfo.AuthorizeType#WEB WEB}
     *
     * @return basic authorize url
     */
    private OAuth2Url initBasicAuthorizeUrlForWeb() {
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize";
        OAuth2Url basic = new OAuth2Url(url);
        basic.getQuery()
                .addAppid(appInfo.getCorpId())
                .addRedirectUri(appInfo.getRedirectUri())
                .addResponseTypeWithValueCode()
                .addScope("snsapi_base");
        basic.setAnchor("wechat_redirect");
        return basic;
    }

    /**
     * Initialize basic authorize url for {@linkplain WechatWorkCorpOAuth2AppInfo.AuthorizeType#QR_CODE QR_CODE}
     *
     * @return basic authorize url
     */
    private OAuth2Url initBasicAuthorizeUrlForQRCode() {
        String url = "https://open.work.weixin.qq.com/wwopen/sso/qrConnect";
        OAuth2Url basic = new OAuth2Url(url);
        basic.getQuery()
                .addAppid(appInfo.getCorpId())
                .add("agentid", appInfo.getAgentId())
                .addRedirectUri(appInfo.getRedirectUri());
        return basic;
    }

    /**
     * Initialize API: get token.
     *
     * @return API: get token
     */
    protected OAuth2SupplierApi<WechatWorkCorpOAuth2Token> initApiGetToken() {
        String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
        OAuth2HttpRequest request = OAuth2HttpRequest.initGet(url);
        request.getUrl().getQuery()
                .add("corpid", appInfo.getCorpId())
                .add("corpsecret", appInfo.getCorpSecret());
        return () -> new WechatWorkCorpOAuth2Token(executeAndCheck(request));
    }

    /**
     * Initialize API: exchange token and userid for user.
     *
     * @return API: exchange token and userid for user
     */
    protected OAuth2FunctionApi<String, WechatWorkCorpOAuth2User> initApiExchangeUseridForUser() {
        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/get";
        OAuth2HttpRequest basic = OAuth2HttpRequest.initGet(url);
        return userid -> {
            OAuth2HttpRequest request = basic.copy();
            request.getUrl().getQuery()
                    .addAccessToken(getToken().getAccessToken())
                    .add("userid", userid);
            return new WechatWorkCorpOAuth2User(executeAndCheck(request));
        };
    }

    @Override
    protected ExchangeRedirectUriQueryForOpenid initApiExchangeRedirectUriQueryForOpenid() {
        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo";
        OAuth2HttpRequest basic = OAuth2HttpRequest.initGet(url);
        return redirectUriQuery -> {
            OAuth2HttpRequest request = basic.copy();
            request.getUrl().getQuery()
                    .addAccessToken(getToken().getAccessToken())
                    .addCode(redirectUriQuery.getCode());
            String userId = executeAndCheck(request).getAsString("UserId");
            if (userId != null) { return userId; }
            throw new InvalidUserAuthorizationException(getOpenPlatform());
        };
    }

    @Override
    protected ExchangeRedirectUriQueryForUser<WechatWorkCorpOAuth2User> initApiExchangeRedirectUriQueryForUser() {
        return redirectUriQuery -> exchangeForUser(exchangeForOpenid(redirectUriQuery));
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
        DataMap dataMap = response.readJsonAsDataMap();
        String errcode = dataMap.getAsString("errcode");
        String errmsg = dataMap.getAsString("errmsg");
        switch (errcode) {
            case "0":
                dataMap.remove("errcode");
                dataMap.remove("errmsg");
                return dataMap;
            case "42001":
                throw new ExpiredAccessTokenException(getOpenPlatform(), errcode, errmsg);
            case "40014":
            case "41001":
                throw new InvalidAccessTokenException(getOpenPlatform(), errcode, errmsg);
            default:
                throw new OAuth2ErrorException(getOpenPlatform(), errcode, errmsg);
        }
    }

}
