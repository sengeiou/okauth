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
package com.github.wautsns.okauth.core.client.kernel.api;

import com.github.wautsns.okauth.core.client.kernel.api.basic.FunctionApi;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2RedirectUriQuery;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2User;

/**
 * API: Exchange redirect uri query for user.
 *
 * @author wautsns
 * @since May 17, 2020
 */
@FunctionalInterface
public interface ExchangeRedirectUriQueryForUser<U extends OAuth2User> extends FunctionApi<OAuth2RedirectUriQuery, U> {}
