package com.infinera.metro.dnam.acceptance.test.factory;

import com.infinera.metro.dnam.acceptance.test.node.IntegrationTest;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.NodeAccessData;
import com.infinera.metro.dnam.acceptance.test.node.mib.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Category(IntegrationTest.class)
public class NodeFactoryTest {
    private final String ipAddressNodeA = "172.17.0.2";
    private final String ipAddressNodeZ = "172.17.0.3";

    private final LinePortEntry linePortEntryNodeA = LinePortEntry.builder()
            .linePort(LinePort.WDM)
            .subrack(1)
            .slot(2)
            .transmitPort(3)
            .receivePort(4)
            .build();

    private final LinePortEntry linePortEntryNodeZ = LinePortEntry.builder()
            .linePort(LinePort.WDM)
            .subrack(1)
            .slot(2)
            .transmitPort(7)
            .receivePort(8)
            .build();

    @Test
    public void test() throws IOException {
        configureNodeA();
        configureNodeZ();
    }

    public void configureNodeA() throws IOException {
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

        nodeA.createBoard(boardEntry);

        PeerEntry peerEntryNodeA = PeerEntry.builder()
                .localLinePortEntry(linePortEntryNodeA)
                .remoteLinePortEntry(linePortEntryNodeZ)
                .remoteNodeIpAddress(ipAddressNodeZ)
                .localMpoIdentifier(MpoIdentifier.createMpoIdentifierModuleNotPresent())
                .remoteMpoIdentifier(MpoIdentifier.createMpoIdentifierModuleNotPresent())
                .isTransmitSide(true)
                .build();

        nodeA.createLocalPeer(peerEntryNodeA);

        ParameterList parameterList = ParameterList.builder()
                .parameterList(Arrays.asList(
                        Configuration.builder()
                                .key("topoPeerLocalLabel")
                                .value(peerEntryNodeA.getPeerLocalLabel())
                                .build(),
                        Configuration.builder()
                                .key("topoPeerRemoteIpAddress")
                                .value(peerEntryNodeA.getPeerRemoteIpAddress())
                                .build(),
                        Configuration.builder()
                                .key("topoPeerRemoteLabel")
                                .value(peerEntryNodeA.getPeerRemoteLabel())
                                .build()
                ))
                .build();

        nodeA.setLocalPeerConfiguration(peerEntryNodeA, parameterList);
    }

    private void configureNodeZ() throws IOException {
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

        nodeZ.createBoard(boardEntry);

        PeerEntry peerEntryNodeZ = PeerEntry.builder()
                .localLinePortEntry(linePortEntryNodeZ)
                .remoteLinePortEntry(linePortEntryNodeA)
                .remoteNodeIpAddress(ipAddressNodeA)
                .localMpoIdentifier(MpoIdentifier.createMpoIdentifierModuleNotPresent())
                .remoteMpoIdentifier(MpoIdentifier.createMpoIdentifierModuleNotPresent())
                .isTransmitSide(false)
                .build();

        nodeZ.createLocalPeer(peerEntryNodeZ);

        ParameterList parameterList = ParameterList.builder()
                .parameterList(Arrays.asList(
                        Configuration.builder()
                                .key("topoPeerLocalLabel")
                                .value(peerEntryNodeZ.getPeerLocalLabel())
                                .build(),
                        Configuration.builder()
                                .key("topoPeerRemoteIpAddress")
                                .value(peerEntryNodeZ.getPeerRemoteIpAddress())
                                .build(),
                        Configuration.builder()
                                .key("topoPeerRemoteLabel")
                                .value(peerEntryNodeZ.getPeerRemoteLabel())
                                .build()
                ))
                .build();

        nodeZ.setLocalPeerConfiguration(peerEntryNodeZ, parameterList);
    }
}
