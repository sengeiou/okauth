package com.github.wautsns.okauth.core.client.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author wautsns
 */
public enum ResponseReader {

    JSON {

        private final ObjectMapper objectMapper = new ObjectMapper();
        private final JavaType mapType = objectMapper
            .getTypeFactory()
            .constructMapType(Map.class, String.class, Object.class);

        @Override
        public Map<String, Object> read(InputStream inputStream) throws IOException {
            return objectMapper.readValue(inputStream, mapType);
        }

    };

    public abstract Map<String, Object> read(InputStream inputStream) throws IOException;

}
