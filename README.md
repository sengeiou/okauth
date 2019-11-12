**okauth 目前仍处于开发阶段, 尚未经过大量测试.**

# 1 概述

okauth 是一个开放平台授权登录（即第三方登录）的工具类库, 它可以让开发者在实现第三方登录时，不再需要关注开放平台的 SDK, 仅需要通过简易的 API即可获取令牌与用户信息.

同时, okauth 也已提供 `okauth-spring-boot-starter` 与 spring 集成, 开发者仅需要进行少量配置即可完成 okauth 的自动化装配, 开箱即用.

# 2 入门

## 2.1 引入依赖

**目前仍处于非稳定版本, 尚未发布至中央仓库**

``` xml
<!-- maven -->
<properties>
    <okauth.version>0.1.0</okauth.version>
</properties>

<dependencies>
    <!-- okauth core dependency -->
    <dependency>
        <groupId>com.github.wautsns</groupId>
        <artifactId>okauth-core</artifactId>
    </dependency>
    <!-- spring boot support -->
    <dependency>
        <groupId>com.github.wautsns</groupId>
        <artifactId>okauth-spring-boot-starter</artifactId>
    </dependency>
</dependencies>

<dependencyManagement>
    <dependencies>
        <!-- import okauth dependencies pom -->
        <dependency>
            <groupId>com.github.wautsns</groupId>
            <artifactId>okauth-dependencies</artifactId>
            <version>${okauth-version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
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
    oauth-app-info: # oauth application info
      client-id: client id
      client-secret: client secret
      redirect-uri: redirect uri
  # other open platforms...
```

配置项 `open-platform-expr` 是开放平台客户端的表达式, 有如下两种类型:

1. 已内置的开放平台  
	对于已内置的开放平台客户端, 使用简称即可(不区分大小写, 但要符合指定客户端的标识符), 具体的可配置标识符见 [`BuiltInOpenPlatform`](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/builtin/BuiltInOpenPlatform.java "点击查看源码")
2. 扩展的开放平台(需要是枚举, 且实现接口 [`OpenPlatform`](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/core/OpenPlatform.java "点击查看源码") , 具体实现可以参考 [`BuiltInOpenPlatform`](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/builtin/BuiltInOpenPlatform.java "点击查看源码") )  
	假定有枚举 `a.b.c.ExtendedOpenPlatform` 符合要求 , 并且该枚举类中有一个枚举值为 `XYZ("xyz",...)` , 则表达式为 `a.b.c.ExtendedOpenPlatform:xyz` , 其中 `:` 后面的 `xyz` 不区分大小写. **特别的, 当枚举类中仅包含一个枚举值时, 可省略不写**

### 2.2.2 非 Spring Boot 环境

非 Spring Boot 环境下需要手动构建 `OkAuthManager` , 以下给出代码样例.

``` java
public OkAuthManager initOkAuthManager() {
    OkAuthManagerBuilder builder = new OkAuthManagerBuilder();
    builder.register(new OkAuthClientProperties()
        // see 2.2.1 for details
        .setOpenPlatformExpr("github")
        .setOauthAppInfo(new OAuthAppInfo()
            .setClientId("client id")
            .setClientSecret("client secret")
            .setRedirectUri("redirect uri")));
    // register other open platforms...
    return builder.build();
}
```

## 2.3 使用

在使用之前, 有几个类需要先了解一下, 以便于后面更好的理解与使用.
1. [`OAuthAuthorizeUrlExtendedQuery`](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/core/dto/OAuthAuthorizeUrlExtendedQuery.java "点击查看源码")  
	该类是 OAuth2.0 授权码模式中, 生成 authorize url 时, 能够携带的额外参数, 目前包含两个属性 `state` 与 `scope` . 为了有更好的可扩展性, 便有了该类.
2. [`OAuthRedirectUriQuery`](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/core/dto/OAuthRedirectUriQuery.java "点击查看源码")  
	该类是 OAuth2.0 授权码模式中, 用户授权后, 开放平台重定向至指定 url 时所携带的参数, 目前包含两个属性 `code` 与 `state` . 由于某些开放平台可能返回的属性名不叫 `code` 与 `state` , 为便于兼容与扩展便有了该类.
3. [`OkAuthManager`](/okauth-core/src/main/java/com/github/wautsns/okauth/core/manager/OkAuthManager.java "点击查看源码")  
	该类统一管理了所有已注册的开放平台客户端, 并提供了两个方法用于获取指定的客户端(不会返回 `null` ): 
	1. `getClient(String caseInsensitiveIdentifier)` : 通过标识符(不区分大小写)获取客户端(**更常用**)
	2. `getClient(OpenPlatform openPlatform)` : 通过 `OpenPlatform` 枚举值获取客户端
4. [`OAuthToken`](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/core/dto/OAuthToken.java "点击查看源码")  
	抽象出的 OAuth2.0 令牌, 提供了如下一些方法:
	1. `String getAccessToken()` : 获取访问令牌
	2. `T get(String name)` : 获取指定名称的属性值
	3. `String getString(String name)` : 获取指定名称的属性值,  若值不为 `null`  , 并且类型不为 `String` , 则会调用 `toString()`
	4. `Map<String, Object> getMap(String name)` : 获取指定名称的 `Map`, 若类型不为 `Map` 则会返回 `null`
	5. `Map<String, Object> getOriginalDataMap()` : 获取原始数据集
