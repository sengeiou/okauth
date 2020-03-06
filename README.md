# 1 概述

okauth 是一个开放平台授权登录(即第三方登录)的工具类库, 它可以让开发者在实现第三方登录时，不再需要关注开放平台的 SDK, 仅需要通过简易的 API 即可获取令牌与用户信息.

目前, okauth 也已提供 okauth-spring-boot-starter 与 spring boot 集成, 开发者仅需要进行少量配置即可完成 okauth 的自动化装配, 开箱即用.

# 2 入门

## 2.1 引入依赖

``` xml
<properties>
    <okauth.version>x.y.z</okauth.version>
</properties>

<dependencies>
    <!-- core -->
    <dependency>
        <groupId>com.github.wautsns</groupId>
        <artifactId>okauth-core</artifactId>
        <version>${okauth.version}</version>
    </dependency>
    <!-- spring boot starter -->
    <dependency>
        <groupId>com.github.wautsns</groupId>
        <artifactId>okauth-spring-boot-starter</artifactId>
        <version>${okauth.version}</version>
    </dependency>
</dependencies>
```

## 2.2 初始化 OAuthClients

`OAuthClients` 是 okauth 中用于管理已注册开放平台客户端的类.

### 2.2.1 Spring Boot 环境

在 Spring Boot 环境下, 在配置好如 `application.yaml` 的配置后便会自动装配 `OAuthClients` , 开箱即用.

``` yaml
# application.yaml
okauth:
  github:
    oauth-app:
      client-id: yourGitHubClientId
      client-secret: yourGitHubClientSecret
      redirect-uri: yourGitHubRedirectUri
```

### 2.2.2 非 Spring Boot 环境

在非 Spring Boot 环境下, 可通过如下方式配置

``` java
OAuthClients oauthClients = new OAuthClientsBuilder()
    .register(BuiltInOpenPlatform.GITEE.initOAuthClient(
        new OAuthAppProperties()
            .setClientId("yourGitHubClientId")
            .setClientSecret("yourGitHubClientSecret")
            .setRedirectUri("yourGitHubRedirectUri")))
    .build();
```

## 2.3 使用

在使用之前, 有几个类需要先了解一下, 以便于后面更好的理解与使用.
1. [`OAuthClients`](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/OAuthClients.java "点击查看源码")  
该类统一管理了所有已注册的开放平台客户端, 并提供了两个方法用于获取指定的客户端(若不存在则会抛出异常):
	1. `getClient(OpenPlatform openPlatform)` : 通过 `OpenPlatform` 获取 `OAuthClient`
	2. `getClient(String name)` : 通过开放平台英文名称(不区分大小写)获取客户端
2. [`OAuthRedirectUriQuery`](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/kernel/model/OAuthRedirectUriQuery.java "点击查看源码")  
在 OAuth2.0 授权码模式中, 用户授权后, 开放平台重定向至指定 uri 时所携带的参数, 目前包含两个属性 `code` 与 `state` . 为便于以后的兼容与扩展便有了该类.
3. [`OAuthToken`](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/kernel/model/OAuthToken.java "点击查看源码")  
OAuth2.0 token 信息, 可以获取如下属性(若不支持, 则为 `null` ): `access_token` , `expires_in` , `refresh_token` . 若需要其他属性可通过 `originalDataMap` 获取.
4. [`OAuthUser`](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/kernel/model/OAuthUser.java "点击查看源码")  
抽象出的 OAuth2.0 用户信息, 各个开放平台具有各自的实现类, 可以获取如下属性(若不支持, 则为 `null`): `openPlatform` (不会为 null) , `openid` (不会为 null), `unionid` , `username` , `nickname` , `avatarUrl` , `gender` , `birthday` . 若需要其他属性可通过 `originalDataMap` 获取.
5. [`OAuthClient`](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/kernel/OAuthClient.java "点击查看源码")  
抽象的 OAuth2.0 客户端. 该客户端提供了通过 `OAuthRedirectUriQuery` 获取 `openid` 与 `OAuthUser` 的 API.
6. [`TokenAvailableOAuthClient`](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/kernel/TokenAvailableOAuthClient.java "点击查看源码")  
该类继承自 `OAuthClient` , 若开放平台支持获取 token, 则对应的客户端应是该类的子类. 该客户端额外提供了通过 `OAuthRedirectUriQuery` 获取 `OAuthToken` , 通过 `OAuthToken` 获取 `openid` 与 `OAuthUser` 的 API.
7. [`TokenRefreshableOAuthClient`](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/kernel/TokenRefreshableOAuthClient.java "点击查看源码")  
该类继承自 `TokenAvailableOAuthClient` , 若开放平台支持刷新 token, 则对应的客户端应是该类的子类. 该客户端额外提供了通过旧 `OAuthToken` 刷新为新 `OAuthToken` 的 API.

