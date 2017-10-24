package com.infinera.metro.dnam.acceptance.test.node.configuration.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Subrack;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Tpd10gbe;
import com.infinera.metro.dnam.acceptance.test.node.configuration.deserialize.ObjectFromFileUtilJackson;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.Port;
import com.infinera.metro.dnam.acceptance.test.node.configuration.topology.PeerConnection;
import com.infinera.metro.dnam.acceptance.test.node.mib.MpoIdentifier;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;

/**
 * Doesn't actually test anything
 */
@Slf4j
public class PeerConnectionSerializationTest {

    private final String ipAddressNodeA = "172.17.0.2";
    private final String ipAddressNodeZ = "172.17.0.3";

    private final Tpd10gbe tpd10gbeOnNodeA = Tpd10gbe.builder()
        .subrack(Subrack.subrack1)
        .slot(Slot.slot2)
        .build();

    private final Tpd10gbe tpd10gbeOnNodeZ = Tpd10gbe.builder()
        .subrack(Subrack.subrack2)
        .slot(Slot.slot3)
        .build();

    private final Port linePortEntryNodeA = Port.builder()
        .transmitPort(3)
        .receivePort(4)
        .build();

    private final Port linePortEntryNodeZ = Port.builder()
        .transmitPort(7)
        .receivePort(8)
        .build();

    @Test
    public void test() throws IOException {
        PeerConnection peerConnectionAtoZ = PeerConnection.builder()
            .localNodeIpAddress(ipAddressNodeA)
            .localBoardEntry(tpd10gbeOnNodeA.getBoardEntry())
            .localPort(linePortEntryNodeA)
            .localMpoIdentifier(MpoIdentifier.NotPresent())
            .remoteNodeIpAddress(ipAddressNodeZ)
            .remoteBoardEntry(tpd10gbeOnNodeZ.getBoardEntry())
            .remotePort(linePortEntryNodeZ)
            .remoteMpoIdentifier(MpoIdentifier.NotPresent())
            .build();
        ObjectMapper mapper = ObjectFromFileUtilJackson.INSTANCE.getMapper();
        log.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(peerConnectionAtoZ));
    }
}
