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
package com.github.wautsns.okauth.core.client.builtin.oschina;

import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatform;
import com.github.wautsns.okauth.core.client.core.OpenPlatform;
import com.github.wautsns.okauth.core.client.core.StandardOkAuthClient;
import com.github.wautsns.okauth.core.client.core.dto.OAuthUser;
import com.github.wautsns.okauth.core.client.core.properties.OAuthAppInfo;
import com.github.wautsns.okauth.core.client.util.http.OkAuthRequest;
import com.github.wautsns.okauth.core.client.util.http.OkAuthRequester;
import com.github.wautsns.okauth.core.client.util.http.OkAuthResponse;

/**
 * OSChina okauth client.
 *
 * @since Feb 27, 2020
 * @author wautsns
 * @see <a href="https://www.oschina.net/openapi/docs">OSChina oauth doc</a>
 */
public class OSChinaOkAuthClient extends StandardOkAuthClient {

    /**
     * Construct an OSChina okauth client.
     *
     * @param oauthAppInfo oauth application info, require nonnull
     * @param requester requester, require nonnull
     */
    public OSChinaOkAuthClient(OAuthAppInfo oauthAppInfo, OkAuthRequester requester) {
        super(oauthAppInfo, requester);
        tokenRequestPrototype.addHeaderAcceptJson();
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatform.OSCHINA;
    }

    @Override
    protected String getAuthorizeUrl() {
        return "https://www.oschina.net/action/oauth2/authorize";
    }

    @Override
    protected String getTokenRequestUrl() {
        return "https://www.oschina.net/action/openapi/token";
    }

    @Override
    protected OkAuthRequest initUserRequestPrototype() {
        return OkAuthRequest.forGet("https://www.oschina.net/action/openapi/user");
    }

    @Override
    protected OAuthUser newOAuthUser(OkAuthResponse response) {
        return new OSChinaUser(response);
    }

}
