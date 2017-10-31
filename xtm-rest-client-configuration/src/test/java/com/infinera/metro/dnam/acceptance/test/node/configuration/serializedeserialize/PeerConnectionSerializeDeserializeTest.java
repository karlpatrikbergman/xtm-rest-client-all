package com.infinera.metro.dnam.acceptance.test.node.configuration.serializedeserialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Subrack;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Tpd10gbe;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.LinePort;
import com.infinera.metro.dnam.acceptance.test.node.configuration.topology.PeerConnection;
import com.infinera.metro.dnam.acceptance.test.util.ResourceString;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PeerConnectionSerializeDeserializeTest extends AbstractYamlSerializeDeserializeTest {

    private final Tpd10gbe tpd10gbeOnNodeA = Tpd10gbe.builder()
        .subrack(Subrack.subrack1)
        .slot(Slot.slot2)
        .build();

    private final Tpd10gbe tpd10gbeOnNodeZ = Tpd10gbe.builder()
        .subrack(Subrack.subrack2)
        .slot(Slot.slot3)
        .build();

    private final LinePort linePortTx3Rx4 = LinePort.builder()
        .transmitPort(3)
        .receivePort(4)
        .build();

    private final LinePort linePortTx7Rx8 = LinePort.builder()
        .transmitPort(7)
        .receivePort(8)
        .build();

    private final PeerConnection peerConnectionNodeAtoNodeZ = PeerConnection.builder()
        .localPeer(tpd10gbeOnNodeA.getPeer(linePortTx3Rx4.getTransmitPort()))
        .remotePeer(tpd10gbeOnNodeZ.getPeer(linePortTx7Rx8.getReceivePort()))
        .build();

    @Test
    public void serializePeerConnection() throws JsonProcessingException {
        final String expectedPeerConnectionString = new ResourceString("configuration/peer_connection.yaml").toString();
        final String serializedPeerConnection = mapper.writeValueAsString(peerConnectionNodeAtoNodeZ).trim();
        assertEquals(expectedPeerConnectionString, serializedPeerConnection);
    }

    @Test
    public void deserializePeerConnection() {
        PeerConnection deserializedPeerConnection = objectFromFileUtil.getObject("configuration/peer_connection.yaml", PeerConnection.class);
        assertEquals(peerConnectionNodeAtoNodeZ, deserializedPeerConnection);
    }
}
