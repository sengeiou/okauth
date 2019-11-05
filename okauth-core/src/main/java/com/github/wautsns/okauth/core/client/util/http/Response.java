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
