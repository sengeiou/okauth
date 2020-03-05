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

import com.github.wautsns.okauth.core.OpenPlatform;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Built-in open platform.
 *
 * @author wautsns
 * @since Mar 04, 2020
 */
@Getter
@RequiredArgsConstructor
public enum BuiltInOpenPlatform implements OpenPlatform {

    /** Baidu(百度) */
    BAIDU,
    /** @deprecated DingTalk(钉钉) oauth client is not tested. */
    @Deprecated
    DINGTALK,
    /** Gitee(码云) */
    GITEE,
    /** GitHub */
    GITHUB,
    /** MicroBlog(微博) */
    MICROBLOG,
    /** OSChina(开源中国) */
    OSCHINA

}
