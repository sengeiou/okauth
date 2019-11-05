package com.github.wautsns.okauth.core.client.core.dto;

import java.util.Map;

/**
 *
 * @author wautsns
 */
class OAuthData {

    private final Map<String, Object> data;

    protected OAuthData(Map<String, Object> data) {
        this.data = data;
    }

    public String getString(String name) {
        Object value = data.get(name);
        return (value == null) ? null : value.toString();
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getMap(String name) {
        Object value = data.get(name);
        return (value instanceof Map) ? (Map<String, Object>) value : null;
    }

    public Map<String, Object> getData() {
        return data;
    }

}
