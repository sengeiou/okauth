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
package com.github.wautsns.okauth.core.client.kernel.model.properties;

import com.github.wautsns.okauth.core.client.OAuthClientInitializer;
import com.github.wautsns.okauth.core.client.OpenPlatform;
import com.github.wautsns.okauth.core.client.builtin.OpenPlatforms;
import com.github.wautsns.okauth.core.client.kernel.http.model.properties.OAuthRequestExecutorProperties;
import com.github.wautsns.okauth.core.util.CheckableProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * OAuth client properties.
 *
 * @author wautsns
 * @see #check()
 * @since Feb 29, 2020
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class OAuthClientProperties extends CheckableProperties {

    /**
     * open platform expr.
     *
     * <ul>
     * There are two types of open platform expr:
     * <li>
     * built-in open platform<br/>
     * For built-in open platform clients, use the short name (case insensitive, but to match the identifier of the
     * specified client), the specific configurable identifier can be found in {@link OpenPlatforms}
     * </li>
     * <li>
     * extended open platform<br>
     * Must be an enum and need to implement the interface {@link OpenPlatform} and {@link OAuthClientInitializer}, the
     * specific implementation can refer to {@link OpenPlatforms}.<br/>
     * Assume that there is an enumeration `a.b.c.ExtendedOpenPlatform` that meets the requirements and that the
     * enumeration class has an enumeration value of `XYZ`, and the expression is `a.b.c.ExtendedOpenPlatform:xyz`,
     * `xyz` after `:` is case insensitive. <strong>Special, when the enumeration class contains only one enumeration
     * value, it can be omitted.</strong>
     * </li>
     * </ul>
     */
    private String openPlatformExpr;
    /** oauth app properties */
    private OAuthAppProperties app;
    /** request executor properties */
    private OAuthRequestExecutorProperties requestExecutor;

    /**
     * {@inheritDoc}
     *
     * <p>App and requestExecutor must not be null and {@code app.check()},
     * {@code requestExecutor.check()} will be called.
     *
     * @throws IllegalArgumentException {@inheritDoc}
     * @see OAuthAppProperties#check()
     * @see OAuthRequestExecutorProperties#check()
     */
    @Override
    public void check() {
        notNull("app", app).check();
        notNull("requestExecutor", requestExecutor).check();
    }

}
