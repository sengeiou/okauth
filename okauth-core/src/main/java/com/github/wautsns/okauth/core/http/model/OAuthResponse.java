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
package com.github.wautsns.okauth.core.http.model;

import com.github.wautsns.okauth.core.http.util.DataMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * OAuth response.
 *
 * @author wautsns
 * @since Mar 03, 2020
 */
@Getter
@RequiredArgsConstructor
public class OAuthResponse implements Serializable {

    private static final long serialVersionUID = -1909597806170273639L;

    /** oauth response status */
    private final int status;
    /** oauth response data map */
    private final DataMap dataMap;

}
