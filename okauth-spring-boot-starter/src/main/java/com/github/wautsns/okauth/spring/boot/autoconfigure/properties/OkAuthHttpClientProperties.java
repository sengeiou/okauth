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
package com.github.wautsns.okauth.spring.boot.autoconfigure.properties;

import com.github.wautsns.okauth.core.assist.http.kernel.OAuth2HttpClient;
import com.github.wautsns.okauth.core.assist.http.kernel.properties.OAuth2HttpClientProperties;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * OkAuth http client properties.
 *
 * @author wautsns
 * @since May 20, 2020
 */
@Data
@Accessors(chain = true)
public class OkAuthHttpClientProperties {

    /** Implementation of OAuth2HttpClient. */
    private Class<? extends OAuth2HttpClient> implementation;
    /** Http client properties. */
    @NestedConfigurationProperty
    private OAuth2HttpClientProperties properties;

    /**
     * Create and return a copy of this object.
     *
     * @return a copy of this properties
     */
    public OkAuthHttpClientProperties copy() {
        return new OkAuthHttpClientProperties()
                .setImplementation(implementation)
                .setProperties((properties == null) ? null : properties.copy());
    }

    /**
     * Fill null properties.
     *
     * @param target target properties
     * @param source source properties
     * @return target properties
     */
    public static OkAuthHttpClientProperties fillNullProperties(
            OkAuthHttpClientProperties target, OkAuthHttpClientProperties source) {
        if (source == null) { return target; }
        if (target == null) { return source.copy(); }
        if (target.implementation == null) { target.implementation = source.implementation; }
        if (source.properties == null) {
            target.properties = OAuth2HttpClientProperties.initDefault();
        } else {
            target.properties = OAuth2HttpClientProperties.fillNullProperties(target.properties, source.properties);
        }
        return target;
    }

}
