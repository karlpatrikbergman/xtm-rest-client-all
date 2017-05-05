//package com.infinera.metro.dnam.acceptance.test.mib;
//
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//
//@Slf4j
//public class AsymmetricPeerEntryTest {
//    //PEER ONE TRANSMIT
//    //Node A: 172.17.0.2
//    //a123_z128_tx
//    private final LinePortEntry a123_z128_tx_LinePortEntry = LinePortEntry.builder()
//            .linePort(LinePort.WDM)
//            .subrack(1)
//            .slot(2)
//            .transmitterPort(3)
//            .receiverPort(4)        //Not used
//            .build();
//
//    //PEER ONE RECEIVE
//    //Node Z: 172.17.0.3
//    //a123_z128_rx
//    private final LinePortEntry a123_z128_rx_LinePortEntry = LinePortEntry.builder()
//            .linePort(LinePort.WDM)
//            .subrack(1)
//            .slot(2)
//            .transmitterPort(7)     //Not used
//            .receiverPort(8)
//            .build();
//
//    //PEER TWO TRANSMIT
//    //Node Z: 172.17.0.3
//    //z123_a128_tx
//    private final LinePortEntry z123_a128_tx = LinePortEntry.builder()
//            .linePort(LinePort.WDM)
//            .subrack(1)
//            .slot(2)
//            .transmitterPort(3)
//            .receiverPort(4)        //Not used
//            .build();
//
//    //PEER ONE RECEIVE
//    //Node A: 172.17.0.2
//    //z123_a128_rx
//    private final LinePortEntry z123_a128_rx = LinePortEntry.builder()
//            .linePort(LinePort.WDM)
//            .subrack(1)
//            .slot(2)
//            .transmitterPort(7)     //Not used in this peer
//            .receiverPort(8)
//            .build();
//
//    @Test
//    public void createNodeApeerEntryTransmit() {
//        PeerEntry peerOneTransmit = PeerEntry.builder()
//                .localLinePortEntry(a123_z128_tx_LinePortEntry)  //Local transmit port 3
//                .remoteLinePortEntry(a123_z128_rx_LinePortEntry) //Remote receive port 8
//                .remoteNodeIpAddress("172.17.0.3")
//                .isTransmitSide(true)
//                .build();
//
////        verify(localPeerEntryTransmit);
//    }
//
////    @Test
////    public void createNodeApeerEntryReceive() {
////        PeerEntry localPeerEntryReceive = localPeerEntryBuilder
////                .isTransmitSide(false)
////                .build();
////        verify(localPeerEntryReceive);
////    }
//
////    private void verifyTransmit(PeerEntry peerEntry) {
////        int localPort = (peerEntry.getIsTransmitSide()) ? localLinePortEntryTransmit.getTransmitPort() : localLinePortEntryTransmit.getReceivePort();
////        int remotePort = (peerEntry.getIsTransmitSide()) ? remoteLinePortEntry.getReceivePort() : remoteLinePortEntry.getTransmitPort();
////        final String expectedMibEntryPath = String.format("/mib/topo/peer/peer:1:2:%d", localPort);
////        final String expectedMibEntryString = String.format("peer:1:2:%d", localPort);
////        final String expectedPeerLocalLabel = String.format("1:2:%d", localPort);
////        final String expectedPeerRemoteIpAddress = "172.17.0.3";
////        final int expectedPeerRemoteSubrack = 1;
////        final int expectedPeerRemoteSlot = 2;
////        final int expectedPeerRemotePort = 4;
////        final String expectedPeerRemoteLabel = String.format("1:2:%d", remotePort);
////
////        final String actualMibEntryPath = peerEntry.getMibEntryPath();
////        assertEquals("MibEntryPath", expectedMibEntryPath, actualMibEntryPath);
////        log.info("MibEntryPath {} ", actualMibEntryPath);
////
////        final String actualMibEntryString = peerEntry.getMibEntryString();
////        assertEquals("Expected mib entry string", expectedMibEntryString, actualMibEntryString);
////        log.info("MibEntryString {}", actualMibEntryString);
////
////        final String actualPeerLocalLabel = peerEntry.getPeerLocalLabel();
////        assertEquals("Expected local label", expectedPeerLocalLabel, actualPeerLocalLabel);
////        log.info("Local label {}", actualPeerLocalLabel);
////
////        final String actualRemoteIpAddress = peerEntry.getPeerRemoteIpAddress();
////        assertEquals("Expected remote node ip address", expectedPeerRemoteIpAddress, actualRemoteIpAddress);
////        log.info("Remode nodeIpAddress {}", actualRemoteIpAddress);
////
////        final int actualPeerRemoteSubrack = peerEntry.getPeerRemoteSubrack();
////        assertEquals("Expected remote subrack", expectedPeerRemoteSubrack, actualPeerRemoteSubrack);
////        log.info("Remote subrack {}", actualPeerRemoteSubrack);
////
////        final int actualPeerRemoteSlot = peerEntry.getPeerRemoteSlot();
////        assertEquals("Expected remote slot", expectedPeerRemoteSlot, actualPeerRemoteSlot);
////        log.info("Remote slot {}", actualPeerRemoteSlot);
////
////
////        final int actualPeerRemotePort = peerEntry.getPeerRemotePort();
////        assertEquals("Expected remote port", expectedPeerRemotePort, actualPeerRemotePort);
////        log.info("Remote port {}", actualPeerRemotePort);
////
////        final String actualPeerRemoteLabel = peerEntry.getPeerRemoteLabel();
////        assertEquals("Expected remote label", expectedPeerRemoteLabel, actualPeerRemoteLabel);
////        log.info("Remote label {}", actualPeerRemoteLabel);
////
////    }
//}
