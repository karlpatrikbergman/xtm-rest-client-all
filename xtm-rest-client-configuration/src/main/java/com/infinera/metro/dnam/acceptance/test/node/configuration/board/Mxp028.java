package com.infinera.metro.dnam.acceptance.test.node.configuration.board;

import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Subrack;
import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.board.BoardAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.mib.client.MRT_IF_MRT_ClientPortBoard;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.mib.line.MUX_IF_MUX_LinePortBoard;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.ClientPort;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.LinePort;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.BoardType;
import lombok.Builder;
import lombok.Singular;

import java.util.List;

public class Mxp028 extends AbstractClientAndLinePortBoard implements MUX_IF_MUX_LinePortBoard, MRT_IF_MRT_ClientPortBoard {

    @Builder
    @java.beans.ConstructorProperties({"subrack", "slot", "boardAttributes", "clientPorts", "linePorts"})
    private Mxp028(Subrack subrack, Slot slot, @Singular List<BoardAttributes> boardAttributes, @Singular  List<ClientPort> clientPorts, @Singular List<LinePort> linePorts) {
        super(BoardType.MXP028, subrack, slot, boardAttributes, clientPorts, linePorts);
    }
}
