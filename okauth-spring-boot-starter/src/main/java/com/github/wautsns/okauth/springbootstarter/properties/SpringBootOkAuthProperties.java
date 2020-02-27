/**
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
package com.github.wautsns.okauth.springbootstarter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.github.wautsns.okauth.core.manager.OkAuthProperties;

/**
 * Extends from {@link OkAuthProperties}, just add
 * {@code @ConfigurationProperties(prefix = "okauth")}.
 *
 * @since Feb 27, 2020
 * @author wautsns
 */
@ConfigurationProperties(prefix = "okauth")
public class SpringBootOkAuthProperties extends OkAuthProperties {}
