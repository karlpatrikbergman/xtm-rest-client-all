package com.infinera.metro.dnam.acceptance.test.node.mib;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class Attribute {
    @NonNull private final String key;
    private final String value;

    @JsonIgnore
    public static Attribute of(String key, String value) {
        return Attribute.builder()
            .key(key)
            .value(value)
            .build();
    }

    @JsonIgnore
    public String asParameters() {
        return (value == null) ? key : key + "=" + value;
    }
}
