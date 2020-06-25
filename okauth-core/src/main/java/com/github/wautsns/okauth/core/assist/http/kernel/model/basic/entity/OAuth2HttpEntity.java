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
package com.github.wautsns.okauth.core.assist.http.kernel.model.basic.entity;

import java.io.Serializable;

/**
 * OAuth2 http entity.
 *
 * @author wautsns
 * @since Jun 25, 2020
 */
public interface OAuth2HttpEntity extends Serializable {

    /**
     * To bytes.
     *
     * @return bytes
     */
    byte[] toBytes();

    /**
     * Create and return a copy of this object.
     *
     * @return oauth2 http entity
     */
    OAuth2HttpEntity copy();

}
