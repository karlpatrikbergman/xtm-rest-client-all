package com.infinera.metro.dnam.acceptance.test.node.configuration.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.configuration.MibEntryAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Port;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.AddDropPortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.LinePortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Value
public class Roadm1x2G50 extends AbstractBoard implements Board  {
    private final List<Port> addDropPorts;
    private final List<Port> linePorts;
    @JsonIgnore private final AddDropPortEntry.AddDropPortEntryBuilder addDropPortEntryBuilder;
    @JsonIgnore private final LinePortEntry.LinePortEntryBuilder linePortEntryBuilder;

    @Builder
    @java.beans.ConstructorProperties({"subrack", "slot", "boardEntryAttributes", "addDropPorts", "linePorts"})
    private Roadm1x2G50(Integer subrack, Slot slot, @Singular List<MibEntryAttributes> boardEntryAttributes, @Singular List<Port> addDropPorts, @Singular List<Port> linePorts) {
        super(BoardType.ROADM1X2G50, subrack, slot, boardEntryAttributes);
        this.addDropPorts = addDropPorts;
        this.linePorts = linePorts;
        this.addDropPortEntryBuilder = getAddDropPortEntryBuilder();
        this.linePortEntryBuilder = getLinePortEntryBuilder();
    }

    @Override
    public void applyTo(Node node) {
        super.applyTo(node);
        configureAddDropPorts(node);
    }

    private void configureAddDropPorts(Node node) {
        addDropPorts.forEach(addDropPort -> configureAddDropPort(node, addDropPort));
    }

    private void configureAddDropPort(Node node, Port addDropPort) {
        final AddDropPortEntry addDropPortEntry = addDropPortEntryBuilder
            .transmitPort(addDropPort.getTransmitPort())
            .receivePort(addDropPort.getReceivePort())
            .build();
        addDropPort.getPortEntryAttributes().forEach(portAttribute -> portAttribute.applyTo(node, addDropPortEntry));
    }

    public AddDropPortEntry.AddDropPortEntryBuilder getAddDropPortEntryBuilder() {
        return AddDropPortEntry.builder()
                .moduleType(ModuleType.ROADM)
                .groupOrTableType(GroupOrTableType.ADD_DROP_IF)
                .clientPortType(ClientPortType.ADD_DROP)
                .subrack(getSubrack())
                .slot(getSlot().getValue());
    }

    public LinePortEntry.LinePortEntryBuilder getLinePortEntryBuilder() {
        return LinePortEntry.builder()
                .moduleType(ModuleType.WDM)
                .groupOrTableType(GroupOrTableType.IF)
                .linePortType(LinePortType.WDM)
                .subrack(getSubrack())
                .slot(getSlot().getValue());
    }
}
