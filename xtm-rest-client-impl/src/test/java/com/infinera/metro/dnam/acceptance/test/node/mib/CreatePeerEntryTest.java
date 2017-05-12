package com.infinera.metro.dnam.acceptance.test.node.mib;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * IMPORTANT NOTE:
 * For XTM with version >=27 an MPO identifier for port is necessary
 *
 *  From auto-node.setup:
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

    //TODO: create static "invert" method for PeerEntry class?
    /**
     * The PeerEntry created here will be used to create a transmit peer entry on Node A (172.17.0.2)
     * Local XTM version is below 27, so no MTO identifier is added to peer name
     * Local peer entry name will be peer:1:2:3
     *
     */
    private final PeerEntry transmitPeerEntryNodeA = PeerEntry.builder()
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
    private final PeerEntry receivePeerEntryNodeZ = PeerEntry.builder()
            .localLinePortEntry(linePortEntryNodeZ)
            .remoteLinePortEntry(linePortEntryNodeA)
            .remoteNodeIpAddress("172.17.0.2")
            .localMpoIdentifier(MpoIdentifier.createMpoIdentifierModuleNotPresent())
            .remoteMpoIdentifier(MpoIdentifier.createBelowXtmVersion27())
            .isTransmitSide(false)
            .build();

    @Test
    public void transmitPeerEntryMibString() {
        verifyEntryMibString(linePortEntryNodeA, transmitPeerEntryNodeA);
    }

    @Test
    public void transmitPeerEntryMibPath() {
        verifyMibEntryPath(linePortEntryNodeA, transmitPeerEntryNodeA);
    }

    @Test
    public void transmitPeerEntryLocalLabel() {
        verifyLocalLabel(linePortEntryNodeA, transmitPeerEntryNodeA);
    }

    @Test
    public void transmitPeerEntryRemoteNodeIpAddress() {
        verifyRemoteNodeIpAddress(transmitPeerEntryNodeA);
    }

    @Test
    public void transmitPeerRemoteLabel() {
        verifyRemoteLabel(linePortEntryNodeZ, transmitPeerEntryNodeA);
    }

    @Test
    public void transmitPeerCreatePeerRequestPath() {
        verifyCreatePeerRequestPath(linePortEntryNodeA, transmitPeerEntryNodeA);
    }

    @Test
    public void transmitPeerSetLocalLabelRequest() {
        verifySetLocalLabelRequest(linePortEntryNodeA, transmitPeerEntryNodeA);
    }

    @Test
    public void transmitPeerSetRemoteNodeIpAddressRequest() {
        verifySetRemoteNodeIpAddressRequest(linePortEntryNodeA, transmitPeerEntryNodeA);
    }

    /***/

    @Test
    public void receivePeerEntryMibString() {
        verifyEntryMibString(linePortEntryNodeZ, receivePeerEntryNodeZ);
    }

    @Test
    public void receivePeerEntryMibPath() {
        verifyMibEntryPath(linePortEntryNodeZ, receivePeerEntryNodeZ);
    }

    @Test
    public void receivePeerEntryLocalLabel() {
        verifyLocalLabel(linePortEntryNodeZ, receivePeerEntryNodeZ);
    }

    @Test
    public void receivePeerEntryRemoteNodeIpAddress() {
        verifyRemoteNodeIpAddress(receivePeerEntryNodeZ);
    }

    @Test
    public void receivePeerRemoteLabel() {
        verifyRemoteLabel(linePortEntryNodeA, receivePeerEntryNodeZ);
    }

    @Test
    public void receivePeerCreatePeerRequestPath() {
        verifyCreatePeerRequestPath(linePortEntryNodeZ, receivePeerEntryNodeZ);
    }

    @Test
    public void receivePeerSetLocalLabelRequest() {
        verifySetLocalLabelRequest(linePortEntryNodeZ, receivePeerEntryNodeZ);
    }

    @Test
    public void receivePeerSetRemoteNodeIpAddressRequest() {
        verifySetRemoteNodeIpAddressRequest(linePortEntryNodeZ, receivePeerEntryNodeZ);
    }

    /**
     *
     * Verify output of PeerEntry.getMibEntryString against the local LinePortEntry used when creating the PeerEntry
     *
     * @param localLinePortEntry        The local LinePortEntry of the PeerEntry under test. Used for verification.
     * @param peerEntry                 Can be PeerEntry of the transmit side or PeerEntry of receive side.
     */
    private void verifyEntryMibString(LinePortEntry localLinePortEntry, PeerEntry peerEntry) {
        final int expectedPort = (peerEntry.getIsTransmitSide()) ? localLinePortEntry.getTransmitPort() : localLinePortEntry.getReceivePort();
        final String mpoIdentifierString = (!peerEntry.getLocalMpoIdentifier().isXtmVersionEqualOrHigherThan27()) ? "" : MpoIdentifier.MODULE_NOT_PRESENT; //Module not present = ":0"
        final String expectedMibEntryString = String.format("peer:%d:%d%s:%d", localLinePortEntry.getSubrack(),
                localLinePortEntry.getSlot(), mpoIdentifierString, expectedPort);
        final String actualMibEntryString = peerEntry.getMibEntryString();
        assertEquals("Expected mib entry string", expectedMibEntryString, actualMibEntryString);
        log.info("MibEntryString {}", actualMibEntryString);
    }

    private void verifyMibEntryPath(LinePortEntry localLinePortEntry, PeerEntry peerEntry) {
        final int port = (peerEntry.getIsTransmitSide()) ? localLinePortEntry.getTransmitPort() : localLinePortEntry.getReceivePort();
        final String mpoIdentifierString = (!peerEntry.getLocalMpoIdentifier().isXtmVersionEqualOrHigherThan27()) ? "" : MpoIdentifier.MODULE_NOT_PRESENT;
        final String expectedMibEntryPath = String.format("/mib/topo/peer/peer:%d:%d%s:%d", localLinePortEntry.getSubrack(),
                localLinePortEntry.getSlot(), mpoIdentifierString, port);
        final String actualMibEntryPath = peerEntry.getMibEntryPath();
        assertEquals("MibEntryPath", expectedMibEntryPath, actualMibEntryPath);
        log.info("MibEntryPath {} ", actualMibEntryPath);
    }

    private void verifyLocalLabel(LinePortEntry localLinePortEntry, PeerEntry peerEntry) {
        final int expectedPort = (peerEntry.getIsTransmitSide()) ? localLinePortEntry.getTransmitPort() : localLinePortEntry.getReceivePort();
        final String mpoIdentifierString = (!peerEntry.getLocalMpoIdentifier().isXtmVersionEqualOrHigherThan27()) ? "" : MpoIdentifier.MODULE_NOT_PRESENT;
        final String expectedPeerLocalLabel = String.format("%d:%d%s:%d", localLinePortEntry.getSubrack(),
                localLinePortEntry.getSlot(), mpoIdentifierString, expectedPort);
        final String actualPeerLocalLabel = peerEntry.getPeerLocalLabel();
        assertEquals("Expected local label", expectedPeerLocalLabel, actualPeerLocalLabel);
        log.info("Local label {}", actualPeerLocalLabel);
    }

    private void verifyRemoteNodeIpAddress(PeerEntry peerEntry) {
        final String expectedPeerRemoteIpAddress = (peerEntry.getIsTransmitSide()) ? "172.17.0.3" : "172.17.0.2";
        final String actualRemoteIpAddress = peerEntry.getPeerRemoteIpAddress();
        assertEquals("Expected remote node ip address", expectedPeerRemoteIpAddress, actualRemoteIpAddress);
        log.info("Remode nodeIpAddress {}", actualRemoteIpAddress);
    }

    private void verifyRemoteLabel(LinePortEntry remoteLinePortEntry, PeerEntry peerEntry) {
        final int port = (peerEntry.getIsTransmitSide()) ? remoteLinePortEntry.getReceivePort() : remoteLinePortEntry.getTransmitPort();
        final String mpoIdentifierString = (!peerEntry.getRemoteMpoIdentifier().isXtmVersionEqualOrHigherThan27()) ? "" : MpoIdentifier.MODULE_NOT_PRESENT;
        final String expectedPeerRemoteLabel = String.format("%d:%d%s:%d", remoteLinePortEntry.getSubrack(),
                remoteLinePortEntry.getSlot(), mpoIdentifierString, port);
        final String actualPeerRemoteLabel = peerEntry.getPeerRemoteLabel();
        assertEquals("Expected remote label", expectedPeerRemoteLabel, actualPeerRemoteLabel);
        log.info("Remote label {}", actualPeerRemoteLabel);
    }

    private void verifyCreatePeerRequestPath(LinePortEntry localLinePortEntry, PeerEntry peerEntry) {
        final int port = (peerEntry.getIsTransmitSide()) ? localLinePortEntry.getTransmitPort() : localLinePortEntry.getReceivePort();
        final String mpoIdentifierString = (!peerEntry.getLocalMpoIdentifier().isXtmVersionEqualOrHigherThan27()) ? "" : MpoIdentifier.MODULE_NOT_PRESENT;
        final String expectedCreatePeerRequest = String.format("/mib/topo/peer/peer:%d:%d%s:%d/create.json",
                localLinePortEntry.getSubrack(), localLinePortEntry.getSlot(), mpoIdentifierString, port);
        final String actualCreatePeerRequest = peerEntry.getMibEntryPath() + "/create.json";
        assertEquals("Expected create peer http request path", expectedCreatePeerRequest, actualCreatePeerRequest);
        log.info("Create peer http request path {}", actualCreatePeerRequest);
    }

    private void verifySetLocalLabelRequest(LinePortEntry localLinePortEntry, PeerEntry peerEntry) {
        final int port = (peerEntry.getIsTransmitSide()) ? localLinePortEntry.getTransmitPort() : localLinePortEntry.getReceivePort();
        final String mpoIdentifierString = (!peerEntry.getLocalMpoIdentifier().isXtmVersionEqualOrHigherThan27()) ? "" : MpoIdentifier.MODULE_NOT_PRESENT;
        final String expectedPeerLocalLabel = String.format("%d:%d%s:%d", localLinePortEntry.getSubrack(),
                localLinePortEntry.getSlot(), mpoIdentifierString, port);
        final String expectedSetTopoPeerLocalLabelRequest = String.format("/mib/topo/peer/peer:%d:%d%s:%d/set.json?topoPeerLocalLabel=%s",
                localLinePortEntry.getSubrack(), localLinePortEntry.getSlot(), mpoIdentifierString, port, expectedPeerLocalLabel);
        final String actualSetTopoPeerLocalLabelRequest = peerEntry.getMibEntryPath() + "/set.json?topoPeerLocalLabel=" + peerEntry.getPeerLocalLabel();
        assertEquals("Expected set local label http request path", expectedSetTopoPeerLocalLabelRequest, actualSetTopoPeerLocalLabelRequest);
        log.info("Set local label http request path {}", actualSetTopoPeerLocalLabelRequest);
    }

    private void verifySetRemoteNodeIpAddressRequest(LinePortEntry localLinePortEntry, PeerEntry peerEntry) {
        final int port = (peerEntry.getIsTransmitSide()) ? localLinePortEntry.getTransmitPort() : localLinePortEntry.getReceivePort();
        final String mpoIdentifierString = (!peerEntry.getLocalMpoIdentifier().isXtmVersionEqualOrHigherThan27()) ? "" : MpoIdentifier.MODULE_NOT_PRESENT;
        final String expectedPeerRemoteIpAddress = (peerEntry.getIsTransmitSide()) ? "172.17.0.3" : "172.17.0.2";
        final String expectedSetRemoteIpAddressRequest = String.format("/mib/topo/peer/peer:%d:%d%s:%d/set.json?topoPeerRemoteIpAddress=%s",
                localLinePortEntry.getSubrack(), localLinePortEntry.getSlot(), mpoIdentifierString,
                port, expectedPeerRemoteIpAddress);
        final String actualSetRemoteIpAddressRequest = peerEntry.getMibEntryPath() + "/set.json?topoPeerRemoteIpAddress=" + peerEntry.getRemoteNodeIpAddress();
        assertEquals("Expected set remote node ip address http request path", expectedSetRemoteIpAddressRequest, actualSetRemoteIpAddressRequest);
        log.info("Set remote node ip address http request path {}", actualSetRemoteIpAddressRequest);
    }
}
