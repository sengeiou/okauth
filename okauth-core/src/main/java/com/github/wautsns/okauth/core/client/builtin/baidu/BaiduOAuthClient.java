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
package com.github.wautsns.okauth.core.client.builtin.baidu;

import com.github.wautsns.okauth.core.client.OpenPlatform;
import com.github.wautsns.okauth.core.client.builtin.OpenPlatforms;
import com.github.wautsns.okauth.core.client.builtin.StandardTokenRefreshableOAuthClient;
import com.github.wautsns.okauth.core.client.kernel.http.OAuthRequestExecutor;
import com.github.wautsns.okauth.core.client.kernel.http.model.dto.OAuthRequest;
import com.github.wautsns.okauth.core.client.kernel.model.dto.OAuthToken;
import com.github.wautsns.okauth.core.client.kernel.model.properties.OAuthAppProperties;
import com.github.wautsns.okauth.core.exception.OAuthIOException;
import com.github.wautsns.okauth.core.exception.error.OAuthErrorException;

/**
 * Baidu oauth client.
 *
 * @since Feb 29, 2020
 * @author wautsns
 * @see <a href="http://developer.baidu.com/wiki/index.php?title=docs/oauth">baidu oauth doc</a>
 */
public class BaiduOAuthClient extends StandardTokenRefreshableOAuthClient<BaiduUser> {

    /**
     * Construct a baidu oauth client.
     *
     * @param app oauth app properties, require nonnull
     * @param executor oauth request executor, require nonnull
     */
    public BaiduOAuthClient(OAuthAppProperties app, OAuthRequestExecutor executor) {
        super(app, executor);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return OpenPlatforms.BAIDU;
    }

    @Override
    protected String getAuthorizeUrl() {
        return "http://openapi.baidu.com/oauth/2.0/authorize";
    }

    @Override
    protected OAuthRequest initBasicTokenRequest() {
        return OAuthRequest.forGet("https://openapi.baidu.com/oauth/2.0/token");
    }

    @Override
    public BaiduUser requestForUser(OAuthToken token) throws OAuthErrorException, OAuthIOException {
        String url = "https://openapi.baidu.com/rest/2.0/passport/users/getInfo";
        OAuthRequest request = OAuthRequest.forGet(url);
        request.getQuery().addAccessToken(token.getAccessToken());
        return new BaiduUser(execute(request));
    }

    @Override
    protected OAuthRequest initBasicRefreshTokenRequest() {
        return OAuthRequest.forGet("https://openapi.baidu.com/oauth/2.0/token");
    }

    @Override
    protected boolean doesTheErrorMeanThatAccessTokenHasExpired(String error) {
        return false;
    }

    @Override
    protected boolean doesTheErrorMeanThatRefreshTokenHasExpired(String error) {
        return "expired_token".equals(error);
    }

}
