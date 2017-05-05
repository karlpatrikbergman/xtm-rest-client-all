package com.infinera.metro.dnam.acceptance.test.node;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectReader;
import com.infinera.metro.dnam.acceptance.test.mib.*;
import com.infinera.metro.dnam.acceptance.test.mib.util.MibPathUtil;
import com.infinera.metro.dnam.acceptance.test.node.dto.AnswerObject;
import com.infinera.metro.dnam.acceptance.test.node.dto.AnswerObjects;
import com.infinera.metro.dnam.acceptance.test.node.dto.deserializer.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.infinera.metro.dnam.acceptance.test.node.RestTemplateFactory.REST_TEMPLATE_FACTORY;

/**
 * Workbench for misc testing. Tests will fail if XTM node is not started.
 * Tests are not supposed to run in pipeline.
 */
@Category(DontLetGradleRun.class)
@Slf4j
public class Workbench {
    private final ObjectReader objectReader = JacksonUtil.INSTANCE.getReader().forType(new TypeReference<List<AnswerObject>>(){});
    private final MibPathUtil mibPathUtil = MibPathUtil.MIB_PATH_UTIL;

    private final NodeConnection nodeConnectionA = new NodeConnection(
            NodeAccessData.builder()
                    .ipAddress("172.17.0.2")
                    .port(80)
                    .userName("root")
                    .password("root")
                    .build(),
            REST_TEMPLATE_FACTORY.createRestTemplate()
    );

    private final NodeConnection nodeConnectionZ = new NodeConnection(
            NodeAccessData.builder()
                    .ipAddress("172.17.0.3")
                    .port(80)
                    .userName("root")
                    .password("root")
                    .build(),
            REST_TEMPLATE_FACTORY.createRestTemplate()
    );

    @Test
    public void runMe() throws IOException {
        configureNodeA();
        configureNodeZ();
    }

    private void configureNodeA() throws IOException {
        createBoard(nodeConnectionA, createBoardEntry());
        PeerEntry peerEntryTransmitSide = createTransmitPeerEntryNodeA();
        createPeer(nodeConnectionA, peerEntryTransmitSide);
        configurePeer(nodeConnectionA, peerEntryTransmitSide, buildConfigurePeerParameterList(peerEntryTransmitSide));
    }

    private void configureNodeZ() throws IOException {
        createBoard(nodeConnectionZ, createBoardEntry());
        PeerEntry peerEntryReceiveSide = createReceivePeerEntryNodeZ();
        createPeer(nodeConnectionZ, peerEntryReceiveSide);
        configurePeer(nodeConnectionZ, peerEntryReceiveSide, buildConfigurePeerParameterList(peerEntryReceiveSide));
    }

    private void createBoard(NodeConnection nodeConnection ,BoardEntry boardEntry) throws IOException {
        configureNode(nodeConnection, boardEntry, Command.CREATE_JSON, null);
    }

    private void getBoard(NodeConnection nodeConnection, BoardEntry boardEntry) throws IOException {
        configureNode(nodeConnection, boardEntry, Command.GET_JSON, null);
    }

    private void deleteBoard(NodeConnection nodeConnection) throws IOException {
        MibEntry mibEntry = BoardEntry.builder()
                .board(Board.TPD10GBE)
                .subrack(1)
                .slot(2)
                .build();
        configureNode(nodeConnection, mibEntry, Command.DELETE_JSON, null);
    }

    /**
     * These are two names for the same parameter
     * "A" (alias): "expectedFrequency",
     * "N" (name) : "wdmIfExpectedTxLambda",
     */
    private void configureLinePortExpectedFrequency(NodeConnection nodeConnection) throws IOException {
        MibEntry mibEntry = LinePortEntry.builder()
                .linePort(LinePort.WDM)
                .subrack(1)
                .slot(2)
                .transmitPort(3)
                .receivePort(4)
                .build();
        Configuration configuration = Configuration.builder()
                .key("expectedFrequency")
                .value("ch926")
                .build();
        configureNode(nodeConnection, mibEntry, Command.SET_JSON, ParameterList.of(configuration));
    }

    /**
     * Do these do the same thing?
     * clientIfConfigurationCommand=lan10GbE yes
     * configure=lan10GbE yes
     */
    private void configureClientPort(NodeConnection nodeConnection) throws IOException {
        MibEntry mibEntry = ClientPortEntry.builder()
                .clientPort(ClientPort.CLIENT)
                .subrack(1)
                .slot(2)
                .transmitterPort(1)
                .receiverPort(2)
                .build();
        Configuration configuration = Configuration.builder()
                .key("configure")
                .value("lan10GbE yes")
                .build();
        configureNode(nodeConnection, mibEntry, Command.CONFIGURE_JSON, ParameterList.of(configuration));
    }

