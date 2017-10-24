package com.infinera.metro.dnam.acceptance.test.node.configuration.port;

import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.LinePortAttributes;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

public class LinePort extends Port {

    @Getter private final List<LinePortAttributes> linePortAttributes;

    @Builder
    @java.beans.ConstructorProperties({"transmitPort", "receivePort", "attributes"})
    private LinePort(Integer transmitPort, Integer receivePort, @Singular List<LinePortAttributes> linePortAttributes) {
        super(transmitPort, receivePort);
        this.linePortAttributes = linePortAttributes;
    }
}
