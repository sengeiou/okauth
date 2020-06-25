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
package com.github.wautsns.okauth.core.assist.http.kernel.model.basic.entity.builtin;

import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.OAuth2UrlEncodedEntries;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.entity.OAuth2HttpEntity;
import com.github.wautsns.okauth.core.assist.http.kernel.util.WriteUtils;

/**
 * OAuth2 http form url encoded entity.
 *
 * @author wautsns
 * @since Jun 25, 2020
 */
public class OAuth2HttpFormUrlEncodedEntity extends OAuth2UrlEncodedEntries implements OAuth2HttpEntity {

    private static final long serialVersionUID = -3092924176481279906L;

    @Override
    public byte[] toBytes() {
        return WriteUtils.writeUrlEncodedEntriesAsQueryLikeTextBytes(this);
    }

    @Override
    public OAuth2HttpFormUrlEncodedEntity copy() {
        OAuth2HttpFormUrlEncodedEntity copy = new OAuth2HttpFormUrlEncodedEntity();
        copy.getOrigin().addAll(this.getOrigin());
        return copy;
    }

}
