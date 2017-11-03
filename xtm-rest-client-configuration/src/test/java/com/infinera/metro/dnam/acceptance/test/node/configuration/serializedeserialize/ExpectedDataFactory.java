package com.infinera.metro.dnam.acceptance.test.node.configuration.serializedeserialize;

import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.NodeAccessData;
import com.infinera.metro.dnam.acceptance.test.node.NodeImpl;
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

import java.util.Optional;

/**
 * This class creates test data for serialization and deserialization tests
 *
 * IMPORTANT !!!
 * Only add one instance of items (as for example NodeEquipmentApplier) to lists if items, since we us findFirst().
 * Else data verification may fail.
 */
enum ExpectedDataFactory {
    INSTANCE;

    private final NodeNetworkApplier nodeNetworkApplier;

    ExpectedDataFactory() {
        this.nodeNetworkApplier = createNodeNetworkApplier();
    }


    NodeNetworkApplier getNodeNetworkApplier() {
        return this.nodeNetworkApplier;
    }

    NodeEquipmentApplier getNodeEquipmentApplier() {
        return nodeNetworkApplier.getNodeEquipmentAppliers().stream()
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No " + NodeEquipmentApplier.class.getSimpleName() + " found"));
    }

    NodeEquipment getNodeEquipment() {
        return getNodeEquipmentApplier().getNodeEquipment();
    }

    Tpd10gbe getTpd10Gbe() {
        Optional<Board> boardOptional = getNodeEquipment().getBoard(Subrack.subrack1, Slot.slot2);
        if(boardOptional.isPresent() && boardOptional.get() instanceof  Tpd10gbe) {
            return (Tpd10gbe) boardOptional.get();
        } else {
            throw new RuntimeException("No " + Tpd10gbe.class.getSimpleName() + " found");
        }
    }


    InternalConnectionApplier getInternalConnectionApplier() {
        return nodeNetworkApplier.getInternalConnectionAppliers().stream()
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No " + InternalConnectionApplier.class.getSimpleName() + " found"));
    }

    InternalConnection getInternalConnection() {
        return getInternalConnectionApplier().getInternalConnections().stream()
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No " + InternalConnection.class.getSimpleName() + " found"));
    }

    PeerConnectionApplier getPeerConnectionApplier() {
        return nodeNetworkApplier.getPeerConnectionAppliers().stream()
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No " + PeerConnectionApplier.class.getSimpleName() + " found"));
    }

    PeerConnection getPeerConnection() {
        return getPeerConnectionApplier().getPeerConnections().stream()
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No " + PeerConnection.class.getSimpleName() + " found"));
    }

    Peer getPeer() {
        return getPeerConnection().getLocalPeer();
    }

    private NodeNetworkApplier createNodeNetworkApplier() {
        final Node nodeA = NodeImpl.createDefault("172.17.0.2");
        final Node nodeZ = NodeImpl.createDefault("172.17.0.3");
        final NodeAccessData accessDataNodeA = NodeAccessData.createDefault("172.17.0.2");
        final NodeAccessData accessDataNodeZ = NodeAccessData.createDefault("172.17.0.3");

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

        final NodeEquipmentApplier nodeAEquipmentApplier = NodeEquipmentApplier.builder()
            .nodeEquipment(tdb10bgeAndMdu40EvenlNodeEquipment)
            .nodeAccessData(accessDataNodeA)
            .build();

        final NodeEquipmentApplier nodeZEquipmentApplier = NodeEquipmentApplier.builder()
            .nodeEquipment(tdb10bgeAndMdu40EvenlNodeEquipment)
            .nodeAccessData(accessDataNodeZ)
            .build();

        final InternalConnection internalConnectionTpd10gbeTx3ToMdu40EvenLRx42 = InternalConnection.builder()
            .fromPeer(tpd10gbe.getPeer(linePortTx3Rx4.getTransmitPort()))
            .toPeer(mdu40EvenL.getPeer(clientPortTx41_Rx42.getReceivePort()))
            .build();

        final InternalConnectionApplier nodeAinternalConnectionApplier = InternalConnectionApplier.builder()
            .internalConnection(internalConnectionTpd10gbeTx3ToMdu40EvenLRx42)
            .internalConnection(internalConnectionTpd10gbeTx3ToMdu40EvenLRx42.invert())
            .nodeAccessData(accessDataNodeA)
            .build();

        final InternalConnectionApplier nodeZinternalConnectionApplier = InternalConnectionApplier.builder()
            .internalConnection(internalConnectionTpd10gbeTx3ToMdu40EvenLRx42)
            .internalConnection(internalConnectionTpd10gbeTx3ToMdu40EvenLRx42.invert())
            .nodeAccessData(accessDataNodeZ)
            .build();

        final PeerConnection peerConnectionNodeANodeZ = PeerConnection.builder()
            .localPeer(mdu40EvenL.getPeer(linePortTx81Rx82.getTransmitPort()))
            .remotePeer(mdu40EvenL.getPeer(linePortTx81Rx82.getReceivePort()))
            .build();

        final PeerConnectionApplier nodeAtoNodeZpeerConnectionApplier = PeerConnectionApplier.builder()
            .fromNodeAccessData(accessDataNodeA)
            .toNodeAccessData(accessDataNodeZ)
            .peerConnection(peerConnectionNodeANodeZ)
            .build();

        final PeerConnectionApplier nodeZtoNodeApeerConnectionApplier = PeerConnectionApplier.builder()
            .fromNodeAccessData(accessDataNodeZ)
            .toNodeAccessData(accessDataNodeA)
            .peerConnection(peerConnectionNodeANodeZ.invert())
            .build();

        return NodeNetworkApplier.builder()
            .nodeEquipmentApplier(nodeAEquipmentApplier)
            .nodeEquipmentApplier(nodeZEquipmentApplier)
            .internalConnectionApplier(nodeAinternalConnectionApplier)
            .internalConnectionApplier(nodeZinternalConnectionApplier)
            .peerConnectionApplier(nodeAtoNodeZpeerConnectionApplier)
            .peerConnectionApplier(nodeZtoNodeApeerConnectionApplier)
            .build();
    }
}
