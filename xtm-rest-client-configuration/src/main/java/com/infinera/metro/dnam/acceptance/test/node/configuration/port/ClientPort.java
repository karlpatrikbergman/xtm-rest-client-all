package com.infinera.metro.dnam.acceptance.test.node.configuration.port;

import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.ClientPortAttributes;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;


public class ClientPort extends Port {

    @Getter private final List<ClientPortAttributes> clientPortAttributes;

    @Builder
    @java.beans.ConstructorProperties({"transmitPort", "receivePort", "attributes"})
    private ClientPort(Integer transmitPort, Integer receivePort, @Singular List<ClientPortAttributes> clientPortAttributes) {
        super(transmitPort, receivePort);
        this.clientPortAttributes = clientPortAttributes;
    }
}
