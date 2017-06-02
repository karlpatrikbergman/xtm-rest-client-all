package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Value
@Builder
public class Tpd10gbe implements Board {
    @Getter(AccessLevel.NONE) private final ModuleType moduleType = ModuleType.EQ;
    @Getter(AccessLevel.NONE) private final GroupOrTableType groupOrTableType = GroupOrTableType.BOARD;
    private final BoardType boardType = BoardType.TPD10GBE;
    @NonNull private final Integer subrack; //These values could be taken from (not yet existing) objects "Subrack" and "Slot"?
    @NonNull private final Slot slot;
    @NonNull private final List<Port> clientPorts;
    @NonNull private final List<Port> linePorts;

    public void tpd10gbeSpecificMethod() {
        log.info("Executing tpd10gbe specific method");
    }

    @Override
    public BoardEntry getBoardEntry() {
        return BoardEntry.builder()
                .subrack(subrack)
                .slot(slot.getValue())
                .boardType(boardType)
                .build();
    }

    @Override
    public void applyTo(Node node) throws RuntimeException {
        node.createBoard(getBoardEntry());

    }

    public void foo() {
        clientPorts.stream()
                .forEach(port -> {
                    ClientPortEntry clientPortEntry = getClientPortEntryBuilder()
                            .transmitPort(port.getTransmitPort())
                            .receivePort(port.getReceivePort())
                            .build();
                    log.info(clientPortEntry.getMibEntryPath());
                    ParameterList parameterList = ParameterList.of(port.getConfiguration());
                    log.info(parameterList.toString());
                });
    }

    /*
  Client signal format
  /client/if/client:1:2:1-2/configure.json?clientIfConfigurationCommand=wan10GbE yes

  Client expected frequency
  /client/if/client:1:2:1-2/set.json?expectedFrequency=w1530

  Line settings
  /wdm/if/wdm:1:2:3-4/set.json&expectedFrequency=w1530"
  */
    private ClientPortEntry.ClientPortEntryBuilder getClientPortEntryBuilder() {
        return ClientPortEntry.builder()
                .moduleType(ModuleType.CLIENT)
                .groupOrTableType(GroupOrTableType.IF)
                .clientPortType(ClientPortType.CLIENT)
                .subrack(this.subrack)
                .slot(this.slot.getValue());
    }

    private LinePortEntry.LinePortEntryBuilder getLinePortEntryBuilder() {
        return LinePortEntry.builder()
                .moduleType(ModuleType.WDM)
                .groupOrTableType(GroupOrTableType.IF)
                .linePortType(LinePortType.WDM)
                .subrack(this.subrack)
                .slot(this.slot.getValue());
    }
}
