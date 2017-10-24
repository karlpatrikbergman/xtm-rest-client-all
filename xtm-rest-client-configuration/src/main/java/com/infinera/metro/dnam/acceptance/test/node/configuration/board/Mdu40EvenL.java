package com.infinera.metro.dnam.acceptance.test.node.configuration.board;


import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Subrack;
import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.BoardAttributes;
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
public class Mdu40EvenL extends AbstractClientAndLinePortBoard implements OA_VOA_CLIENT_IF_CLIENT_ClientPortBoard {
    @Builder
    @java.beans.ConstructorProperties({"subrack", "slot", "boardAttributes", "clientPorts", "linePorts"})
    private Mdu40EvenL(Subrack subrack, Slot slot, @Singular List<BoardAttributes> boardAttributes, @Singular  List<ClientPort> clientPorts, @Singular List<LinePort> linePorts) {
        super(BoardType.MDU40EVENL, subrack, slot, boardAttributes, clientPorts, linePorts);
    }
}