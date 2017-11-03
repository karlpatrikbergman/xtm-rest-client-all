package com.infinera.metro.dnam.acceptance.test.node.configuration.topology;

import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.NodeImpl;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Subrack;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Tpd10gbe;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.LinePort;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attribute;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attributes;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.PeerEntry;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Slf4j
public class PeerConnectionTest {

    private final Node nodeA = NodeImpl.createDefault("172.17.0.2");
    private final Node nodeZ = NodeImpl.createDefault("172.17.0.3");

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

    private final PeerEntry localPeerEntry_1_2_0_3_peerConnectionNodeAtoNodeZ = peerConnectionNodeAtoNodeZ.getLocalPeerEntry();
    private final PeerEntry remotePeerEntry_2_3_0_8_peerConnectionNodeAtoNodeZ = peerConnectionNodeAtoNodeZ.getRemotePeerEntry();

    private final PeerConnection peerConnectionNodeZtoNodeA = peerConnectionNodeAtoNodeZ.invert();
//    private final PeerConnection peerConnectionNodeZtoNodeA = PeerConnection.builder()
//        .localPeer(tpd10gbeOnNodeZ.getPeer(linePortTx7Rx8.getTransmitPort()))
//        .remotePeer(tpd10gbeOnNodeA.getPeer(linePortTx3Rx4.getReceivePort()))
//        .build();
    private final PeerEntry localPeerEntry_2_3_0_7_peerConnectionNodeZtoNodeA = peerConnectionNodeZtoNodeA.getLocalPeerEntry();
    private final PeerEntry remotePeerEntry_1_2_0_4_peerConnectionNodeZtoNodeA = peerConnectionNodeZtoNodeA.getRemotePeerEntry();

    @Test
    public void localPeerEntryMibString() {
        assertEquals("peer:1:2:0:3", localPeerEntry_1_2_0_3_peerConnectionNodeAtoNodeZ.getMibEntryString());
        assertEquals("peer:2:3:0:7", localPeerEntry_2_3_0_7_peerConnectionNodeZtoNodeA.getMibEntryString());
    }

    @Test
    public void localPeerEntryMibPath() {
        assertEquals("/mib/topo/peer/peer:1:2:0:3", localPeerEntry_1_2_0_3_peerConnectionNodeAtoNodeZ.getMibEntryPath());
        assertEquals("/mib/topo/peer/peer:2:3:0:7", localPeerEntry_2_3_0_7_peerConnectionNodeZtoNodeA.getMibEntryPath());
    }

    @Test
    public void localPeerEntryLocalLabel() {
        assertEquals("1:2:3", localPeerEntry_1_2_0_3_peerConnectionNodeAtoNodeZ.getLocalLabel());
        assertEquals("2:3:7", localPeerEntry_2_3_0_7_peerConnectionNodeZtoNodeA.getLocalLabel());
    }

    @Test
    public void remotePeerEntryRemoteLabel() {
        assertEquals("2:3:8", remotePeerEntry_2_3_0_8_peerConnectionNodeAtoNodeZ.getLocalLabel());
        assertEquals("1:2:4", remotePeerEntry_1_2_0_4_peerConnectionNodeZtoNodeA.getLocalLabel());
    }

