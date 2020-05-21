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

import com.github.wautsns.okauth.core.client.kernel.OAuth2AppInfo;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * OkAuth app info properties.
 *
 * @author wautsns
 * @since Mar 05, 2020
 */
@Data
@Accessors(chain = true)
public class OkAuthAppInfoProperties<I extends OAuth2AppInfo> {

    /** whether to enable the open platform, default is {@code true} */
    private Boolean enabled = Boolean.TRUE;
    /** oauth app info */
    @NestedConfigurationProperty
    private final I appInfo;
    /** oauth http client properties */
    @NestedConfigurationProperty
    private final OkAuthHttpClientProperties httpClient = new OkAuthHttpClientProperties();

}
