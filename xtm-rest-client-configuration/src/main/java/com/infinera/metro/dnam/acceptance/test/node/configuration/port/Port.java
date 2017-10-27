package com.infinera.metro.dnam.acceptance.test.node.configuration.port;

import com.google.common.base.Preconditions;
import lombok.Getter;

class Port {
    @Getter private final Integer transmitPort;
    @Getter private final Integer receivePort;

    Port(Integer transmitPort, Integer receivePort) {
        Preconditions.checkNotNull(transmitPort, "transmitPort must be an Integer");
        Preconditions.checkNotNull(receivePort, "receivePort must be an Integer");

        this.transmitPort = transmitPort;
        this.receivePort = receivePort;
    }
}

