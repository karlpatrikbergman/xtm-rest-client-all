package com.infinera.metro.dnam.acceptance.test.node.configuration.board;

import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Subrack;
import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.board.BoardAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.AddDropPort;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.LinePort;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.BoardType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@Getter(AccessLevel.PUBLIC)
class AbstractAddDropAndLinePortBoard extends AbstractBoard {
    @NonNull private final List<AddDropPort> addDropPorts;
    @NonNull private final List<LinePort> linePorts;

    public AbstractAddDropAndLinePortBoard(BoardType boardType, Subrack subrack, Slot slot, List<BoardAttributes> boardAttributes, List<AddDropPort> addDropPorts, List<LinePort> linePorts) {
        super(boardType, subrack, slot, boardAttributes);
        this.addDropPorts= addDropPorts;
        this.linePorts = linePorts;
    }
}
