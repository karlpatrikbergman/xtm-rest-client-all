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
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

import static com.infinera.metro.dnam.acceptance.test.node.RestTemplateFactory.REST_TEMPLATE_FACTORY;

/**
 * Workbench for misc testing. Tests will fail if XTM node is not started.
 * Tests are not supposed to run in pipeline.
 */
@Slf4j
public class Workbench {

    final NodeConnection nodeConnection = new NodeConnection(
            NodeAccessData.builder()
                    .ipAddress("172.17.0.2")
                    .port(80)
                    .userName("root")
                    .password("root")
                    .build(),
            REST_TEMPLATE_FACTORY.createRestTemplate()
    );

    private final ObjectReader objectReader = JacksonUtil.INSTANCE.getReader().forType(new TypeReference<List<AnswerObject>>(){});
    private final MibPathUtil mibPathUtil = MibPathUtil.MIB_PATH_UTIL;

    @Test
    public void getBoard() throws IOException {
        MibEntry mibEntry = BoardEntry.builder()
                .board(Board.TPD10GBE)
                .subrack(1)
                .slot(2)
                .build();
        common(mibEntry, Command.GET_JSON, null);
//        String mibPathAndCommand = mibPathUtil.getMibPathAndCommand(mibEntry, Command.GET_JSON);
//        String flags ="_RFLAGS_=RAISEMGNOQPCYVULTBJK&_AFLAGS_=AVNDHPUIMJOSE";
//        String all = mibPathAndCommand + "?" + flags;
//
//        nodeConnection.loginAndSetSessionId();
//        ResponseEntity<String> responseEntity = nodeConnection.performRestAction(all);
//
//        AnswerObjects answerObjects = new AnswerObjects(objectReader.readValue(responseEntity.getBody()));
//        log.info(responseEntity.getBody());
//
//        answerObjects.checkResponse(Operation.GET, mibEntry);
    }

    @Test
    public void createBoard() throws IOException {
        MibEntry mibEntry = BoardEntry.builder()
                .board(Board.TPD10GBE)
                .subrack(1)
                .slot(2)
                .build();

        common(mibEntry, Command.CREATE_JSON, null);

//        String mibPathAndCommand = mibPathUtil.getMibPathAndCommand(mibEntry, Command.CREATE_JSON);
//        String flags ="_RFLAGS_=RAISEMGNOQPCYVULTBJK&_AFLAGS_=AVNDHPUIMJOSE";
//        String all = mibPathAndCommand + "?" + flags;
//
//        nodeConnection.loginAndSetSessionId();
//        ResponseEntity<String> responseEntity = nodeConnection.performRestAction(all);
//
//        AnswerObjects answerObjects = new AnswerObjects(objectReader.readValue(responseEntity.getBody()));
//        log.info(responseEntity.getBody());
//
//        answerObjects.checkResponse(Operation.CREATE, mibEntry);
    }

    @Test
    public void deleteBoard() throws IOException {
        MibEntry mibEntry = BoardEntry.builder()
                .board(Board.TPD10GBE)
                .subrack(1)
                .slot(2)
                .build();
        common(mibEntry, Command.DELETE_JSON, null);
//        String mibPathAndCommand = mibPathUtil.getMibPathAndCommand(mibEntry, Command.DELETE_JSON);
//        String flags ="_RFLAGS_=RAISEMGNOQPCYVULTBJK&_AFLAGS_=AVNDHPUIMJOSE";
//        String all = mibPathAndCommand + "?" + flags;
//
//        nodeConnection.loginAndSetSessionId();
//        ResponseEntity<String> responseEntity = nodeConnection.performRestAction(all);
//
//        AnswerObjects answerObjects = new AnswerObjects(objectReader.readValue(responseEntity.getBody()));
//        log.info(responseEntity.getBody());
//
//        answerObjects.checkResponse(Operation.DELETE, mibEntry);

    }
    /**
     * These are two names for the same parameter
     * "A" (alias): "expectedFrequency",
     * "N" (name) : "wdmIfExpectedTxLambda",
     */
    @Test
    public void configureLinePortExpectedFrequency() throws IOException {
        MibEntry mibEntry = LinePortEntry.builder()
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
        common(mibEntry, Command.SET_JSON, configuration);
//        String mibPathAndCommand = mibPathUtil.getMibPathAndCommand(mibEntry, Command.SET_JSON);
//        String flags ="_RFLAGS_=RAISEMGNOQPCYVULTBJK&_AFLAGS_=AVNDHPUIMJOSE";
//        String parameters= "expectedFrequency=ch926"; //equals to wdmIfExpectedTxLambda=ch926
//        String all = mibPathAndCommand + "?" + flags + "&" + parameters;
//
//        nodeConnection.loginAndSetSessionId();
//        ResponseEntity<String> responseEntity = nodeConnection.performRestAction(all);
//
//        AnswerObjects answerObjects = new AnswerObjects(objectReader.readValue(responseEntity.getBody()));
//        log.info(responseEntity.getBody());
//
//        answerObjects.checkResponse(Operation.SET, mibEntry);
    }

    /**
     * Could be placed in NodeRestClient
     */
    private void common(MibEntry mibEntry, Command command, Configuration configuration) throws IOException {
        String mibPathAndCommand = mibPathUtil.getMibPathAndCommand(mibEntry, command);
        String flags ="_RFLAGS_=RAISEMGNOQPCYVULTBJK&_AFLAGS_=AVNDHPUIMJOSE";
        String parameters = (configuration == null) ? "" : configuration.asParameters();
        String all = mibPathAndCommand + "?" + flags + "&" + parameters;

        nodeConnection.loginAndSetSessionId();
        ResponseEntity<String> responseEntity = nodeConnection.performRestAction(all);

        AnswerObjects answerObjects = new AnswerObjects(objectReader.readValue(responseEntity.getBody()));
        log.info(responseEntity.getBody());

        answerObjects.checkResponse(command.getOperation(), mibEntry);

    }
}
