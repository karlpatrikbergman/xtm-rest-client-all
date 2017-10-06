package com.infinera.metro.dnam.acceptance.test.node;

import com.infinera.metro.dnam.acceptance.test.node.mib.Attribute;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attributes;
import com.infinera.metro.dnam.acceptance.test.node.mib.MpoIdentifier;
import com.infinera.metro.dnam.acceptance.test.node.mib.OperationType;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.*;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * To run this test from command line:
 * ./gradlew clean xtm-rest-client-impl:integrationTest -DintegrationTest.single=NodeImplSmokeTest
 *
 * To run this test and keep containers running:
 * ./gradlew clean xtm-rest-client-impl:integrationTest -DintegrationTest.single=NodeImplSmokeTest -PshutdownStrategy=SKIP
 */
@Category(IntegrationTest.class)
@Slf4j
public class NodeImplSmokeTest extends DockerComposeRuleTest {
    private String ipAddressNodeA;
    private String ipAddressNodeZ;

    @Before
    public void setup() throws IOException {
        ipAddressNodeA = getContainerIpAddress("nodeA");
        ipAddressNodeZ = getContainerIpAddress("nodeZ");
    }

    //TODO: Put in utility class
    private String getContainerIpAddress(String nodeName) throws IOException {
        InputStream inputStream = docker.dockerExecutable().execute("inspect", "-f", "'{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}'", nodeName).getInputStream();
        String ipAddress = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name())
            .replaceAll("\n","")
            .replaceAll("\'","");
        log.info("IPAddress for {}: {}", nodeName, ipAddress);
        return ipAddress;
    }

    @Test
    public void nodeImplSmokeTest() {
        final Node nodeA = NodeImpl.createDefault(ipAddressNodeA);
        final Node nodeZ = NodeImpl.createDefault(ipAddressNodeZ);
        setupNode(nodeA, ipAddressNodeZ);
        setupNode(nodeZ, ipAddressNodeA);
        createSymmetricalPeerConnectionBetweenNodes(nodeA, nodeZ);
    }

    private void setupNode(Node node, String ipAddressRemoteNode) {
        addTpd10gbeBoardInSlotTwo(node);
        setExpectedFrequencyForTpd10gbeClientPort(node);
        configureSignalFormatForTpd10gbeClientPort(node);
        setExpectedFrequencyForTpd10gbeLinePort(node);
        addMdu40EvenLBoardInSlotThree(node);
        createSymmetricInternalConnectionBetweenTpd10gbeAndMdu40EvenL(node);
    }

    private void addTpd10gbeBoardInSlotTwo(Node node) {
        //Given
        final BoardEntry boardEntry = BoardEntry.builder()
            .boardType(BoardType.TPD10GBE)
            .subrack(1)
            .slot(2)
            .build();

        //When
        AnswerObjects createBoardAnswerObjects = node.createBoard(boardEntry);
        assertNotNull(createBoardAnswerObjects);

        //Then
        Attributes getBoardAttributes = Attributes.of(
            Attribute.builder()
            .key("equipmentBoardName")
            .build()
        );

        AnswerObjects getBoardAnswerObjects = node.getBoard(boardEntry, getBoardAttributes);
        assertNotNull(getBoardAnswerObjects);

        Optional<AnswerObject> answerObjectOptional = getBoardAnswerObjects.findSuccessAnswerObject(OperationType.GET, boardEntry);
        assertTrue(answerObjectOptional.isPresent());

        Optional<AttributeObject> attributeObjectOptional = answerObjectOptional.get().getAttributeObject("equipmentBoardName");
        assertTrue(attributeObjectOptional.isPresent());
        assertEquals(boardEntry.getMibEntryString(), attributeObjectOptional.get().getValue());
    }

    private void setExpectedFrequencyForTpd10gbeClientPort(Node node) {
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
                .key("clientIfExpectedTxFrequency")
                .value("w1530")
                .build()
        );

        //When
        AnswerObjects setClientPortAttributesAnswerObjects = node.setClientPortAttributes(clientPortEntry, clientPortAttributes);
        assertNotNull(setClientPortAttributesAnswerObjects);

        //Then
        AnswerObjects getClientPortEntryAttributesAnswerObjects = node.getClientPortAttributes(clientPortEntry, clientPortAttributes.onlyKeys());
        assertNotNull(getClientPortEntryAttributesAnswerObjects);

        Optional<AnswerObject> answerObjectOptional = getClientPortEntryAttributesAnswerObjects.findSuccessAnswerObject(OperationType.GET, clientPortEntry);
        assertTrue(answerObjectOptional.isPresent());

        Optional<AttributeObject> attributeObjectOptional = answerObjectOptional.get().getAttributeObject("clientIfExpectedTxFrequency");
        assertTrue(attributeObjectOptional.isPresent());
        assertEquals("w1530", attributeObjectOptional.get().getValue());
    }

    private void configureSignalFormatForTpd10gbeClientPort(Node node) {
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
                .value("wan10GbE yes")
                .build()
        );

        // Of course an exception to what I hoped was a rule. When reading "config"-attributes we can't reuse the
        // config attributes used when setting, since they are not the same. When configuring (as opposed to set)
        // the attribute key is for example  "configure" or "clientIfConfigurationCommand" but when getting the same
        // value we must use attribute name, which is in this case is "clientIfFormat"
        final Attributes clientPortAttributesOnlyKeys = Attributes.of(
            Attribute.builder()
                .key("clientIfFormat")
                .build()
        );

        //When
        AnswerObjects setClientPortAttributesAnswerObjects = node.configureClientPortAttributes(clientPortEntry, clientPortAttributes);
        assertNotNull(setClientPortAttributesAnswerObjects);

        //Then
        AnswerObjects getClientPortEntryAttributesAnswerObjects = node.getClientPortAttributes(clientPortEntry, clientPortAttributesOnlyKeys);
        assertNotNull(getClientPortEntryAttributesAnswerObjects);

        Optional<AnswerObject> answerObjectOptional = getClientPortEntryAttributesAnswerObjects.findSuccessAnswerObject(OperationType.GET, clientPortEntry);
        assertTrue(answerObjectOptional.isPresent());

        Optional<AttributeObject> attributeObjectOptional = answerObjectOptional.get().getAttributeObject("clientIfFormat");
        assertTrue(attributeObjectOptional.isPresent());
        assertEquals("wan10GbE", attributeObjectOptional.get().getValue());
    }

    private void setExpectedFrequencyForTpd10gbeLinePort(Node node) {
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
                .value("ch939")
                .build()
        );

        //When
        AnswerObjects setLinePortAttributesAnswerObjects = node.setLinePortAttributes(linePortEntry, linePortAttributes);
        assertNotNull(setLinePortAttributesAnswerObjects);

        //Then
        AnswerObjects getLinePortEntryAttributes = node.getLinePortAttributes(linePortEntry, linePortAttributes.onlyKeys());
        assertNotNull(getLinePortEntryAttributes);

        Optional<AnswerObject> answerObjectOptional = getLinePortEntryAttributes.findSuccessAnswerObject(OperationType.GET, linePortEntry);
        assertTrue(answerObjectOptional.isPresent());

        Optional<AttributeObject> attributeObjectOptional = answerObjectOptional.get().getAttributeObjectByAlias("expectedFrequency");
        assertTrue(attributeObjectOptional.isPresent());
        assertEquals("ch939", attributeObjectOptional.get().getValue());
    }

    private void addMdu40EvenLBoardInSlotThree(Node node) {
        //Given
        final BoardEntry boardEntry = BoardEntry.builder()
            .boardType(BoardType.MDU40EVENL)
            .subrack(1)
            .slot(3)
            .build();

        //When
        AnswerObjects createBoardAnswerObjects = node.createBoard(boardEntry);
        assertNotNull(createBoardAnswerObjects);

        //Then
        Attributes getBoardAttributes = Attributes.of(
            Attribute.builder()
                .key("equipmentBoardName")
                .build()
        );

        AnswerObjects getBoardAnswerObjects = node.getBoard(boardEntry, getBoardAttributes);
        assertNotNull(getBoardAnswerObjects);

        Optional<AnswerObject> answerObjectOptional = getBoardAnswerObjects.findSuccessAnswerObject(OperationType.GET, boardEntry);
        assertTrue(answerObjectOptional.isPresent());

        Optional<AttributeObject> attributeObjectOptional = answerObjectOptional.get().getAttributeObject("equipmentBoardName");
        assertTrue(attributeObjectOptional.isPresent());
        assertEquals(boardEntry.getMibEntryString(), attributeObjectOptional.get().getValue());
    }

    /**
     * From:    wdm:1:2:3-4
     * To:      client:1:3:41-42:939
     * @param node  An instance of Node
     */
    private void createSymmetricInternalConnectionBetweenTpd10gbeAndMdu40EvenL(Node node) {
        //Given
        final InternalConnectionEntry internalConnectionEntry = InternalConnectionEntry.builder()
            .fromSubrack(1)
            .fromSlot(2)
            .fromMpoIdentifier(MpoIdentifier.NotPresent())
            .fromPort(3)
            .toSubrack(1)
            .toSlot(3)
            .toMpoIdentifier(MpoIdentifier.NotPresent())
            .toPort(42)
            .build();

        createOneWayInternalConnectionBetweenTpd10gbeAndMdu40EvenL(node, internalConnectionEntry);
        createOneWayInternalConnectionBetweenTpd10gbeAndMdu40EvenL(node, internalConnectionEntry.reverse());
    }

    private void createOneWayInternalConnectionBetweenTpd10gbeAndMdu40EvenL(Node node, InternalConnectionEntry internalConnectionEntry) {
        //When
        AnswerObjects createInternalConnectionAnswerObjects = node.createInternalConnection(internalConnectionEntry);
        assertNotNull(createInternalConnectionAnswerObjects);

        //Then
        Attributes getInternalConnectionAttributes = Attributes.of(
            Attribute.builder()
                .key("topoIntName")
                .build()
        );
        AnswerObjects getInternalConnectionAnswerObjects = node.getInternalConnection(internalConnectionEntry, getInternalConnectionAttributes);
        assertNotNull(getInternalConnectionAnswerObjects);

        Optional<AnswerObject> answerObjectOptional = getInternalConnectionAnswerObjects.findSuccessAnswerObject(OperationType.GET, internalConnectionEntry);
        assertTrue(answerObjectOptional.isPresent());

        Optional<AttributeObject> attributeObjectOptional = answerObjectOptional.get().getAttributeObject("topoIntName");
        assertTrue(attributeObjectOptional.isPresent());
        assertEquals(internalConnectionEntry.getMibEntryString(), attributeObjectOptional.get().getValue());
    }

    private void createSymmetricalPeerConnectionBetweenNodes(Node nodeA, Node nodeZ) {
        createlPeerEntryForMdu40EvenL(nodeA, nodeZ);
        createlPeerEntryForMdu40EvenL(nodeZ, nodeA);
    }

    private void createlPeerEntryForMdu40EvenL(Node transmitNode, Node recieveNode) {
        //Given
        final PeerEntry transmitPeerEntry = PeerEntry.builder()
            .subrack(1)
            .slot(3)
            .port(81)
            .mpoIdentifier(MpoIdentifier.NotPresent())
            .build();

        final PeerEntry receivePeerEntry = PeerEntry.builder()
            .subrack(1)
            .slot(3)
            .port(82)
            .mpoIdentifier(MpoIdentifier.NotPresent())
            .build();

        final Attributes transmitPeerConfig = Attributes.builder()
            .attribute(
                Attribute.builder()
                    .key("topoPeerLocalLabel")
                    .value(transmitPeerEntry.getLocalLabel())
                    .build()
            )
            .attribute(
                Attribute.builder()
                    .key("topoPeerRemoteIpAddress")
                    .value(recieveNode.getIpAddress())
                    .build()
            )
            .attribute(
                Attribute.builder()
                    .key("topoPeerRemoteLabel")
                    .value(receivePeerEntry.getLocalLabel())
                    .build()
            )
            .build();

        final Attributes receivePeerConfig = Attributes.builder()
            .attribute(
                Attribute.builder()
                    .key("topoPeerLocalLabel")
                    .value(receivePeerEntry.getLocalLabel())
                    .build()
            )
            .attribute(
                Attribute.builder()
                    .key("topoPeerRemoteIpAddress")
                    .value(transmitNode.getIpAddress())
                    .build()
            )
            .attribute(
                Attribute.builder()
                    .key("topoPeerRemoteLabel")
                    .value(transmitPeerEntry.getLocalLabel())
                    .build()
            )
            .build();

        //When
        createPeerEntry(transmitNode, transmitPeerEntry, transmitPeerConfig);
        createPeerEntry(recieveNode, receivePeerEntry, receivePeerConfig);
    }

    private void createPeerEntry(Node node, PeerEntry peerEntry, Attributes peerConfig) {
        //when continued
        AnswerObjects createPeerAnswerObjects = node.createPeer(peerEntry);
        assertNotNull(createPeerAnswerObjects);

        AnswerObjects setPeerConfigAnswerObjects = node.setPeerAttributes(peerEntry, peerConfig);
        assertNotNull(setPeerConfigAnswerObjects);

        //Then
        AnswerObjects getPeerAnswerObjects = node.getPeer(peerEntry);
        assertNotNull(getPeerAnswerObjects);

        Optional<AnswerObject> answerObjectOptional = getPeerAnswerObjects.findSuccessAnswerObject(OperationType.GET, peerEntry);
        assertTrue(answerObjectOptional.isPresent());

        Optional<AttributeObject> attributeObjectOptional = answerObjectOptional.get().getAttributeObject("topoPeerName");
        assertTrue(attributeObjectOptional.isPresent());
        assertEquals(peerEntry.getMibEntryString(), attributeObjectOptional.get().getValue());
    }
}