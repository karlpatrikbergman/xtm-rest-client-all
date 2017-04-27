package com.infinera.metro.dnam.acceptance.test.node;

import com.infinera.metro.dnam.acceptance.test.mib.Board;
import com.infinera.metro.dnam.acceptance.test.mib.BoardEntry;
import com.infinera.metro.dnam.acceptance.test.mib.Operation;
import com.infinera.metro.dnam.acceptance.test.node.dto.AnswerObject;
import com.infinera.metro.dnam.acceptance.test.node.dto.AnswerObjects;
import com.infinera.metro.dnam.acceptance.test.node.dto.AttributeObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;
import java.util.Optional;

import static com.infinera.metro.dnam.acceptance.test.node.RestTemplateFactory.REST_TEMPLATE_FACTORY;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

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

    private final BoardEntry BOARD_ENTRY = BoardEntry.builder()
            .board(Board.TPD10GBE)
            .subrack(1)
            .slot(2)
            .build();

    @Test
    public void createBoard() throws IOException, InterruptedException {
        //Given
        boardIsNotAdded(BOARD_ENTRY);

        //When
        AnswerObjects answerObjects = node.createBoard(BOARD_ENTRY);

        //Then
        createBoardSucceeded(answerObjects);

        getBoardSuccessful(BOARD_ENTRY);

        //Clean up after test
//        node.deleteBoard(BOARD_ENTRY);
    }

    private void getBoardSuccessful(BoardEntry boardEntry) throws IOException {
        AnswerObjects answerObjects = node.getBoard(boardEntry);
        Optional<AnswerObject> answerObjectOptional = answerObjects.getAnswerObject(Operation.GET, boardEntry);
        assertThat("AnswerObject.operation=get and entry="+BOARD_ENTRY.getMibEntryString()+" exists", answerObjectOptional.isPresent(), is(true));

        AnswerObject answerObject = answerObjectOptional.get();
        assertThat("AnswerObject.isSuccess=true" , answerObject.isSuccess(), is(true));

        Optional<AttributeObject> attributeObjectOptional = answerObject.getAttributeObject("equipmentBoardName");
        assertThat("AttributeObject.name=equipmentBoardName' exists", attributeObjectOptional.isPresent(), is(true));
        AttributeObject attributeObject = attributeObjectOptional.get();
        assertThat("AttributeObject.value="+BOARD_ENTRY.getMibEntryString(), attributeObject.getValue(), is(BOARD_ENTRY.getMibEntryString()));
    }

    private void createBoardSucceeded(AnswerObjects answerObjects) {
        Optional<AnswerObject> answerObjectOptional = answerObjects.getAnswerObject(Operation.CREATE); //Create response has no entry set in R-attributes
        assertThat("AnswerObject.operation=create' and 'entry="+BOARD_ENTRY.getMibEntryString()+"' exists", answerObjectOptional.isPresent(), is(true));

        AnswerObject answerObject = answerObjectOptional.get();
        assertThat("answerObject.isSuccess=true" , answerObject.isSuccess(), is(true));

        Optional<AttributeObject> attributeObjectOptional = answerObject.getAttributeObject(BOARD_ENTRY.getMibEntryString());
        assertThat("AttributeObject.name=" + BOARD_ENTRY.getMibEntryString(), attributeObjectOptional.isPresent(), is(true));
    }

    private void boardIsNotAdded(BoardEntry boardEntry) throws IOException {
        AnswerObjects answerObjects = node.getBoard(boardEntry);
        Optional<AnswerObject> answerObjectOptional = answerObjects.getAnswerObject(Operation.ERROR);
        assertThat("AnswerObject.operation=error exists", answerObjectOptional.isPresent(), is(true));

        AnswerObject answerObject = answerObjectOptional.get();
        assertThat("AnswerObject.entry=" + BOARD_ENTRY.getMibEntryString(), answerObject.getEntry(), is(BOARD_ENTRY.getMibEntryString()));
        assertThat("AnswerObject.isSuccess=false" , answerObject.isSuccess(), is(false));
        assertThat("Error message contains 'Entry not found'", answerObject.getError().contains("Entry not found"), is(true));
    }
}
