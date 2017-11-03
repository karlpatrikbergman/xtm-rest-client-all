package com.infinera.metro.dnam.acceptance.test.node.configuration.port;

import com.google.common.base.Preconditions;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
class Port {
    @Getter private final Integer transmitPort;
    @Getter private final Integer receivePort;

    Port(Integer transmitPort, Integer receivePort) {
        Preconditions.checkNotNull(transmitPort, "transmitPort cannot be null");
        Preconditions.checkNotNull(receivePort, "receivePort cannot be null");

        this.transmitPort = transmitPort;
        this.receivePort = receivePort;
    }
}

