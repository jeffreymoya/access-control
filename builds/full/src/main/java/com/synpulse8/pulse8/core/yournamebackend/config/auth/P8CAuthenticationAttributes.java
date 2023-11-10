package com.synpulse8.pulse8.core.yournamebackend.config.auth;

import java.util.Map;

public interface P8CAuthenticationAttributes {

    default <T> T getAttribute(String key) {
        Map<String, Object> attributes = getAttributes();
        if (attributes != null && attributes.containsKey(key)) {
            return (T) getAttributes().get(key);
        }
        return null;
    }

    Map<String, Object> getAttributes();

}
