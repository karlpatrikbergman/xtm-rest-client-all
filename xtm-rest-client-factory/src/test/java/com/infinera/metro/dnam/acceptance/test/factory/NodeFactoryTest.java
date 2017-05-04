package com.infinera.metro.dnam.acceptance.test.factory;

import com.infinera.metro.dnam.acceptance.test.node.IntegrationTest;
import com.infinera.metro.dnam.acceptance.test.mib.Board;
import com.infinera.metro.dnam.acceptance.test.mib.BoardEntry;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.NodeAccessData;
import com.infinera.metro.dnam.acceptance.test.node.dto.AnswerObjects;
import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.connection.waiting.HealthChecks;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

@Category(IntegrationTest.class)
public class NodeFactoryTest {
    @ClassRule
    public static DockerComposeRule docker = DockerComposeRule.builder()
            .file("src/test/resources/docker-compose.yml")
            .waitingForService("node1", HealthChecks.toHaveAllPortsOpen())
            .build();

    private Node node;

    private final BoardEntry BOARD_ENTRY = BoardEntry.builder()
            .board(Board.TP10G)
            .subrack(1)
            .slot(1)
            .build();

    private final NodeAccessData nodeAccessData = NodeAccessData.builder()
            .ipAddress("172.45.0.101")
            .port(80)
            .userName("root")
            .password("root")
            .build();

    @Test
    public void foo() throws IOException {

        //Given
        node = NodeFactory.INSTANCE.createNode(nodeAccessData);

        //When
        AnswerObjects createBoardAnswerObjects = node.createBoard(BOARD_ENTRY);

        //Then
        AnswerObjects getBoardAnswerObjects = node.getBoard(BOARD_ENTRY);

        //Clean up after test
        node.deleteBoard(BOARD_ENTRY);

    }
}
