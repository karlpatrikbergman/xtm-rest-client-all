package com.infinera.metro.dnam.acceptance.test.node;

import com.infinera.metro.dnam.acceptance.test.mib.*;
import com.infinera.metro.dnam.acceptance.test.node.dto.AnswerObjects;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

import static com.infinera.metro.dnam.acceptance.test.node.RestTemplateFactory.REST_TEMPLATE_FACTORY;
import static org.junit.Assert.assertNotNull;

@Category(IntegrationTest.class)
@Slf4j
public class NodeImplTest {

//    @ClassRule
//    public static DockerComposeRule docker = DockerComposeRule.builder()
//            .file("src/test/resources/docker-compose.yml")
//            .waitingForService("node1", HealthChecks.toHaveAllPortsOpen())
//            .build();

    private final String nodeIpAddress ="172.17.0.2";
//    private final String nodeIpAddress = "172.45.0.101";

    private final NodeImpl node = new NodeImpl(
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

    private BoardEntry boardEntry = BoardEntry.builder()
            .board(Board.TPD10GBE)
            .subrack(1)
            .slot(2)
            .build();


    @Test
    public void createGetAndDeleteBoard() throws IOException, InterruptedException {
        //Given
        boardEntry = BoardEntry.builder()
                .board(Board.TPD10GBE)
                .subrack(1)
                .slot(2)
                .build();
        LinePortEntry linePortEntry = LinePortEntry.builder()
                .linePort(LinePort.WDM)
                .subrack(1)
                .slot(2)
                .transceiverPort(3)
                .receiverPort(4)
                .build();
        Configuration configuration = Configuration.builder()
                .key("expectedFrequency")
                .value("ch926")
                .build();

        //When
        AnswerObjects createBoardAnswerObjects = node.createBoard(boardEntry);


        AnswerObjects setLinePortConfigurationAnswerObjects = node.setLinePortConfiguration(linePortEntry, configuration);

        //Then
        //TODO: Verify that expected frequency is correctly set in response
        AnswerObjects geBoardAnswerObjects = node.getBoard(boardEntry);

        //Clean up
        node.deleteBoard(boardEntry);

        //TODO: Verify error message?
        //Then
        try {
            AnswerObjects geBoardAnswerObjectsAfterDelete = node.getBoard(boardEntry);
        } catch (RuntimeException e) {
            assertNotNull(e);
        }
    }
}
