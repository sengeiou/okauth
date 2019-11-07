/**
 * Copyright 2019 the original author or authors.
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
package com.github.wautsns.okauth.core.client.core.dto;

import java.util.Map;

/**
 *
 * @author wautsns
 */
class OAuthData {

    private final Map<String, Object> data;

    protected OAuthData(Map<String, Object> data) {
        this.data = data;
    }

    public String getString(String name) {
        Object value = data.get(name);
        return (value == null) ? null : value.toString();
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getMap(String name) {
        Object value = data.get(name);
        return (value instanceof Map) ? (Map<String, Object>) value : null;
    }

    public Map<String, Object> getData() {
        return data;
    }

}
