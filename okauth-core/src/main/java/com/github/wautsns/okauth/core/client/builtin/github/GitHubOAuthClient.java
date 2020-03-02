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
package com.github.wautsns.okauth.core.client.builtin.github;

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
 * GitHub client.
 *
 * @author wautsns
 * @see <a href="https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/">github
 * oauth doc</a>
 * @since Feb 29, 2020
 */
public class GitHubOAuthClient extends StandardTokenAvailableOAuthClient<GitHubUser> {

    /**
     * Construct GitHub oauth client.
     *
     * @param app oauth app properties, require nonnull
     * @param executor oauth request executor, require nonnull
     */
    public GitHubOAuthClient(OAuthAppProperties app, OAuthRequestExecutor executor) {
        super(app, executor);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return OpenPlatforms.GITHUB;
    }

    @Override
    protected String getAuthorizeUrl() {
        return "https://github.com/login/oauth/authorize";
    }

    @Override
    protected OAuthRequest initBasicTokenRequest() {
        String url = "https://github.com/login/oauth/access_token";
        OAuthRequest request = OAuthRequest.forGet(url);
        request.getHeaders().addAcceptWithValueJson();
        return request;
    }

    @Override
    public GitHubUser requestForUser(OAuthToken token)
        throws OAuthErrorException, OAuthIOException {
        String url = "https://api.github.com/user";
        OAuthRequest request = OAuthRequest.forGet(url);
        request.getHeaders()
            .addAuthorization("token", token.getAccessToken());
        return new GitHubUser(execute(request));
    }

    @Override
    protected boolean doesTheErrorMeanThatAccessTokenHasExpired(String error) {
        // FIXME not found in doc
        return false;
    }

}
