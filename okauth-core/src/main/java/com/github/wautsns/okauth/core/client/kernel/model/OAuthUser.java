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
package com.github.wautsns.okauth.core.client.kernel.model;

import com.github.wautsns.okauth.core.OpenPlatform;
import com.github.wautsns.okauth.core.http.model.OAuthResponse;
import com.github.wautsns.okauth.core.http.util.DataMap;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * OAuth user.
 *
 * @author wautsns
 * @since Mar 04, 2020
 */
public abstract class OAuthUser implements Serializable {

    /** gender */
    public enum Gender {MALE, FEMALE, SECRET, UNKNOWN}

    private static final long serialVersionUID = 1936481682522482511L;

    /** original data map */
    private final DataMap originalDataMap;

    /**
     * Construct oauth user.
     *
     * @param response correct response, require nonnull
     */
    public OAuthUser(OAuthResponse response) {
        this.originalDataMap = response.getDataMap();
    }

    /**
     * Get original data map.
     *
     * @return original data map
     */
    public DataMap getOriginalDataMap() {
        return originalDataMap;
    }

    /**
     * Get open platform.
     *
     * @return open platform
     */
    public abstract OpenPlatform getOpenPlatform();

    /**
     * Get openid.
     *
     * @return openid
     */
    public abstract String getOpenid();

    /**
     * Get unionid.
     *
     * @return unionid, or {@code null} if not present
     */
    public String getUnionid() {
        return null;
    }

    /**
     * Get username.
     *
     * @return username, or {@code null} if not present
     */
    public String getUsername() {
        return null;
    }

    /**
     * Get nickname.
     *
     * @return nickname, or {@code null} if not present
     */
    public String getNickname() {
        return null;
    }

    /**
     * Get avatar url.
     *
     * @return avatar url, or {@code null} if not present
     */
    public String getAvatarUrl() {
        return null;
    }

    /**
     * Get gender.
     *
     * @return gender
     */
    public Gender getGender() {
        return Gender.UNKNOWN;
    }

    /**
     * Get birthday.
     *
     * @return birthday, or {@code null} if not present
     */
    public LocalDate getBirthday() {
        return null;
    }

}
