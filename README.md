# 1 概述

okauth 是一个开放平台授权登录（即第三方登录）的工具类库, 它可以让开发者在实现第三方登录时，不再需要关注开放平台的 SDK, 仅需要通过简易的 API 即可获取令牌与用户信息.

目前, okauth 也已提供 okauth-spring-boot-starter 与 spring boot 集成, 开发者仅需要进行少量配置即可完成 okauth 的自动化装配, 开箱即用.

# 2 入门

## 2.1 引入依赖

``` xml
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

## 2.2 初始化 OkAuthManager

`OkAuthManager` 是 okauth 中一个重要的类, 可以通过这个管理器获取已注册的所有开放平台客户端, 以下根据是否为 Spring Boot 环境分为两种初始化方式.

### 2.2.1 Spring Boot 环境

在 Spring Boot 环境下, 在配置好 `application.yaml` 后便会自动装配 `OkAuthManager` , 开箱即用.

``` yaml
# application.yaml
okauth:
  clients:
  - open-platform-expr: github # see below
    oauth-app-info: # oauth 应用信息
      client-id: client id
      client-secret: client secret
      redirect-uri: redirect uri
```

配置项 `open-platform-expr` 是开放平台客户端的表达式, 有如下两种类型:

1. 已内置的开放平台  
	对于已内置的开放平台客户端, 使用简称即可(不区分大小写, 但要符合指定客户端的标识符), 具体的可配置标识符见 [`BuiltInOpenPlatform`](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/builtin/BuiltInOpenPlatform.java "点击查看源码")
2. 扩展的开放平台(需要是枚举, 且实现接口 [`OkAuthClientInitializer`](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/core/OkAuthClientInitializer.java "点击查看源码") , 具体实现可以参考 [`BuiltInOpenPlatform`](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/builtin/BuiltInOpenPlatform.java "点击查看源码") )  
	假定有枚举 `a.b.c.ExtendedOpenPlatform` 符合要求 , 并且该枚举类中有一个枚举值为 `XYZ("xyz",...)` , 则表达式为 `a.b.c.ExtendedOpenPlatform:xyz` , 其中 `:` 后面的 `xyz` 不区分大小写. **特别的, 当枚举类中仅包含一个枚举值时, 可省略不写**

### 2.2.2 非 Spring Boot 环境

非 Spring Boot 环境下需要手动构建 `OkAuthManager` , 以下给出代码样例.

``` java
public OkAuthManager initOkAuthManager() {
    OkAuthManagerBuilder builder = new OkAuthManagerBuilder();
    OkAuthProperties properties = new OkAuthProperties();
    properties.setClients(Arrays.asList(
        new OkAuthClientProperties()
            // openPlatformExpr 的详细描述见 2.2.1
            .setOpenPlatformExpr("github")
            .setOauthAppInfo(new OAuthAppInfo()
                .setClientId("client id")
                .setClientSecret("client secret")
                .setRedirectUri("redirect uri"))));
    return builder.register(properties).build();
}
```

## 2.3 使用

在使用之前, 有几个类需要先了解一下, 以便于后面更好的理解与使用.
1. [`OkAuthManager`](/okauth-core/src/main/java/com/github/wautsns/okauth/core/manager/OkAuthManager.java "点击查看源码")  
	该类统一管理了所有已注册的开放平台客户端, 并提供了两个方法用于获取指定的客户端(不会返回 `null` ,若不存在则会抛出异常): 
	
	1. `getClient(String identifier)` : 通过标识符(不区分大小写)获取客户端
	2. `getClient(OpenPlatform openPlatform)` : 通过 `OpenPlatform` 枚举值获取客户端
2. [`OAuthRedirectUriQuery`](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/core/dto/OAuthRedirectUriQuery.java "点击查看源码")  
	在 OAuth2.0 授权码模式中, 用户授权后, 开放平台重定向至指定 uri 时所携带的参数, 目前包含两个属性 `code` 与 `state` . 由于某些开放平台可能返回的属性名不叫 `code` 与 `state` , 为便于以后的兼容与扩展便有了该类.
3. [`OAuthToken`](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/core/dto/OAuthToken.java "点击查看源码")  
	OAuth2.0 令牌类, 提供了如下一些方法:
	1. `String getAccessToken()` : 获取访问令牌(使用属性名 `access_token` )
	2. `T get(String name)` : 获取指定名称的值
	3. `String getString(String name)` : 获取指定名称的字符串形式值, 若值为 `null` , 则返回 `null` ; 若值类型为 `String` 直接返回, 若值是 `Number` 或 `Boolean` 的实例, 则通过 `toString()` 返回字符串; 否则返回对应 JSON 字符串
	4. `Map<String, Object> getMap(String name)` : 获取指定名称的 Map 对象, 若不存在或类型不为 `Map` 则返回 `null`
	5. `Map<String, Object> getOriginalDataMap()` : 获取原始数据集
4. [`OAuthUser`](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/core/dto/OAuthUser.java "点击查看源码")  
	抽象出的 OAuth2.0 用户信息, 提供了如下一些方法:
	
	1. `OpenPlatform getOpenPlatform()` : 获取该用户所在的开放平台
	2. `String getOpenPlatformIdentifier()` : 获取该用户所在的开放平台标识符
	3. `String getOpenId()` : 获取用户在开放平台的唯一标识符
	4. `String getNickname()` : 获取用户昵称
	5. `String getAvatarUrl()` : 获取用户头像
	6. 还有其他的一些方法与上述 `3. OAuthToken` 的 2, 3, 4, 5 相同, 这里不作赘述
5. [`OkAuthClient`](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/core/OkAuthClient.java "点击查看源码")  
	所有开放平台客户端都需要继承该父类, 该类提供了以下几个方法:
	1. `OpenPlatform getOpenPlatform()` : 获取该客户端对应的开放平台
	2. `String initAuthorizeUrl(String state)` : 初始化一个 authorize url(state 用于防止 CSRF 攻击)
	3. `OAuthToken requestToken(OAuthRedirectUriQuery query)` : 用 `query` 请求令牌
	4. `OAuthUser requestUser(OAuthToken token)` : 用令牌请求用户信息
	5. `OAuthUser requestUser(OAuthRedirectUriQuery query)` : 用 `query` 直接请求用户信息

	**okauth 提供了 [`StandardOkAuthClient`](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/core/StandardOkAuthClient.java "点击查看源码") 以便于对遵循了标准 OAuth2.0 的开放平台进行更容易的扩展.**

在大致了解了上述几个类后, 接下来给出一个使用的样例.

``` java
@Controller
@RequestMapping("/api/cmd")
@RequiredArgsConstructor // lombok 注解
public class OAuthController {

