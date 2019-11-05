package com.github.wautsns.okauth.core.client.core.dto;

/**
 *
 * @author wautsns
 */
public class OAuthRedirectUriQuery {

    private String code;
    private String state;

    /** Get {@link #code}. */
    public String getCode() {
        return code;
    }

    /** Set {@link #code}. */
    public OAuthRedirectUriQuery setCode(String code) {
        this.code = code;
        return this;
    }

    /** Get {@link #state}. */
    public String getState() {
        return state;
    }

    /** Set {@link #state}. */
    public OAuthRedirectUriQuery setState(String state) {
        this.state = state;
        return this;
    }

}
