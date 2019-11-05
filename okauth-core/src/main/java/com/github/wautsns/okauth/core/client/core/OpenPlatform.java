package com.github.wautsns.okauth.core.client.core;

import java.io.Serializable;

import com.github.wautsns.okauth.core.client.core.properties.OAuthAppInfo;
import com.github.wautsns.okauth.core.client.util.http.Requester;

/**
 *
 * @author wautsns
 */
public interface OpenPlatform extends Serializable {

    String getIdentifier();

    OkAuthClient constructOkAuthClient(OAuthAppInfo oauthAppInfo, Requester requester);

}
