package com.infinera.metro.dnam.acceptance.test.node.configuration.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.configuration.MibEntryAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Port;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.ClientPortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.LinePortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

// TODO:
// Reuse code for client ports and line ports on boards that uses the same mib tables as Tpd10gbe
// i.e for line ports:
//      ModuleType.WDM
//      GroupOrTableType.IF
//      LinePortType.WDM

// and for client ports
//      ModuleType.CLIENT
//      GroupOrTableType.IF
//      ClientPortType.CLIENT

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Value
public class Tpd10gbe extends AbstractBoard implements Board {
    @NonNull private final List<Port> clientPorts;
    @NonNull private final List<Port> linePorts;
    @JsonIgnore @NonNull private final ClientPortEntry.ClientPortEntryBuilder clientPortEntryBuilder;
    @JsonIgnore @NonNull private final LinePortEntry.LinePortEntryBuilder linePortEntryBuilder;

    @Builder
    @java.beans.ConstructorProperties({"subrack", "slot", "clientPorts", "linePorts", "boardEntryAttributes"})
    private Tpd10gbe(Integer subrack, Slot slot, @Singular  List<Port> clientPorts, @Singular List<Port> linePorts, @Singular List<MibEntryAttributes> boardEntryAttributes) {
        super(BoardType.TPD10GBE, subrack, slot, boardEntryAttributes);
        this.clientPorts = clientPorts;
        this.linePorts = linePorts;
        this.clientPortEntryBuilder = getClientPortEntryBuilder();
        this.linePortEntryBuilder = getLinePortEntryBuilder();
    }

    @Override
    public void applyTo(Node node) throws RuntimeException {
        super.createBoard(node);
        super.configureBoardAttributes(node);
        configureClientPorts(node);
        configureLinePorts(node);
    }

    //Used when creating PeerConnection
    public LinePortEntry getLinePortEntry(Port port) {
        return linePortEntryBuilder
            .transmitPort(port.getTransmitPort())
            .receivePort(port.getReceivePort())
            .build();
    }

    private void configureClientPorts(Node node) {
        clientPorts.forEach(clientPort -> configureClientPort(node, clientPort));
    }

    private void configureClientPort(Node node, Port clientPort) {
        final ClientPortEntry clientPortEntry = clientPortEntryBuilder
                .transmitPort(clientPort.getTransmitPort())
                .receivePort(clientPort.getReceivePort())
                .build();
        clientPort.getPortEntryAttributes().forEach(portAttribute -> portAttribute.applyTo(node, clientPortEntry));
    }



    private void configureLinePorts(Node node) {
        linePorts.forEach(port -> configureLinePort(node, port));
    }

    private void configureLinePort(Node node, Port linePort) {
        final LinePortEntry linePortEntry = getLinePortEntry(linePort);
        linePort.getPortEntryAttributes().forEach(portAttribute -> portAttribute.applyTo(node, linePortEntry));
    }

    private ClientPortEntry.ClientPortEntryBuilder getClientPortEntryBuilder() {
        return ClientPortEntry.builder()
                .moduleType(ModuleType.CLIENT)
                .groupOrTableType(GroupOrTableType.IF)
                .clientPortType(ClientPortType.CLIENT)
                .subrack(super.getSubrack())
                .slot(super.getSlot().getValue());
    }

    public LinePortEntry.LinePortEntryBuilder getLinePortEntryBuilder() {
        return LinePortEntry.builder()
                .moduleType(ModuleType.WDM)
                .groupOrTableType(GroupOrTableType.IF)
                .linePortType(LinePortType.WDM)
                .subrack(super.getSubrack())
                .slot(super.getSlot().getValue());
    }
}
