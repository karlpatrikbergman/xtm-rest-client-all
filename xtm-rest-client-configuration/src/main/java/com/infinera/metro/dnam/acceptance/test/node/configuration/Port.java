package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.infinera.metro.dnam.acceptance.test.node.mib.Configuration;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Port {
    @NonNull private final Integer transmitPort;
    @NonNull private final Integer receivePort;
    @NonNull private final Configuration configuration;
}