    private final OkAuthManager okauthManager;

    @GetMapping("/redirect-to-authorize-url")
    public String redirectToAuthorizeUrl(String openPlatform) throws OkAuthException {
        String state = "generate state and save if needed";
        return "redirect:" + okauthManager.getClient(openPlatform)
            .initAuthorizeUrl(state);
    }

    @GetMapping("/handle-authorize-callback/{openPlatform}")
    public String handleAuthorizeCallback(
            // 某些开放平台的 redirect uri 不支持 query, 故采用这种方式
            @PathVariable String openPlatform, OAuthRedirectUriQuery query)
            throws OkAuthException {
        // 如果有需要可以对 state 进行校验
        String state = query.getState();
        OkAuthClient client = okauthManager.getClient(openPlatform);
        // oauthUser 不会为 null, 错误将以异常的形式抛出
        OAuthUser oauthUser = client.requestUser(query);
        String identifier = oauthUser.getOpenPlatformIdentifier();
        String openId = oauthUser.getOpenId();
        // 接下来可以通过 identifier 与 openId 获取 userId 完成业务逻辑
    }

}
```

## 2.4 http 客户端配置

okauth 底层默认使用的是 `okhttp3` 作为与开放平台交互的 http 客户端, 支持自定义扩展(详细见进阶内容).

### 2.4.1 Spring Boot 环境

``` yaml
# application.yaml
okauth:
  # 默认的 http 请求器, 默认值如下
  default-requester:
    requester-class: com.github.wautsns.okauth.core.client.util.http.builtin.okhttp.OkHttpRequester
    connect-timeout-milliseconds: 7000
    max-concurrent-requests: 64
    max-idle-connections: 8
    keep-alive: 300000
    keep-alive-time-unit: milliseconds
  clients:
  - open-platform-expr: ...
    oauth-app-info: ...
    # 这里配置的值优先级比默认高, 且仅作用于该开放平台
    requester:
      max-concurrent-requests: 200
      connect-timeout-milliseconds: 7000
      keep-alive: 300000
      # 若设置了 keep-alive-time-unit 则必须同时设置 keep-alive, 否则不会生效
      keep-alive-time-unit: milliseconds
```

### 2.4.2 非 Spring Boot 环境

``` java
public OkAuthManager initOkAuthManager() {
    OkAuthManagerBuilder builder = new OkAuthManagerBuilder();
    OkAuthProperties properties = new OkAuthProperties();
    // null 值会用默认值替代
    properties.setDefaultProperties(...);
    properties.setClients(Arrays.asList(
        new OkAuthClientProperties()
            .setOpenPlatformExpr(...)
            .setOauthAppInfo(...)
            // 这里配置的值优先级比默认高, 且仅作用于该开放平台
            .setRequester(new OkAuthRequesterProperties()
                .setMaxConcurrentRequests(200)
                .setConnectTimeoutMilliseconds(7_000)
                .setKeepAlive(300000)
                // 若设置了 keep-alive-time-unit 则必须同时设置 keep-alive, 否则不会生效
                .setKeepAliveTimeUnit(TimeUnit.MILLISECONDS))));
    return builder.register(properties).build();
}
```

# 3 进阶

// TODO 待完善进阶相关文档

# 4 目前已支持的开放平台

| 🏢 开放平台 | ✅ OkAuthClient | 📄 官方文档 |
|:----------|:---------------:|:----------:|
| Baidu(百度) | [BaiduOkAuthClient](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/builtin/baidu/BaiduOkAuthClient.java "点击查看源码") | [查看官方文档](http://developer.baidu.com/wiki/index.php?title=docs/oauth) |
| Gitee(码云) | [GiteeOkAuthClient](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/builtin/gitee/GiteeOkAuthClient.java "点击查看源码") | [查看官方文档](https://gitee.com/api/v5/oauth_doc) |
| GitHub | [GitHubOkAuthClient](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/builtin/github/GitHubOkAuthClient.java "点击查看源码") | [查看官方文档](https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/) |
| MicroBlog(微博) | [MicroBlogOkAuthClient](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/builtin/microblog/MicroBlogOkAuthClient.java "点击查看源码") | [查看官方文档](https://open.weibo.com/wiki/%E6%8E%88%E6%9D%83%E6%9C%BA%E5%88%B6%E8%AF%B4%E6%98%8E) |
| OSChina(开源中国) | [OSChinaOkAuthClient](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/builtin/oschina/OSChinaOkAuthClient.java "点击查看源码") | [查看官方文档](https://www.oschina.net/openapi/docs) |
| ~~WeChat(微信)~~ | [***尚未经过测试***](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/builtin/wechat/WeChatOkAuthClient.java "点击查看源码") | [查看官方文档](https://developers.weixin.qq.com/doc/oplatform/Website_App/WeChat_Login/Wechat_Login.html) |
