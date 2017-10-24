//package com.infinera.metro.dnam.acceptance.test.node.configuration.topology;
//
//import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
//import com.infinera.metro.dnam.acceptance.test.node.configuration.Subrack;
//import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Tpd10gbe;
//import com.infinera.metro.dnam.acceptance.test.node.configuration.port.Port;
//import com.infinera.metro.dnam.acceptance.test.node.mib.MpoIdentifier;
//import com.infinera.metro.dnam.acceptance.test.node.mib.entry.PeerEntry;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//
//import static org.junit.Assert.assertEquals;
//
///**
// * IMPORTANT NOTE:
// * For XTM with version >=27 an MPO identifier for port is necessary
// * <p>
// * From auto-node.setup:
// * Depending on container version, add a MPO identifier.
// * if (nodeVersion === "latest" || nodeVersion >= 27) {
// * const slotPortSepPos = subSlotPort.indexOf(":", 2);
// * return subSlotPort.slice(0, slotPortSepPos) + ":0" + subSlotPort.slice(slotPortSepPos);
// * } else {
// * return subSlotPort;
// * }
// */
//
////TODO: Look at tests that are commented out, and fix the ones that seems necessary/important
//
//@Slf4j
//public class PeerConnectionTest {
//
//    private final String ipAddressNodeA = "172.17.0.2";
//    private final String ipAddressNodeZ = "172.17.0.3";
//
//    private final Tpd10gbe tpd10gbeOnNodeA = Tpd10gbe.builder()
//        .subrack(Subrack.subrack1)
//        .slot(Slot.slot2)
//        .build();
//
//    private final Tpd10gbe tpd10gbeOnNodeZ = Tpd10gbe.builder()
//        .subrack(Subrack.subrack2)
//        .slot(Slot.slot3)
//        .build();
//
//    private final Port linePortEntryNodeA = Port.builder()
//        .transmitPort(3)
//        .receivePort(4)
//        .build();
//
//    private final Port linePortEntryNodeZ = Port.builder()
//        .transmitPort(7)
//        .receivePort(8)
//        .build();
//
//    private final PeerConnection peerConnectionAtoZ = PeerConnection.builder()
//        .localNodeIpAddress(ipAddressNodeA)
//        .localBoardEntry(tpd10gbeOnNodeA.getBoardEntry())
//        .localPort(linePortEntryNodeA)
//        .localMpoIdentifier(MpoIdentifier.NotPresent())
//        .remoteNodeIpAddress(ipAddressNodeZ)
//        .remoteBoardEntry(tpd10gbeOnNodeZ.getBoardEntry())
//        .remotePort(linePortEntryNodeZ)
//        .remoteMpoIdentifier(MpoIdentifier.NotPresent())
//        .build();
//
//    private final PeerEntry localPeerEntry = peerConnectionAtoZ.getLocalPeerEntry();
//    private final PeerEntry remotePeerEntry = peerConnectionAtoZ.getRemotePeerEntry();
//
//    @Test
//    public void test() {
//        log.info(peerConnectionAtoZ.toString());
//    }
//
//    @Test
//    public void transmitPeerEntryMibString() {
//        assertEquals("peer:1:2:0:3", localPeerEntry.getMibEntryString());
//    }
//
//    @Test
//    public void transmitPeerEntryMibPath() {
//        assertEquals("/mib/topo/peer/peer:1:2:0:3", localPeerEntry.getMibEntryPath());
//    }
//
//    @Test
//    public void transmitPeerEntryLocalLabel() {
//        assertEquals("1:2:3", localPeerEntry.getLocalLabel());
//    }
//
//    @Test
//    public void transmitPeerRemoteLabel() {
//        assertEquals("2:3:8", remotePeerEntry.getLocalLabel());
//    }
//
//    @Test
//    public void receivePeerEntryMibString() {
//        assertEquals("peer:2:3:0:8", remotePeerEntry.getMibEntryString());
//    }
//
//    @Test
//    public void receivePeerEntryMibPath() {
//        assertEquals("/mib/topo/peer/peer:2:3:0:8", remotePeerEntry.getMibEntryPath());
//    }
//
//    @Test
//    public void receivePeerEntryLocalLabel() {
//        assertEquals("2:3:8", remotePeerEntry.getLocalLabel());
//    }
//
//}
