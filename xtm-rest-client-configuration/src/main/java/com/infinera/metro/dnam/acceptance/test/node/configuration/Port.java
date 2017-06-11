package com.infinera.metro.dnam.acceptance.test.node.configuration;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Port {
    @NonNull private final Integer transmitPort;
    @NonNull private final Integer receivePort;
    @NonNull private final List<PortAttribute> portAttributes;
}
