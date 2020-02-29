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
package com.github.wautsns.okauth.core.client.builtin.gitee;

import com.github.wautsns.okauth.core.client.OpenPlatform;
import com.github.wautsns.okauth.core.client.builtin.OpenPlatforms;
import com.github.wautsns.okauth.core.client.builtin.StandardTokenAvailableOAuthClient;
import com.github.wautsns.okauth.core.client.kernel.http.OAuthRequestExecutor;
import com.github.wautsns.okauth.core.client.kernel.http.model.dto.OAuthRequest;
import com.github.wautsns.okauth.core.client.kernel.model.dto.OAuthToken;
import com.github.wautsns.okauth.core.client.kernel.model.properties.OAuthAppProperties;
import com.github.wautsns.okauth.core.exception.OAuthIOException;
import com.github.wautsns.okauth.core.exception.error.OAuthErrorException;

/**
 * Gitee oauth client.
 *
 * @since Feb 29, 2020
 * @author wautsns
 * @see <a href="https://gitee.com/api/v5/oauth_doc">gitee oauth doc</a>
 */
public class GiteeOAuthClient extends StandardTokenAvailableOAuthClient<GiteeUser> {

    /**
     * Construct a gitee oauth client.
     *
     * @param app oauth app properties, require nonnull
     * @param executor oauth request executor, require nonnull
     */
    public GiteeOAuthClient(OAuthAppProperties app, OAuthRequestExecutor executor) {
        super(app, executor);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return OpenPlatforms.GITEE;
    }

    @Override
    protected String getAuthorizeUrl() {
        return "https://gitee.com/oauth/authorize";
    }

    @Override
    protected OAuthRequest initBasicTokenRequest() {
        return OAuthRequest.forPost("https://gitee.com/oauth/token");
    }

    @Override
    public GiteeUser requestForUser(OAuthToken token) throws OAuthErrorException, OAuthIOException {
        String url = "https://gitee.com/api/v5/user";
        OAuthRequest request = OAuthRequest.forGet(url);
        request.getQuery().addAccessToken(token.getAccessToken());
        return new GiteeUser(execute(request));
    }

    @Override
    protected boolean doesTheErrorMeanThatAccessTokenHasExpired(String error) {
        // TODO access_token(20/2/29 22:31): fd63d93ffe8eb276a1df7193d4582f7f
        return false;
    }

}
