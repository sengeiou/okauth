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
package com.github.wautsns.okauth.core.client.builtin.tencentcloud;

import java.util.Map;

import com.github.wautsns.okauth.core.client.OpenPlatform;
import com.github.wautsns.okauth.core.client.builtin.OpenPlatforms;
import com.github.wautsns.okauth.core.client.builtin.StandardTokenAvailableOAuthClient;
import com.github.wautsns.okauth.core.client.kernel.http.OAuthRequestExecutor;
import com.github.wautsns.okauth.core.client.kernel.http.model.dto.OAuthRequest;
import com.github.wautsns.okauth.core.client.kernel.http.model.dto.OAuthResponse;
import com.github.wautsns.okauth.core.client.kernel.model.dto.OAuthToken;
import com.github.wautsns.okauth.core.client.kernel.model.properties.OAuthAppProperties;
import com.github.wautsns.okauth.core.exception.OAuthIOException;
import com.github.wautsns.okauth.core.exception.error.OAuthErrorException;

/**
 * TencentCloud oauth client.
 *
 * @since Mar 01, 2020
 * @author wautsns
 * @see <a href="https://dev.tencent.com/help/doc/faq/b4e5b7aee786/oauth">TencentCloud oauth doc</a>
 */
public class TencentCloudOAuthClient extends StandardTokenAvailableOAuthClient<TencentCloudUser> {

    /**
     * Construct TencentCloud oauth client.
     *
     * @param app oauth app properties, require nonnull
     * @param executor oauth request executor, require nonnull
     */
    public TencentCloudOAuthClient(OAuthAppProperties app, OAuthRequestExecutor executor) {
        super(app, executor);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return OpenPlatforms.TENCENTCLOUD;
    }

    @Override
    protected String getAuthorizeUrl() {
        return "https://dev.tencent.com/oauth_authorize.html";
    }

    @Override
    protected OAuthRequest initBasicTokenRequest() {
        return OAuthRequest.forGet("https://dev.tencent.com/api/oauth/access_token");
    }

    @Override
    public TencentCloudUser requestForUser(OAuthToken token)
            throws OAuthErrorException, OAuthIOException {
        String url = "https://dev.tencent.com/api/current_user";
        OAuthRequest request = OAuthRequest.forGet(url);
        request.getQuery()
            .addAccessToken(token.getAccessToken());
        return new TencentCloudUser(execute(request));
    }

    @Override
    protected String getErrorFromResponse(OAuthResponse response) {
        Integer code = (Integer) response.getData().get("code");
        if (code == 0) { return null; }
        @SuppressWarnings("unchecked")
        Map<String, String> msg = (Map<String, String>) response.getData().get("msg");
        return msg.entrySet().iterator().next().getKey();
    }

    @Override
    protected String getErrorDescriptionFromResponse(OAuthResponse response) {
        @SuppressWarnings("unchecked")
        Map<String, String> msg = (Map<String, String>) response.getData().get("msg");
        return msg.entrySet().iterator().next().getValue();
    }

    @Override
    protected boolean doesTheErrorMeanThatAccessTokenHasExpired(String error) {
        // FIXME refresh token
        // TencentCloud token has refresh token, but the api for refreshing token was not found in
        // the official doc.
        return "oauth_auth_expired".equals(error);
    }

}
