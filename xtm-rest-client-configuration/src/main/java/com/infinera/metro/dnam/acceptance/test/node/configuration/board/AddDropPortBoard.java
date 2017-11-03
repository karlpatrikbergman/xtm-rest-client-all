package com.infinera.metro.dnam.acceptance.test.node.configuration.board;


import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.AddDropPort;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.AddDropPortEntry;

import java.util.List;

interface AddDropPortBoard {
    default void configureAddDropPorts(Node node) {
        getAddDropPorts().forEach(addDropPort -> configureAddDropPort(node, addDropPort));
    }
    default void configureAddDropPort(Node node, AddDropPort addDropPort) {
        final AddDropPortEntry addDropPortEntry = getAddDropPortEntry(addDropPort);
        addDropPort.getAddDropPortAttributes().forEach(portAttribute -> portAttribute.applyTo(node, addDropPortEntry));
    }
    default AddDropPortEntry getAddDropPortEntry(AddDropPort addDropPort) {
        return getAddDropPortEntryBuilder()
            .transmitPort(addDropPort.getTransmitPort())
            .receivePort(addDropPort.getReceivePort())
            .build();

    }
    List<AddDropPort> getAddDropPorts();
    AddDropPortEntry.AddDropPortEntryBuilder getAddDropPortEntryBuilder();
}
