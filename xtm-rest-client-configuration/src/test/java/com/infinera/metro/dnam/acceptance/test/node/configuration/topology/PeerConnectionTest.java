package com.infinera.metro.dnam.acceptance.test.node.configuration.topology;

import com.infinera.metro.dnam.acceptance.test.node.mib.Attributes;
import com.infinera.metro.dnam.acceptance.test.node.mib.MpoIdentifier;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.LinePortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.PeerEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.LinePortType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * IMPORTANT NOTE:
 * For XTM with version >=27 an MPO identifier for port is necessary
 * <p>
 * From auto-node.setup:
 * Depending on container version, add a MPO identifier.
 * if (nodeVersion === "latest" || nodeVersion >= 27) {
 * const slotPortSepPos = subSlotPort.indexOf(":", 2);
 * return subSlotPort.slice(0, slotPortSepPos) + ":0" + subSlotPort.slice(slotPortSepPos);
 * } else {
 * return subSlotPort;
 * }
 */

//TODO: Look at tests that are commented out, and fix the ones that seems necessary/important

@Slf4j
public class PeerConnectionTest {

    private final String ipAddressNodeA = "172.17.0.2";
    private final String ipAddressNodeZ = "172.17.0.3";

    /**
     * At some point during test setup this line port entry is created in Node A: 172.17.0.2
     * XTM version < 27 therefore MPO identifier not is used.
     * The transmit port will be used when creating transmitting peer on Node A (peer:1:2:3):
     * 172.17.0.2/mib/topo/peer/peer:1:2:3/create.json
     */
    private final LinePortEntry linePortEntryNodeA = LinePortEntry.builder()
        .moduleType(ModuleType.WDM)
        .groupOrTableType(GroupOrTableType.IF)
        .linePortType(LinePortType.WDM)
        .subrack(1)
        .slot(2)
        .transmitPort(3)
        .receivePort(4)
        .build();

    /**
     * At some point during test setup this line port entry is created in Node Z: 172.17.0.3
     * XTM version >= 27 therefore MPO identifier is used.
     * The transmit port will be used when creating transmitting peer on Node Z (peer:1:2:0:3):
     * 172.17.0.3/mib/topo/peer/peer:1:2:0:3/create.json
     */
    private final LinePortEntry linePortEntryNodeZ = LinePortEntry.builder()
        .moduleType(ModuleType.WDM)
        .groupOrTableType(GroupOrTableType.IF)
        .linePortType(LinePortType.WDM)
        .subrack(2)
        .slot(3)
        .transmitPort(7)
        .receivePort(8)
        .build();

    private final PeerConnection peerConnectionAtoZ = PeerConnection.builder()
        .localNodeIpAddress(ipAddressNodeA)
        .localPortEntry(linePortEntryNodeA)
        .localMpoIdentifier(MpoIdentifier.NotPresent())
        .remoteNodeIpAddress(ipAddressNodeZ)
        .remotePortEntry(linePortEntryNodeZ)
        .remoteMpoIdentifier(MpoIdentifier.NotPresent())
        .build();

    private final PeerEntry localPeerEntry = peerConnectionAtoZ.getLocalPeerEntry();
    private final Attributes localPeerConfiguration = peerConnectionAtoZ.getLocalPeerConfiguration();
    private final PeerEntry remotePeerEntry = peerConnectionAtoZ.getRemotePeerEntry();
    private final Attributes remotePeerConfiguration = peerConnectionAtoZ.getRemotePeerConfiguration();

    @Test
    public void test() {
        log.info(peerConnectionAtoZ.toString());
    }

    @Test
    public void transmitPeerEntryMibString() {
        assertEquals("peer:1:2:0:3", localPeerEntry.getMibEntryString());
    }

    @Test
    public void transmitPeerEntryMibPath() {
        assertEquals("/mib/topo/peer/peer:1:2:0:3", localPeerEntry.getMibEntryPath());
    }

    @Test
    public void transmitPeerEntryLocalLabel() {
        assertEquals("1:2:3", localPeerEntry.getLocalLabel());
    }

    @Test
    public void transmitPeerEntryRemoteNodeIpAddress() {
//        assertEquals("172.17.0.3", localPeerConfiguration.getAttributes().stream().filter());
    }

    @Test
    public void transmitPeerRemoteLabel() {
        assertEquals("2:3:8", remotePeerEntry.getLocalLabel());
    }

    @Test
    public void receivePeerEntryMibString() {
        assertEquals("peer:2:3:0:8", remotePeerEntry.getMibEntryString());
    }

    @Test
    public void receivePeerEntryMibPath() {
        assertEquals("/mib/topo/peer/peer:2:3:0:8", remotePeerEntry.getMibEntryPath());
    }

    @Test
    public void receivePeerEntryLocalLabel() {
        assertEquals("2:3:8", remotePeerEntry.getLocalLabel());
    }

