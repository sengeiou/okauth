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
package com.github.wautsns.okauth.core.assist.http.kernel;

import com.github.wautsns.okauth.core.assist.http.kernel.model.OAuth2HttpRequest;
import com.github.wautsns.okauth.core.assist.http.kernel.model.OAuth2HttpResponse;
import com.github.wautsns.okauth.core.exception.OAuth2IOException;

/**
 * OAuth2 http client.
 *
 * @author wautsns
 * @since May 16, 2020
 */
public interface OAuth2HttpClient {

    /**
     * Execute oauth request and return oauth response.
     *
     * @param request oauth request
     * @return oauth response
     * @throws OAuth2IOException if IO exception occurs
     */
    OAuth2HttpResponse execute(OAuth2HttpRequest request) throws OAuth2IOException;

}
