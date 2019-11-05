package com.github.wautsns.okauth.core.exception;

import com.github.wautsns.okauth.core.client.core.OpenPlatform;

/**
 *
 * @author wautsns
 */
public class OkAuthException extends RuntimeException {

    /** serialVersionUID */
    private static final long serialVersionUID = -5934099746259324746L;

    private final OpenPlatform openPlatform;
    private final String error;

    public OkAuthException(OpenPlatform openPlatform, String error, String errorDescription) {
        super(errorDescription);
        this.openPlatform = openPlatform;
        this.error = error;
    }

    /** Get {@link #openPlatform}. */
    public OpenPlatform getOpenPlatform() {
        return openPlatform;
    }

    /** Get {@link #error}. */
    public String getError() {
        return error;
    }

}
