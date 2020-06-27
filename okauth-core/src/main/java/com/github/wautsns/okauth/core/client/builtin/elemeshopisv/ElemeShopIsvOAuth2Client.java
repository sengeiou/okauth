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
package com.github.wautsns.okauth.core.client.builtin.elemeshopisv;

import com.github.wautsns.okauth.core.assist.http.builtin.httpclient4.HttpClient4OAuth2HttpClient;
import com.github.wautsns.okauth.core.assist.http.kernel.OAuth2HttpClient;
import com.github.wautsns.okauth.core.assist.http.kernel.model.OAuth2HttpRequest;
import com.github.wautsns.okauth.core.assist.http.kernel.model.OAuth2HttpResponse;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.OAuth2Url;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.entity.builtin.OAuth2HttpJsonEntity;
import com.github.wautsns.okauth.core.assist.http.kernel.util.WriteUtils;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatformNames;
import com.github.wautsns.okauth.core.client.builtin.elemeshopisv.model.ElemeShopIsvOAuth2Token;
import com.github.wautsns.okauth.core.client.builtin.elemeshopisv.model.ElemeShopIsvOAuth2User;
import com.github.wautsns.okauth.core.client.kernel.TokenRefreshableOAuth2Client;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeRedirectUriQueryForToken;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeTokenForOpenid;
import com.github.wautsns.okauth.core.client.kernel.api.ExchangeTokenForUser;
import com.github.wautsns.okauth.core.client.kernel.api.RefreshToken;
import com.github.wautsns.okauth.core.client.kernel.util.Encryptors;
import com.github.wautsns.okauth.core.exception.OAuth2ErrorException;
import com.github.wautsns.okauth.core.exception.OAuth2Exception;
import com.github.wautsns.okauth.core.exception.specific.token.InvalidAccessTokenException;
import com.github.wautsns.okauth.core.exception.specific.user.UserRefusedAuthorizationException;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ElemeShopIsv oauth2 client.
 *
 * @author wautsns
 * @see <a href="https://open.shop.ele.me/openapi/documents/isvoauth">ElemeShopIsvOAuth2 doc</a>
 * @since Jun 25, 2020
 */
