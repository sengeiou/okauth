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
package com.github.wautsns.okauth.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wautsns.okauth.core.client.builtin.github.GithubOAuthClient;
import com.github.wautsns.okauth.core.client.builtin.github.GithubUser;
import com.github.wautsns.okauth.core.client.kernel.http.builtin.okhttp.OkHttpOAuthRequestExecutor;
import com.github.wautsns.okauth.core.client.kernel.model.dto.OAuthRedirectUriQuery;
import com.github.wautsns.okauth.core.client.kernel.model.dto.OAuthToken;
import com.github.wautsns.okauth.core.client.kernel.model.properties.OAuthAppProperties;

import okhttp3.OkHttpClient;

/**
 *
 * @since Mar 01, 2020
 * @author wautsns
 */
public class MyTest {

    public static void main(String[] args) throws Exception {
        GithubOAuthClient client = init();
        System.out.println(client.initAuthorizeUrl(""));
        OAuthToken token = client.requestForToken(new OAuthRedirectUriQuery()
            .setCode("5b27d594362cb1e22465"));
        GithubUser user = client.requestForUser(token);
        System.out.println(new ObjectMapper().writeValueAsString(user.getOriginalDataMap()));
    }

    private static GithubOAuthClient init() {
        OAuthAppProperties app = new OAuthAppProperties()
            .setClientId("ae43815167e88e2bfdf7")
            .setClientSecret("d88861d46d4727451d7ba93ea0b8c908e8600f7c")
            .setRedirectUri("http://per1024.com/api/cmd/handle-authorization-callback/github");
        return new GithubOAuthClient(app, new OkHttpOAuthRequestExecutor(new OkHttpClient()));
    }

}