5. [`OAuthUser`](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/core/dto/OAuthUser.java "点击查看源码")  
	抽象出的 OAuth2.0 用户信息, 提供了如下一些方法:
	1. `String getOpenId()` : 获取用户在开放平台的唯一标识符
	2. `String getNickname()` : 获取用户昵称
	3. `String getAvatarUrl()` : 获取用户头像
	4. 还有其他的一些方法与上述 `4. OAuthToken` 的 2, 3, 4, 5 相同
6. [`OkAuthClient`](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/core/OkAuthClient.java "点击查看源码")  
	该类是所有开放平台客户端的父类, 并提供了以下几个方法:
	
	1. `OpenPlatform getOpenPlatform()` : 获取该客户端对应的 `OpenPlatform` 枚举值
	2. `String initAuthorizeUrl(OAuthAuthorizeUrlExtendedQuery query)` : 用 `query` 初始化一个 authorize url
	3. `OAuthToken exchangeForToken(OAuthRedirectUriQuery query)` : 用 `query` 交换令牌
	4. `OAuthUser exchangeForUser(OAuthToken)` : 用令牌交换用户信息
5. `OAuthUser exchangeForUser(OAuthRedirectUriQuery query)` : 用 `query` 直接交换用户信息
	
	**同时 okauth 提供了 [`StandardOAuthClient`](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/core/standard/StandardOAuthClient.java "点击查看源码") 以便于对遵循了标准 OAuth2.0 的开放平台进行更容易的扩展.**

在大致了解了上述几个类后, 接下来给出一个使用的样例.

``` java
// ...
    private final OkAuthManager okAuthManager;

    @GetMapping("/cmd/redirect-to-authorize-url")
    public String redirectToAuthorizeUrl(String openPlatform) throws OkAuthException {
        String state = "generate state and save if needed";
        return "redirect:" + okAuthManager.getClient(openPlatform)
            .initAuthorizeUrl(new OAuthAuthorizeUrlExtendedQuery()
                .setState(state)
                .setScope("if needed"));
    }

    @GetMapping("/cmd/handle-authorize-callback/{openPlatform}")
    public String handleAuthorizeCallback(
            // some open platforms' callback url may not support query
            @PathVariable String openPlatform,
            OAuthRedirectUriQuery query)
            throws OkAuthException, Exception {
        String state = query.getState();
        // check state if needed(state is used to prevent CSRF attacks)
        OkAuthClient client = okAuthManager.getClient(openPlatform);
        // oauthUser will not be null, because if an error occurs(like
        // code is invalid), an OkAuthException will be thrown
        OAuthUser oauthUser = client.exchangeForUser(query);
        String identifier = client.getOpenPlatform().getIdentifier();
        String openId = oauthUser.getOpenId();
        // next, select user id by identifier and openId and continue
        // processing according to your business logic...
    }
// ...
```

## 2.4 http 客户端配置

okauth 底层默认使用的是 `okhttp3` 作为与开放平台交互的 http 客户端, 支持自定义扩展(详细见进阶内容).

### 2.4.1 Spring Boot 环境

``` yaml
# application.yaml
okauth:
  clients:
    # open-platform-expr: ...
    # oauth-app-info: ...
  - requester:
      # requester-class: in general, no need to set
      # the default values are as follows
      max-concurrent-requests: 64
      max-idle-connections: 5
      keep-alive: 300000
      keep-alive-time-unit: milliseconds
      connect-timeout-milliseconds: 5000
  # other open platforms...
```

### 2.4.2 非 Spring Boot 环境

``` java
public OkAuthManager initOkAuthManager() {
    OkAuthManagerBuilder builder = new OkAuthManagerBuilder();
    builder.register(new OkAuthClientProperties()
        // .setOpenPlatformExpr(...)
        // .setOauthAppInfo(...)
        .setRequester(new OkAuthRequesterProperties()
            // .setRequesterClass() in general, no need to set
            // the default values are as follows
            .setMaxConcurrentRequests(64)
            .setMaxIdleConnections(5)
            .setKeepAlive(5L * 60_000)
            .setKeepAliveTimeUnit(TimeUnit.MILLISECONDS)
            .setConnectTimeoutMilliseconds(5_000));
    // register other open platforms...
    return builder.build();
}
```

# 3 进阶

// TODO 待完善进阶相关文档

# 4 目前已支持的开放平台

| 🏢 开放平台 | ✅ OkAuthClient | 📄 官方文档 |
|:----------:|:---------------:|:----------:|
| Github | [GithubOkAuthClient](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/builtin/github/GithubOkAuthClient.java "点击查看源码") | [查看官方文档](https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/) |
| Gitee | [GiteeOkAuthClient](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/builtin/gitee/GiteeOkAuthClient.java "点击查看源码") | [查看官方文档](https://gitee.com/api/v5/oauth_doc) |
| Baidu | [BaiduOkAuthClient](/okauth-core/src/main/java/com/github/wautsns/okauth/core/client/builtin/baidu/BaiduOkAuthClient.java "点击查看源码") | [查看官方文档](http://developer.baidu.com/wiki/index.php?title=docs/oauth) |
