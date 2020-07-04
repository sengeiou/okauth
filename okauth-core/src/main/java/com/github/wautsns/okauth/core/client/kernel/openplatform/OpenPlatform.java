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
package com.github.wautsns.okauth.core.client.kernel.openplatform;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Open platform.
 *
 * @author wautsns
 * @since Jul 04, 2020
 */
@Data
@Accessors(chain = true)
public class OpenPlatform {

    /** Name. */
    private final String name;

}
