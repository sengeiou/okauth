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
package com.github.wautsns.okauth.core.client.builtin.microblog;

import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatform;
import com.github.wautsns.okauth.core.client.core.OpenPlatform;
import com.github.wautsns.okauth.core.client.core.dto.OAuthUser;
import com.github.wautsns.okauth.core.client.util.http.Response;

/**
 * MicroBlog user.
 *
 * <p>Official example:
 *
 * <pre>
 * {
 *    "id": 1404376560,
 *    "screen_name": "zaku",
 *    "name": "zaku",
 *    "province": "11",
 *    "city": "5",
 *    "location": "北京 朝阳区",
 *    "description": "人生五十年，乃如梦如幻；有生斯有死，壮士复何憾。",
 *    "url": "http://blog.sina.com.cn/zaku",
 *    "profile_image_url": "http://tp1.sinaimg.cn/1404376560/50/0/1",
 *    "domain": "zaku",
 *    "gender": "m",
 *    "followers_count": 1204,
 *    "friends_count": 447,
 *    "statuses_count": 2908,
 *    "favourites_count": 0,
 *    "created_at": "Fri Aug 28 00:00:00 +0800 2009",
 *    "following": false,
 *    "allow_all_act_msg": false,
 *    "geo_enabled": true,
 *    "verified": false,
 *    "status": {
 *        "created_at": "Tue May 24 18:04:53 +0800 2011",
 *        "id": 11142488790,
 *        "text": "我的相机到了。",
 *        "source": "{@code <a href="http://weibo.com" rel="nofollow">新浪微博</a>}",
 *        "favorited": false,
 *        "truncated": false,
 *        "in_reply_to_status_id": "",
 *        "in_reply_to_user_id": "",
 *        "in_reply_to_screen_name": "",
 *        "geo": null,
 *        "mid": "5610221544300749636",
 *        "annotations": [],
 *        "reposts_count": 5,
 *        "comments_count": 8,
 *    },
 *    "allow_all_comment": true,
 *    "avatar_large": "http://tp1.sinaimg.cn/1404376560/180/0/1",
 *    "verified_reason": "",
 *    "follow_me": false,
 *    "online_status": 0,
 *    "bi_followers_count": 215,
 * }
 * </pre>
 *
 * @author wautsns
 */
public class MicroBlogUser extends OAuthUser {

    /**
     * Construct a MicroBlog user.
     *
     * @param response response, require nonnull
     */
    public MicroBlogUser(Response response) {
        super(response);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatform.MICROBLOG;
    }

    @Override
    public String getOpenId() {
        return getString("id");
    }

    @Override
    public String getNickname() {
        return getString("screen_name");
    }

    @Override
    public String getAvatarUrl() {
        return getString("profile_image_url");
    }

}
