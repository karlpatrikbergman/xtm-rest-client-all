package com.infinera.metro.dnam.acceptance.test.factory;

import com.infinera.metro.dnam.acceptance.test.mib.Board;
import com.infinera.metro.dnam.acceptance.test.mib.BoardEntry;
import com.infinera.metro.dnam.acceptance.test.mib.Operation;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.NodeAccessData;
import com.infinera.metro.dnam.acceptance.test.node.dto.AnswerObject;
import com.infinera.metro.dnam.acceptance.test.node.dto.AnswerObjects;
import com.infinera.metro.dnam.acceptance.test.node.dto.AttributeObject;
import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.connection.waiting.HealthChecks;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

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
            .slot(2)
            .build();

    private final NodeAccessData nodeAccessData = NodeAccessData.builder()
            .ipAddress("172.45.0.101")
            .port(80)
            .userName("root")
            .password("root")
            .build();

    @Test
    public void foo() throws IOException {
        node = NodeFactory.INSTANCE.createNode(nodeAccessData);

        //Given
        boardIsNotAdded(BOARD_ENTRY);

        //When
        AnswerObjects answerObjects = node.createBoard(BOARD_ENTRY);

        //Then
        createBoardSucceeded(answerObjects);

        getBoardSuccessful(BOARD_ENTRY);

        //Clean up after test
        node.deleteBoard(BOARD_ENTRY);


    }

    //TODO: Add this to published interface? Can it be part of Node?
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

    //TODO: Add this to published interface? Can it be part of Node?
    private void createBoardSucceeded(AnswerObjects answerObjects) {
        Optional<AnswerObject> answerObjectOptional = answerObjects.getAnswerObject(Operation.CREATE); //Create response has no entry set in R-attributes
        assertThat("AnswerObject.operation=create' and 'entry="+BOARD_ENTRY.getMibEntryString()+"' exists", answerObjectOptional.isPresent(), is(true));

        AnswerObject answerObject = answerObjectOptional.get();
        assertThat("AttributeObject.isSuccess=true" , answerObject.isSuccess(), is(true));

        Optional<AttributeObject> attributeObjectOptional = answerObject.getAttributeObject(BOARD_ENTRY.getMibEntryString());
        assertThat("AttributeObject.name=" + BOARD_ENTRY.getMibEntryString(), attributeObjectOptional.isPresent(), is(true));
    }

    //TODO: Add this to published interface? Can it be part of Node?
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
