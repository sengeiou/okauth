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
package com.github.wautsns.okauth.spring.boot.test.basic;

import com.github.wautsns.okauth.core.client.OAuth2ClientManager;
import com.github.wautsns.okauth.core.client.kernel.OAuth2Client;
import com.github.wautsns.okauth.core.client.kernel.TokenAvailableOAuth2Client;
import com.github.wautsns.okauth.core.client.kernel.TokenRefreshableOAuth2Client;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2RedirectUriQuery;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2RefreshableToken;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2Token;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2User;
import com.github.wautsns.okauth.core.client.kernel.openplatform.OpenPlatform;
import com.github.wautsns.okauth.core.exception.OAuth2Exception;
import com.github.wautsns.okauth.core.exception.specific.openplatform.UnsupportedOpenPlatformException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;

import java.util.UUID;

/**
 * Basic OkAuth test.
 *
 * @author wautsns
 * @since Jul 11, 2020
 */
@Slf4j
public abstract class BasicOkAuthTest {

    protected abstract OAuth2ClientManager getManager();

    protected void test(OpenPlatform openPlatform, String code) {
        try {
            OAuth2Client<?, ?> client = getManager().get(openPlatform);
            if (client instanceof TokenRefreshableOAuth2Client) {
                testTokenRefreshableOAuth2Client((TokenRefreshableOAuth2Client<?, ?, ?>) client, code);
            } else if (client instanceof TokenAvailableOAuth2Client) {
                testTokenAvailableOAuth2Client((TokenAvailableOAuth2Client<?, ?, ?>) client, code);
            } else {
                testOAuth2Client(client, code);
            }
        } catch (UnsupportedOpenPlatformException e) {
            log.warn(e.getMessage());
        } catch (OAuth2Exception e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    private void testOAuth2Client(OAuth2Client<?, ?> client, String code) throws OAuth2Exception {
        showAuthorizeUrl(client);
        showUser(client.exchangeForUser(initRedirectUriQuery(code)));
    }

    private <T extends OAuth2Token> void testTokenAvailableOAuth2Client(
            TokenAvailableOAuth2Client<?, T, ?> client, String code) throws OAuth2Exception {
        showAuthorizeUrl(client);
        T token = showToken(client.exchangeForToken(initRedirectUriQuery(code)));
        showUser(client.exchangeForUser(token));
    }

    private <T extends OAuth2RefreshableToken> void testTokenRefreshableOAuth2Client(
            TokenRefreshableOAuth2Client<?, T, ?> client, String code) throws OAuth2Exception {
        showAuthorizeUrl(client);
        T token = showToken(client.exchangeForToken(initRedirectUriQuery(code)));
        showUser(client.exchangeForUser(token));
        showToken(client.refreshToken(token));
    }

    // ==================== utils =======================================================

    private void showAuthorizeUrl(OAuth2Client<?, ?> client) {
        show("authorizeUrl", client.initAuthorizeUrl(UUID.randomUUID().toString()));
    }

    private OAuth2RedirectUriQuery initRedirectUriQuery(String code) {
        return new OAuth2RedirectUriQuery().setCode(code);
    }

    private <T extends OAuth2Token> T showToken(T token) {
        return show("token", token);
    }

    private void showUser(OAuth2User user) {
        show("user", user);
    }

    private <T> T show(String name, T value) {
        Assert.assertNotNull(value);
        log.info("{} => {}", name, value);
        return value;
    }

}
