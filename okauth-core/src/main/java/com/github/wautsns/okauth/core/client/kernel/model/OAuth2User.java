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
import com.github.wautsns.okauth.core.client.kernel.openplatform.OpenPlatformSupplier;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * OAuth2 user.
 *
 * @author wautsns
 * @since May 17, 2020
 */
@Data
@Accessors(chain = true)
public abstract class OAuth2User implements OpenPlatformSupplier, Serializable {

    private static final long serialVersionUID = 1366329814055492214L;

    /** Original data map. */
    private final DataMap originalDataMap;

    /**
     * Construct an oauth2 user.
     *
     * @param originalDataMap original data map
     */
    public OAuth2User(DataMap originalDataMap) {
        this.originalDataMap = originalDataMap;
    }

    /**
     * Get open id.
     *
     * @return open id
     */
    public abstract String getOpenid();

    /**
     * Get union id.
     *
     * @return union id
     */
    public String getUnionid() { return null; }

    /**
     * Get uid.
     *
     * @return uid
     */
    public String getUid() { return null; }

    /**
     * Get username.
     *
     * @return username
     */
    public String getUsername() { return null; }

    /**
     * Get nickname.
     *
     * @return nickname
     */
    public String getNickname() { return null; }

    /**
     * Get avatar url.
     *
     * @return avatar url
     */
    public String getAvatarUrl() { return null; }

    /** Gender. */
    public enum Gender {
        MALE, FEMALE,
        SECRET, UNKNOWN
    }

    /**
     * Get gender.
     *
     * @return gender
     */
    public Gender getGender() { return Gender.UNKNOWN; }

    /**
     * Get birthday.
     *
     * @return birthday
     */
    public LocalDate getBirthday() { return null; }

    /**
     * Get email.
     *
     * @return email
     */
    public String getEmail() { return null; }

    /**
     * Get cellphone.
     *
     * @return cellphone
     */
    public String getCellphone() { return null; }

}
