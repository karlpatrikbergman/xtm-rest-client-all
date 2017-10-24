package com.infinera.metro.dnam.acceptance.test.node.configuration.board;

import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.ClientPort;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.ClientPortEntry;

import java.util.List;

//Example of virtual field pattern
//https://kerflyn.wordpress.com/2012/07/09/java-8-now-you-have-mixins/
public interface ClientPortBoard extends Board{
    default void configureClientPorts(Node node) {
        getClientPorts().forEach(clientPort -> configureClientPort(node, clientPort));
    }
    default void configureClientPort(Node node, ClientPort clientPort) {
        final ClientPortEntry clientPortEntry = getClientPortEntry(clientPort);
        clientPort.getClientPortAttributes().forEach(portAttribute -> portAttribute.applyTo(node, clientPortEntry));
    }
    default ClientPortEntry getClientPortEntry(ClientPort clientPort) {
        return getClientPortEntryBuilder()
            .transmitPort(clientPort.getTransmitPort())
            .receivePort(clientPort.getReceivePort())
            .build();

    }
    List<ClientPort> getClientPorts();
    ClientPortEntry.ClientPortEntryBuilder getClientPortEntryBuilder();
}
