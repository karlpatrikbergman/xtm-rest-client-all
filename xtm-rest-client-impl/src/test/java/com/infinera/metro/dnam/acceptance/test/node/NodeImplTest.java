package com.infinera.metro.dnam.acceptance.test.node;

import com.infinera.metro.dnam.acceptance.test.node.mib.*;

import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.connection.waiting.HealthChecks;
import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;
import java.util.Arrays;

import static com.infinera.metro.dnam.acceptance.test.node.RestTemplateFactory.REST_TEMPLATE_FACTORY;
import static org.junit.Assert.assertNotNull;

@Category(IntegrationTest.class)
@Slf4j
public class NodeImplTest {

    @ClassRule
    public static DockerComposeRule docker = DockerComposeRule.builder()
            .file("src/test/resources/docker-compose.yml")
            .waitingForService("node1", HealthChecks.toHaveAllPortsOpen())
            .build();

//    private final String nodeIpAddressNodeA ="172.17.0.2";
    private final String nodeIpAddressNodeA = "172.45.0.101";

    private final Node node = new NodeImpl(
        new NodeRestClient(
            new NodeConnection(
                    NodeAccessData.builder()
                            .ipAddress(nodeIpAddressNodeA)
                            .port(80)
                            .userName("root")
                            .password("root")
                            .build(),
                    REST_TEMPLATE_FACTORY.createRestTemplate()
            )
        )
    );

    @Test
    public void configureNodeA() throws IOException, InterruptedException {
        //Given
        BoardEntry boardEntry = BoardEntry.builder()
                .board(Board.TPD10GBE)
                .subrack(1)
                .slot(2)
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
                .transmitPort(1)
                .receivePort(2)
                .build();

        Configuration clientPortConfiguration = Configuration.builder()
                .key("configure")
                .value("lan10GbE yes")
                .build();

        LinePortEntry remoteLinePortEntry = LinePortEntry.builder()
                .linePort(LinePort.WDM)
                .subrack(1)
                .slot(2)
                .transmitPort(7)
                .receivePort(8)
                .build();

        PeerEntry peerEntry = PeerEntry.builder()
                .localLinePortEntry(linePortEntry)
                .remoteLinePortEntry(remoteLinePortEntry)
                .nodeIpAddress(nodeIpAddressNodeA)
                .remoteNodeIpAddress("172.17.0.3")
                .localMpoIdentifier(MpoIdentifier.createMpoIdentifierModuleNotPresent())
                .remoteMpoIdentifier(MpoIdentifier.createMpoIdentifierModuleNotPresent())
                .isTransmitSide(true)
                .build();

        ParameterList parameterList = ParameterList.builder()
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

        //When
        AnswerObjects createBoardAnswerObjects = node.createBoard(boardEntry);

        AnswerObjects setLinePortConfigurationAnswerObjects = node.setLinePortConfiguration(linePortEntry, ParameterList.of(linePortConfiguration));

        AnswerObjects setClientPortConfigurationAnswerObjects = node.setClientPortConfiguration(clientPortEntry, ParameterList.of(clientPortConfiguration));

        AnswerObjects createPeerAnswerObjects = node.createLocalPeer(peerEntry);

        AnswerObjects setPeerConfigAnswerObjects = node.setLocalPeerConfiguration(peerEntry, parameterList);

        //TODO: Verify line port settings in response
        //TODO: Verify client port settings in response
        //Then
//        AnswerObjects geBoardAnswerObjects = node.getBoard(boardEntry);

//        //Clean up
        node.deleteBoard(boardEntry);
//
//        //TODO: Verify error message?
        try {
            AnswerObjects geBoardAnswerObjectsAfterDelete = node.getBoard(boardEntry);
        } catch (RuntimeException e) {
            assertNotNull(e);
        }
    }
}
