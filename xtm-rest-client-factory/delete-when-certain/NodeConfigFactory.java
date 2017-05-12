package com.infinera.metro.dnam.acceptance.test.factory;

import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.NodeAccessData;
import com.infinera.metro.dnam.acceptance.test.node.NodeConfig;
import com.infinera.metro.dnam.acceptance.test.node.mib.*;

import java.util.Arrays;

public enum NodeConfigFactory {
    INSTANCE;

    public NodeConfig createNodeConfig(String nodeIpAddress, String peerNodeIpAddress, boolean isTransmitSide) {
        NodeAccessData nodeAccessData = NodeAccessData.builder()
                .ipAddress(nodeIpAddress)
                .port(80)
                .userName("root")
                .password("root")
                .build();

        Node node = NodeFactory.INSTANCE.createNode(nodeAccessData);

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

        LinePortEntry localLinePortEntry = LinePortEntry.builder()
                .linePort(LinePort.WDM)
                .subrack(1)
                .slot(2)
                .transmitPort(3)
                .receivePort(4)
                .build();

        LinePortEntry remoteLinePortEntry = LinePortEntry.builder()
                .linePort(LinePort.WDM)
                .subrack(1)
                .slot(2)
                .transmitPort(3)
                .receivePort(4)
                .build();

        PeerEntry peerEntry = PeerEntry.builder()
                .localLinePortEntry(localLinePortEntry)
                .remoteLinePortEntry(remoteLinePortEntry)
                .remoteNodeIpAddress(peerNodeIpAddress)
                .localMpoIdentifier(MpoIdentifier.createMpoIdentifierModuleNotPresent())
                .remoteMpoIdentifier(MpoIdentifier.createMpoIdentifierModuleNotPresent())
                .isTransmitSide(isTransmitSide)
                .build();

        ParameterList peerConfiguration = ParameterList.builder()
                .parameterList(Arrays.asList(
                        Configuration.builder()
                                .key("topoPeerLocalLabel")
                                .value(peerEntry.getPeerLocalLabel())
                                .build(),
                        Configuration.builder()
                                .key("topoPeerRemoteIpAddress")
                                .value(peerEntry.getPeerRemoteIpAddress())
                                .build(),
                        Configuration.builder()
                                .key("topoPeerRemoteLabel")
                                .value(peerEntry.getPeerRemoteLabel())
                                .build()
                ))
                .build();

        return NodeConfig.builder()
                .node(node)
                .boardEntry(boardEntry)
                .linePortEntry(localLinePortEntry)
                .linePortConfiguration(linePortConfiguration)
                .clientPortEntry(clientPortEntry)
                .clientPortConfiguration(clientPortConfiguration)
//                .peerEntry(peerEntry)
//                .peerConfiguration(peerConfiguration)
                .build();
    }
}
