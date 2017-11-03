package com.infinera.metro.dnam.acceptance.test.node.configuration.topology;

import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Subrack;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Mdu40EvenL;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Tpd10gbe;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.ClientPort;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.LinePort;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Slf4j
public class InternalConnectionTest {

    private final LinePort linePortTx3Rx4 = LinePort.builder()
        .transmitPort(3)
        .receivePort(4)
        .build();

    private final Tpd10gbe tpd10gbe = Tpd10gbe.builder()
        .subrack(Subrack.subrack1)
        .slot(Slot.slot2)
        .clientPort(ClientPort.builder()
            .transmitPort(1)
            .receivePort(2)
            .build()
        )
        .linePort(linePortTx3Rx4)
        .build();

    private final ClientPort clientPortTx41_Rx42 = ClientPort.builder()
        .transmitPort(41)
        .receivePort(42)
        .build();

    private final Mdu40EvenL mdu40EvenL = Mdu40EvenL.builder()
        .subrack(Subrack.subrack2)
        .slot(Slot.slot4)
        .clientPort(clientPortTx41_Rx42)
        .linePort(LinePort.builder()
            .transmitPort(81)
            .receivePort(82)
            .build())
        .build();

    private final InternalConnection internalConnection = InternalConnection.builder()
        .fromPeer(tpd10gbe.getPeer(linePortTx3Rx4.getTransmitPort()))
        .toPeer(mdu40EvenL.getPeer(clientPortTx41_Rx42.getReceivePort()))
        .build();

    @Test
    public void testInternalConnectionMibPath() {
        final String actualInternalConnectionMibPath = internalConnection.getInternalConnectionEntry().getMibEntryPath();
        assertEquals("/mib/topo/internal/int:1:2:0:3:2:4:0:42", actualInternalConnectionMibPath);
        log.info(actualInternalConnectionMibPath);
    }

    @Test
    public void testInvertedInternalConnectionMibPath() {
        final String actualInternalConnectionMibPath = internalConnection.invert()
            .getInternalConnectionEntry()
            .getMibEntryPath();
        assertEquals("/mib/topo/internal/int:2:4:0:41:1:2:0:4", actualInternalConnectionMibPath);
        log.info(actualInternalConnectionMibPath);
    }
}
