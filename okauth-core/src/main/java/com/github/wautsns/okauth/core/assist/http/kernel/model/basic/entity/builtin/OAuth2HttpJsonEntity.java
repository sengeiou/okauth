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

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.entity.OAuth2HttpEntity;
import com.github.wautsns.okauth.core.assist.http.kernel.util.WriteUtils;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * OAuth2 http json entity.
 *
 * @author wautsns
 * @since Jun 25, 2020
 */
public class OAuth2HttpJsonEntity implements OAuth2HttpEntity {

    private static final long serialVersionUID = -5283149053170420932L;

    /** DataMap for shallow copyable values. */
    @JsonUnwrapped
    private DataMap shallowCopyableDataMap;
    /** DataMap for deep copy values. */
    @JsonUnwrapped
    private DataMap deepCopyDataMap;

    /**
     * Put shallow copyable value.
     *
     * @param name name
     * @param value shallow copyable value
     * @return self reference
     */
    public OAuth2HttpJsonEntity putShallowCopyableValue(String name, Serializable value) {
        if (shallowCopyableDataMap == null) {
            shallowCopyableDataMap = new DataMap();
        }
        shallowCopyableDataMap.put(name, value);
        return this;
    }

    /**
     * Put deep copy value.
     *
     * @param name name
     * @param value shallow copyable value
     * @return self reference
     */
    public OAuth2HttpJsonEntity putDeepCopyValue(String name, Serializable value) {
        if (deepCopyDataMap == null) {
            deepCopyDataMap = new DataMap();
        }
        deepCopyDataMap.put(name, value);
        return this;
    }

    @Override
    public byte[] toBytes() {
        return WriteUtils.writeObjectAsJsonString(this).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public OAuth2HttpJsonEntity copy() {
        OAuth2HttpJsonEntity copy = new OAuth2HttpJsonEntity();
        copy.shallowCopyableDataMap = this.shallowCopyableDataMap;
        if (this.deepCopyDataMap != null) {
            copy.deepCopyDataMap = copyDataMap(this.deepCopyDataMap);
        }
        return copy;
    }

    /**
     * Create and return a copy of data map.
     *
     * @param source source
     * @return a copy of data map
     */
    private DataMap copyDataMap(DataMap source) {
        DataMap copy = new DataMap(source.size());
        source.forEach((name, value) -> {
            if (value instanceof DataMap) {
                value = copyDataMap((DataMap) value);
            }
            copy.put(name, value);
        });
        return copy;
    }

}
