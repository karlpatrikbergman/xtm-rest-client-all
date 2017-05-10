package com.infinera.metro.dnam.acceptance.test.node;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectReader;
import com.infinera.metro.dnam.acceptance.test.mib.Command;
import com.infinera.metro.dnam.acceptance.test.mib.MibEntry;
import com.infinera.metro.dnam.acceptance.test.mib.ParameterList;
import com.infinera.metro.dnam.acceptance.test.mib.util.MibPathUtil;
import com.infinera.metro.dnam.acceptance.test.node.dto.AnswerObject;
import com.infinera.metro.dnam.acceptance.test.node.dto.AnswerObjects;
import com.infinera.metro.dnam.acceptance.test.node.dto.deserializer.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

@Slf4j
class NodeRestClient {

    private final NodeConnection nodeConnection;
    private final MibPathUtil mibPathUtil;
    private final ObjectReader reader = JacksonUtil.INSTANCE.getReader().forType(new TypeReference<List<AnswerObject>>(){});

    NodeRestClient(NodeConnection nodeConnection) {
        this.nodeConnection = nodeConnection;
        this.mibPathUtil = MibPathUtil.MIB_PATH_UTIL;
    }

    AnswerObjects performRestAction(MibEntry mibEntry, Command command) throws IOException, RuntimeException {
        return performRestAction(mibEntry, command, null);
    }

    AnswerObjects performRestAction(MibEntry mibEntry, Command command, ParameterList parameterList) throws IOException, RuntimeException {
        String mibPathAndCommand = mibPathUtil.getMibPathAndCommand(mibEntry, command);
        String flags ="_RFLAGS_=RAISEMGNOQPCYVULTBJK&_AFLAGS_=AVNDHPUIMJOSE";
        String parameters = (parameterList == null) ? "" : parameterList.toString();
        String all = mibPathAndCommand + "?" + flags + "&" + parameters;
        ResponseEntity<String> responseEntity = nodeConnection.performRestAction(all);

        log.info(responseEntity.getBody());

        checkHttpStatusCode(responseEntity);
        List<AnswerObject> answerObjectList = readValueAsList(responseEntity);
        AnswerObjects answerObjects = new AnswerObjects(answerObjectList);
        answerObjects.checkResponse(command.getOperation(), mibEntry);
        return answerObjects;
    }

    String getNodeIpAddress() {
        return nodeConnection.getNodeIpAddress();
    }

    private void checkHttpStatusCode(ResponseEntity<String> responseEntity) throws RuntimeException {
        if(responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("XTM responded with status code " + responseEntity.getStatusCode());
        }
    }

    private List<AnswerObject> readValueAsList(ResponseEntity<String> responseEntity) throws IOException {
        return reader.readValue(responseEntity.getBody());
    }

}
