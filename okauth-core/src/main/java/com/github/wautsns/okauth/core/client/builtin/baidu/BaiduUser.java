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
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatform;
import com.github.wautsns.okauth.core.client.core.OpenPlatform;
import com.github.wautsns.okauth.core.client.core.dto.OAuthUser;
import com.github.wautsns.okauth.core.client.util.http.Response;

/**
 * Baidu user.
 *
 * <p>Take the author as an example:
 *
 * <pre>
 * {
 *     "userid": "3319259417",
 *     "portrait": "afc577617574736e73c6d3",
 *     "username": "w***s",
 *     "is_bind_mobile": "1",
 *     "is_realname": "1",
 *     "birthday": "1998-02-05",
 *     "sex": "1",
 *     "openid": "oF6xVTPl83G5Q2tAeh5w5j1uNy15C5O"
 * }
 * </pre>
 *
 * <p>Official example(inconsistent with the former):
 *
 * <pre>
 * {
 *     "userid":"2097322476",
 *     "username":"wl19871011",
 *     "realname":"阳光",
 *     "userdetail":"喜欢自由",
 *     "birthday":"1987-01-01",
 *     "marriage":"恋爱",
 *     "sex":"男", // <strong><i>The official document may be wrong.</i></strong>
 *     "blood":"O",
 *     "constellation":"射手",
 *     "figure":"小巧",
 *     "education":"大学/专科",
 *     "trade":"计算机/电子产品",
 *     "job":"未知",
 *     "birthday_year":"1987",
 *     "birthday_month":"01",
 *     "birthday_day":"01",
 * }
 * </pre>
 *
 * @author wautsns
 */
@JsonNaming(SnakeCaseStrategy.class)
public class BaiduUser extends OAuthUser {

    /**
     * Construct a Baidu user.
     *
     * @param response response, require nonnull
     */
    public BaiduUser(Response response) {
        super(response);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatform.BAIDU;
    }

    @Override
    public String getOpenId() {
        return getString("userid");
    }

    @Override
    public String getNickname() {
        return getString("username");
    }

    @Override
    public String getAvatarUrl() {
        return "http://tb.himg.baidu.com/sys/portrait/item/" + getString("portrait");
    }

}
