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
package com.github.wautsns.okauth.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.client.builtin.baidu.BaiduOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.builtin.baidu.BaiduOAuth2Client;
import com.github.wautsns.okauth.core.client.builtin.gitee.GiteeOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.builtin.gitee.GiteeOAuth2Client;
import com.github.wautsns.okauth.core.client.builtin.github.GitHubOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.builtin.github.GitHubOAuth2Client;
import com.github.wautsns.okauth.core.client.builtin.oschina.OSChinaOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.builtin.oschina.OSChinaOAuth2Client;
import com.github.wautsns.okauth.core.client.builtin.oschina.model.OSChinaOAuth2Token;
import com.github.wautsns.okauth.core.client.builtin.wechatworkcorp.WechatWorkCorpOAuth2AppInfo;
import com.github.wautsns.okauth.core.client.builtin.wechatworkcorp.WechatWorkCorpOAuth2Client;
import com.github.wautsns.okauth.core.client.builtin.wechatworkcorp.model.WechatWorkCorpOAuth2User;
import com.github.wautsns.okauth.core.client.kernel.TokenAvailableOAuth2Client;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2Token;

import java.util.LinkedList;

/**
 * OAuth2 test.
 *
 * @author wautsns
 * @since May 22, 2020
 */
public class OAuth2Test {

    public static void main(String[] args) throws Exception {
        String code = "JRtc22";
        //tokenAvailable(oschina(), code);
        WechatWorkCorpOAuth2Client client = weChatWorkCorp();
        print(client.getToken());
        WechatWorkCorpOAuth2User user = client.exchangeForUser("1657");
        print(user);
        //client.exchangeForOpenid(new OAuth2RedirectUriQuery().setCode(code));
        //print(client.initAuthorizeUrl(null).toString());
        //print(client.getToken().getOriginalDataMap());
    }

    private static <T extends OAuth2Token> void tokenAvailable(TokenAvailableOAuth2Client<?, T, ?> client, String code)
            throws Exception {
        print(client.initAuthorizeUrl(null).toString());
        //T token = client.exchangeForToken(new OAuth2RedirectUriQuery().setCode(code));
        DataMap dataMap = new DataMap();
        dataMap.put("access_token", "e5af859c-ba05-4967-8724-7d9269e5920a");
        dataMap.put("refresh_token", "602f8907-0569-42e2-9fd7-f7cff87f9474");
        OSChinaOAuth2Token token = new OSChinaOAuth2Token(dataMap);
        print(token);
        print(((OSChinaOAuth2Client)client).exchangeForUser(token));
    }

    // #################### internal ####################################################

    private static final ObjectMapper OM = new ObjectMapper();

    private static void print(Object value) throws Exception {
        System.out.println(OM.writeValueAsString(value));
    }

    // #################### open platform ###############################################

    private static BaiduOAuth2Client baidu() {
        BaiduOAuth2AppInfo appInfo = new BaiduOAuth2AppInfo()
                .setApiKey("7infkIe5sNdq9IOCdNQjEfH5")
                .setSecretKey("RtWfee3kgR8vdRmEt8Xvm7G4VP2DizV6")
                .setRedirectUri("http://per1024.com/api/cmd/handle-authorization-callback/baidu");
        return new BaiduOAuth2Client(appInfo);
    }

    private static GiteeOAuth2Client gitee() {
        GiteeOAuth2AppInfo appInfo = new GiteeOAuth2AppInfo()
                .setClientId("6f4a2c896669ca39538ada496440225a5b8efa8c6a66195896fa18196530715c")
                .setClientSecret("7220a4a6fce4eaa74b686048a9db8021e71eaf93ce91020b8f1088d7806427a0")
                .setRedirectUri("http://per1024.com/api/cmd/handle-authorization-callback/gitee");
        appInfo.setScopes(new LinkedList<>());
        appInfo.getScopes().add(GiteeOAuth2AppInfo.Scope.USER_INFO);
        appInfo.getScopes().add(GiteeOAuth2AppInfo.Scope.PROJECTS);
        return new GiteeOAuth2Client(appInfo);
    }

    private static GitHubOAuth2Client github() {
        GitHubOAuth2AppInfo appInfo = new GitHubOAuth2AppInfo()
                .setClientId("ae43815167e88e2bfdf7")
                .setClientSecret("d88861d46d4727451d7ba93ea0b8c908e8600f7c")
                .setRedirectUri("http://per1024.com/api/cmd/handle-authorization-callback/github");
        return new GitHubOAuth2Client(appInfo);
    }

    private static OSChinaOAuth2Client oschina() {
        OSChinaOAuth2AppInfo appInfo = new OSChinaOAuth2AppInfo()
                .setClientId("lXCzhBGEqrwnhBdk1QGv")
                .setClientSecret("psOXgZUtXSi3cJ5KELlotL8ur3QWAOL9")
                .setRedirectUri("http://per1024.com/api/cmd/handle-authorization-callback/oschina");
        return new OSChinaOAuth2Client(appInfo);
    }

    private static WechatWorkCorpOAuth2Client weChatWorkCorp() {
        WechatWorkCorpOAuth2AppInfo appInfo = new WechatWorkCorpOAuth2AppInfo()
                .setCorpId("wwad5387da586768d8")
                .setCorpSecret("MEsk6ytKetMfjkehk8T2GoCTh6Zbmq1KbIYxurO93lQ")
                .setRedirectUri("http://per1024.com/api/cmd/handle-authorization-callback/oschina")
                .setAgentId("1000002");
        // addressBookSecret: ALiaGD6WodorwCRqLITgAS4JwLo7cNNTeQ0zeeqxM98
        return new WechatWorkCorpOAuth2Client(appInfo);
    }

}
