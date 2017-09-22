package com.infinera.metro.dnam.acceptance.test.node;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectReader;
import com.infinera.metro.dnam.acceptance.test.node.mib.CommandType;
import com.infinera.metro.dnam.acceptance.test.node.mib.Configurations;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.MibEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.util.MibPathUtil;
import com.infinera.metro.dnam.acceptance.test.util.ThreadSleepWrapper;
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

    AnswerObjects performRestAction(MibEntry mibEntry, CommandType commandType) throws RuntimeException {
        return performRestAction(mibEntry, commandType, null);
    }

    AnswerObjects performRestAction(MibEntry mibEntry, CommandType commandType, Configurations configurations) throws RuntimeException {
        final String mibPathAndCommand = mibPathUtil.getMibPathAndCommand(mibEntry, commandType);
        final String flags ="_RFLAGS_=RAISEMGNOQPCYVULTBJK&_AFLAGS_=AVNDHPUIMJOSE";
        final String parameters = (configurations == null || configurations.getConfigurations().isEmpty()) ? "" : configurations.toString();
        final String colon = (parameters.isEmpty()) ? "" : "&";
        final String all = mibPathAndCommand + "?" + flags + colon + parameters;

        int attempts = 0;
        while(attempts++ < 4) {
            try  {
                ResponseEntity<String> responseEntity = nodeConnection.performRestAction(all);

                log.info(responseEntity.getBody());

                checkHttpStatusCode(responseEntity);
                List<AnswerObject> answerObjectList = readValueAsList(responseEntity);
                AnswerObjects answerObjects = new AnswerObjects(answerObjectList);
                answerObjects.checkResponse(commandType.getOperation(), mibEntry);
                return answerObjects;

            } catch (RuntimeException | IOException e) {
                log.error("performRestAction failed. Sleeps for 10 seconds. Error: " + e.getMessage());
                ThreadSleepWrapper.sleep(10);
            }
        }
        throw new RuntimeException("Tried performRestAction three times and failed. Request was: " + all);
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
