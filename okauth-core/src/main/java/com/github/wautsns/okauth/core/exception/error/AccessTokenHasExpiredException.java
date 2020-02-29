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
package com.github.wautsns.okauth.core.exception.error;

import com.github.wautsns.okauth.core.client.OpenPlatform;

/**
 * Expired access token exception.
 *
 * @since Feb 29, 2020
 * @author wautsns
 */
public class AccessTokenHasExpiredException extends OAuthErrorException {

    /** serialVersionUID */
    private static final long serialVersionUID = -1198327228165589279L;

    public AccessTokenHasExpiredException(OpenPlatform openPlatform) {
        super(openPlatform, "access_token_has_expired", "Access token has expired.");
    }

}
