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
package com.github.wautsns.okauth.core.client.kernel.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Encryptor.
 *
 * @author wautsns
 * @since Jun 23, 2020
 */
@FunctionalInterface
public interface Encryptor {

    /**
     * Encrypt bytes.
     *
     * @param bytes bytes
     * @return bytes after being encrypted
     */
    byte[] encrypt(byte[] bytes);

    /**
     * Encrypt string.
     *
     * @param string string
     * @return string after being encrypted
     */
    default String encrypt(String string) {
        return new String(encrypt(string.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

}
