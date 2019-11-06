package com.github.wautsns.okauth.core.exception;

import java.io.IOException;

/**
 *
 * @author wautsns
 * @version 0.1.0 Nov 06, 2019
 */
public class OkAuthIOException extends RuntimeException {

    /** serialVersionUID */
    private static final long serialVersionUID = -5985811988466801848L;

    public OkAuthIOException(IOException ioException) {
        super(ioException);
    }

}
