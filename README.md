okauth
======

目前仍处于开发阶段, 尚未经过大量测试.

------------------------------------------------------------------------

简述
----

okauth 是一个**开放平台授权登录（即第三方登录）**的工具类库，它可以让开发者在实现第三方登录时，不再需要关注开放平台的 SDK，仅需要通过**简易的 API** 即可获取令牌与用户信息。

同时，okauth 也已提供 okauth-spring-boot-starter 与 spring 集成，开发者仅需要进行少量配置即可完成 okauth 的自动化装配, 开箱即用。

入门级使用(基于 okauth-spring-boot-starter)
-------------------------------------------

1.  引入依赖(目前尚未发布至中央仓库)

    ``` xml
    <dependency>
        <groupId>com.github.wautsns</groupId>
        <artifactId>okauth-spring-boot-starter</artifactId>
        <version>0.1.0</version>
    </dependency>
    ```

2.  配置 application.yaml

    ``` yaml
    okauth:
      clients:
        # see OkAuthClientProperties#openPlatformExpr for details
      - open-platform-expr: github
        # oauth application info
        oauth-app-info:
           client-id: your client id
           client-secret: your client secret
           redirect-uri: your redirect uri
    ```

3.  引入 OkAuthManager 并使用

    ``` java
    // ...
        private final OkAuthManager okAuthManager;

        /**
         * Oauth login.
         * 
         * @param openPlatform open platform identifier(case insensitive)
         * @param query oauth redirect uri query(contains state and code)
         * @throws OkAuthException if an oauth exception occurs
         * @throws OtherExceptions ...
         */
        @GetMapping("/cmd/oauth-login/{openPlatform}")
        public Token oauthLogin(
                @PathVariable String openPlatform,
                OAuthRedirectUriQuery query)
                throws OkAuthException, OtherExceptions {
            String state = query.getState();
            // check state if needed(state is used to prevent CSRF attacks)
            OkAuthClient client = okAuthManager.getClient(openPlatform);
            OAuthUser oauthUser = client.exchangeForUser(query);
            // oauthUser will not be null, because if an error occurs(like
            // code is invalid), an OkAuthException will be thrown
            String identifier = client.getOpenPlatform().getIdentifier();
            String openId = oauthUser.getOpenId();
            // select user by identifier and openId...
        }
    // ...
    ```

目前已支持的开放平台
--------------------

| 🏢 开放平台 | ✅ OkAuthClient | 📄 官方文档 |
|:----------:|:---------------:|:----------:|
| Github | [GithubOkAuthClient](https://github.com/wautsns/okauth/blob/master/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/builtin/github/GithubOkAuthClient.java) | [查看官方文档](https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/) |
| Gitee | [GiteeOkAuthClient](https://github.com/wautsns/okauth/blob/master/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/builtin/gitee/GiteeOkAuthClient.java) | [查看官方文档](https://gitee.com/api/v5/oauth_doc) |
| Baidu | [BaiduOkAuthClient](https://github.com/wautsns/okauth/blob/master/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/builtin/baidu/BaiduOkAuthClient.java) | [查看官方文档](http://developer.baidu.com/wiki/index.php?title=docs/oauth) |


