package com.infinera.metro.dnam.acceptance.test.mib;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * IMPORTANT NOTE:
 * For XTM with version >=27 an MPO identifier for port is necessary
 *
 *  From auto-node.setup
 *  Depending on container version, add a MPO identifier.
 *  if (nodeVersion === "latest" || nodeVersion >= 27) {
 *      const slotPortSepPos = subSlotPort.indexOf(":", 2);
 *      return subSlotPort.slice(0, slotPortSepPos) + ":0" + subSlotPort.slice(slotPortSepPos);
 *  } else {
 *      return subSlotPort;
 *  }
 */

@Slf4j
public class CreatePeerEntryTest {
    /**
     * At some point during test setup this line port entry is created in Node A: 172.17.0.2
     * XTM version < 27 therefore MPO identifier not is used.
     * The transmit port will be used when creating transmitting peer on Node A (peer:1:2:3):
     *      172.17.0.2/mib/topo/peer/peer:1:2:3/create.json
     */
    private final LinePortEntry linePortEntryNodeA = LinePortEntry.builder()
            .linePort(LinePort.WDM)
            .subrack(1)
            .slot(2)
            .transmitPort(3)
            .receivePort(4)
            .build();

    /**
     * At some point during test setup this line port entry is created in Node Z: 172.17.0.3
     * XTM version >= 27 therefore MPO identifier is used.
     * The transmit port will be used when creating transmitting peer on Node Z (peer:1:2:0:3):
     *      172.17.0.3/mib/topo/peer/peer:1:2:0:3/create.json
     */
    private final LinePortEntry linePortEntryNodeZ = LinePortEntry.builder()
            .linePort(LinePort.WDM)
            .subrack(2)
            .slot(3)
            .transmitPort(7)
            .receivePort(8)
            .build();

    //TODO: create static "invert" method for PeerEntry class
    /**
     * The PeerEntry created here will be used to create a transmit peer entry on Node A (172.17.0.2)
     * Local XTM version is below 27, so no MTO identifier is added to peer name
     * Local peer entry name will be peer:1:2:3
     *
     */
    PeerEntry transmitPeerEntryNodeA = PeerEntry.builder()
            .localLinePortEntry(linePortEntryNodeA)
            .remoteLinePortEntry(linePortEntryNodeZ)
            .remoteNodeIpAddress("172.17.0.3")
            .localMpoIdentifier(MpoIdentifier.createBelowXtmVersion27())
            .remoteMpoIdentifier(MpoIdentifier.createMpoIdentifierModuleNotPresent())
            .isTransmitSide(true)
            .build();

    /**
     * The PeerEntry created here will be used to create a receive peer entry on Node Z (172.17.0.3)
     * Local XTM version is higher than 27, so MTO identifier is added to local peer name
     * Local peer entry name will be peer:2:3:0:8
     *
     */
    PeerEntry receivePeerEntryNodeZ = PeerEntry.builder()
            .localLinePortEntry(linePortEntryNodeZ)
            .remoteLinePortEntry(linePortEntryNodeA)
            .remoteNodeIpAddress("172.17.0.2")
            .localMpoIdentifier(MpoIdentifier.createMpoIdentifierModuleNotPresent())
            .remoteMpoIdentifier(MpoIdentifier.createBelowXtmVersion27())
            .isTransmitSide(false)
            .build();

    @Test
    public void transmitPeerEntryMibString() {
        verifyEntryMibString(linePortEntryNodeA, transmitPeerEntryNodeA, true, true);
    }

    @Test
    public void transmitPeerEntryMibPath() {
        verifyMibEntryPath(linePortEntryNodeA, transmitPeerEntryNodeA, true, true);
    }

    @Test
    public void transmitPeerEntryLocalLabel() {
        verifyLocalLabel(linePortEntryNodeA, transmitPeerEntryNodeA, true, true);
    }

    @Test
    public void transmitPeerEntryRemoteNodeIpAddress() {
        veriyRemoteNodeIpAddress(transmitPeerEntryNodeA, true);
    }

    @Test
    public void transmitPeerRemoteSubrackSlotPort() {
        verifyRemoteSubrackSlotPort(linePortEntryNodeZ, transmitPeerEntryNodeA, true);
    }