    @Test
    public void localPeerEntryAttributes() {
        final Attributes localPeerEntryAttributes_peerConnectionNodeAtoNodeZ = peerConnectionNodeAtoNodeZ.getPeerConfiguration(localPeerEntry_1_2_0_3_peerConnectionNodeAtoNodeZ, nodeZ, remotePeerEntry_2_3_0_8_peerConnectionNodeAtoNodeZ);
        verifyAttribute(localPeerEntryAttributes_peerConnectionNodeAtoNodeZ.getAttributeByKey("topoPeerLocalLabel"), "1:2:3");
        verifyAttribute(localPeerEntryAttributes_peerConnectionNodeAtoNodeZ.getAttributeByKey("topoPeerRemoteIpAddress"), "172.17.0.3");
        verifyAttribute(localPeerEntryAttributes_peerConnectionNodeAtoNodeZ.getAttributeByKey("topoPeerRemoteLabel"), "2:3:8");

        final Attributes localPeerEntryAttributes_peerConnectionNodeZtoNodeA = peerConnectionNodeZtoNodeA.getPeerConfiguration(localPeerEntry_2_3_0_7_peerConnectionNodeZtoNodeA, nodeA, remotePeerEntry_1_2_0_4_peerConnectionNodeZtoNodeA);
        verifyAttribute(localPeerEntryAttributes_peerConnectionNodeZtoNodeA.getAttributeByKey("topoPeerLocalLabel"), "2:3:7");
        verifyAttribute(localPeerEntryAttributes_peerConnectionNodeZtoNodeA.getAttributeByKey("topoPeerRemoteIpAddress"), "172.17.0.2");
        verifyAttribute(localPeerEntryAttributes_peerConnectionNodeZtoNodeA.getAttributeByKey("topoPeerRemoteLabel"), "1:2:4");
    }

    /**/

    @Test
    public void remotePeerEntryMibString() {
        assertEquals("peer:2:3:0:8", remotePeerEntry_2_3_0_8_peerConnectionNodeAtoNodeZ.getMibEntryString());
        assertEquals("peer:1:2:0:4", remotePeerEntry_1_2_0_4_peerConnectionNodeZtoNodeA.getMibEntryString());
    }

    @Test
    public void remotePeerEntryMibPath() {
        assertEquals("/mib/topo/peer/peer:2:3:0:8", remotePeerEntry_2_3_0_8_peerConnectionNodeAtoNodeZ.getMibEntryPath());
        assertEquals("/mib/topo/peer/peer:1:2:0:4", remotePeerEntry_1_2_0_4_peerConnectionNodeZtoNodeA.getMibEntryPath());
    }

    @Test
    public void remotePeerEntryLocalLabel() {
        assertEquals("2:3:8", remotePeerEntry_2_3_0_8_peerConnectionNodeAtoNodeZ.getLocalLabel());
        assertEquals("1:2:4", remotePeerEntry_1_2_0_4_peerConnectionNodeZtoNodeA.getLocalLabel());
    }

    @Test
    public void remotePeerEntryAttributes() {
        final Attributes remotePeerEntryAttributes_peerConnectionNodeAtoNodeZ = peerConnectionNodeAtoNodeZ.getPeerConfiguration(remotePeerEntry_2_3_0_8_peerConnectionNodeAtoNodeZ, nodeA, localPeerEntry_1_2_0_3_peerConnectionNodeAtoNodeZ);
        verifyAttribute(remotePeerEntryAttributes_peerConnectionNodeAtoNodeZ.getAttributeByKey("topoPeerLocalLabel"), "2:3:8");
        verifyAttribute(remotePeerEntryAttributes_peerConnectionNodeAtoNodeZ.getAttributeByKey("topoPeerRemoteIpAddress"), "172.17.0.2");
        verifyAttribute(remotePeerEntryAttributes_peerConnectionNodeAtoNodeZ.getAttributeByKey("topoPeerRemoteLabel"), "1:2:3");

        final Attributes remotePeerEntryAttributes_peerConnectionNodeZtoNodeA = peerConnectionNodeZtoNodeA.getPeerConfiguration(remotePeerEntry_1_2_0_4_peerConnectionNodeZtoNodeA, nodeZ, localPeerEntry_2_3_0_7_peerConnectionNodeZtoNodeA);
        verifyAttribute(remotePeerEntryAttributes_peerConnectionNodeZtoNodeA.getAttributeByKey("topoPeerLocalLabel"), "1:2:4");
        verifyAttribute(remotePeerEntryAttributes_peerConnectionNodeZtoNodeA.getAttributeByKey("topoPeerRemoteIpAddress"), "172.17.0.3");
        verifyAttribute(remotePeerEntryAttributes_peerConnectionNodeZtoNodeA.getAttributeByKey("topoPeerRemoteLabel"), "2:3:7");
    }

    /**/

    private void verifyAttribute(Attribute attribute, String expectedValue) {
        assertEquals(expectedValue, attribute.getValue());
    }

}
