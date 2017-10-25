package com.infinera.metro.dnam.acceptance.test.node.configuration.port;

import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.client.ClientPortAttributes;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Singular;
import lombok.Value;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Value
public class ClientPort extends Port {

    private final List<ClientPortAttributes> clientPortAttributes;

    @Builder
    @java.beans.ConstructorProperties({"transmitPort", "receivePort", "attributes"})
    private ClientPort(Integer transmitPort, Integer receivePort, @Singular List<ClientPortAttributes> clientPortAttributes) {
        super(transmitPort, receivePort);
        this.clientPortAttributes = clientPortAttributes;
    }
}