    private void createPeer(NodeConnection nodeConnection, PeerEntry peerEntry) throws IOException {
        configureNode(nodeConnection, peerEntry, Command.CREATE_JSON, null);
    }

    private void configurePeer(NodeConnection nodeConnection, PeerEntry peerEntry, ParameterList parameterList) throws IOException {
        configureNode(nodeConnection, peerEntry, Command.SET_JSON, parameterList);
    }

    /**
     * Placed in NodeRestClient
     */
    private void configureNode(NodeConnection nodeConnection, MibEntry mibEntry, Command command, ParameterList parameterList) throws IOException {
        String mibPathAndCommand = mibPathUtil.getMibPathAndCommand(mibEntry, command);
        String flags ="_RFLAGS_=RAISEMGNOQPCYVULTBJK&_AFLAGS_=AVNDHPUIMJOSE";
        String parameters = (parameterList == null) ? "" : parameterList.toString();
        String all = mibPathAndCommand + "?" + flags + "&" + parameters;

        nodeConnection.loginAndSetSessionId();
        ResponseEntity<String> responseEntity = nodeConnection.performRestAction(all);

        AnswerObjects answerObjects = new AnswerObjects(objectReader.readValue(responseEntity.getBody()));
        log.info(responseEntity.getBody());

        answerObjects.checkResponse(command.getOperation(), mibEntry);
    }

    private ParameterList buildConfigurePeerParameterList(PeerEntry peerEntry) {
        return ParameterList.builder()
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
    }

    private BoardEntry createBoardEntry() {
        return BoardEntry.builder()
                .board(Board.TPD10GBE)
                .subrack(1)
                .slot(2)
                .build();
    }

    /**
     * The PeerEntry created here will be used to create a transmit peer entry on Node A (172.17.0.2)
     * Local XTM version is below 27, so no MTO identifier is added to peer name
     * Local peer entry name will be peer:1:2:3
     *
     */
    private PeerEntry createTransmitPeerEntryNodeA() {
        return PeerEntry.builder()
                .localLinePortEntry(createLinePortEntryNodeA())
                .remoteLinePortEntry(createLinePortEntryNodeZ())
                .remoteNodeIpAddress("172.17.0.3")
                .localMpoIdentifier(MpoIdentifier.createMpoIdentifierModuleNotPresent())
                .remoteMpoIdentifier(MpoIdentifier.createMpoIdentifierModuleNotPresent())
                .isTransmitSide(true)
                .build();
    }

    /**
     * The PeerEntry created here will be used to create a receive peer entry on Node Z (172.17.0.3)
     * Local XTM version is higher than 27, so MTO identifier is added to local peer name
     * Local peer entry name will be peer:2:3:0:8
     *
     */
    private PeerEntry createReceivePeerEntryNodeZ() {
        return PeerEntry.builder()
                .localLinePortEntry(createLinePortEntryNodeZ())
                .remoteLinePortEntry(createLinePortEntryNodeA())
                .remoteNodeIpAddress("172.17.0.2")
                .localMpoIdentifier(MpoIdentifier.createMpoIdentifierModuleNotPresent())
                .remoteMpoIdentifier(MpoIdentifier.createMpoIdentifierModuleNotPresent())
                .isTransmitSide(false)
                .build();
    }

    /**
     * At some point during test setup this line port entry is created in Node A: 172.17.0.2
     * XTM version < 27 therefore MPO identifier not is used.
     * The transmit port will be used when creating transmitting peer on Node A (peer:1:2:3):
     *      172.17.0.2/mib/topo/peer/peer:1:2:3/create.json
     */
    private LinePortEntry createLinePortEntryNodeA() {
        return LinePortEntry.builder()
                .linePort(LinePort.WDM)
                .subrack(1)
                .slot(2)
                .transmitPort(3)
                .receivePort(4)
                .build();
    }

    /**
     * At some point during test setup this line port entry is created in Node Z: 172.17.0.3
     * XTM version >= 27 therefore MPO identifier is used.
     * The transmit port will be used when creating transmitting peer on Node Z (peer:1:2:0:3):
     *      172.17.0.3/mib/topo/peer/peer:1:2:0:3/create.json
     */
    private LinePortEntry createLinePortEntryNodeZ() {
        return LinePortEntry.builder()
                .linePort(LinePort.WDM)
                .subrack(1)
                .slot(2)
                .transmitPort(7)
                .receivePort(8)
                .build();
    }
}
