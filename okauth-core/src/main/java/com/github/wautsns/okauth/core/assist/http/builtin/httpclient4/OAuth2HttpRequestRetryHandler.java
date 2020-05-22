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
public class OAuth2HttpRequestRetryHandler extends DefaultHttpRequestRetryHandler {

    /** the IOException types that should not be retried */
    private static final List<Class<? extends IOException>> EXCEPTIONS_NOT_RETRIED = Arrays.asList(
            InterruptedIOException.class,
            UnknownHostException.class,
            HttpHostConnectException.class,
            SSLException.class);

    /** Retry 3 times. */
    public static final OAuth2HttpRequestRetryHandler THREE_TIMES = new OAuth2HttpRequestRetryHandler(3);

    /**
     * Create the request retry handler using the specified IOException classes.
     *
     * @param retryCount how many times to retry; 0 means no retries
     */
    protected OAuth2HttpRequestRetryHandler(int retryCount) {
        super(retryCount, false, EXCEPTIONS_NOT_RETRIED);
    }

}
