package com.github.wautsns.okauth.springbootstarter.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.github.wautsns.okauth.core.client.core.properties.OkAuthClientProperties;

/**
 *
 * @author wautsns
 */
@ConfigurationProperties(prefix = "okauth")
public class OkAuthProperties {

    private List<OkAuthClientProperties> clients;

    /** Get {@link #clients}. */
    public List<OkAuthClientProperties> getClients() {
        return clients;
    }

    /** Set {@link #clients}. */
    public OkAuthProperties setClients(List<OkAuthClientProperties> clients) {
        this.clients = clients;
        return this;
    }

}
