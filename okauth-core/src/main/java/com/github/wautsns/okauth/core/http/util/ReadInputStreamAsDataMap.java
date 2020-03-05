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
package com.github.wautsns.okauth.core.http.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Read input stream as data map.
 *
 * @author wautsns
 * @since Mar 03, 2020
 */
public interface ReadInputStreamAsDataMap extends Serializable {

    /**
     * Read input stream as data map.
     *
     * @param inputStream input stream, require nonnull
     * @return data map
     * @throws IOException if IO exception occurs
     */
    DataMap read(InputStream inputStream) throws IOException;

    /** @see Readers#readJsonAsDataMap(InputStream) */
    ReadInputStreamAsDataMap JSON = Readers::readJsonAsDataMap;

    /** @see Readers#readQueryLikeAsDataMap(InputStream) */
    ReadInputStreamAsDataMap QUERY_LIKE = Readers::readQueryLikeAsDataMap;

}
