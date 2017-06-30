package com.infinera.metro.dnam.acceptance.test.node;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectReader;
import com.infinera.metro.dnam.acceptance.test.node.mib.*;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.*;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.BoardType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ClientPortType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.LinePortType;
import com.infinera.metro.dnam.acceptance.test.node.mib.util.MibPathUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

import static com.infinera.metro.dnam.acceptance.test.node.RestTemplateFactory.REST_TEMPLATE_FACTORY;

/**
 * Workbench for misc testing. Tests will fail if XTM node is not started manually.
 * Tests are not supposed to run in pipeline.
 */
@Category(DontLetGradleRun.class)
@Slf4j
public class Workbench {
    private final ObjectReader objectReader = JacksonUtil.INSTANCE.getReader().forType(new TypeReference<List<AnswerObject>>() {
    });
    private final MibPathUtil mibPathUtil = MibPathUtil.MIB_PATH_UTIL;
    private final MibObjectFactory mibObjectFactory = MibObjectFactory.MIB_OBJECT_FACTORY;
    private final String NODE_A_IP_ADDRESS = "172.17.0.2";
    private final String NODE_Z_IP_ADDRESS = "172.17.0.3";

    private final NodeConnection NODECONNECTION_A = new NodeConnection(
        NodeAccessData.builder()
            .ipAddress(NODE_A_IP_ADDRESS)
            .port(80)
            .userName("root")
            .password("root")
            .build(),
        REST_TEMPLATE_FACTORY.createRestTemplate()
    );

    private final NodeConnection NODECONNECTION_Z = new NodeConnection(
        NodeAccessData.builder()
            .ipAddress(NODE_Z_IP_ADDRESS)
            .port(80)
            .userName("root")
            .password("root")
            .build(),
        REST_TEMPLATE_FACTORY.createRestTemplate()
    );

    private final BoardEntry TPD10GBE_BOARD_ENTRY = BoardEntry.builder()
        .boardType(BoardType.TPD10GBE)
        .subrack(1)
        .slot(2)
        .build();

    @Test
    public void runMe() throws IOException {
        configurePeerConnection();
    }

    private void configurePeerConnection() throws IOException {
        createBoard(NODECONNECTION_A, TPD10GBE_BOARD_ENTRY);
        PeerEntry peerEntryNodeA = PeerEntry.builder()
            .subrack(1)
            .slot(2)
            .port(3)
            .mpoIdentifier(MpoIdentifier.NotPresent())
            .build();
        createPeer(NODECONNECTION_A, peerEntryNodeA);

        createBoard(NODECONNECTION_Z, TPD10GBE_BOARD_ENTRY);
        PeerEntry peerEntryNodeZ = PeerEntry.builder()
            .subrack(1)
            .slot(2)
            .port(4)
            .mpoIdentifier(MpoIdentifier.NotPresent())
            .build();
        createPeer(NODECONNECTION_Z, peerEntryNodeZ);

        configurePeer(NODECONNECTION_A, peerEntryNodeA, buildConfigurePeerParameterList(peerEntryNodeA, peerEntryNodeZ, NODE_Z_IP_ADDRESS ));
        configurePeer(NODECONNECTION_Z, peerEntryNodeZ, buildConfigurePeerParameterList(peerEntryNodeZ, peerEntryNodeA, NODE_A_IP_ADDRESS));
    }

    private void createBoard(NodeConnection nodeConnection, BoardEntry boardEntry) throws IOException {
        configureNode(nodeConnection, boardEntry, CommandType.CREATE_JSON, null);
    }

    private void getBoard(NodeConnection nodeConnection, BoardEntry boardEntry) throws IOException {
        configureNode(nodeConnection, boardEntry, CommandType.GET_JSON, null);
    }

    private void deleteBoard(NodeConnection nodeConnection) throws IOException {
        MibEntry mibEntry = BoardEntry.builder()
            .boardType(BoardType.TPD10GBE)
            .subrack(1)
            .slot(2)
            .build();
        configureNode(nodeConnection, mibEntry, CommandType.DELETE_JSON, null);
    }

    /**
     * These are two names for the same parameter
     * "A" (alias): "expectedFrequency",
     * "N" (name) : "wdmIfExpectedTxLambda",
     */
    private void configureLinePortExpectedFrequency(NodeConnection nodeConnection) throws IOException {
        MibEntry mibEntry = LinePortEntry.builder()
            .linePortType(LinePortType.WDM)
            .subrack(1)
            .slot(2)
            .transmitPort(3)
            .receivePort(4)
            .build();
        Configuration configuration = Configuration.builder()
            .key("expectedFrequency")
            .value("ch926")
            .build();
        configureNode(nodeConnection, mibEntry, CommandType.SET_JSON, Configurations.of(configuration));
    }

    /**
     * Do these do the same thing?
     * clientIfConfigurationCommand=lan10GbE yes
     * configure=lan10GbE yes
     */
    private void configureClientPort(NodeConnection nodeConnection) throws IOException {
        MibEntry mibEntry = ClientPortEntry.builder()
            .clientPortType(ClientPortType.CLIENT)
            .subrack(1)
            .slot(2)
            .transmitPort(1)
            .receivePort(2)
            .build();
        Configuration configuration = Configuration.builder()
            .key("configure")
            .value("lan10GbE yes")
            .build();
        configureNode(nodeConnection, mibEntry, CommandType.CONFIGURE_JSON, Configurations.of(configuration));
    }

    private void createPeer(NodeConnection nodeConnection, PeerEntry peerEntry) throws IOException {
        configureNode(nodeConnection, peerEntry, CommandType.CREATE_JSON, null);
    }


    private void configurePeer(NodeConnection nodeConnection, PeerEntry peerEntry, Configurations configurations) throws IOException {
        configureNode(nodeConnection, peerEntry, CommandType.SET_JSON, configurations);
    }

    /**
     * Placed in NodeRestClient
     */
    private void configureNode(NodeConnection nodeConnection, MibEntry mibEntry, CommandType commandType, Configurations configurations) throws IOException, RuntimeException {
        String mibPathAndCommand = mibPathUtil.getMibPathAndCommand(mibEntry, commandType);
        String flags = "_RFLAGS_=RAISEMGNOQPCYVULTBJK&_AFLAGS_=AVNDHPUIMJOSE";
        String parameters = (configurations == null) ? "" : configurations.toString();
        String all = mibPathAndCommand + "?" + flags + "&" + parameters;

        nodeConnection.loginAndSetSessionId();
        ResponseEntity<String> responseEntity = nodeConnection.performRestAction(all);

        AnswerObjects answerObjects = new AnswerObjects(objectReader.readValue(responseEntity.getBody()));
        log.info(responseEntity.getBody());

        answerObjects.checkResponse(commandType.getOperation(), mibEntry);
    }

    private Configurations buildConfigurePeerParameterList(PeerEntry localPeerEntry, PeerEntry remotePeerEntry, String remoteNodeIpAddress) {
        return Configurations.builder()
            .configuration(
                Configuration.builder()
                    .key("topoPeerLocalLabel")
                    .value(localPeerEntry.getLocalLabel())
                    .build()
            )
            .configuration(
                Configuration.builder()
                    .key("topoPeerRemoteIpAddress")
                    .value(remoteNodeIpAddress)
                    .build()
            )
            .configuration(
                Configuration.builder()
                    .key("topoPeerRemoteLabel")
                    .value(remotePeerEntry.getLocalLabel())
                    .build()
            )
            .build();
    }
}
