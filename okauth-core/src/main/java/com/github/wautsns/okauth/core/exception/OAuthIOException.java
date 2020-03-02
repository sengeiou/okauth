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
package com.github.wautsns.okauth.core.exception;

import com.github.wautsns.okauth.core.client.OpenPlatform;
import com.github.wautsns.okauth.core.client.kernel.http.model.dto.OAuthRequest;
import java.io.IOException;
import lombok.Getter;

/**
 * OAuth IO exception.
 *
 * @author wautsns
 * @since Feb 28, 2020
 */
@Getter
public class OAuthIOException extends OAuthException {

    private static final long serialVersionUID = 775296624639159639L;

    /** open platform */
    private final OpenPlatform openPlatform;
    /** oauth request */
    private final OAuthRequest request;

    public OAuthIOException(OpenPlatform openPlatform, OAuthRequest request, IOException cause) {
        super(cause);
        this.openPlatform = openPlatform;
        this.request = request;
    }

}