    @Test
    public void receivePeerEntryRemoteNodeIpAddress() {
//        assertEquals("", peerConnectionAtoZ.getLocalNodeIpAddress());
    }
//
//    @Test
//    public void receivePeerRemoteLabel() {
//        verifyRemoteLabel(linePortEntryNodeA, receivePeerEntryNodeZ);
//    }
//
//    @Test
//    public void receivePeerCreatePeerRequestPath() {
//        verifyCreatePeerRequestPath(linePortEntryNodeZ, receivePeerEntryNodeZ);
//    }
//
//    @Test
//    public void receivePeerSetLocalLabelRequest() {
//        verifySetLocalLabelRequest(linePortEntryNodeZ, receivePeerEntryNodeZ);
//    }
//
//    @Test
//    public void receivePeerSetRemoteNodeIpAddressRequest() {
//        verifySetRemoteNodeIpAddressRequest(linePortEntryNodeZ, receivePeerEntryNodeZ);
//    }
//
//    /**
//     *
//     * Verify output of PeerEntry.getMibEntryString against the local LinePortEntry used when creating the PeerEntry
//     *
//     * @param localLinePortEntry        The local LinePortEntry of the PeerEntry under test. Used for verification.
//     * @param peerEntry                 Can be PeerEntry of the transmit side or PeerEntry of receive side.
//     */
//    private void verifyEntryMibString(LinePortEntry localLinePortEntry, PeerEntry peerEntry) {
//        final int expectedPort = (peerEntry.getIsTransmitSide()) ? localLinePortEntry.getTransmitPort() : localLinePortEntry.getReceivePort();
//        final String mpoIdentifierString = (!peerEntry.getLocalMpoIdentifier().isXtmVersionEqualOrHigherThan27()) ? "" : MpoIdentifier.MODULE_NOT_PRESENT; //Module not present = ":0"
//        final String expectedMibEntryString = String.format("peer:%d:%d%s:%d", localLinePortEntry.getSubrack(),
//                localLinePortEntry.getSlot(), mpoIdentifierString, expectedPort);
//        final String actualMibEntryString = peerEntry.getMibEntryString();
//        assertEquals("Expected mib entry string", expectedMibEntryString, actualMibEntryString);
//        log.info("MibEntryString {}", actualMibEntryString);
//    }
//
//    private void verifyMibEntryPath(LinePortEntry localLinePortEntry, PeerEntry peerEntry) {
//        final int port = (peerEntry.getIsTransmitSide()) ? localLinePortEntry.getTransmitPort() : localLinePortEntry.getReceivePort();
//        final String mpoIdentifierString = (!peerEntry.getLocalMpoIdentifier().isXtmVersionEqualOrHigherThan27()) ? "" : MpoIdentifier.MODULE_NOT_PRESENT;
//        final String expectedMibEntryPath = String.format("/mib/topo/peer/peer:%d:%d%s:%d", localLinePortEntry.getSubrack(),
//                localLinePortEntry.getSlot(), mpoIdentifierString, port);
//        final String actualMibEntryPath = peerEntry.getMibEntryPath();
//        assertEquals("MibEntryPath", expectedMibEntryPath, actualMibEntryPath);
//        log.info("MibEntryPath {} ", actualMibEntryPath);
//    }
//
//    private void verifyLocalLabel(LinePortEntry localLinePortEntry, PeerEntry peerEntry) {
//        final int expectedPort = (peerEntry.getIsTransmitSide()) ? localLinePortEntry.getTransmitPort() : localLinePortEntry.getReceivePort();
//        final String mpoIdentifierString = (!peerEntry.getLocalMpoIdentifier().isXtmVersionEqualOrHigherThan27()) ? "" : MpoIdentifier.MODULE_NOT_PRESENT;
//        final String expectedPeerLocalLabel = String.format("%d:%d%s:%d", localLinePortEntry.getSubrack(),
//                localLinePortEntry.getSlot(), mpoIdentifierString, expectedPort);
//        final String actualPeerLocalLabel = peerEntry.getPeerLocalLabel();
//        assertEquals("Expected local label", expectedPeerLocalLabel, actualPeerLocalLabel);
//        log.info("Local label {}", actualPeerLocalLabel);
//    }
//
//    private void verifyRemoteNodeIpAddress(PeerEntry peerEntry) {
//        final String expectedPeerRemoteIpAddress = (peerEntry.getIsTransmitSide()) ? "172.17.0.3" : "172.17.0.2";
//        final String actualRemoteIpAddress = peerEntry.getPeerRemoteIpAddress();
//        assertEquals("Expected remote node ip address", expectedPeerRemoteIpAddress, actualRemoteIpAddress);
//        log.info("Remode nodeIpAddress {}", actualRemoteIpAddress);
//    }
//
//    private void verifyRemoteLabel(LinePortEntry remoteLinePortEntry, PeerEntry peerEntry) {
//        final int port = (peerEntry.getIsTransmitSide()) ? remoteLinePortEntry.getReceivePort() : remoteLinePortEntry.getTransmitPort();
//        final String mpoIdentifierString = (!peerEntry.getRemoteMpoIdentifier().isXtmVersionEqualOrHigherThan27()) ? "" : MpoIdentifier.MODULE_NOT_PRESENT;
//        final String expectedPeerRemoteLabel = String.format("%d:%d%s:%d", remoteLinePortEntry.getSubrack(),
//                remoteLinePortEntry.getSlot(), mpoIdentifierString, port);
//        final String actualPeerRemoteLabel = peerEntry.getPeerRemoteLabel();
//        assertEquals("Expected remote label", expectedPeerRemoteLabel, actualPeerRemoteLabel);
//        log.info("Remote label {}", actualPeerRemoteLabel);
//    }
//
//    private void verifyCreatePeerRequestPath(LinePortEntry localLinePortEntry, PeerEntry peerEntry) {
//        final int port = (peerEntry.getIsTransmitSide()) ? localLinePortEntry.getTransmitPort() : localLinePortEntry.getReceivePort();
//        final String mpoIdentifierString = (!peerEntry.getLocalMpoIdentifier().isXtmVersionEqualOrHigherThan27()) ? "" : MpoIdentifier.MODULE_NOT_PRESENT;
//        final String expectedCreatePeerRequest = String.format("/mib/topo/peer/peer:%d:%d%s:%d/create.json",
//                localLinePortEntry.getSubrack(), localLinePortEntry.getSlot(), mpoIdentifierString, port);
//        final String actualCreatePeerRequest = peerEntry.getMibEntryPath() + "/create.json";
//        assertEquals("Expected create peer http request path", expectedCreatePeerRequest, actualCreatePeerRequest);
//        log.info("Create peer http request path {}", actualCreatePeerRequest);
//    }
//
//    private void verifySetLocalLabelRequest(LinePortEntry localLinePortEntry, PeerEntry peerEntry) {
//        final int port = (peerEntry.getIsTransmitSide()) ? localLinePortEntry.getTransmitPort() : localLinePortEntry.getReceivePort();
//        final String mpoIdentifierString = (!peerEntry.getLocalMpoIdentifier().isXtmVersionEqualOrHigherThan27()) ? "" : MpoIdentifier.MODULE_NOT_PRESENT;
//        final String expectedPeerLocalLabel = String.format("%d:%d%s:%d", localLinePortEntry.getSubrack(),
//                localLinePortEntry.getSlot(), mpoIdentifierString, port);
//        final String expectedSetTopoPeerLocalLabelRequest = String.format("/mib/topo/peer/peer:%d:%d%s:%d/set.json?topoPeerLocalLabel=%s",
//                localLinePortEntry.getSubrack(), localLinePortEntry.getSlot(), mpoIdentifierString, port, expectedPeerLocalLabel);
//        final String actualSetTopoPeerLocalLabelRequest = peerEntry.getMibEntryPath() + "/set.json?topoPeerLocalLabel=" + peerEntry.getPeerLocalLabel();
//        assertEquals("Expected set local label http request path", expectedSetTopoPeerLocalLabelRequest, actualSetTopoPeerLocalLabelRequest);
//        log.info("Set local label http request path {}", actualSetTopoPeerLocalLabelRequest);
//    }
//
//    private void verifySetRemoteNodeIpAddressRequest(LinePortEntry localLinePortEntry, PeerEntry peerEntry) {
//        final int port = (peerEntry.getIsTransmitSide()) ? localLinePortEntry.getTransmitPort() : localLinePortEntry.getReceivePort();
//        final String mpoIdentifierString = (!peerEntry.getLocalMpoIdentifier().isXtmVersionEqualOrHigherThan27()) ? "" : MpoIdentifier.MODULE_NOT_PRESENT;
//        final String expectedPeerRemoteIpAddress = (peerEntry.getIsTransmitSide()) ? "172.17.0.3" : "172.17.0.2";
//        final String expectedSetRemoteIpAddressRequest = String.format("/mib/topo/peer/peer:%d:%d%s:%d/set.json?topoPeerRemoteIpAddress=%s",
//                localLinePortEntry.getSubrack(), localLinePortEntry.getSlot(), mpoIdentifierString,
//                port, expectedPeerRemoteIpAddress);
//        final String actualSetRemoteIpAddressRequest = peerEntry.getMibEntryPath() + "/set.json?topoPeerRemoteIpAddress=" + peerEntry.getRemoteNodeIpAddress();
//        assertEquals("Expected set remote node ip address http request path", expectedSetRemoteIpAddressRequest, actualSetRemoteIpAddressRequest);
//        log.info("Set remote node ip address http request path {}", actualSetRemoteIpAddressRequest);
//    }
}
