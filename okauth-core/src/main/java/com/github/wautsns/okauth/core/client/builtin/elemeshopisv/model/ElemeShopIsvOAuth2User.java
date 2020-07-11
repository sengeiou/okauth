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
package com.github.wautsns.okauth.core.client.builtin.elemeshopisv.model;

import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatforms;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2User;
import com.github.wautsns.okauth.core.client.kernel.openplatform.OpenPlatform;
import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ElemeShopIsv oauth2 user.
 *
 * @author wautsns
 * @since Jun 25, 2020
 */
public class ElemeShopIsvOAuth2User extends OAuth2User {

    private static final long serialVersionUID = 8870928954038155141L;

    /**
     * Construct an ElemeShopIsv oauth2 user.
     *
     * @param originalDataMap original data map
     */
    public ElemeShopIsvOAuth2User(DataMap originalDataMap) {
        super(originalDataMap);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatforms.ELEME_SHOP_ISV;
    }

    @Override
    public String getOpenid() {
        return getOriginalDataMap().getAsString("userId");
    }

    @Override
    public String getUsername() {
        return getOriginalDataMap().getAsString("userName");
    }

    /**
     * Get authorized shops.
     *
     * @return authorized shops
     */
    public List<AuthorizedShop> getAuthorizedShops() {
        return getOriginalDataMap().getAsDataMapList("authorizedShops").stream()
                .map(AuthorizedShop::new)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Data
    public static class AuthorizedShop implements Serializable {

        private static final long serialVersionUID = -3247156523818038685L;

        /** Original data map. */
        private final DataMap originalDataMap;

        /**
         * Get id.
         *
         * @return id
         */
        public String getId() {
            return getOriginalDataMap().getAsString("id");
        }

        /**
         * Get name.
         *
         * @return name
         */
        public String getName() {
            return getOriginalDataMap().getAsString("name");
        }

    }

}
