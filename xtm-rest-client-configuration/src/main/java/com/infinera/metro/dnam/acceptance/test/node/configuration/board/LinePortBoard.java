package com.infinera.metro.dnam.acceptance.test.node.configuration.board;

import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.Port;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.LinePortEntry;

import java.util.List;

//Example of virtual field pattern
//https://kerflyn.wordpress.com/2012/07/09/java-8-now-you-have-mixins/
public interface LinePortBoard extends SlotSubrackInsertable {
    default void configureLinePorts(Node node) {
        getLinePorts().forEach(port -> configureLinePort(node, port));
    }

    default void configureLinePort(Node node, Port linePort) {
        final LinePortEntry linePortEntry = getLinePortEntry(linePort);
        linePort.getPortAttributes().forEach(portAttribute -> portAttribute.applyTo(node, linePortEntry));
    }

    default LinePortEntry getLinePortEntry(Port port) {
        return getLinePortEntryBuilder()
            .transmitPort(port.getTransmitPort())
            .receivePort(port.getReceivePort())
            .build();

    }

    List<Port> getLinePorts();

    LinePortEntry.LinePortEntryBuilder getLinePortEntryBuilder();

}
