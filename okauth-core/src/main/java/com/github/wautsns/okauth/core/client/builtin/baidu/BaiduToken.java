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
package com.github.wautsns.okauth.core.client.builtin.baidu;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.wautsns.okauth.core.client.core.standard.oauth2.StandardOAuth2Token;
import com.github.wautsns.okauth.core.client.util.http.Response;

/**
 * Baidu token.
 *
 * <p>摘自百度官网: session_key 和 session_secret 参数不是 OAuth2.0 协议标准规定的返回参数,
 * 而是百度 OAuth2.0 服务扩展加入的, 目的是使得开发者可以基于 http 调用百度的 Open API, 因为基于
 * https 调用 Open API 虽然更为简单, 但毕竟响应速度更差(比基于 http 的要差一倍时间左右).
 *
 * @author wautsns
 */
@JsonNaming(SnakeCaseStrategy.class)
public class BaiduToken extends StandardOAuth2Token {

    /**
     * Construct a baidu token.
     *
     * @param response response, require nonnull
     */
    public BaiduToken(Response response) {
        super(response);
    }

    /** Get session key. */
    public String getSessionKey() {
        return getString("session_key");
    }

    /** Get session secret. */
    public String getSessionSecret() {
        return getString("session_secret");
    }

}