public class ElemeShopIsvOAuth2Client
        extends
        TokenRefreshableOAuth2Client<ElemeShopIsvOAuth2AppInfo, ElemeShopIsvOAuth2Token, ElemeShopIsvOAuth2User> {

    /**
     * Construct a ElemeShopIsv oauth2 client.
     *
     * @param appInfo oauth2 app info
     */
    public ElemeShopIsvOAuth2Client(ElemeShopIsvOAuth2AppInfo appInfo) {
        this(appInfo, new HttpClient4OAuth2HttpClient(), TokenRefreshCallback.IGNORE);
    }

    /**
     * Construct a ElemeShopIsv oauth2 client.
     *
     * @param appInfo oauth2 app info
     * @param httpClient oauth2 http client
     * @param tokenRefreshCallback token refresh call back
     */
    public ElemeShopIsvOAuth2Client(
            ElemeShopIsvOAuth2AppInfo appInfo, OAuth2HttpClient httpClient,
            TokenRefreshCallback tokenRefreshCallback) {
        super(appInfo, httpClient, tokenRefreshCallback);
    }

    @Override
    public String getOpenPlatform() {
        return BuiltInOpenPlatformNames.ELEME_SHOP_ISV;
    }

    // #################### initialize api ##############################################

    @Override
    protected InitializeAuthorizeUrl initApiInitializeAuthorizeUrl() {
        String url = getHostOfCurrentEnv() + "/authorize";
        OAuth2Url basic = new OAuth2Url(url);
        basic.getQuery()
                .addClientId(appInfo.getKey())
                .addResponseTypeWithValueCode()
                .addRedirectUri(appInfo.getRedirectUri())
                .addScope("all");
        return state -> {
            OAuth2Url authorizeUrl = basic.copy();
            authorizeUrl.getQuery().addState(state);
            return authorizeUrl;
        };
    }

    @Override
    protected ExchangeRedirectUriQueryForToken<ElemeShopIsvOAuth2Token> initApiExchangeRedirectUriQueryForToken() {
        String url = getHostOfCurrentEnv() + "/token";
        OAuth2HttpRequest basic = OAuth2HttpRequest.initPost(url);
        basic.getHeaders().addAuthorizationBasic(appInfo.getKey(), appInfo.getSecret());
        basic.getEntityFormUrlEncoded()
                .addGrantTypeWithValueAuthorizationCode()
                .addRedirectUri(appInfo.getRedirectUri())
                .addClientId(appInfo.getKey());
        Integer refreshTokenExpiresIn = getRefreshTokenExpiresInOfCurrentEnv();
        return redirectUriQuery -> {
            String error = redirectUriQuery.getError();
            if (error != null) {
                String errorDescription = redirectUriQuery.getErrorDescription();
                throw new UserRefusedAuthorizationException(getOpenPlatform());
            } else {
                OAuth2HttpRequest request = basic.copy();
                request.getEntityFormUrlEncoded().addCode(redirectUriQuery.getCode());
                ElemeShopIsvOAuth2Token token = new ElemeShopIsvOAuth2Token(executeGetOrRefreshTokenAndCheck(request));
                token.getOriginalDataMap().put("refresh_token_expires_in", refreshTokenExpiresIn);
                return token;
            }
        };
    }

    @Override
    protected RefreshToken<ElemeShopIsvOAuth2Token> initApiRefreshToken() {
        String url = getHostOfCurrentEnv() + "/token";
        OAuth2HttpRequest basic = OAuth2HttpRequest.initPost(url);
        basic.getHeaders().addAuthorizationBasic(appInfo.getKey(), appInfo.getSecret());
        basic.getEntityFormUrlEncoded().addGrantTypeWithValueRefreshToken();
        Integer refreshTokenExpiresIn = getRefreshTokenExpiresInOfCurrentEnv();
        return token -> {
            OAuth2HttpRequest request = basic.copy();
            request.getEntityFormUrlEncoded().addRefreshToken(token.getRefreshToken());
            token = new ElemeShopIsvOAuth2Token(executeGetOrRefreshTokenAndCheck(request));
            token.getOriginalDataMap().put("refresh_token_expires_in", refreshTokenExpiresIn);
            return token;
        };
    }

    @Override
    protected ExchangeTokenForOpenid<ElemeShopIsvOAuth2Token> initApiExchangeTokenForOpenid() {
        return token -> exchangeForUser(token).getOpenid();
    }

    @Override
    protected ExchangeTokenForUser<ElemeShopIsvOAuth2Token, ElemeShopIsvOAuth2User> initApiExchangeTokenForUser() {
        String url = getHostOfCurrentEnv() + "/api/v1/";
        OAuth2HttpRequest basic = OAuth2HttpRequest.initPost(url);
        basic.getHeaders().addContentTypeWithValueJson();
        basic.getEntityJson()
                .putUnchangedValue("nop", "1.0.0")
                .putUnchangedValue("id", "useless")
                .putUnchangedValue("action", "eleme.user.getUser")
                .putUnchangedValue("params", (Serializable) Collections.emptyMap());
        return token -> {
            OAuth2HttpRequest request = basic.copy();
            DataMap metas = new DataMap(2, 1f)
                    .with("app_key", appInfo.getKey())
                    .with("timestamp", System.currentTimeMillis());
            request.getEntityJson()
                    .putUnchangedValue("token", token.getAccessToken())
                    .putUnchangedValue("metas", metas);
            sign(request.getEntityJson());
            return new ElemeShopIsvOAuth2User(executeNotGetOrRefreshTokenAndCheck(request));
        };
    }

    /**
     * Get host of the current environment.
     *
     * @return host of the current environment
     */
    protected String getHostOfCurrentEnv() {
        switch (appInfo.getEnv()) {
            case PRODUCTION:
                return "https://open-api.shop.ele.me";
            case SANDBOX:
                return "https://open-api-sandbox.shop.ele.me";
            default:
                throw new IllegalStateException("Unsupported env: " + appInfo.getEnv());
        }
    }

    /**
     * Sign.
     *
     * @param entity entity
     */
    protected void sign(OAuth2HttpJsonEntity entity) {
        String action = entity.getAs("action");
        String token = entity.getAs("token");
        Map<String, Serializable> metas = entity.getAs("metas");
        Map<String, Serializable> params = entity.getAs("params");
        // Do sign.
        Map<String, Serializable> metasAndParams;
        if (params.isEmpty()) {
            metasAndParams = metas;
        } else {
            metasAndParams = new HashMap<>(metas.size() + params.size(), 1);
            metasAndParams.putAll(metas);
            metasAndParams.putAll(params);
        }
        String metasAndParamsPart = metasAndParams.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + "=" + WriteUtils.writeObjectAsJsonString(e.getValue()))
                .collect(Collectors.joining());
        String signature = Encryptors.MD5.encrypt(action + token + metasAndParamsPart + appInfo.getSecret());
        entity.putUnchangedValue("signature", signature);
    }

    /**
     * Get refresh_token_expires_in of the current environment.
     *
     * @return refresh_token_expires_in
     */
    private Integer getRefreshTokenExpiresInOfCurrentEnv() {
        switch (appInfo.getEnv()) {
            case PRODUCTION:
                return 24 * 3600;
            case SANDBOX:
                return 30 * 24 * 3600;
            default:
                throw new IllegalStateException("Unsupported env: " + appInfo.getEnv());
        }
    }

    // #################### execute request and check response ##########################

    /**
     * Execute request that is GET_TOKEN or REFRESH_TOKEN, and check response.
     *
     * @param request request
     * @return correct data map
     * @throws OAuth2Exception if oauth2 failed
     */
    protected DataMap executeGetOrRefreshTokenAndCheck(OAuth2HttpRequest request) throws OAuth2Exception {
        OAuth2HttpResponse response = httpClient.execute(request);
        DataMap dataMap = response.readJsonAsDataMap();
        String error = dataMap.getAsString("error");
        String errorDescription = dataMap.getAsString("error_description");
        if (error == null) {
            return dataMap.getAsDataMap("result");
        } else if ("UNAUTHORIZED".equals(error)) {
            throw new InvalidAccessTokenException(getOpenPlatform(), error, errorDescription);
        } else {
            throw new OAuth2ErrorException(getOpenPlatform(), error, errorDescription);
        }
    }

    /**
     * Execute request that is neither GET_TOKEN nor REFRESH_TOKEN, and check response.
     *
     * @param request request
     * @return correct data map
     * @throws OAuth2Exception if oauth2 failed
     */
    protected DataMap executeNotGetOrRefreshTokenAndCheck(OAuth2HttpRequest request) throws OAuth2Exception {
        OAuth2HttpResponse response = httpClient.execute(request);
        DataMap dataMap = response.readJsonAsDataMap();
        DataMap errorDataMap = dataMap.getAsDataMap("error");
        if (errorDataMap == null) { return dataMap.getAsDataMap("result"); }
        String code = errorDataMap.getAsString("code");
        String message = errorDataMap.getAsString("message");
        if ("UNAUTHORIZED".equals(code)) {
            throw new InvalidAccessTokenException(getOpenPlatform(), code, message);
        } else {
            throw new OAuth2ErrorException(getOpenPlatform(), code, message);
        }
    }

}
