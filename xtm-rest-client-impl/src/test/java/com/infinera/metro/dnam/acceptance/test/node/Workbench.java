package com.infinera.metro.dnam.acceptance.test.node;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectReader;
import com.infinera.metro.dnam.acceptance.test.node.mib.*;
import com.infinera.metro.dnam.acceptance.test.node.mib.util.MibPathUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.infinera.metro.dnam.acceptance.test.node.RestTemplateFactory.REST_TEMPLATE_FACTORY;

/**
 * Workbench for misc testing. Tests will fail if XTM node is not started manually.
 * Tests are not supposed to run in pipeline.
 */
@Category(DontLetGradleRun.class)
@Slf4j
public class Workbench {
    private final ObjectReader objectReader = JacksonUtil.INSTANCE.getReader().forType(new TypeReference<List<AnswerObject>>(){});
    private final MibPathUtil mibPathUtil = MibPathUtil.MIB_PATH_UTIL;
    private final MibObjectFactory mibObjectFactory = MibObjectFactory.MIB_OBJECT_FACTORY;

    private final String ipAddressNodeA = "172.17.0.2";
    private final String ipAddressNodeZ = "172.17.0.3";

    private final NodeConnection nodeConnectionA = new NodeConnection(
            NodeAccessData.builder()
                    .ipAddress(ipAddressNodeA)
                    .port(80)
                    .userName("root")
                    .password("root")
                    .build(),
            REST_TEMPLATE_FACTORY.createRestTemplate()
    );

    private final NodeConnection nodeConnectionZ = new NodeConnection(
            NodeAccessData.builder()
                    .ipAddress(ipAddressNodeZ)
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
        createBoard(nodeConnectionA, MibObjectFactory.createBoardEntry(Board.TPD10GBE));
        PeerEntry peerEntryTransmitSide = MibObjectFactory.createDefaultTransmitPeerEntryNodeA();
        createPeer(nodeConnectionA, peerEntryTransmitSide);
        configurePeer(nodeConnectionA, peerEntryTransmitSide, buildConfigurePeerParameterList(peerEntryTransmitSide));
    }

    private void configureNodeZ() throws IOException {
        createBoard(nodeConnectionZ, MibObjectFactory.createBoardEntry(Board.TPD10GBE));
        PeerEntry peerEntryReceiveSide = MibObjectFactory.createDefaultReceivePeerEntryNodeZ();
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
                .transmitPort(1)
                .receivePort(2)
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
    private void configureNode(NodeConnection nodeConnection, MibEntry mibEntry, Command command, ParameterList parameterList) throws IOException, RuntimeException {
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
}
