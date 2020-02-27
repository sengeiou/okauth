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
package com.github.wautsns.okauth.core.client.util.http;

import java.util.function.BiConsumer;

import com.github.wautsns.okauth.core.client.util.http.OkAuthRequest.Method;
import com.github.wautsns.okauth.core.exception.OkAuthIOException;

/**
 * Abstract requester for exchanging.
 *
 * @since Feb 27, 2020
 * @author wautsns
 */
public abstract class OkAuthRequester {

    /**
     * Exchange request for response.
     *
     * @param request request, require nonnull
     * @return response
     * @throws OkAuthIOException
     */
    public OkAuthResponse exchange(OkAuthRequest request) throws OkAuthIOException {
        Method method = request.getMethod();
        if (method == Method.GET) {
            return doGet(request);
        } else if (method == Method.POST) {
            return doPost(request);
        }
        throw new IllegalStateException();
    }

    /**
     * Do GET request.
     *
     * @param request request, require nonnull
     * @return response
     * @throws OkAuthIOException if an IO exception occurs
     * @see OkAuthRequest#forEachHeader(BiConsumer)
     */
    protected abstract OkAuthResponse doGet(OkAuthRequest request) throws OkAuthIOException;

    /**
     * Do POST request.
     *
     * @param request request, require nonnull
     * @param reader response input stream reader, require nonnull
     * @return response
     * @throws OkAuthIOException if an IO exception occurs
     * @see OkAuthRequest#forEachHeader(BiConsumer)
     * @see OkAuthRequest#forEachUrlEncodedFormItem(BiConsumer)
     */
    protected abstract OkAuthResponse doPost(OkAuthRequest request) throws OkAuthIOException;

}
