package com.infinera.metro.dnam.acceptance.test.node;

import com.infinera.metro.dnam.acceptance.test.node.mib.*;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.BoardEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.ClientPortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.LinePortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.PeerEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.*;
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
                .boardType(BoardType.TPD10GBE)
                .subrack(1)
                .slot(2)
                .build();

        LinePortEntry linePortEntry = LinePortEntry.builder()
                .moduleType(ModuleType.WDM)
                .groupOrTableType(GroupOrTableType.IF)
                .linePortType(LinePortType.WDM)
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
                .moduleType(ModuleType.CLIENT)
                .groupOrTableType(GroupOrTableType.IF)
                .clientPortType(ClientPortType.CLIENT)
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
                .moduleType(ModuleType.WDM)
                .groupOrTableType(GroupOrTableType.IF)
                .linePortType(LinePortType.WDM)
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

        Configurations configurations = Configurations.builder()
                .configurations(Arrays.asList(
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

        AnswerObjects setLinePortConfigurationAnswerObjects = node.setLinePortAttributes(linePortEntry, Configurations.of(linePortConfiguration));

        AnswerObjects setClientPortConfigurationAnswerObjects = node.configureClientPortAttributes(clientPortEntry, Configurations.of(clientPortConfiguration));

        AnswerObjects createPeerAnswerObjects = node.createLocalPeer(peerEntry);

        AnswerObjects setPeerConfigAnswerObjects = node.setLocalPeerConfiguration(peerEntry, configurations);

        //TODO: Verify line port settings in response
        //TODO: Verify CLIENT port settings in response
        //Then
//        AnswerObjects geBoardAnswerObjects = node.getBoard(boardEntry);

//        //Clean up
        node.deleteBoard(boardEntry);
//
//        //TODO: Verify error message instead of waiting for exceeding number of retries?
        try {
            AnswerObjects geBoardAnswerObjectsAfterDelete = node.getBoard(boardEntry);
        } catch (RuntimeException e) {
            assertNotNull(e);
        }
    }
}
