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
package com.github.wautsns.okauth.core.assist.http.builtin.httpclient4;

import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

/**
 * OAuth2 http request retry handler.
 *
 * @author wautsns
 * @since May 22, 2020
 */
class OAuth2HttpRequestRetryHandler extends DefaultHttpRequestRetryHandler {

    /** The IOException types that should not be retried. */
    private static final List<Class<? extends IOException>> EXCEPTIONS_NOT_RETRIED = Arrays.asList(
            InterruptedIOException.class,
            UnknownHostException.class,
            HttpHostConnectException.class,
            SSLException.class);

    /**
     * Create the request retry handler using the specified IOException classes.
     *
     * @param retryCount how many times to retry; 0 means no retries
     */
    protected OAuth2HttpRequestRetryHandler(int retryCount) {
        super(retryCount, false, EXCEPTIONS_NOT_RETRIED);
    }

}
