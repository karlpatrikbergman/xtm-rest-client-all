package com.infinera.metro.dnam.acceptance.test.node;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectReader;
import com.infinera.metro.dnam.acceptance.test.mib.*;
import com.infinera.metro.dnam.acceptance.test.node.dto.AnswerObject;
import com.infinera.metro.dnam.acceptance.test.node.dto.AnswerObjects;
import com.infinera.metro.dnam.acceptance.test.node.dto.deserializer.JacksonUtil;
import com.infinera.metro.dnam.acceptance.test.xtmrest.XtmRestMibUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

import static com.infinera.metro.dnam.acceptance.test.node.RestTemplateFactory.REST_TEMPLATE_FACTORY;

@Slf4j
public class Workbench {

    NodeConnection nodeConnection = new NodeConnection(
            NodeAccessData.builder()
                    .ipAddress("172.17.0.2")
                    .port(80)
                    .userName("root")
                    .password("root")
                    .build(),
            REST_TEMPLATE_FACTORY.createRestTemplate()
    );

    private final ObjectReader objectReader = JacksonUtil.INSTANCE.getReader().forType(new TypeReference<List<AnswerObject>>(){});
    private XtmRestMibUtil xtmRestMibUtil = XtmRestMibUtil.INSTANCE;


    @Test
    public void getBoard() throws IOException {
        MibEntry mibEntry = BoardEntry.builder()
                .board(Board.TPD10GBE)
                .subrack(1)
                .slot(2)
                .build();
        String mibPathAndCommand = xtmRestMibUtil.mibRestUrl(mibEntry, Command.GET_JSON);
        String flags ="_RFLAGS_=RAISEMGNOQPCYVULTBJK&_AFLAGS_=AVNDHPUIMJOSE";
        String all = mibPathAndCommand + "?" + flags;

        nodeConnection.loginAndSetSessionId();
        ResponseEntity<String> responseEntity = nodeConnection.performRestAction(all);

        AnswerObjects answerObjects = new AnswerObjects(objectReader.readValue(responseEntity.getBody()));
        log.info(responseEntity.getBody());

        ResponseUtil.checkGetResponse(answerObjects, mibEntry);
    }

    @Test
    public void createBoard() throws IOException {
        MibEntry mibEntry = BoardEntry.builder()
                .board(Board.TPD10GBE)
                .subrack(1)
                .slot(2)
                .build();
        String mibPathAndCommand = xtmRestMibUtil.mibRestUrl(mibEntry, Command.CREATE_JSON);
        String flags ="_RFLAGS_=RAISEMGNOQPCYVULTBJK&_AFLAGS_=AVNDHPUIMJOSE";
        String all = mibPathAndCommand + "?" + flags;

        nodeConnection.loginAndSetSessionId();
        ResponseEntity<String> responseEntity = nodeConnection.performRestAction(all);

        AnswerObjects answerObjects = new AnswerObjects(objectReader.readValue(responseEntity.getBody()));
        log.info(responseEntity.getBody());

        ResponseUtil.checkCreateResponse(answerObjects, mibEntry);
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
        String mibPathAndCommand = xtmRestMibUtil.mibRestUrl(mibEntry, Command.SET_JSON);
        String flags ="_RFLAGS_=RAISEMGNOQPCYVULTBJK&_AFLAGS_=AVNDHPUIMJOSE";
        String parameters= "expectedFrequency=ch926"; //equals to wdmIfExpectedTxLambda=ch926
        String all = mibPathAndCommand + "?" + flags + "&" + parameters;

        nodeConnection.loginAndSetSessionId();
        ResponseEntity<String> responseEntity = nodeConnection.performRestAction(all);

        AnswerObjects answerObjects = new AnswerObjects(objectReader.readValue(responseEntity.getBody()));
        log.info(responseEntity.getBody());

        ResponseUtil.checkSetResponse(answerObjects, mibEntry);

    }
}
