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
package com.github.wautsns.okauth.core.client.util.http;

import java.util.Map;

import com.github.wautsns.okauth.core.client.core.OpenPlatform;
import com.github.wautsns.okauth.core.exception.OkAuthException;

/**
 *
 * @author wautsns
 */
public class Response {

    private final int statusCode;
    private final Map<String, Object> data;

    public Response(int statusCode, Map<String, Object> data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public Response check(OpenPlatform openPlatform, String errorName, String errorDescriptionName)
            throws OkAuthException {
        Object temp = data.get(errorName);
        String error;
        String errorDescription = null;
        if (temp != null) {
            error = temp.toString();
            temp = data.get(errorDescriptionName);
            if (temp != null) { errorDescription = temp.toString(); }
        } else {
            if (statusCode < 300 && statusCode >= 200) {
                return this;
            } else {
                error = String.valueOf(statusCode);
            }
        }
        if (errorDescription == null) { errorDescription = error; }
        throw new OkAuthException(openPlatform, error, errorDescription);
    }

    /** Get {@link #statusCode}. */
    public int getStatusCode() {
        return statusCode;
    }

    /** Get {@link #data}. */
    public Map<String, Object> getData() {
        return data;
    }

}
