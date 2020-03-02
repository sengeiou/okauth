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
package com.github.wautsns.okauth.core.client.builtin.qq;

import com.github.wautsns.okauth.core.client.OpenPlatform;
import com.github.wautsns.okauth.core.client.builtin.OpenPlatforms;
import com.github.wautsns.okauth.core.client.kernel.http.model.dto.OAuthResponse;
import com.github.wautsns.okauth.core.client.kernel.model.dto.OpenPlatformUser;
import java.util.Objects;
import lombok.EqualsAndHashCode;

/**
 * QQ user.
 *
 * @author wautsns
 * @since Mar 01, 2020
 */
@EqualsAndHashCode(callSuper = true)
public class QQUser extends OpenPlatformUser {

    private static final long serialVersionUID = 4607846376386651426L;

    private final String openid;

    /**
     * Construct QQ user.
     *
     * @param openid openid, require nonnull
     * @param response okauth response, require nonnull
     */
    public QQUser(String openid, OAuthResponse response) {
        super(response);
        this.openid = Objects.requireNonNull(openid);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return OpenPlatforms.QQ;
    }

    @Override
    public String getOpenid() {
        return openid;
    }

}
