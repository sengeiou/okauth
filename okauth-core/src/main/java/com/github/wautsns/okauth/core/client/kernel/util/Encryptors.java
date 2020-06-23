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

import lombok.experimental.UtilityClass;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Encryptors.
 *
 * @author wautsns
 * @since Jun 23, 2020
 */
@UtilityClass
public class Encryptors {

    /**
     * Encryption algorithm: HmacSHA256
     *
     * @param key key
     * @return encryptor of HmacSHA256 with the specified key
     */
    public static Encryptor hmacSha256(String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "HmacSHA256");
        return bytes -> {
            try {
                Mac mac = Mac.getInstance("HmacSHA256");
                mac.init(secretKeySpec);
                byte[] signatureBytes = mac.doFinal(bytes);
                return Base64.encodeBase64(signatureBytes);
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalStateException("Unreachable.");
            } catch (InvalidKeyException e) {
                throw new IllegalArgumentException("Invalid key", e);
            }
        };
    }

}