    @Test
    public void transmitPeerRemoteLabel() {
        verifyRemoteLabel(linePortEntryNodeZ, transmitPeerEntryNodeA, true, false);
    }

    @Test
    public void transmitPeerCreatePeerRequestPath() {
        verifyCreatePeerRequestPath(linePortEntryNodeA, transmitPeerEntryNodeA, true, true);
    }

    /***/

    @Test
    public void receivePeerEntryMibString() {
        verifyEntryMibString(linePortEntryNodeZ, receivePeerEntryNodeZ, false, false);
    }

    @Test
    public void receivePeerEntryMibPath() {
        verifyMibEntryPath(linePortEntryNodeZ, receivePeerEntryNodeZ, false, false);
    }

    @Test
    public void receivePeerEntryLocalLabel() {
        verifyLocalLabel(linePortEntryNodeZ, receivePeerEntryNodeZ, false, false);
    }

    @Test
    public void receivePeerEntryRemoteNodeIpAddress() {
        veriyRemoteNodeIpAddress(receivePeerEntryNodeZ, false);
    }

    @Test
    public void receivePeerRemoteSubrackSlotPort() {
        verifyRemoteSubrackSlotPort(linePortEntryNodeA, receivePeerEntryNodeZ, false);
    }

    @Test
    public void receivePeerRemoteLabel() {
        verifyRemoteLabel(linePortEntryNodeA, receivePeerEntryNodeZ, false, true);
    }

    @Test
    public void receivePeerCreatePeerRequestPath() {
        verifyCreatePeerRequestPath(linePortEntryNodeZ, receivePeerEntryNodeZ, false, false);
    }

    private void verifyEntryMibString(LinePortEntry linePortEntry, PeerEntry peerEntry, boolean isTransmitSide, boolean isXtmVersionBelow27) {
        final int port = (isTransmitSide) ? linePortEntry.getTransmitPort() : linePortEntry.getReceivePort();
        final String mpoIdentifierString = (isXtmVersionBelow27) ? "" : MpoIdentifier.MODULE_NOT_PRESENT;
        final String expectedMibEntryString = String.format("peer:%d:%d%s:%d", linePortEntry.getSubrack(),
                linePortEntry.getSlot(), mpoIdentifierString, port);
        final String actualMibEntryString = peerEntry.getMibEntryString();
        assertEquals("Expected mib entry string", expectedMibEntryString, actualMibEntryString);
        log.info("MibEntryString {}", actualMibEntryString);
    }

    private void verifyMibEntryPath(LinePortEntry linePortEntry, PeerEntry peerEntry, boolean isTransmitSide, boolean isXtmVersionBelow27) {
        final int port = (isTransmitSide) ? linePortEntry.getTransmitPort() : linePortEntry.getReceivePort();
        final String mpoIdentifierString = (isXtmVersionBelow27) ? "" : MpoIdentifier.MODULE_NOT_PRESENT;
        final String expectedMibEntryPath = String.format("/mib/topo/peer/peer:%d:%d%s:%d", linePortEntry.getSubrack(),
                linePortEntry.getSlot(), mpoIdentifierString, port);
        final String actualMibEntryPath = peerEntry.getMibEntryPath();
        assertEquals("MibEntryPath", expectedMibEntryPath, actualMibEntryPath);
        log.info("MibEntryPath {} ", actualMibEntryPath);
    }

    private void verifyLocalLabel(LinePortEntry linePortEntry, PeerEntry peerEntry, boolean isTransmitSide, boolean isXtmVersionBelow27) {
        final int port = (isTransmitSide) ? linePortEntry.getTransmitPort() : linePortEntry.getReceivePort();
        final String mpoIdentifierString = (isXtmVersionBelow27) ? "" : MpoIdentifier.MODULE_NOT_PRESENT;
        final String expectedPeerLocalLabel = String.format("%d:%d%s:%d", linePortEntry.getSubrack(),
                linePortEntry.getSlot(), mpoIdentifierString, port);
        final String actualPeerLocalLabel = peerEntry.getLocalLabel();
        assertEquals("Expected local label", expectedPeerLocalLabel, actualPeerLocalLabel);
        log.info("Local label {}", actualPeerLocalLabel);
    }

