package com.infinera.metro.dnam.acceptance.test.node.configuration.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.configuration.MibEntryAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Port;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.ClientPortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.LinePortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

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
    private Tpd10gbe(Integer subrack, Slot slot, List<Port> clientPorts, List<Port> linePorts, List<MibEntryAttributes> boardEntryAttributes) {
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
        final LinePortEntry linePortEntry = linePortEntryBuilder
                .transmitPort(linePort.getTransmitPort())
                .receivePort(linePort.getReceivePort())
                .build();
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

    private LinePortEntry.LinePortEntryBuilder getLinePortEntryBuilder() {
        return LinePortEntry.builder()
                .moduleType(ModuleType.WDM)
                .groupOrTableType(GroupOrTableType.IF)
                .linePortType(LinePortType.WDM)
                .subrack(super.getSubrack())
                .slot(super.getSlot().getValue());
    }
}
