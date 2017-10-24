package com.infinera.metro.dnam.acceptance.test.node.configuration.port;

import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.AddDropPortConfigAttributes;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

public class AddDropPort extends Port {
    @Getter private final List<AddDropPortConfigAttributes> addDropPortAttributes;

    @Builder
    @java.beans.ConstructorProperties({"transmitPort", "receivePort", "attributes"})
    private AddDropPort(Integer transmitPort, Integer receivePort, @Singular List<AddDropPortConfigAttributes> addDropPortAttributes) {
        super(transmitPort, receivePort);
        this.addDropPortAttributes = addDropPortAttributes;
    }
}
