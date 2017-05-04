package com.infinera.metro.dnam.acceptance.test.node;

import com.infinera.metro.dnam.acceptance.test.mib.*;
import org.junit.Ignore;
import org.junit.Test;

import static com.infinera.metro.dnam.acceptance.test.node.RestTemplateFactory.REST_TEMPLATE_FACTORY;

public class NodeConfigTest {
    private final String nodeIpAddress ="172.17.0.2";

    private final Node node = new NodeImpl(
            new NodeRestClient(
                    new NodeConnection(
                            NodeAccessData.builder()
                                    .ipAddress(nodeIpAddress)
                                    .port(80)
                                    .userName("root")
                                    .password("root")
                                    .build(),
                            REST_TEMPLATE_FACTORY.createRestTemplate()
                    )
            )
    );
    BoardEntry boardEntry = BoardEntry.builder()
            .board(Board.TPD10GBE)
            .subrack(1)
            .slot(20)
            .build();

    LinePortEntry linePortEntry = LinePortEntry.builder()
            .linePort(LinePort.WDM)
            .subrack(1)
            .slot(2)
            .transmitPort(3)
            .receivePort(4)
            .build();

    Configuration linePortConfiguration = Configuration.builder()
            .key("expectedFrequency")
            .value("ch926")
            .build();

    ClientPortEntry clientPortEntry = ClientPortEntry.builder()
            .clientPort(ClientPort.CLIENT)
            .subrack(1)
            .slot(2)
            .transmitterPort(1)
            .receiverPort(2)
            .build();

    Configuration clientPortConfiguration = Configuration.builder()
            .key("configure")
            .value("lan10GbE yes")
            .build();

    @Ignore
    @Test
    public void test() {
        NodeConfig nodeConfig = NodeConfig.builder()
                .node(node)
                .boardEntry(boardEntry)
                .linePortEntry(linePortEntry)
                .linePortConfiguration(linePortConfiguration)
                .clientPortEntry(clientPortEntry)
                .clientPortConfiguration(clientPortConfiguration)
                .build();
    }
}