在大致了解了上述几个类后, 接下来给出一个使用的简单样例.

``` java
@Controller
@RequestMapping("/api/cmd")
@RequiredArgsConstructor // lombok 注解
public class TestController {

    private final OAuthClients oauthClients;

    @GetMapping("/redirect-to-authorize-url")
    public String redirectToAuthorizeUrl(String openPlatform) throws OAuthException {
        String state = "generate state and save if needed";
        return "redirect:" + oauthClients.getClient(openPlatform)
            .initAuthorizeUrl(state);
    }

    @GetMapping("/handle-authorize-callback/{openPlatformName}")
    public String handleAuthorizeCallback(
        // Some open platforms do not support adding query to the redirect uri!
        @PathVariable String openPlatformName, OAuthRedirectUriQuery query) throws OAuthException {
        String state = query.getState();
        // check state if needed
        OAuthClient client = oauthClients.getClient(openPlatformName);
        OpenPlatform openPlatform = client.getOpenPlatform();
        String openid = client.exchangeForOpenid(query);
        // ... business logic
        return "something";
    }

}
```

## 2.4 http 客户端配置

okauth 底层默认使用的是 `okhttp3` 作为与开放平台交互的 http 客户端, 支持一部分的参数设置, 若有特殊需求也可以自定义扩展(自定义扩展的详细方式见进阶内容).

### 2.4.1 Spring Boot 环境

``` yaml
# application.yaml
okauth:
  # default http client properties are as followers:
  default-http-client:
    connect-timeout-milliseconds: 7000
    max-concurrent-requests: 64
    max-idle-connections: 8
    keep-alive: 5m
  github:
    http-client:
      # you can set value here to overwrite
      # default http client properties 
      keep-alive: 15m

```

### 2.4.2 非 Spring Boot 环境

``` java
OAuthClients oauthClients = new OAuthClientsBuilder()
    .register(BuiltInOpenPlatform.GITEE.initOAuthClient(
        new OAuthAppProperties()
            .setClientId("yourGitHubClientId")
            .setClientSecret("yourGitHubClientSecret")
            .setRedirectUri("yourGitHubRedirectUri"),
        new OkHttp3HttpClient(HttpClientProperties.initDefault()
            .setKeepAlive(Duration.ofMinutes(3)))))
    .build();
```

# 3 进阶

// TODO 待完善进阶相关文档

# 4 目前已支持的开放平台

| 🏢 开放平台 | ✅ OkAuthClient | 📄 官方文档 |
|:----------|:---------------:|:----------:|
| Baidu(百度) | [BaiduOkAuthClient](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/builtin/baidu/BaiduOAuthClient.java "点击查看源码") | [查看官方文档](http://developer.baidu.com/wiki/index.php?title=docs/oauth) |
| ~~DingTalk(钉钉)~~ | [***尚未经过测试***](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/builtin/dingtalk/DingTalkOAuthClient.java "点击查看源码") | [查看官方文档](https://ding-doc.dingtalk.com/doc#/serverapi2/kymkv6) |
| Gitee(码云) | [GiteeOkAuthClient](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/builtin/gitee/GiteeOAuthClient.java "点击查看源码") | [查看官方文档](https://gitee.com/api/v5/oauth_doc) |
| GitHub | [GitHubOkAuthClient](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/builtin/github/GitHubOAuthClient.java "点击查看源码") | [查看官方文档](https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/) |
| MicroBlog(微博) | [MicroBlogOkAuthClient](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/builtin/microblog/MicroBlogOAuthClient.java "点击查看源码") | [查看官方文档](https://open.weibo.com/wiki) |
| OSChina(开源中国) | [OSChinaOkAuthClient](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/builtin/oschina/OSChinaOAuthClient.java "点击查看源码") | [查看官方文档](https://www.oschina.net/openapi/docs) |
