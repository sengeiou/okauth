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
package com.github.wautsns.okauth.core.assist.http.kernel.model;

import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.assist.http.kernel.util.ReadUtils;
import com.github.wautsns.okauth.core.exception.OAuth2IOException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * OAuth2 http response.
 *
 * @author wautsns
 * @since May 16, 2020
 */
public interface OAuth2HttpResponse {

    // #################### response line ###############################################

    /**
     * Get http status.
     *
     * @return http status
     */
    int getStatus();

    // #################### response header #############################################

    /**
     * Get value of specified header.
     *
     * @param name header name
     * @return value of specified header
     */
    String getHeader(String name);

    /**
     * Get values of specified header.
     *
     * @param name header name
     * @return values of specified header
     */
    List<String> getHeaders(String name);

    // #################### response entity #############################################

    /**
     * Get http response input stream.
     *
     * @return http response input stream
     */
    InputStream getInputStream() throws IOException;

    /**
     * Read http response input stream as {@code String}.
     *
     * @return {@code String} value
     * @throws OAuth2IOException if IO exception occurs
     */
    default String readInputStreamAsString() throws OAuth2IOException {
        try {
            return ReadUtils.readInputStreamAsString(getInputStream());
        } catch (IOException e) {
            throw new OAuth2IOException(e);
        } finally {
            close();
        }
    }

    /**
     * Read http response input stream(json) as {@code DataMap}.
     *
     * @return {@code DataMap} value
     * @throws OAuth2IOException if IO exception occurs
     */
    default DataMap readJsonAsDataMap() throws OAuth2IOException {
        try {
            return ReadUtils.readJsonAsDataMap(getInputStream());
        } catch (IOException e) {
            throw new OAuth2IOException(e);
        } finally {
            close();
        }
    }

    /**
     * Read http response input stream(query-like text) as {@code DataMap}.
     *
     * @return {@code DataMap} value
     * @throws OAuth2IOException if IO exception occurs
     */
    default DataMap readQueryLikeTextAsDataMap() throws OAuth2IOException {
        try {
            return ReadUtils.readQueryLikeTextAsDataMap(getInputStream());
        } catch (IOException e) {
            throw new OAuth2IOException(e);
        } finally {
            close();
        }
    }

    // #################### close #######################################################

    /**
     * Close the response.
     *
     * @throws OAuth2IOException if IO exception occurs
     */
    void close() throws OAuth2IOException;

}
