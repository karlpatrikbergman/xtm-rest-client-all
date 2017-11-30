package com.infinera.metro.dnam.acceptance.test.node.configuration.serializedeserialize;

import com.infinera.metro.dnam.acceptance.test.node.NodeAccessData;
import com.infinera.metro.dnam.acceptance.test.node.configuration.*;
import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.board.BoardSetAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.client.ClientPortConfigAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.client.ClientPortSetAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.line.LinePortSetAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Board;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Mdu40EvenL;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Tpd10gbe;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.ClientPort;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.LinePort;
import com.infinera.metro.dnam.acceptance.test.node.configuration.topology.InternalConnection;
import com.infinera.metro.dnam.acceptance.test.node.configuration.topology.Peer;
import com.infinera.metro.dnam.acceptance.test.node.configuration.topology.PeerConnection;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

/**
 * This class creates test data for serialization and deserialization tests
 *
 * IMPORTANT !!!
 * Only add one instance of items (as for example NodeEquipmentApplier) to lists if items, since we us findFirst().
 * Else data verification may fail.
 */
enum ExpectedTestDataFactory {
    INSTANCE;

    private final NodeConfiguration nodeConfiguration;
    private final String NODE_A = "nodeA", NODE_Z = "nodeZ";
    private final Peers FROM_NODEA_TO_NODEZ = Peers.of(NODE_A, NODE_Z), FROM_NODEZ_TO_NODEA = FROM_NODEA_TO_NODEZ.invert();

    ExpectedTestDataFactory() {
        this.nodeConfiguration = createNodeNetwork();
    }

    NodeConfiguration getNodeConfiguration() {
        return this.nodeConfiguration;
    }

    Tpd10gbe getTpd10Gbe() {
        Optional<Board> boardOptional = getNodeEquipment().getBoard(Subrack.subrack1, Slot.slot2);
        if(boardOptional.isPresent() && boardOptional.get() instanceof  Tpd10gbe) {
            return (Tpd10gbe) boardOptional.get();
        } else {
            throw new RuntimeException("No " + Tpd10gbe.class.getSimpleName() + " found");
        }
    }

    NodeEquipment getNodeEquipment() {
        return getNodeConfiguration().getNodeEquipmentMap().get(NODE_A);
    }

    InternalConnection getInternalConnection() {
        return getNodeConfiguration().getInternalConnectionMap().get(NODE_Z).stream()
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No " + InternalConnection.class.getSimpleName() + " found"));
    }

    PeerConnection getPeerConnection() {
        return getNodeConfiguration().getPeerConnectionMap().get(FROM_NODEA_TO_NODEZ).stream()
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No " + PeerConnection.class.getSimpleName() + " found"));
    }

    Peer getPeer() {
        return getPeerConnection().getLocalPeer();
    }

    private NodeConfiguration createNodeNetwork() {
        final LinePort linePortTx3Rx4 = LinePort.builder()
            .transmitPort(3)
            .receivePort(4)
            .linePortAttribute(LinePortSetAttributes.of("expectedFrequency", "ch939"))
            .build();

        final ClientPort clientPortTx1Rx2 = ClientPort.builder()
            .transmitPort(1)
            .receivePort(2)
            .clientPortAttribute(ClientPortSetAttributes.of("clientIfExpectedTxFrequency", "w1530"))
            .clientPortAttribute(ClientPortConfigAttributes.of("clientIfConfigurationCommand", "wan10GbE yes"))
            .build();

        final Tpd10gbe tpd10gbe = Tpd10gbe.builder()
            .subrack(Subrack.subrack1)
            .slot(Slot.slot2)
            .boardAttribute(BoardSetAttributes.of("adminStatus", "up")) //Admin status could be set to up by default?
            .clientPort(clientPortTx1Rx2)
            .linePort(linePortTx3Rx4)
            .build();

        final ClientPort clientPortTx41_Rx42 = ClientPort.builder()
            .transmitPort(41)
            .receivePort(42)
            .clientPortAttribute(ClientPortSetAttributes.of("descr", "My description"))
            .build();

        final LinePort linePortTx81Rx82 = LinePort.builder()
            .transmitPort(81)
            .receivePort(82)
            .linePortAttribute(LinePortSetAttributes.of("descr", "My description"))
            .build();

        final Mdu40EvenL mdu40EvenL = Mdu40EvenL.builder()
            .subrack(Subrack.subrack1)
            .slot(Slot.slot3)
            .boardAttribute(BoardSetAttributes.of("adminStatus", "up"))
            .clientPort(clientPortTx41_Rx42)
            .linePort(linePortTx81Rx82)
            .build();

        //Same equipment and configuration on both two nodes
        final NodeEquipment tdb10bgeAndMdu40EvenlNodeEquipment = NodeEquipment.builder()
            .board(tpd10gbe)
            .board(mdu40EvenL)
            .build();

        final InternalConnection internalConnectionTpd10gbeTx3ToMdu40EvenLRx42 = InternalConnection.builder()
            .fromPeer(tpd10gbe.getPeer(linePortTx3Rx4.getTransmitPort()))
            .toPeer(mdu40EvenL.getPeer(clientPortTx41_Rx42.getReceivePort()))
            .build();

        final PeerConnection peerConnectionNodeANodeZ = PeerConnection.builder()
            .localPeer(mdu40EvenL.getPeer(linePortTx81Rx82.getTransmitPort()))
            .remotePeer(mdu40EvenL.getPeer(linePortTx81Rx82.getReceivePort()))
            .build();

        return NodeConfiguration.builder()
            .accessDataForNode(NODE_A, NodeAccessData.createDefault("172.17.0.2"))
            .accessDataForNode(NODE_Z, NodeAccessData.createDefault("172.17.0.3"))
            .nodeEquipmentForNode(NODE_A, tdb10bgeAndMdu40EvenlNodeEquipment)
            .nodeEquipmentForNode(NODE_Z, tdb10bgeAndMdu40EvenlNodeEquipment)
            .internalConnectionForNode(NODE_A, Arrays.asList(internalConnectionTpd10gbeTx3ToMdu40EvenLRx42, internalConnectionTpd10gbeTx3ToMdu40EvenLRx42.invert()))
            .internalConnectionForNode(NODE_Z, Arrays.asList(internalConnectionTpd10gbeTx3ToMdu40EvenLRx42, internalConnectionTpd10gbeTx3ToMdu40EvenLRx42.invert()))
            .peerConnectionForPeers(FROM_NODEA_TO_NODEZ, Collections.singletonList(peerConnectionNodeANodeZ))
            .peerConnectionForPeers(FROM_NODEZ_TO_NODEA, Collections.singletonList(peerConnectionNodeANodeZ))
            .build();
    }
}
