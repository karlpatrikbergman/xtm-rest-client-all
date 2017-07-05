package com.infinera.metro.dnam.acceptance.test.node.configuration.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Port;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.LinePortEntry;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.List;



@Slf4j
@Value
class LinePortBoard {
    @NonNull private final List<Port> linePorts;
    @JsonIgnore @NonNull private final LinePortEntry.LinePortEntryBuilder linePortEntryBuilder;

    void configureLinePorts(Node node) {
        linePorts.forEach(port -> configureLinePort(node, port));
    }

    private void configureLinePort(Node node, Port linePort) {
        final LinePortEntry linePortEntry = getLinePortEntry(linePort);
        linePort.getPortEntryAttributes().forEach(portAttribute -> portAttribute.applyTo(node, linePortEntry));
    }

    public LinePortEntry getLinePortEntry(Port port) {
        return linePortEntryBuilder
            .transmitPort(port.getTransmitPort())
            .receivePort(port.getReceivePort())
            .build();
    }
}
