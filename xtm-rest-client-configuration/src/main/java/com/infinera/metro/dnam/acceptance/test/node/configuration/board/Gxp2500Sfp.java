package com.infinera.metro.dnam.acceptance.test.node.configuration.board;

import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Subrack;
import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.board.BoardAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.mib.client.CLIENT_IF_CLIENT_ClientPortBoard;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.mib.line.WDM_IF_WDM_LinePortBoard;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.ClientPort;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.LinePort;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.BoardType;
import lombok.Builder;
import lombok.Singular;

import java.util.List;

public class Gxp2500Sfp extends AbstractClientAndLinePortBoard implements WDM_IF_WDM_LinePortBoard, CLIENT_IF_CLIENT_ClientPortBoard {

    @Builder
    @java.beans.ConstructorProperties({"subrack", "slot", "boardAttributes", "clientPorts", "linePorts"})
    private Gxp2500Sfp(Subrack subrack, Slot slot, @Singular List<BoardAttributes> boardAttributes, @Singular List<ClientPort> clientPorts, @Singular List<LinePort> linePorts) {
        super(BoardType.GXP2500SFP, subrack, slot, boardAttributes, clientPorts, linePorts);
    }
}
