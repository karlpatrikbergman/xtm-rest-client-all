package com.infinera.metro.dnam.acceptance.test.node;

import com.infinera.metro.dnam.acceptance.test.node.mib.Attribute;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attributes;
import com.infinera.metro.dnam.acceptance.test.node.mib.MpoIdentifier;
import com.infinera.metro.dnam.acceptance.test.node.mib.OperationType;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.*;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.*;
import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.connection.waiting.HealthChecks;
import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Optional;

import static com.infinera.metro.dnam.acceptance.test.node.RestTemplateFactory.REST_TEMPLATE_FACTORY;
import static org.junit.Assert.*;

@Category(IntegrationTest.class)
@Slf4j
public class NodeImplSmokeTest {

    @ClassRule
    public static DockerComposeRule docker = DockerComposeRule.builder()
        .file("src/integrationTest/resources/docker-compose.yml")
        .waitingForService("node1", HealthChecks.toHaveAllPortsOpen())
        .build();

    private final Node nodeA = new NodeImpl(
        new NodeRestClient(
            new NodeConnection(
                    NodeAccessData.builder()
                            .ipAddress("172.45.0.101")
                            .port(80)
                            .userName("root")
                            .password("root")
                            .build(),
                    REST_TEMPLATE_FACTORY.createRestTemplate()
            )
        )
    );


    @Test
    public void nodeImplSmokeTest() {
        addTpd10gbeBoardInSlotTwo();
        setLineAttributesForTpd10gbeBoardInSlotTwo();
        setClientPortAttributesForTpd10gbeBoardInSlotTwo();
        createLocalPeerForTpd10gbeBoardInSlotTwo_to_some_remote_node();
        addTpd10gbeBoardInSlotThree();
        createInternalConnectionBetweenTpd10gbeBoards();
    }

    private void addTpd10gbeBoardInSlotTwo() {
        //Given
        final BoardEntry boardEntry = BoardEntry.builder()
            .boardType(BoardType.TPD10GBE)
            .subrack(1)
            .slot(2)
            .build();

        //When
        AnswerObjects createBoardAnswerObjects = nodeA.createBoard(boardEntry);
        assertNotNull(createBoardAnswerObjects);

        //Then
        Attributes getBoardAttributes = Attributes.of(
            Attribute.builder()
            .key("equipmentBoardName")
            .build()
        );

        AnswerObjects getBoardAnswerObjects = nodeA.getBoard(boardEntry, getBoardAttributes);
        assertNotNull(getBoardAnswerObjects);

        Optional<AnswerObject> answerObjectOptional = getBoardAnswerObjects.findSuccessAnswerObject(OperationType.GET, boardEntry);
        assertTrue(answerObjectOptional.isPresent());

        Optional<AttributeObject> attributeObjectOptional = answerObjectOptional.get().getAttributeObject("equipmentBoardName");
        assertTrue(attributeObjectOptional.isPresent());
        assertEquals(boardEntry.getMibEntryString(), attributeObjectOptional.get().getValue());
    }

    private void setLineAttributesForTpd10gbeBoardInSlotTwo() {
        //Given
        final LinePortEntry linePortEntry = LinePortEntry.builder()
            .moduleType(ModuleType.WDM)
            .groupOrTableType(GroupOrTableType.IF)
            .linePortType(LinePortType.WDM)
            .subrack(1)
            .slot(2)
            .transmitPort(3)
            .receivePort(4)
            .build();

        final Attributes linePortAttributes = Attributes.of(
            Attribute.builder()
                .key("expectedFrequency")
                .value("ch926")
                .build()
        );

        //When
        AnswerObjects setLinePortAttributesAnswerObjects = nodeA.setLinePortAttributes(linePortEntry, linePortAttributes);
        assertNotNull(setLinePortAttributesAnswerObjects);

        //Then
        AnswerObjects getLinePortEntryAttributes = nodeA.getLinePortAttributes(linePortEntry, linePortAttributes.onlyKeys());
        assertNotNull(getLinePortEntryAttributes);

        Optional<AnswerObject> answerObjectOptional = getLinePortEntryAttributes.findSuccessAnswerObject(OperationType.GET, linePortEntry);
        assertTrue(answerObjectOptional.isPresent());

        Optional<AttributeObject> attributeObjectOptional = answerObjectOptional.get().getAttributeObjectByAlias("expectedFrequency");
        assertTrue(attributeObjectOptional.isPresent());
        assertEquals("ch926", attributeObjectOptional.get().getValue());
    }

    private void setClientPortAttributesForTpd10gbeBoardInSlotTwo() {
        //Given
        final ClientPortEntry clientPortEntry = ClientPortEntry.builder()
            .moduleType(ModuleType.CLIENT)
            .groupOrTableType(GroupOrTableType.IF)
            .clientPortType(ClientPortType.CLIENT)
            .subrack(1)
            .slot(2)
            .transmitPort(1)
            .receivePort(2)
            .build();

        final Attributes clientPortAttributes = Attributes.of(
            Attribute.builder()
                .key("clientIfConfigurationCommand")
                .value("lan10GbE yes")
                .build()
        );

        // Of course an exception to what I hoped was a rule. When reading "config"-attributes we can't reuse the
        // config attributes used when setting, since they are not the same. When configuring (as opposed to set)
        // the attribute key is for example  "configure" or "clientIfConfigurationCommand" but when getting the same
        // value we must use attribute name, which is "clientIfFormat"
        final Attributes clientPortAttributesOnlyKeys = Attributes.of(
            Attribute.builder()
            .key("clientIfFormat")
            .build()
        );

        //When
        AnswerObjects setClientPortAttributesAnswerObjects = nodeA.configureClientPortAttributes(clientPortEntry, clientPortAttributes);
        assertNotNull(setClientPortAttributesAnswerObjects);

        //Then
        AnswerObjects getClientPortEntryAttributesAnswerObjects = nodeA.getClientPortAttributes(clientPortEntry, clientPortAttributesOnlyKeys);
        assertNotNull(getClientPortEntryAttributesAnswerObjects);

        Optional<AnswerObject> answerObjectOptional = getClientPortEntryAttributesAnswerObjects.findSuccessAnswerObject(OperationType.GET, clientPortEntry);
        assertTrue(answerObjectOptional.isPresent());

        Optional<AttributeObject> attributeObjectOptional = answerObjectOptional.get().getAttributeObject("clientIfFormat");
        assertTrue(attributeObjectOptional.isPresent());
        assertEquals("lan10GbE", attributeObjectOptional.get().getValue());

    }

