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
package com.github.wautsns.okauth.core.client.builtin.elemeshopisv;

import com.github.wautsns.okauth.core.client.kernel.OAuth2AppInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * ElemeShopIsv oauth2 app info.
 *
 * @author wautsns
 * @since Jun 25, 2020
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ElemeShopIsvOAuth2AppInfo extends OAuth2AppInfo {

    /** Key. */
    private String key;
    /** Secret. */
    private String secret;
    /** Redirect uri. */
    private String redirectUri;
    /** Environment. */
    private Environment env = Environment.PRODUCTION;

    /** Environment. */
    public enum Environment {SANDBOX, PRODUCTION}

}
