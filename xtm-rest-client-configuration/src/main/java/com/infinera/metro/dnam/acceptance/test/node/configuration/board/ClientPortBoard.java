package com.infinera.metro.dnam.acceptance.test.node.configuration.board;

import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.Port;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.ClientPortEntry;

import java.util.List;

//Example of virtual field pattern
//https://kerflyn.wordpress.com/2012/07/09/java-8-now-you-have-mixins/
public interface ClientPortBoard extends SlotSubrackInsertable{
    default void configureClientPorts(Node node) {
        getClientPorts().forEach(clientPort -> configureClientPort(node, clientPort));
    }
    default void configureClientPort(Node node, Port clientPort) {
        final ClientPortEntry clientPortEntry = getClientPortEntry(clientPort);
        clientPort.getPortAttributes().forEach(portAttribute -> portAttribute.applyTo(node, clientPortEntry));
    }
    default ClientPortEntry getClientPortEntry(Port port) {
        return getClientPortEntryBuilder()
            .transmitPort(port.getTransmitPort())
            .receivePort(port.getReceivePort())
            .build();

    }
    List<Port> getClientPorts();
    ClientPortEntry.ClientPortEntryBuilder getClientPortEntryBuilder();
}
