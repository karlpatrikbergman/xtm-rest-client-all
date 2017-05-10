package com.infinera.metro.dnam.acceptance.test.factory;

import com.infinera.metro.dnam.acceptance.test.mib.*;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.NodeAccessData;
import com.infinera.metro.dnam.acceptance.test.node.config.NodeConfig;
import com.infinera.metro.dnam.acceptance.test.node.config.PeerConfig;
import org.junit.Test;

import java.io.IOException;

public class NodeConfigTest {
    private final String ipAddressNodeA ="172.17.0.2";
    private final String ipAddressNodeZ = "172.17.0.3";

//    private final LinePortEntry linePortEntryNodeA = LinePortEntry.builder()
//            .linePort(LinePort.WDM)
//            .subrack(1)
//            .slot(2)
//            .transmitPort(3)
//            .receivePort(4)
//            .build();
//
//    private final LinePortEntry linePortEntryNodeZ = LinePortEntry.builder()
//            .linePort(LinePort.WDM)
//            .subrack(1)
//            .slot(2)
//            .transmitPort(7)
//            .receivePort(8)
//            .build();

    @Test
    public void configureNodes() throws IOException {
        NodeConfig configNodeZ = createNodeZConfig();
        configNodeZ.apply();
        NodeConfig configNodeA = createNodeAConfig();
        configNodeA.apply();

        PeerConfig peerConfig = new PeerConfig(configNodeA, configNodeZ);
        peerConfig.apply();
    }

    private NodeConfig createNodeAConfig() {
        NodeAccessData nodeAccessDataNodeA = NodeAccessData.builder()
                .ipAddress(ipAddressNodeA)
                .port(80)
                .userName("root")
                .password("root")
                .build();

        Node nodeA = NodeFactory.INSTANCE.createNode(nodeAccessDataNodeA);

        BoardEntry boardEntry = BoardEntry.builder()
                .board(Board.TPD10GBE)
                .subrack(1)
                .slot(2)
                .build();

        Configuration linePortConfiguration = Configuration.builder()
                .key("expectedFrequency")
                .value("ch926")
                .build();

        ClientPortEntry clientPortEntry = ClientPortEntry.builder()
                .clientPort(ClientPort.CLIENT)
                .subrack(1)
                .slot(2)
                .transmitPort(1)
                .receivePort(2)
                .build();

        Configuration clientPortConfiguration = Configuration.builder()
                .key("configure")
                .value("lan10GbE yes")
                .build();

        LinePortEntry linePortEntryNodeA = LinePortEntry.builder()
            .linePort(LinePort.WDM)
            .subrack(1)
            .slot(2)
            .transmitPort(3)
            .receivePort(4)
            .build();
//
//        PeerEntry peerEntryNodeA = PeerEntry.builder()
//                .localLinePortEntry(linePortEntryNodeA)
//                .remoteLinePortEntry(linePortEntryNodeZ)
//                .remoteNodeIpAddress(ipAddressNodeZ)
//                .localMpoIdentifier(MpoIdentifier.createMpoIdentifierModuleNotPresent())
//                .remoteMpoIdentifier(MpoIdentifier.createMpoIdentifierModuleNotPresent())
//                .isTransmitSide(true)
//                .build();
//
//        ParameterList peerConfiguration = ParameterList.builder()
//                .parameterList(Arrays.asList(
//                        Configuration.builder()
//                                .key("topoPeerLocalLabel")
//                                .value(peerEntryNodeA.getPeerLocalLabel())
//                                .build(),
//                        Configuration.builder()
//                                .key("topoPeerRemoteIpAddress")
//                                .value(peerEntryNodeA.getPeerRemoteIpAddress())
//                                .build(),
//                        Configuration.builder()
//                                .key("topoPeerRemoteLabel")
//                                .value(peerEntryNodeA.getPeerRemoteLabel())
//                                .build()
//                ))
//                .build();

        return NodeConfig.builder()
                .node(nodeA)
                .boardEntry(boardEntry)
                .linePortEntry(linePortEntryNodeA)
                .linePortConfiguration(linePortConfiguration)
                .clientPortEntry(clientPortEntry)
                .clientPortConfiguration(clientPortConfiguration)
//                .peerEntry(peerEntryNodeA)
//                .peerConfiguration(peerConfiguration)
                .mpoIdentifier(MpoIdentifier.createMpoIdentifierModuleNotPresent())
                .build();
    }

    private NodeConfig createNodeZConfig() {
        NodeAccessData nodeAccessDataNodeZ = NodeAccessData.builder()
                .ipAddress(ipAddressNodeZ)
                .port(80)
                .userName("root")
                .password("root")
                .build();

        Node nodeZ = NodeFactory.INSTANCE.createNode(nodeAccessDataNodeZ);

        BoardEntry boardEntry = BoardEntry.builder()
                .board(Board.TPD10GBE)
                .subrack(1)
                .slot(2)
                .build();

        Configuration linePortConfiguration = Configuration.builder()
                .key("expectedFrequency")
                .value("ch926")
                .build();

        ClientPortEntry clientPortEntry = ClientPortEntry.builder()
                .clientPort(ClientPort.CLIENT)
                .subrack(1)
                .slot(2)
                .transmitPort(1)
                .receivePort(2)
                .build();

        Configuration clientPortConfiguration = Configuration.builder()
                .key("configure")
                .value("lan10GbE yes")
                .build();

        LinePortEntry linePortEntryNodeZ = LinePortEntry.builder()
            .linePort(LinePort.WDM)
            .subrack(1)
            .slot(2)
            .transmitPort(7)
            .receivePort(8)
            .build();

//        PeerEntry peerEntryNodeZ = PeerEntry.builder()
//                .localLinePortEntry(linePortEntryNodeZ)
//                .remoteLinePortEntry(linePortEntryNodeA)
//                .remoteNodeIpAddress(ipAddressNodeA)
//                .localMpoIdentifier(MpoIdentifier.createMpoIdentifierModuleNotPresent())
//                .remoteMpoIdentifier(MpoIdentifier.createMpoIdentifierModuleNotPresent())
//                .isTransmitSide(false)
//                .build();
//
//        ParameterList peerConfiguration = ParameterList.builder()
//                .parameterList(Arrays.asList(
//                        Configuration.builder()
//                                .key("topoPeerLocalLabel")
//                                .value(peerEntryNodeZ.getPeerLocalLabel())
//                                .build(),
//                        Configuration.builder()
//                                .key("topoPeerRemoteIpAddress")
//                                .value(peerEntryNodeZ.getPeerRemoteIpAddress())
//                                .build(),
//                        Configuration.builder()
//                                .key("topoPeerRemoteLabel")
//                                .value(peerEntryNodeZ.getPeerRemoteLabel())
//                                .build()
//                ))
//                .build();

        return NodeConfig.builder()
                .node(nodeZ)
                .boardEntry(boardEntry)
                .linePortEntry(linePortEntryNodeZ)
                .linePortConfiguration(linePortConfiguration)
                .clientPortEntry(clientPortEntry)
                .clientPortConfiguration(clientPortConfiguration)
//                .peerEntry(peerEntryNodeZ)
//                .peerConfiguration(peerConfiguration)
                .mpoIdentifier(MpoIdentifier.createMpoIdentifierModuleNotPresent())
                .build();
    }
}
