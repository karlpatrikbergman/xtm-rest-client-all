package com.infinera.metro.dnam.acceptance.test.node.configuration.board;

import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Subrack;
import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.board.BoardAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.ClientPort;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.LinePort;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.BoardType;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.List;

//TODO: Cane board List<MibEntryAttributes> be converted to List<BordEntryAttributes>?

@Getter(AccessLevel.PUBLIC)
abstract class AbstractClientAndLinePortBoard extends AbstractBoard {
    private final List<ClientPort> clientPorts;
    private final List<LinePort> linePorts;

    AbstractClientAndLinePortBoard(BoardType boardType, Subrack subrack, Slot slot, List<BoardAttributes> boardAttributes, List<ClientPort> clientPorts, List<LinePort> linePorts) {
        super(boardType, subrack, slot, boardAttributes);
        this.clientPorts = clientPorts;
        this.linePorts = linePorts;
    }
}