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
package com.github.wautsns.okauth.core.client.builtin.wechat.work.corp.service;

import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.client.builtin.wechat.work.corp.WeChatWorkCorpOAuth2Client;
import com.github.wautsns.okauth.core.client.builtin.wechat.work.corp.model.WeChatWorkCorpOAuth2Token;

/**
 * WeCharWorkCorp token cache.
 *
 * <p><strong>No need to consider concurrency issues.(See {@link WeChatWorkCorpOAuth2Client#getToken()} for
 * details)</strong>
 *
 * @author wautsns
 * @since May 23, 2020
 */
public interface WeChatWorkCorpTokenCache {

    /**
     * Get oauth2 token {@linkplain WeChatWorkCorpOAuth2Token#getOriginalDataMap() original data map}.
     *
     * @return oauth2 token original data map
     */
    DataMap get();

    /**
     * Save oauth2 token {@linkplain WeChatWorkCorpOAuth2Token#getOriginalDataMap() original data map}.
     *
     * @param originalDataMap oauth2 token original data map
     * @param accessTokenExpirationSeconds access token expiration seconds
     */
    void save(DataMap originalDataMap, int accessTokenExpirationSeconds);

    /** Delete oauth2 token {@linkplain WeChatWorkCorpOAuth2Token#getOriginalDataMap() original data map}. */
    void delete();

    // #################### instance ####################################################

    /** Instance of local cache. */
    WeChatWorkCorpTokenCache LOCAL_CACHE = new WeChatWorkCorpTokenCache() {
        private DataMap value;
        private long expirationTimestamp = Long.MAX_VALUE;

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
    };

}
