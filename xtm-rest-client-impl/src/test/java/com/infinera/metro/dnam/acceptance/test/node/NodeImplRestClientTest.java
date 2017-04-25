package com.infinera.metro.dnam.acceptance.test.node;

import com.infinera.metro.dnam.acceptance.test.mib.Board;
import com.infinera.metro.dnam.acceptance.test.mib.BoardEntry;
import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.connection.waiting.HealthChecks;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.infinera.metro.dnam.acceptance.test.node.RestTemplateFactory.REST_TEMPLATE_FACTORY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * TODO
 * Check more than 200, GET actual payload and verify
 *
 * Add rule that starts new docker nodeRestClient
 */
@Deprecated
@Category(IntegrationTest.class)
@Slf4j
public class NodeImplRestClientTest {
    @ClassRule
    public static DockerComposeRule docker = DockerComposeRule.builder()
            .file("src/test/resources/docker-compose.yml")
            .waitingForService("node1", HealthChecks.toHaveAllPortsOpen())
            .build();

//    private String nodeIpAddress ="172.17.0.2";
    private String nodeIpAddress = "172.45.0.101";
    private final NodeRestClient nodeRestClient = new NodeRestClient(
            new NodeConnection(
                NodeAccessData.builder()
                    .ipAddress(nodeIpAddress)
                    .port(80)
                    .userName("root")
                    .password("root")
                    .build(),
                REST_TEMPLATE_FACTORY.createRestTemplate()
            )
    );

    private final BoardEntry BOARD_ENTRY = BoardEntry.builder()
            .board(Board.TP10G)
            .subrack(1)
            .slot(2)
            .build();

    @Before
    public void setup() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
    }

    @Test
    public void createBoard() throws IOException {
        //Given
        //Assert that there is no board on subrack, slot

        //When
        ResponseEntity<String> responseEntity = nodeRestClient.createBoard(BOARD_ENTRY);

        //Then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        log.info("responseEntity: {}", responseEntity);

        //Get board and verify board is in result
    }

    @Test
    public void getBoard() {
        //Given
        ResponseEntity<String> responseEntity1 = nodeRestClient.createBoard(BOARD_ENTRY);
        log.info("responseEntity: {}", responseEntity1);
        assertNotNull(responseEntity1);
        assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());

        //When
        ResponseEntity<String> responseEntity2 = nodeRestClient.getBoard(BOARD_ENTRY);

        //Then
        assertNotNull(responseEntity2);
        assertEquals(HttpStatus.OK, responseEntity2.getStatusCode());
        log.info("responseEntity: {}", responseEntity2);
    }

    @Test
    public void deleteBoard() {
        //Given
        ResponseEntity<String> responseEntity1 = nodeRestClient.createBoard(BOARD_ENTRY);
        log.info("responseEntity: {}", responseEntity1);
        assertNotNull(responseEntity1);
        assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());

        //When
        ResponseEntity<String> responseEntity = nodeRestClient.deleteBoard(BOARD_ENTRY);

        //Then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        log.info("responseEntity: {}", responseEntity);
        //Add verification that board does not exit (nodeRestClient.getBoard)
    }

    @Test
    public void listBoards() {
        //Given
        ResponseEntity<String> responseEntity1 = nodeRestClient.createBoard(BOARD_ENTRY);
        log.info("responseEntity: {}", responseEntity1);
        assertNotNull(responseEntity1);
        assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());

        //When
        ResponseEntity<String> responseEntity = nodeRestClient.listBoards();

        //Then
        assertNotNull(responseEntity);
        log.info("responseEntity: {}", responseEntity);
        //Verify that added board is in result data
    }
}
