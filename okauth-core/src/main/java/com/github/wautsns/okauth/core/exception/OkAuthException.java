package com.github.wautsns.okauth.core.exception;

/**
 *
 * @author wautsns
 */
public class OkAuthException extends Exception {

    /** serialVersionUID */
    private static final long serialVersionUID = -5934099746259324746L;

    private final String openPlatform;
    private final String error;

    public OkAuthException(String openPlatform, String error, String errorDescription) {
        super(errorDescription);
        this.openPlatform = openPlatform;
        this.error = error;
    }

    /** Get {@link #openPlatform}. */
    public String getOpenPlatform() {
        return openPlatform;
    }

    /** Get {@link #error}. */
    public String getError() {
        return error;
    }

}
