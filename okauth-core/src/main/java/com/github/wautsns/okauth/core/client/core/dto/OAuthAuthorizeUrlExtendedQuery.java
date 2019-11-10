/**
 * Copyright 2019 the original author or authors.
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
package com.github.wautsns.okauth.core.client.core.dto;

import java.io.Serializable;

/**
 * OAuth authorize url extended query.
 *
 * @author wautsns
 */
public class OAuthAuthorizeUrlExtendedQuery implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = -4632390700462116748L;

    /**
     * The application generates a random string and includes it in the request. It should then
     * check that the same value is returned after the user authorizes the app. This is used to
     * prevent <a href="https://www.owasp.org/index.php/Cross-Site_Request_Forgery_%28CSRF%29">CSRF
     * attacks</a>.
     */
    private String state;
    /**
     * One or more space-separated strings indicating which permissions the application is
     * requesting. The specific OAuth API you’re using will define the scopes that it supports.
     */
    private String scope;

    /** Get {@link #state}. */
    public String getState() {
        return state;
    }

    /** Set {@link #state}. */
    public void setState(String state) {
        this.state = state;
    }

    /** Get {@link #scope}. */
    public String getScope() {
        return scope;
    }

    /** Set {@link #scope}. */
    public void setScope(String scope) {
        this.scope = scope;
    }

}
