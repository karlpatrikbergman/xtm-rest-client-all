package com.infinera.metro.dnam.acceptance.test.node.mib;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class Attribute {
    @NonNull private final String key;
    private final String value;

    public static Attribute of(String key, String value) {
        return Attribute.builder()
            .key(key)
            .value(value)
            .build();
    }

    public String asParameters() {
        return (value == null) ? key : key + "=" + value;
    }
}
