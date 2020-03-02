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
package com.github.wautsns.okauth.core.client.builtin.qq;

import com.github.wautsns.okauth.core.client.OpenPlatform;
import com.github.wautsns.okauth.core.client.builtin.OpenPlatforms;
import com.github.wautsns.okauth.core.client.builtin.StandardTokenRefreshableOAuthClient;
import com.github.wautsns.okauth.core.client.kernel.http.OAuthRequestExecutor;
import com.github.wautsns.okauth.core.client.kernel.http.model.dto.OAuthRequest;
import com.github.wautsns.okauth.core.client.kernel.http.model.dto.OAuthRequest.Method;
import com.github.wautsns.okauth.core.client.kernel.http.model.dto.OAuthResponse;
import com.github.wautsns.okauth.core.client.kernel.http.util.OAuthResponseInputStreamReader;
import com.github.wautsns.okauth.core.client.kernel.model.dto.OAuthToken;
import com.github.wautsns.okauth.core.client.kernel.model.properties.OAuthAppProperties;
import com.github.wautsns.okauth.core.exception.OAuthIOException;
import com.github.wautsns.okauth.core.exception.error.OAuthErrorException;
import com.github.wautsns.okauth.core.util.Reader;

/**
 * QQ oauth client.
 *
 * @author wautsns
 * @see <a href="https://wiki.connect.qq.com/">QQ oauth doc</a>
 * @since Mar 01, 2020
 */
public class QQOAuthClient extends StandardTokenRefreshableOAuthClient<QQUser> {

    /**
     * basic openid request
     */
    private final OAuthRequest basicOpenidRequest;
    /**
     * basic user request.
     *
     * <p>Query items added are as follows:
     * <ul>
     * <li>oauth_consumer_key: {@code app.getClientId()}</li>
     * </ul>
     */
    private final OAuthRequest basicUserRequest;

    /**
     * Construct QQ oauth client.
     *
     * @param app oauth app properties, require nonnull
     * @param executor oauth request executor, require nonnull
     */
    public QQOAuthClient(OAuthAppProperties app, OAuthRequestExecutor executor) {
        super(app, executor);
        // basic openid request
        String openidRequestUrl = "https://graph.qq.com/oauth2.0/me";
        OAuthResponseInputStreamReader openidReader = inputStream -> {
            // eg. callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} )
            String tmp = Reader.readAsString(inputStream);
            int left = tmp.indexOf('{');
            int right = tmp.lastIndexOf('}');
            tmp = tmp.substring(left, right + 1);
            return Reader.readJsonAsMap(tmp);
        };
        basicOpenidRequest = OAuthRequest.init(Method.GET, openidRequestUrl, openidReader);
        // basic user request
        String userRequestUrl = "https://graph.qq.com/user/get_user_info";
        basicUserRequest = OAuthRequest.forGet(userRequestUrl);
        basicUserRequest.getQuery()
            .add("oauth_consumer_key", app.getClientId());
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return OpenPlatforms.QQ;
    }

    @Override
    protected String getAuthorizeUrl() {
        return "https://graph.qq.com/oauth2.0/authorize";
    }

    @Override
    protected OAuthRequest initBasicTokenRequest() {
        return OAuthRequest.forGet("https://graph.qq.com/oauth2.0/token");
    }

    @Override
    protected OAuthRequest initBasicRefreshTokenRequest() {
        return OAuthRequest.forGet("https://graph.qq.com/oauth2.0/token");
    }

    /**
     * Exchange token for openid.
     *
     * @param token {@inheritDoc}
     * @return {@inheritDoc}
     * @throws OAuthErrorException {@inheritDoc}
     * @throws OAuthIOException {@inheritDoc}
     */
    @Override
    public String requestForOpenid(OAuthToken token) throws OAuthErrorException, OAuthIOException {
        OAuthRequest request = basicOpenidRequest.copy();
        request.getQuery()
            .addAccessToken(token.getAccessToken());
        return (String) execute(request).getData().get("openid");
    }

    @Override
    public QQUser requestForUser(OAuthToken token) throws OAuthErrorException, OAuthIOException {
        String openid = requestForOpenid(token);
        OAuthRequest request = basicUserRequest.copy();
        request.getQuery()
            .addAccessToken(token.getAccessToken())
            .add("openid", openid);
        return new QQUser(openid, execute(request));
    }

    @Override
    protected String getErrorFromResponse(OAuthResponse response) {
        Integer ret = (Integer) response.getData().get("ret");
        return (ret == 0) ? null : ret.toString();
    }

    @Override
    protected String getErrorDescriptionFromResponse(OAuthResponse response) {
        return (String) response.getData().get("msg");
    }

    @Override
    protected boolean doesTheErrorMeanThatAccessTokenHasExpired(String error) {
        return "100014".equals(error);
    }

    @Override
    protected boolean doesTheErrorMeanThatRefreshTokenHasExpired(String error) {
        return "100037".equals(error);
    }

}
