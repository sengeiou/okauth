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
package com.github.wautsns.okauth.core.client.kernel.model.properties;

import com.github.wautsns.okauth.core.util.CheckableProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * OAuth app properties.
 *
 * @author wautsns
 * @see #check()
 * @since Feb 28, 2020
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class OAuthAppProperties extends CheckableProperties {

    /** client id */
    private String clientId;
    /** client secret */
    private String clientSecret;
    /** redirect uri */
    private String redirectUri;

    /**
     * {@inheritDoc}
     *
     * <p>ClientId, clientSecret and redirectUri must not be null or blank.({@code String#trim()} will be called)
     *
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Override
    public void check() {
        clientId = trimAndNotEmpty("clientId", clientId);
        clientSecret = trimAndNotEmpty("clientSecret", clientSecret);
        redirectUri = trimAndNotEmpty("redirectUri", redirectUri);
    }

}
