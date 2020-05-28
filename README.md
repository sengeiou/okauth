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

## 2.2 OAuth2ClientManager

`OAuth2ClientManager` 是 okauth 中用于统一管理开放平台客户端的类.

### 2.2.1 Spring Boot

在 Spring Boot 环境下, 在配置好如 `application.yaml` 的配置后便会自动装配 `OAuth2ClientManager` , 开箱即用.

``` yaml
# application.yaml
okauth:
  # enabled: true 必须填写(下同), 否则不会自动装配
  enabled: true
  apps-info:
    github:
      enabled: true
      app-info:
        client-id: CLIENT_ID
        client-secret: CLIENT_SECRET
        redirect-uri: REDIRECT_URI
        # scope 为枚举列表, 可自动提示
        scope: [user_email, notifications]
        extra-authorize-url-query:
          allow-signup: disabled
```

### 2.2.2 非 Spring Boot

在非 Spring Boot 环境下, 可通过如下方式配置

``` java
private static GitHubOAuth2Client github() {
    GitHubOAuth2AppInfo appInfo = new GitHubOAuth2AppInfo()
            .setClientId("CLIENT_ID")
            .setClientSecret("CLIENT_SECRET")
            .setRedirectUri("REDIRECT_URI");
    appInfo.getExtraAuthorizeUrlQuery()
        .setAllowSignup(GitHubOAuth2AppInfo.ExtraAuthorizeUrlQuery.AllowSignup.DISABLED);
    return new GitHubOAuth2Client(appInfo);
}
```

## 2.3 简单示例

``` java
@Controller
@RequestMapping("/api/cmd/oauth2")
@RequiredArgsConstructor
public class OAuth2Controller {

    /** OAuth2 client manager. */
    private final OAuth2ClientManager manager;

    @GetMapping("/redirect-to-authorize-url")
    public String redirectToAuthorizeUrl(String openPlatform) throws OAuth2Exception {
        String state = "generate state and save if needed";
        return "redirect:" + manager.get(openPlatform).initAuthorizeUrl(state);
    }

    @GetMapping("/handle-authorize-callback/{openPlatform}")
    public String handleAuthorizeCallback(
            // Some open platforms do not support adding query to the redirect uri!
            @PathVariable String openPlatform, OAuth2RedirectUriQuery query)
            throws OAuth2Exception {
        // check state if needed
        String state = query.getState();
        OAuth2Client<?, ?> client = manager.get(openPlatform);
        openPlatform = client.getOpenPlatform();
        String openid = client.exchangeForOpenid(query);
        // ... business logic
        return "something";
    }

}
```

## 2.4 Http 客户端配置

okauth 底层默认使用的是 `httpClient4` 作为与开放平台交互的 Http 客户端, 支持一部分的参数设置, 若有特殊需求也可以自定义实现.

### 2.4.1 Spring Boot 环境

``` yaml
# application.yaml
okauth:
  # default http client properties are as followers:
  default-http-client:
    implementation: com.github.wautsns.okauth.core.assist.http.builtin.httpclient4.HttpClient4OAuth2HttpClient
    properties:
      connect-timeout: 3S
      read-timeout: 7S
      max-concurrent-requests: 64
      max-idle-connections: 16
      max-idle-time: 5M
      keep-alive-timout: 3M
      retry-times: 1
  apps-info:
    github:
      app-info:
        # ....
      http-client:
        properties:
          connect-timeout: 5S
          retry-times: 3
```

### 2.4.2 非 Spring Boot 环境

``` java
HttpClient4OAuth2HttpClient oauth2HttpClient = new HttpClient4OAuth2HttpClient(
    OAuth2HttpClientProperties.initDefault()
        .setConnectTimeout(Duration.ofSeconds(5))
        .setRetryTimes(3));
GitHubOAuth2Client client = new GitHubOAuth2Client(null, oauth2HttpClient);
```

# 3 进阶

// TODO 待完善进阶相关文档

# 4 目前已支持的开放平台

| 🏢 开放平台 | ✅ OkAuthClient | 📄 官方文档 |
|:----------|:---------------:|:----------:|
| Baidu(百度) | [BaiduOAuth2Client](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/builtin/baidu/BaiduOAuth2Client.java "点击查看源码") | [查看官方文档](http://developer.baidu.com/wiki/index.php?title=docs/oauth) |
| Gitee(码云) | [GiteeOAuth2Client](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/builtin/gitee/GiteeOAuth2Client.java "点击查看源码") | [查看官方文档](https://gitee.com/api/v5/oauth_doc) |
| GitHub | [GitHubOAuth2Client](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/builtin/github/GitHubOAuth2Client.java "点击查看源码") | [查看官方文档](https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/) |
| OSChina(开源中国) | [OSChinaOAuth2Client](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/builtin/oschina/OSChinaOAuth2Client.java "点击查看源码") | [查看官方文档](https://www.oschina.net/openapi/docs) |
| ~~WeChatWorkCorp(企业微信-企业内部应用)~~ | [~~WeChatWorkCorpOAuth2Client~~](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/builtin/wechat/work/corp/WeChatWorkCorpOAuth2Client.java "点击查看源码(尚未通过完整验证)") | [查看官方文档](https://work.weixin.qq.com/api/doc/90000/90135/91039) |
