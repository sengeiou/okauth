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
package com.github.wautsns.okauth.core.client.builtin;

import com.github.wautsns.okauth.core.client.kernel.openplatform.OpenPlatform;
import lombok.experimental.UtilityClass;

/**
 * Built-in open platform names.
 *
 * @author wautsns
 * @since May 17, 2020
 */
@UtilityClass
public class BuiltInOpenPlatformNames {

    /** Baidu(百度). */
    public static final OpenPlatform BAIDU = new OpenPlatform("Baidu");
    /** DingTalk(钉钉). */
    public static final OpenPlatform DING_TALK = new OpenPlatform("DingTalk");
    /** ElemeShopIsv(饿了么-商家开放平台-企业应用/平台应用). */
    public static final OpenPlatform ELEME_SHOP_ISV = new OpenPlatform("ElemeShopIsv");
    /** Gitee(码云). */
    public static final OpenPlatform GITEE = new OpenPlatform("Gitee");
    /** GitHub. */
    public static final OpenPlatform GITHUB = new OpenPlatform("GitHub");
    /** OSChina(开源中国). */
    public static final OpenPlatform OSCHINA = new OpenPlatform("OSChina");
    /** TikTok(抖音) */
    public static final OpenPlatform TIK_TOK = new OpenPlatform("TikTok");
    /** WechatOfficialAccount(微信公众号). */
    public static final OpenPlatform WECHAT_OFFICIAL_ACCOUNT = new OpenPlatform("WechatOfficialAccount");
    /** WechatWorkCorp(企业微信-企业内部引用) */
    public static final OpenPlatform WECHAT_WORK_CORP = new OpenPlatform("WechatWorkCorp");

}