    private void createLocalPeerForTpd10gbeBoardInSlotTwo_to_some_remote_node() {
        //Given
        final PeerEntry localPeerEntry = PeerEntry.builder()
            .subrack(1)
            .slot(2)
            .port(3)
            .mpoIdentifier(MpoIdentifier.NotPresent())
            .build();

        final PeerEntry remotePeerEntry = PeerEntry.builder()
            .subrack(1)
            .slot(2)
            .port(4)
            .mpoIdentifier(MpoIdentifier.NotPresent())
            .build();

        final Attributes peerConfig = Attributes.builder()
            .attribute(
                Attribute.builder()
                    .key("topoPeerLocalLabel")
                    .value(localPeerEntry.getLocalLabel())
                    .build()
            )
            .attribute(
                Attribute.builder()
                    .key("topoPeerRemoteIpAddress")
                    .value("172.17.0.3") //This node is not expected to be running in this test
                    .build()
            )
            .attribute(
                Attribute.builder()
                    .key("topoPeerRemoteLabel")
                    .value(remotePeerEntry.getLocalLabel())
                    .build()
            )
            .build();
        //When
        AnswerObjects createPeerAnswerObjects = nodeA.createPeer(localPeerEntry);
        assertNotNull(createPeerAnswerObjects);
        AnswerObjects setPeerConfigAnswerObjects = nodeA.setPeerAttributes(localPeerEntry, peerConfig);
        assertNotNull(setPeerConfigAnswerObjects);

        //Then
        AnswerObjects getPeerAnswerObjects = nodeA.getPeer(localPeerEntry);
        assertNotNull(getPeerAnswerObjects);

        Optional<AnswerObject> answerObjectOptional = getPeerAnswerObjects.findSuccessAnswerObject(OperationType.GET, localPeerEntry);
        assertTrue(answerObjectOptional.isPresent());

        Optional<AttributeObject> attributeObjectOptional = answerObjectOptional.get().getAttributeObject("topoPeerName");
        assertTrue(attributeObjectOptional.isPresent());
        assertEquals("peer:1:2:0:3", attributeObjectOptional.get().getValue());
    }

    private void addTpd10gbeBoardInSlotThree() {
        //Given
        final BoardEntry boardEntry = BoardEntry.builder()
            .boardType(BoardType.TPD10GBE)
            .subrack(1)
            .slot(3)
            .build();

        //When
        AnswerObjects createBoardAnswerObjects = nodeA.createBoard(boardEntry);
        assertNotNull(createBoardAnswerObjects);

        //Then
        Attributes getBoardAttributes = Attributes.of(
            Attribute.builder()
                .key("equipmentBoardName")
                .build()
        );

        AnswerObjects getBoardAnswerObjects = nodeA.getBoard(boardEntry, getBoardAttributes);
        assertNotNull(getBoardAnswerObjects);

        Optional<AnswerObject> answerObjectOptional = getBoardAnswerObjects.findSuccessAnswerObject(OperationType.GET, boardEntry);
        assertTrue(answerObjectOptional.isPresent());

        Optional<AttributeObject> attributeObjectOptional = answerObjectOptional.get().getAttributeObject("equipmentBoardName");
        assertTrue(attributeObjectOptional.isPresent());
        assertEquals(boardEntry.getMibEntryString(), attributeObjectOptional.get().getValue());
    }

    private void createInternalConnectionBetweenTpd10gbeBoards() {
        //Given
        final InternalConnectionEntry internalConnectionEntry = InternalConnectionEntry.builder()
            .fromSubrack(1)
            .fromSlot(2)
            .fromMpoIdentifier(MpoIdentifier.NotPresent())
            .fromPort(7)
            .toSubrack(1)
            .toSlot(3)
            .toMpoIdentifier(MpoIdentifier.NotPresent())
            .toPort(8)
            .build();

        //When
        AnswerObjects createInternalConnectionAnswerObjects = nodeA.createInternalConnection(internalConnectionEntry);
        assertNotNull(createInternalConnectionAnswerObjects);

        //Then
        Attributes getInternalConnectionAttributes = Attributes.of(
            Attribute.builder()
                .key("topoIntName")
                .build()
        );
        AnswerObjects getInternalConnectionAnswerObjects = nodeA.getInternalConnection(internalConnectionEntry, getInternalConnectionAttributes);
        assertNotNull(getInternalConnectionAnswerObjects);

        Optional<AnswerObject> answerObjectOptional = getInternalConnectionAnswerObjects.findSuccessAnswerObject(OperationType.GET, internalConnectionEntry);
        assertTrue(answerObjectOptional.isPresent());

        Optional<AttributeObject> attributeObjectOptional = answerObjectOptional.get().getAttributeObject("topoIntName");
        assertTrue(attributeObjectOptional.isPresent());
        assertEquals(internalConnectionEntry.getMibEntryString(), attributeObjectOptional.get().getValue());
    }
}