    private void veriyRemoteNodeIpAddress(PeerEntry peerEntry, boolean isTransmitSide) {
        final String expectedPeerRemoteIpAddress = (isTransmitSide) ? "172.17.0.3" : "172.17.0.2";
        final String actualRemoteIpAddress = peerEntry.getPeerRemoteIpAddress();
        assertEquals("Expected remote node ip address", expectedPeerRemoteIpAddress, actualRemoteIpAddress);
        log.info("Remode nodeIpAddress {}", actualRemoteIpAddress);
    }

    private void verifyRemoteSubrackSlotPort(LinePortEntry remoteLinePortEntry, PeerEntry peerEntry, boolean isTransmitSide) {
        final int actualPeerRemoteSubrack = peerEntry.getPeerRemoteSubrack();
        assertTrue("Expected remote subrack", remoteLinePortEntry.getSubrack() == actualPeerRemoteSubrack);
        log.info("Remote subrack {}", actualPeerRemoteSubrack);

        final int actualPeerRemoteSlot = peerEntry.getPeerRemoteSlot();
        assertTrue("Expected remote slot", remoteLinePortEntry.getSlot() == actualPeerRemoteSlot);
        log.info("Remote slot {}", actualPeerRemoteSlot);

        final int expectedPeerRemotePort = (isTransmitSide) ? remoteLinePortEntry.getReceivePort() : remoteLinePortEntry.getTransmitPort();
        final int actualPeerRemotePort = peerEntry.getPeerRemotePort();
        assertTrue("Expected remote port", expectedPeerRemotePort == actualPeerRemotePort);
        log.info("Remote port {}", actualPeerRemotePort);
    }

    private void verifyRemoteLabel(LinePortEntry linePortEntry, PeerEntry peerEntry, boolean isTransmitSide, boolean isXtmVersionBelow27) {
        final int port = (isTransmitSide) ? linePortEntry.getReceivePort() : linePortEntry.getTransmitPort();
        final String mpoIdentifierString = (isXtmVersionBelow27) ? "" : MpoIdentifier.MODULE_NOT_PRESENT;
        final String expectedPeerRemoteLabel = String.format("%d:%d%s:%d", linePortEntry.getSubrack(),
                linePortEntry.getSlot(), mpoIdentifierString, port);
        final String actualPeerRemoteLabel = peerEntry.getPeerRemoteLabel();
        assertEquals("Expected remote label", expectedPeerRemoteLabel, actualPeerRemoteLabel);
        log.info("Remote label {}", actualPeerRemoteLabel);
    }

    private void verifyCreatePeerRequestPath(LinePortEntry linePortEntry, PeerEntry peerEntry, boolean isTransmitSide, boolean isXtmVersionBelow27) {
        final int port = (isTransmitSide) ? linePortEntry.getTransmitPort() : linePortEntry.getReceivePort();
        final String mpoIdentifierString = (isXtmVersionBelow27) ? "" : MpoIdentifier.MODULE_NOT_PRESENT;
        final String expectedCreatePeerRequest = String.format("/mib/topo/peer/peer:%d:%d%s:%d/create.json",
                linePortEntry.getSubrack(), linePortEntry.getSlot(), mpoIdentifierString, port);
        final String actualCreatePeerRequest = peerEntry.getMibEntryPath() + "/create.json";
        assertEquals("Expected create peer http request path", expectedCreatePeerRequest, actualCreatePeerRequest);
        log.info("Create peer http request path {}", actualCreatePeerRequest);
    }

