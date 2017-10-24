package com.infinera.metro.dnam.acceptance.test.node.configuration.board;

import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Subrack;
import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.MibEntryAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.Port;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.BoardType;
import lombok.*;

import java.util.List;

@Getter(AccessLevel.PUBLIC)
class AbstractClientAndLinePortBoard extends AbstractBoard {
    @NonNull private final List<Port> clientPorts;
    @NonNull private final List<Port> linePorts;

    public AbstractClientAndLinePortBoard(BoardType boardType, Subrack subrack, Slot slot, List<MibEntryAttributes> boardAttributes, List<Port> clientPorts, List<Port> linePorts) {
        super(boardType, subrack, slot, boardAttributes);
        this.clientPorts = clientPorts;
        this.linePorts = linePorts;
    }
}
