package com.infinera.metro.dnam.acceptance.test.mib;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Slf4j
public class SymmetricPeerEntryTest {
    /**
     * At some point during test setup this line port entry is created in Node A: 172.17.0.2
     *
     * The transmit port will be used when creating transmitting peer on Node A (peer:1:2:3):
     *      172.17.0.2/mib/topo/peer/peer:1:2:3/create.json
     *
     * The receive port  will be used when creating receiving peer on Node A (peer:1:2:4):
     *      172.17.0.2/mib/topo/peer/peer:1:2:4/create.json
     *
     The peers described above does not belong to the same peer "rx-tx-pair"
     */
    private final LinePortEntry localLinePortEntry = LinePortEntry.builder()
            .linePort(LinePort.WDM)
            .subrack(1)
            .slot(2)
            .transmitterPort(3)
            .receiverPort(4)
            .build();

    /**
     * At some point during test setup this line port entry is created in Node Z: 172.17.0.3
     *
     * The transmit port will be used when creating transmitting peer on Node Z (peer:1:2:3):
     *      172.17.0.3/mib/topo/peer/peer:1:2:3/create.json
     *
     * The receive port  will be used when creating receiving peer on Node Z (peer:1:2:4)
     *      172.17.0.3/mib/topo/peer/peer:1:2:4/create.json
     *
     * The peers described above does not belong to the same peer "rx-tx-pair"
     */
    private final LinePortEntry remoteLinePortEntry = LinePortEntry.builder()
            .linePort(LinePort.WDM)
            .subrack(1)
            .slot(2)
            .transmitterPort(3)
            .receiverPort(4)
            .build();

    private final  PeerEntry.PeerEntryBuilder localPeerEntryBuilder = PeerEntry.builder()
            .localLinePortEntry(localLinePortEntry)
            .remoteLinePortEntry(remoteLinePortEntry)
            .remoteNodeIpAddress("172.17.0.3");

    /**
     * The PeerEntry created here will be used to create a peer entry on Node A (172.17.0.2)
     * Peer entry name will be peer:1:2:3
     */
    @Test
    public void createNodeApeerEntryTransmit() {
        PeerEntry localPeerEntryTransmit = localPeerEntryBuilder
                .isTransmitSide(true)
                .build();
        verify(localPeerEntryTransmit);
    }

    @Test
    public void createNodeApeerEntryReceive() {
        PeerEntry localPeerEntryReceive = localPeerEntryBuilder
                .isTransmitSide(false)
                .build();
        verify(localPeerEntryReceive);
    }

    private void verify(PeerEntry peerEntry) {
        int localPort = (peerEntry.getIsTransmitSide()) ? localLinePortEntry.getTransmitterPort() : localLinePortEntry.getReceiverPort();
        int remotePort = (peerEntry.getIsTransmitSide()) ? remoteLinePortEntry.getReceiverPort() : remoteLinePortEntry.getTransmitterPort();
        final String expectedMibEntryPath = String.format("/mib/topo/peer/peer:1:2:%d", localPort);
        final String expectedMibEntryString = String.format("peer:1:2:%d", localPort);
        final String expectedPeerLocalLabel = String.format("1:2:%d", localPort);
        final String expectedPeerRemoteIpAddress = "172.17.0.3";
        final int expectedPeerRemoteSubrack = 1;
        final int expectedPeerRemoteSlot = 2;
        final int expectedPeerRemotePort = 4;
        final String expectedPeerRemoteLabel = String.format("1:2:%d", remotePort);

        final String actualMibEntryPath = peerEntry.getMibEntryPath();
        assertEquals("MibEntryPath", expectedMibEntryPath, actualMibEntryPath);
        log.info("MibEntryPath {} ", actualMibEntryPath);

        final String actualMibEntryString = peerEntry.getMibEntryString();
        assertEquals("Expected mib entry string", expectedMibEntryString, actualMibEntryString);
        log.info("MibEntryString {}", actualMibEntryString);

        final String actualPeerLocalLabel = peerEntry.getLocalLabel();
        assertEquals("Expected local label", expectedPeerLocalLabel, actualPeerLocalLabel);
        log.info("Local label {}", actualPeerLocalLabel);

        final String actualRemoteIpAddress = peerEntry.getPeerRemoteIpAddress();
        assertEquals("Expected remote node ip address", expectedPeerRemoteIpAddress, actualRemoteIpAddress);
        log.info("Remode nodeIpAddress {}", actualRemoteIpAddress);

        final int actualPeerRemoteSubrack = peerEntry.getPeerRemoteSubrack();
        assertEquals("Expected remote subrack", expectedPeerRemoteSubrack, actualPeerRemoteSubrack);
        log.info("Remote subrack {}", actualPeerRemoteSubrack);

        final int actualPeerRemoteSlot = peerEntry.getPeerRemoteSlot();
        assertEquals("Expected remote slot", expectedPeerRemoteSlot, actualPeerRemoteSlot);
        log.info("Remote slot {}", actualPeerRemoteSlot);


        final int actualPeerRemotePort = peerEntry.getPeerRemotePort();
        assertEquals("Expected remote port", expectedPeerRemotePort, actualPeerRemotePort);
        log.info("Remote port {}", actualPeerRemotePort);

        final String actualPeerRemoteLabel = peerEntry.getPeerRemoteLabel();
        assertEquals("Expected remote label", expectedPeerRemoteLabel, actualPeerRemoteLabel);
        log.info("Remote label {}", actualPeerRemoteLabel);

    }
}
