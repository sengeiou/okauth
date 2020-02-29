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
package com.github.wautsns.okauth.core.client.kernel.model.dto;

import java.io.Serializable;
import java.time.LocalDate;

import java.util.Map;

import com.github.wautsns.okauth.core.client.OpenPlatform;
import com.github.wautsns.okauth.core.client.kernel.http.model.dto.OAuthResponse;

/**
 * Open platform user.
 *
 * @since Feb 28, 2020
 * @author wautsns
 */
public abstract class OpenPlatformUser extends OAuthResponseDataMap {

    public enum Gender { MALE, FEMALE, SECRET }

    /** serialVersionUID */
    private static final long serialVersionUID = -9204638137197883064L;

    /**
     * Construct an open platform user.
     *
     * @param originalDataMap original data map, require nonnull
     */
    public OpenPlatformUser(Map<String, Serializable> originalDataMap) {
        super(originalDataMap);
    }

    /**
     * Construct an open platform user.
     *
     * @param response okauth response, require nonnull
     */
    public OpenPlatformUser(OAuthResponse response) {
        super(response);
    }

    /**
     * Get open platform to which the user belongs.
     *
     * @return open platform to which the user belongs
     */
    public abstract OpenPlatform getOpenPlatform();

    /**
     * Get openid of the user.
     *
     * @return openid of the user
     */
    public abstract String getOpenid();

    /**
     * Get unionid of the user.
     *
     * @return unionid of the user({@code null} if not present)
     */
    public String getUnionid() {
        return null;
    }

    /**
     * Get username of the user.
     *
     * @return username of the user({@code null} if not present)
     */
    public String getUsername() {
        return null;
    }

    /**
     * Get nickname of the user.
     *
     * @return nickname of the user({@code null} if not present)
     */
    public String getNickname() {
        return null;
    }

    /**
     * Get avatar url of the user.
     *
     * @return avatar url of the user({@code null} if not present)
     */
    public String getAvatarUrl() {
        return null;
    }

    /**
     * Get gender of the user.
     *
     * @return gender of the user({@code null} if not present)
     */
    public Gender getGender() {
        return null;
    }

    /**
     * Get birthday of the user.
     *
     * @return birthday of the user({@code null} if not present)
     */
    public LocalDate getBirthday() {
        return null;
    }

}
