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
package com.github.wautsns.okauth.core.client;

import com.github.wautsns.okauth.core.client.kernel.OAuthClient;
import com.github.wautsns.okauth.core.client.kernel.http.OAuthRequestExecutor;
import com.github.wautsns.okauth.core.client.kernel.model.properties.OAuthAppProperties;

/**
 * OAuth client initializer.
 *
 * @author wautsns
 * @since Feb 29, 2020
 */
public interface OAuthClientInitializer {

    /**
     * Initialize oauth client.
     *
     * @param app oauth application properties, require nonnull
     * @param executor oauth request executor, require nonnull
     * @return oauth client
     */
    OAuthClient<?> initOAuthClient(OAuthAppProperties app, OAuthRequestExecutor executor);

}
