package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/*
  Client signal format
  /client/if/client:1:2:1-2/configure.json?clientIfConfigurationCommand=wan10GbE yes

  Client expected frequency
  /client/if/client:1:2:1-2/set.json?expectedFrequency=w1530

  Line settings
  /wdm/if/wdm:1:2:3-4/set.json&expectedFrequency=w1530"
  */

@Slf4j
@Value
public class Tpd10gbe implements Board {
    @Getter(AccessLevel.NONE) private final ModuleType moduleType = ModuleType.EQ;
    @Getter(AccessLevel.NONE) private final GroupOrTableType groupOrTableType = GroupOrTableType.BOARD;
    @JsonIgnore private final BoardType boardType = BoardType.TPD10GBE;
    @JsonIgnore @NonNull private final BoardEntry boardEntry;
    @NonNull private final Integer subrack; //These values could be taken from (not yet existing) objects "Subrack" and "Slot"?
    @NonNull private final Slot slot;
    @NonNull private final List<Port> clientPorts;
    @NonNull private final List<Port> linePorts;
    @JsonIgnore @NonNull private final ClientPortEntry.ClientPortEntryBuilder clientPortEntryBuilder;
    @JsonIgnore @NonNull private final LinePortEntry.LinePortEntryBuilder linePortEntryBuilder;

    @Builder
    @java.beans.ConstructorProperties({"subrack", "slot", "clientPorts", "linePorts"})
    private Tpd10gbe(Integer subrack, Slot slot, List<Port> clientPorts, List<Port> linePorts) {
        this.subrack = subrack;
        this.slot = slot;
        this.clientPorts = clientPorts;
        this.linePorts = linePorts;
        this.boardEntry = getBoardEntry();
        this.clientPortEntryBuilder = getClientPortEntryBuilder();
        this.linePortEntryBuilder = getLinePortEntryBuilder();
    }

    @Override
    public void applyTo(Node node) throws RuntimeException {
        createBoard(node);
        configureClientPorts(node);
//        configureLinePorts(node);
    }

    private void createBoard(Node node) {
        node.createBoard(getBoardEntry());
    }

    private void configureClientPorts(Node node) {
        clientPorts.forEach(port -> configureClientPort(node, port));
    }

    private void configureClientPort(Node node, Port port) {
        final ClientPortEntry clientPortEntry = clientPortEntryBuilder
                .transmitPort(port.getTransmitPort())
                .receivePort(port.getReceivePort())
                .build();
        final ConfigurationList configurationList = ConfigurationList.of(port.getConfiguration());
        node.setClientPortConfiguration(clientPortEntry, configurationList);
    }

    private void configureLinePorts(Node node) {
        linePorts.forEach(port -> configureLinePort(node, port));
    }

    private void configureLinePort(Node node, Port port) {
        final LinePortEntry linePortEntry = linePortEntryBuilder
                .transmitPort(port.getTransmitPort())
                .receivePort(port.getReceivePort())
                .build();
        final ConfigurationList configurationList = ConfigurationList.of(port.getConfiguration());
        node.setLinePortConfiguration(linePortEntry, configurationList);
    }

    @Override
    public BoardEntry getBoardEntry() {
        return BoardEntry.builder()
                .subrack(subrack)
                .slot(slot.getValue())
                .boardType(boardType)
                .build();
    }

    public ClientPortEntry.ClientPortEntryBuilder getClientPortEntryBuilder() {
        return ClientPortEntry.builder()
                .moduleType(ModuleType.CLIENT)
                .groupOrTableType(GroupOrTableType.IF)
                .clientPortType(ClientPortType.CLIENT)
                .subrack(this.subrack)
                .slot(this.slot.getValue());
    }

    public LinePortEntry.LinePortEntryBuilder getLinePortEntryBuilder() {
        return LinePortEntry.builder()
                .moduleType(ModuleType.WDM)
                .groupOrTableType(GroupOrTableType.IF)
                .linePortType(LinePortType.WDM)
                .subrack(this.subrack)
                .slot(this.slot.getValue());
    }

    public void tpd10gbeSpecificMethod() {
        log.info("Executing tpd10gbe specific method");
    }
}
