package com.infinera.metro.dnam.acceptance.test.node.configuration.port;

import com.google.common.base.Preconditions;
import lombok.Getter;

class Port {
    @Getter private final Integer transmitPort;
    @Getter private final Integer receivePort;

    Port(Integer transmitPort, Integer receivePort) {
        Preconditions.checkArgument(transmitPort instanceof Integer, "transmitPort must be an Integer");
        Preconditions.checkArgument(receivePort instanceof Integer, "receivePort must be an Integer");
        this.transmitPort = transmitPort;
        this.receivePort = receivePort;
    }

}

