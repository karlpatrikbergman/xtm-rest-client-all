package com.infinera.metro.dnam.acceptance.test.mib;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Configuration {
    private final String key;
    private final String value;

    public String asParameters() {
        return key + "=" + value;
    }
}
