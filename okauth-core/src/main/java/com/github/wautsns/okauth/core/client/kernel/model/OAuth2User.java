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

import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * OAuth2 user.
 *
 * @author wautsns
 * @since May 17, 2020
 */
public interface OAuth2User extends OpenPlatformSupplier, Serializable {

    /**
     * Get original data map.
     *
     * @return original data map.
     */
    DataMap getOriginalDataMap();

    /**
     * Get open id.
     *
     * @return open id
     */
    String getOpenid();

    /**
     * Get union id.
     *
     * @return union id
     */
    default String getUnionid() { return null; }

    /**
     * Get uid.
     *
     * @return uid
     */
    default String getUid() { return null; }

    /**
     * Get username.
     *
     * @return username
     */
    default String getUsername() { return null; }

    /**
     * Get nickname.
     *
     * @return nickname
     */
    default String getNickname() { return null; }

    /**
     * Get avatar url.
     *
     * @return avatar url
     */
    default String getAvatarUrl() { return null; }

    enum Gender {MALE, FEMALE, SECRET, UNKNOWN}

    /**
     * Get gender.
     *
     * @return gender
     */
    default Gender getGender() { return Gender.UNKNOWN; }

    /**
     * Get birthday.
     *
     * @return birthday
     */
    default LocalDate getBirthday() { return null; }

    /**
     * Get email.
     *
     * @return email
     */
    default String getEmail() { return null; }

    /**
     * Get phone.
     *
     * @return phone
     */
    default String getPhone() { return null; }

}
