package com.infinera.metro.dnam.acceptance.test.node;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectReader;
import com.infinera.metro.dnam.acceptance.test.mib.BoardEntry;
import com.infinera.metro.dnam.acceptance.test.mib.Configuration;
import com.infinera.metro.dnam.acceptance.test.mib.LinePortEntry;
import com.infinera.metro.dnam.acceptance.test.node.dto.AnswerObject;
import com.infinera.metro.dnam.acceptance.test.node.dto.AnswerObjects;
import com.infinera.metro.dnam.acceptance.test.node.dto.deserializer.JacksonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public class NodeImpl implements Node {
    private final NodeRestClient nodeRestClient;
    private final ObjectReader reader = JacksonUtil.INSTANCE.getReader().forType(new TypeReference<List<AnswerObject>>(){});

    public NodeImpl(NodeRestClient nodeRestClient) {
        this.nodeRestClient = nodeRestClient;
    }

    public static NodeImpl create(NodeAccessData nodeAccessData) {
        return new NodeImpl(
                new NodeRestClient(
                    new NodeConnection(nodeAccessData, RestTemplateFactory.REST_TEMPLATE_FACTORY.createRestTemplate())
                )
        );
    }

    public AnswerObjects createBoard(BoardEntry boardEntry) throws IOException {
        return getAnswerObjects(nodeRestClient.createBoard(boardEntry));

    }

    public AnswerObjects getBoard(BoardEntry boardEntry) throws IOException {
        return getAnswerObjects(nodeRestClient.getBoard(boardEntry));
    }

    public AnswerObjects deleteBoard(BoardEntry boardEntry) throws IOException {
        return getAnswerObjects(nodeRestClient.deleteBoard(boardEntry));
    }

    public AnswerObject setLinePortConfiguration(LinePortEntry linePortEntry, Configuration configuration) {
//        nodeRestClient.setLinePortConfiguration(linePortEntry, configuration);
        return null;
    }


    private AnswerObjects getAnswerObjects(ResponseEntity<String> responseEntity) throws IOException {
        return new AnswerObjects(readValueAsList(responseEntity));
    }

    private List<AnswerObject> readValueAsList(ResponseEntity<String> responseEntity) throws IOException {
        if(responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("XTM responded with status code " + responseEntity.getStatusCode());
        }
        return reader.readValue(responseEntity.getBody());
    }
}
