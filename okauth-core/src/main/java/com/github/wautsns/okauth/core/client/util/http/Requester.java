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

import java.util.function.BiConsumer;

import com.github.wautsns.okauth.core.client.util.http.Request.Method;
import com.github.wautsns.okauth.core.exception.OkAuthIOException;

/**
 * Abstract requester for creating {@linkplain Request request}.
 *
 * @author wautsns
 */
public abstract class Requester {

    /**
     * Exchange.
     *
     * @param request request, require nonnull
     * @return response
     * @throws OkAuthIOException
     */
    public Response exchange(Request request)
            throws OkAuthIOException {
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
     * <p>*** Remember to call {@link #forEachHeader(BiConsumer)}
     *
     * @param request request, require nonnull
     * @return response
     * @throws OkAuthIOException if an IO exception occurs
     */
    protected abstract Response doGet(Request request) throws OkAuthIOException;

    /**
     * Do POST request.
     *
     * <p>*** Remember to call {@link #forEachHeader(BiConsumer)} and
     * {@link #forEachUrlEncodedFormItem(BiConsumer)}.
     *
     * @param request request, require nonnull
     * @param reader response input stream reader, require nonnull
     * @return response
     * @throws OkAuthIOException if an IO exception occurs
     */
    protected abstract Response doPost(Request request) throws OkAuthIOException;

}
