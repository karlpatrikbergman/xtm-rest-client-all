package com.infinera.metro.dnam.acceptance.test.mib;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Configuration {
    @NonNull private final String key;
    private final String value;

    public String asParameters() {
        return (value == null) ? key : key + "=" + value;
    }
}
