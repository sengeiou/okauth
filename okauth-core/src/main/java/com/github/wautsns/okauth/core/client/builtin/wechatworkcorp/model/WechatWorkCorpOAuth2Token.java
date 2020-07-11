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
package com.github.wautsns.okauth.core.client.builtin.wechatworkcorp.model;

import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatforms;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2Token;
import com.github.wautsns.okauth.core.client.kernel.openplatform.OpenPlatform;

/**
 * WechatWorkCorp oauth2 token.
 *
 * <pre>
 * {
 * 	"access_token": "ACCESS_TOKEN",
 * 	"expires_in": 7200
 * }
 * </pre>
 *
 * @author wautsns
 * @since May 23, 2020
 */
public class WechatWorkCorpOAuth2Token extends OAuth2Token {

    private static final long serialVersionUID = -8354114542294039343L;

    /**
     * Construct a WechatWorkCorp oauth2 token.
     *
     * @param originalDataMap original data map
     */
    public WechatWorkCorpOAuth2Token(DataMap originalDataMap) {
        super(originalDataMap);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatforms.WECHAT_WORK_CORP;
    }

}
