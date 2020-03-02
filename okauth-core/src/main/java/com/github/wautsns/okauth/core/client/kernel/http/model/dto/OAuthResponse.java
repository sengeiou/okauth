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
package com.github.wautsns.okauth.core.client.kernel.http.model.dto;

import java.io.Serializable;
import java.util.Map;
import lombok.Data;
import lombok.NonNull;

/**
 * OkAuth response.
 *
 * @author wautsns
 * @since Feb 28, 2020
 */
@Data
public class OAuthResponse implements Serializable {

    private static final long serialVersionUID = -5038582625365510640L;

    /** request of the response */
    private final @NonNull OAuthRequest request;
    /** response status code */
    private final int status;
    /** response data */
    private final @NonNull Map<String, Serializable> data;

}
