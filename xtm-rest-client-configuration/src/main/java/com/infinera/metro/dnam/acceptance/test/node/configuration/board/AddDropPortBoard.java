package com.infinera.metro.dnam.acceptance.test.node.configuration.board;


import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Port;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.AddDropPortEntry;

import java.util.List;

public interface AddDropPortBoard {
    default void configureAddDropPorts(Node node) {
        getAddDropPorts().forEach(addDropPort -> configureAddDropPort(node, addDropPort));
    }
    default void configureAddDropPort(Node node, Port addDropPort) {
        final AddDropPortEntry addDropPortEntry = getAddDropPortEntry(addDropPort);
        addDropPort.getPortEntryAttributes().forEach(portAttribute -> portAttribute.applyTo(node, addDropPortEntry));
    }
    default AddDropPortEntry getAddDropPortEntry(Port port) {
        return getAddDropPortEntryBuilder()
            .transmitPort(port.getTransmitPort())
            .receivePort(port.getReceivePort())
            .build();

    }
    List<Port> getAddDropPorts();
    AddDropPortEntry.AddDropPortEntryBuilder getAddDropPortEntryBuilder();
}
