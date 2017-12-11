package com.infinera.metro.dnam.acceptance.test.node.configuration.board;

import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Subrack;
import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.board.BoardAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.ClientPort;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.LinePort;
import com.infinera.metro.dnam.acceptance.test.node.configuration.topology.Peer;
import com.infinera.metro.dnam.acceptance.test.node.mib.MpoIdentifier;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.BoardType;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter(AccessLevel.PUBLIC)
abstract class AbstractClientAndLinePortBoard extends AbstractBoard implements ClientPortBoard, LinePortBoard{
    private final List<ClientPort> clientPorts;
    private final List<LinePort> linePorts;

    AbstractClientAndLinePortBoard(BoardType boardType, Subrack subrack, Slot slot, List<BoardAttributes> boardAttributes, List<ClientPort> clientPorts, List<LinePort> linePorts) {
        super(boardType, subrack, slot, boardAttributes);
        this.clientPorts = clientPorts;
        this.linePorts = linePorts;
    }

    public Peer getPeer(Integer port) {
        return Peer.builder()
            .subrack(getSubrack())
            .slot(getSlot())
            .mpoIdentifier(MpoIdentifier.NotPresent())
            .port(port)
            .build();

    }

    @Override
    public void applyTo(Node node) throws RuntimeException {
        super.applyTo(node);
        configureLinePorts(node);
        configureClientPorts(node);
    }
}