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
package com.github.wautsns.okauth.core.client.builtin.microblog;

import com.github.wautsns.okauth.core.OpenPlatform;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatform;
import com.github.wautsns.okauth.core.client.kernel.model.OAuthUser;
import com.github.wautsns.okauth.core.http.model.OAuthResponse;

/**
 * MicroBlog user.
 *
 * <pre>
 * {
 *     "id": 56608xxxxx,
 *     "idstr": "56608xxxxx",
 *     "class": 1,
 *     "screen_name": "wautsns",
 *     "name": "wautsns",
 *     "province": "31",
 *     "city": "1000",
 *     "location": "上海",
 *     "description": "",
 *     "url": "",
 *     "profile_image_url": "https://tvax1.sinaimg.cn/default/images/default_avatar_male_50.gif?KID=imgbed,tva&Expires=1583004052&ssig=EC8mZyeHH3",
 *     "profile_url": "u/56608xxxxx",
 *     "domain": "",
 *     "weihao": "",
 *     "gender": "m",
 *     "followers_count": 1,
 *     "friends_count": 59,
 *     "pagefriends_count": 0,
 *     "statuses_count": 0,
 *     "video_status_count": 0,
 *     "favourites_count": 0,
 *     "created_at": "Thu Oct 24 21:53:03 +0800 2019",
 *     "following": false,
 *     "allow_all_act_msg": false,
 *     "geo_enabled": true,
 *     "verified": false,
 *     "verified_type": -1,
 *     "remark": "",
 *     "insecurity": {
 *         "sexual_content": false
 *     },
 *     "ptype": 0,
 *     "allow_all_comment": true,
 *     "avatar_large": "https://tvax1.sinaimg.cn/default/images/default_avatar_male_180.gif?KID=imgbed,tva&Expires=1583004052&ssig=YS8a9TWSH2",
 *     "avatar_hd": "https://tvax1.sinaimg.cn/default/images/default_avatar_male_180.gif?KID=imgbed,tva&Expires=1583004052&ssig=YS8a9TWSH2",
 *     "verified_reason": "",
 *     "verified_trade": "",
 *     "verified_reason_url": "",
 *     "verified_source": "",
 *     "verified_source_url": "",
 *     "follow_me": false,
 *     "like": false,
 *     "like_me": false,
 *     "online_status": 0,
 *     "bi_followers_count": 0,
 *     "lang": "zh-cn",
 *     "star": 0,
 *     "mbtype": 0,
 *     "mbrank": 0,
 *     "block_word": 0,
 *     "block_app": 0,
 *     "credit_score": 80,
 *     "user_ability": 0,
 *     "urank": 0,
 *     "story_read_state": -1,
 *     "vclub_member": 0,
 *     "is_teenager": 0,
 *     "is_guardian": 0,
 *     "is_teenager_list": 0,
 *     "special_follow": false
 * }
 * </pre>
 *
 * @author wautsns
 * @since Mar 04, 2020
 */
public class MicroBlogUser extends OAuthUser {

    private static final long serialVersionUID = 902553786678247428L;

    /**
     * Construct MicroBlog user.
     *
     * @param response correct response, require nonnull
     */
    public MicroBlogUser(OAuthResponse response) {
        super(response);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatform.MICROBLOG;
    }

    @Override
    public String getOpenid() {
        return getOriginalDataMap().getAsString("idstr");
    }

    /**
     * {@inheritDoc}
     *
     * @return nickname
     */
    @Override
    public String getNickname() {
        return getOriginalDataMap().getAsString("screen_name");
    }

    /**
     * {@inheritDoc}
     *
     * @return avatar url
     */
    @Override
    public String getAvatarUrl() {
        return getOriginalDataMap().getAsString("avatar_large");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Possible values: {@linkplain com.github.wautsns.okauth.core.client.kernel.model.OAuthUser.Gender#MALE MALE},
     * {@linkplain com.github.wautsns.okauth.core.client.kernel.model.OAuthUser.Gender#FEMALE FEMALE}, {@linkplain
     * com.github.wautsns.okauth.core.client.kernel.model.OAuthUser.Gender#UNKNOWN UNKNOWN}.
     *
     * @return gender
     */
    @Override
    public Gender getGender() {
        String gender = getOriginalDataMap().getAsString("gender");
        if ("m".equals(gender)) {
            return Gender.MALE;
        } else if ("f".equals(gender)) {
            return Gender.FEMALE;
        } else {
            return Gender.UNKNOWN;
        }
    }

}
