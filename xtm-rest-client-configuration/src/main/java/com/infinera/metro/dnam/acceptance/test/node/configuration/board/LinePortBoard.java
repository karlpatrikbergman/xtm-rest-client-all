package com.infinera.metro.dnam.acceptance.test.node.configuration.board;

import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.LinePort;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.LinePortEntry;

import java.util.List;

//Example of virtual field pattern
//https://kerflyn.wordpress.com/2012/07/09/java-8-now-you-have-mixins/
interface LinePortBoard extends Board {
    default void configureLinePorts(Node node) {
        getLinePorts().forEach(port -> configureLinePort(node, port));
    }

    default void configureLinePort(Node node, LinePort linePort) {
        final LinePortEntry linePortEntry = getLinePortEntry(linePort);
        linePort.getLinePortAttributes().forEach(portAttribute -> portAttribute.applyTo(node, linePortEntry));
    }

    default LinePortEntry getLinePortEntry(LinePort linePort) {
        return getLinePortEntryBuilder()
            .transmitPort(linePort.getTransmitPort())
            .receivePort(linePort.getReceivePort())
            .build();
    }


    List<LinePort> getLinePorts();
    LinePortEntry.LinePortEntryBuilder getLinePortEntryBuilder();
}
