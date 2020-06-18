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
package com.github.wautsns.okauth.core.client.builtin.wechat.work.corp.service.tokencache.builtin;

import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.client.builtin.wechat.work.corp.model.WeChatWorkCorpOAuth2Token;
import com.github.wautsns.okauth.core.client.builtin.wechat.work.corp.service.tokencache.WeChatWorkCorpTokenCache;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Local WeChatWorkCorp token cache.
 *
 * @author wautsns
 * @since Jun 16, 2020
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WeChatWorkCorpTokenLocalCache implements WeChatWorkCorpTokenCache {

    /** Local WeChatWorkCorp token cache instance. */
    public static final WeChatWorkCorpTokenLocalCache INSTANCE = new WeChatWorkCorpTokenLocalCache();

    /** {@linkplain WeChatWorkCorpOAuth2Token#getOriginalDataMap() Original token data map}. */
    private DataMap value;
    /** Timestamp token expire at. */
    private long expirationTimestamp = Long.MIN_VALUE;

    @Override
    public DataMap get() {
        if (value == null) {
            return null;
        } else if (expirationTimestamp < System.currentTimeMillis()) {
            return null;
        } else {
            return value;
        }
    }

    @Override
    public void save(DataMap originalDataMap, int accessTokenExpirationSeconds) {
        value = originalDataMap;
        expirationTimestamp = System.currentTimeMillis() + accessTokenExpirationSeconds * 1000;
    }

    @Override
    public void delete() {
        value = null;
    }

}
