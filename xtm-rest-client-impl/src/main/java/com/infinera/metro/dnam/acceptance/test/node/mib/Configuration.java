package com.infinera.metro.dnam.acceptance.test.node.mib;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class Configuration {
//    @NonNull private final OperationType operationType;
    @NonNull private final String key;
    private final String value;

    public String asParameters() {
        return (value == null) ? key : key + "=" + value;
    }
}
