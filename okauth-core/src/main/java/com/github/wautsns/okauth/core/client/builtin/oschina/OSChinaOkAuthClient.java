/**
 * Copyright 2019 the original author or authors.
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
import com.github.wautsns.okauth.core.client.util.http.Request;
import com.github.wautsns.okauth.core.client.util.http.Requester;
import com.github.wautsns.okauth.core.client.util.http.Response;

/**
 * OSChina okauth client.
 *
 * @since Feb 19, 2020
 * @author wautsns
 * @see <a href="https://www.oschina.net/openapi/docs">OSChina oauth doc</a>
 */
public class OSChinaOkAuthClient extends StandardOkAuthClient {

    /**
     * Construct a OSChina okauth client.
     *
     * @param oauthAppInfo oauth application info, require nonnull
     * @param requester requester, require nonnull
     */
    public OSChinaOkAuthClient(OAuthAppInfo oauthAppInfo, Requester requester) {
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
    protected String getTokenUrl() {
        return "https://www.oschina.net/action/openapi/token";
    }

    @Override
    protected Request initUserRequestPrototype() {
        return Request.initGet("https://www.oschina.net/action/openapi/user");
    }

    @Override
    protected OAuthUser initOAuthUser(Response response) {
        return new OSChinaUser(response);
    }

}
