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
import lombok.EqualsAndHashCode;
import lombok.Singular;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Value
public class Tpd10gbe extends AbstractClientAndLinePortBoard implements WDM_IF_WDM_LinePortBoard, CLIENT_IF_CLIENT_ClientPortBoard {

    @Builder
    @java.beans.ConstructorProperties({"subrack", "slot", "boardAttributes", "clientPorts", "linePorts"})
    private Tpd10gbe(Subrack subrack, Slot slot, @Singular List<BoardAttributes> boardAttributes, @Singular  List<ClientPort> clientPorts, @Singular List<LinePort> linePorts) {
        super(BoardType.TPD10GBE, subrack, slot, boardAttributes, clientPorts, linePorts);
    }

//    //TODO: Can this be specified in AbstractClientAndLinePortBoard only?
//    @Override
//    public void applyTo(Node node) throws RuntimeException {
//        super.applyTo(node);
//        configureClientPorts(node);
//        configureLinePorts(node);
//    }
}