    private void foo() {

//
//        final String expectedSetTopoPeerLocalLabelRequest = String.format("/mib/topo/peer/peer:%d:%d%s:%d/set.json?topoPeerLocalLabel=%s",
//                transmitLinePortEntry.getSubrack(), transmitLinePortEntry.getSlot(), mpoIdentifierString, transmitLinePortEntry.getTransmitPort(), expectedPeerLocalLabel);
//        final String actualSetTopoPeerLocalLabelRequest = peerEntry.getMibEntryPath() + "/set.json?topoPeerLocalLabel=" + peerEntry.getLocalLabel();
//        assertEquals("Expected set local label http request path", expectedSetTopoPeerLocalLabelRequest, actualSetTopoPeerLocalLabelRequest);
//        log.info("Set local label http request path {}", actualSetTopoPeerLocalLabelRequest);
//
//        final String expectedSetRemoteIpAddressRequest = String.format("/mib/topo/peer/peer:%d:%d%s:%d/set.json?topoPeerRemoteIpAddress=%s",
//                transmitLinePortEntry.getSubrack(), transmitLinePortEntry.getSlot(), mpoIdentifierString,
//                transmitLinePortEntry.getTransmitPort(), expectedPeerRemoteIpAddress);
//        final String actualSetRemoteIpAddressRequest = peerEntry.getMibEntryPath() + "/set.json?topoPeerRemoteIpAddress=" + peerEntry.getRemoteNodeIpAddress();
//        assertEquals("Expected set remote node ip address http request path", expectedSetRemoteIpAddressRequest, actualSetRemoteIpAddressRequest);
//        log.info("Set remote node ip address http request path {}", actualSetRemoteIpAddressRequest);
//
//        final String expectedSetRemoteSubrackRequest = String.format("/mib/topo/peer/peer:%d:%d%s:%d/set.json?topoPeerRemoteSubrack=%s",
//                transmitLinePortEntry.getSubrack(), transmitLinePortEntry.getSlot(), mpoIdentifierString,
//                transmitLinePortEntry.getTransmitPort(), receiveLinePortEntry.getSubrack());
//        final String actualSetRemoteSubrackRequest = peerEntry.getMibEntryPath() + "/set.json?topoPeerRemoteSubrack=" + peerEntry.getRemoteLinePortEntry().getSubrack();
//        assertEquals("Expected set remote subrack http request path", expectedSetRemoteSubrackRequest, actualSetRemoteSubrackRequest);
//        log.info("Set remote subrack http request path {}", actualSetRemoteSubrackRequest);
//
//        final String expectedSetRemoteSlotRequest = String.format("/mib/topo/peer/peer:%d:%d%s:%d/set.json?topoPeerRemoteSlot=%s",
//                transmitLinePortEntry.getSubrack(), transmitLinePortEntry.getSlot(), mpoIdentifierString,
//                transmitLinePortEntry.getTransmitPort(), receiveLinePortEntry.getSlot());
//        final String actualSetRemoteSlotRequest = peerEntry.getMibEntryPath() + "/set.json?topoPeerRemoteSlot=" + peerEntry.getRemoteLinePortEntry().getSlot();
//        assertEquals("Expected set remote slot http request path", expectedSetRemoteSlotRequest, actualSetRemoteSlotRequest);
//        log.info("Set remote slot http request path {}", actualSetRemoteSlotRequest);
//
//        final String expectedSetRemotePortRequest = String.format("/mib/topo/peer/peer:%d:%d%s:%d/set.json?topoPeerRemotePort=%s",
//                transmitLinePortEntry.getSubrack(), transmitLinePortEntry.getSlot(), mpoIdentifierString,
//                transmitLinePortEntry.getTransmitPort(), receiveLinePortEntry.getReceivePort());
//        final String actualSetRemotePortRequest = peerEntry.getMibEntryPath() + "/set.json?topoPeerRemotePort=" + peerEntry.getRemoteLinePortEntry().getReceivePort();
//        assertEquals("Expected set remote port http request path", expectedSetRemotePortRequest, actualSetRemotePortRequest);
//        log.info("Set remote port http request path {}", actualSetRemotePortRequest);
//
//        final String expectedSetRemoteLabelRequest = String.format("/mib/topo/peer/peer:%d:%d%s:%d/set.json?topoPeerRemoteLabel=%s",
//                transmitLinePortEntry.getSubrack(), transmitLinePortEntry.getSlot(), mpoIdentifierString,
//                transmitLinePortEntry.getTransmitPort(), expectedPeerRemoteLabel);
//        final String actualSetRemoteLabelRequest = peerEntry.getMibEntryPath() + "/set.json?topoPeerRemoteLabel=" + peerEntry.getPeerRemoteLabel();
//        assertEquals("Expected set remote label http request path", expectedSetRemoteLabelRequest, actualSetRemoteLabelRequest);
//        log.info("Set remote label http request path {}", actualSetRemoteLabelRequest);
    }
}
