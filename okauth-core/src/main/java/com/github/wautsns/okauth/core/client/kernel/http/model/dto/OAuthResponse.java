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
package com.github.wautsns.okauth.core.client.kernel.http.model.dto;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

/**
 * OkAuth response.
 *
 * @since Feb 28, 2020
 * @author wautsns
 */
public class OAuthResponse {

    /** request of the response */
    private final OAuthRequest request;
    /** response status code */
    private final int status;
    /** response data */
    private final Map<String, Serializable> data;

    /**
     * Construct okauth response.
     *
     * @param request request of the response, require nonnull
     * @param status response status code
     * @param data response data, require nonnull
     */
    public OAuthResponse(
            OAuthRequest request,
            int status, Map<String, Serializable> data) {
        this.request = request;
        this.status = status;
        this.data = Objects.requireNonNull(data);
    }

    /**
     * Get oauth request of the response.
     *
     * @return oauth request of the response.
     */
    public OAuthRequest getRequest() {
        return request;
    }

    /**
     * Get response status code.
     *
     * @return response status code
     */
    public int getStatus() {
        return status;
    }

    /**
     * Get response data.
     *
     * @return response data
     */
    public Map<String, Serializable> getData() {
        return data;
    }

}
