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
package com.github.wautsns.okauth.spring.boot.test;

import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.OAuth2Url;
import com.github.wautsns.okauth.core.client.OAuth2ClientManager;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatforms;
import com.github.wautsns.okauth.core.client.kernel.OAuth2Client;
import com.github.wautsns.okauth.core.exception.OAuth2Exception;
import com.github.wautsns.okauth.spring.boot.autoconfigure.OkAuthAutoConfiguration;
import com.github.wautsns.okauth.spring.boot.test.basic.BasicOkAuthTest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * OkAuth test.
 *
 * @author wautsns
 * @since Jul 11, 2020
 */
@Slf4j
@Getter
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OkAuthAutoConfiguration.class)
@SuppressWarnings("SpellCheckingInspection")
public class OkAuthTest extends BasicOkAuthTest {

    @Autowired
    private OAuth2ClientManager manager;

    @Test
    public void listAuthorizeUrls() {
        Set<OAuth2Client<?, ?>> clients = new HashSet<>();
        manager.forEach((name, client) -> clients.add(client));
        clients.stream()
                .sorted(Comparator.comparing(client -> client.getOpenPlatform().getName()))
                .forEach(client -> {
                    String name = client.getOpenPlatform().getName();
                    OAuth2Url authorizeUrl = client.initAuthorizeUrl("onlyForTest");
                    log.info("{}: {}", name, authorizeUrl);
                });
    }

    @Test
    public void baidu() {
        String code = "5b72c01ee1d79cd151576cb2fddf72c8";
        test(BuiltInOpenPlatforms.BAIDU, code);
    }

    @Test
    public void dingTalk() {
        String code = "";
        test(BuiltInOpenPlatforms.DING_TALK, code);
    }

    @Test
    public void elemeShopIsv() {
        String code = "";
        test(BuiltInOpenPlatforms.ELEME_SHOP_ISV, code);
    }

    @Test
    public void gitee() {
        String code = "";
        test(BuiltInOpenPlatforms.GITEE, code);
    }

    @Test
    public void gitHub() {
        String code = "48d566c91d824c84578a";
        test(BuiltInOpenPlatforms.GITHUB, code);
    }

    @Test
    public void oschina() {
        String code = "";
        test(BuiltInOpenPlatforms.OSCHINA, code);
    }

    @Test
    public void tikTok() {
        String code = "";
        test(BuiltInOpenPlatforms.TIK_TOK, code);
    }

    @Test
    public void wechatOfficialAccount() {
        String code = "";
        test(BuiltInOpenPlatforms.WECHAT_OFFICIAL_ACCOUNT, code);
    }

    @Test
    public void wechatWorkCorp() throws OAuth2Exception {
        String code = "";
        test(BuiltInOpenPlatforms.WECHAT_WORK_CORP, code);
    }

}
