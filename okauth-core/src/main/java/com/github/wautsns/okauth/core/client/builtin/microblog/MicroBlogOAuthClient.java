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
package com.github.wautsns.okauth.core.client.builtin.microblog;

import java.io.Serializable;

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
 * MicroBlog oauth client.
 *
 * @since Feb 29, 2020
 * @author wautsns
 * @see <a
 *      href="https://open.weibo.com/wiki/%E6%8E%88%E6%9D%83%E6%9C%BA%E5%88%B6%E8%AF%B4%E6%98%8E">weibo
 *      oauth doc</a>
 */
public class MicroBlogOAuthClient extends StandardTokenAvailableOAuthClient<MicroBlogUser> {

    /**
     * Construct MicroBlog oauth client.
     *
     * @param app oauth app properties, require nonnull
     * @param executor oauth request executor, require nonnull
     */
    public MicroBlogOAuthClient(OAuthAppProperties app, OAuthRequestExecutor executor) {
        super(app, executor);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return OpenPlatforms.MICROBLOG;
    }

    @Override
    protected String getAuthorizeUrl() {
        return "https://api.weibo.com/oauth2/authorize";
    }

    @Override
    protected OAuthRequest initBasicTokenRequest() {
        return OAuthRequest.forPost("https://api.weibo.com/oauth2/access_token");
    }

    @Override
    public MicroBlogUser requestForUser(OAuthToken token)
            throws OAuthErrorException, OAuthIOException {
        String url = "https://api.weibo.com/2/users/show.json";
        OAuthRequest request = OAuthRequest.forGet(url);
        request.getQuery()
            .add("uid", token.getString("uid"))
            .addAccessToken(token.getAccessToken());
        return new MicroBlogUser(execute(request));
    }

    @Override
    protected String getErrorFromResponse(OAuthResponse response) {
        Serializable error = response.getData().get("error_code");
        return (error == null) ? null : error.toString();
    }

    @Override
    protected String getErrorDescriptionFromResponse(OAuthResponse response) {
        return (String) response.getData().get("error");
    }

    @Override
    protected boolean doesTheErrorMeanThatAccessTokenHasExpired(String error) {
        // FIXME need check
        // In official doc, there are two codes mean that the access token has expired
        return "21315".equals(error) || "21327".equals(error);
    }

}
