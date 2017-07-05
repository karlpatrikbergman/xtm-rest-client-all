package com.infinera.metro.dnam.acceptance.test.node.configuration.board;

import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.configuration.MibEntryAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Port;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.BoardType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Value
public class Tpd10gbe extends AbstractBoard implements Board, WDM_IF_WDM_LinePortBoard, CLIENT_IF_CLIENT_ClientPortBoard {
    @NonNull private final List<Port> clientPorts;
    @NonNull private final List<Port> linePorts;

    @Builder
    @java.beans.ConstructorProperties({"subrack", "slot", "boardEntryAttributes", "clientPorts", "linePorts"})
    private Tpd10gbe(Integer subrack, Slot slot, @Singular List<MibEntryAttributes> boardEntryAttributes, @Singular  List<Port> clientPorts, @Singular List<Port> linePorts) {
        super(BoardType.TPD10GBE, subrack, slot, boardEntryAttributes);
        this.clientPorts = clientPorts;
        this.linePorts = linePorts;
    }

    @Override
    public void applyTo(Node node) throws RuntimeException {
        super.applyTo(node);
        configureClientPorts(node);
        configureLinePorts(node);
    }
}
