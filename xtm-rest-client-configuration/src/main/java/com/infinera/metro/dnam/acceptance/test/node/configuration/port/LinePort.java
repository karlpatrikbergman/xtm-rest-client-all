package com.infinera.metro.dnam.acceptance.test.node.configuration.port;

import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.line.LinePortAttributes;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Value
public class LinePort extends Port {

    private final List<LinePortAttributes> linePortAttributes;

    @Builder
    @java.beans.ConstructorProperties({"transmitPort", "receivePort", "attributes"})
    private LinePort(Integer transmitPort, Integer receivePort, @Singular List<LinePortAttributes> linePortAttributes) {
        super(transmitPort, receivePort);
        this.linePortAttributes = linePortAttributes;
    }
}
