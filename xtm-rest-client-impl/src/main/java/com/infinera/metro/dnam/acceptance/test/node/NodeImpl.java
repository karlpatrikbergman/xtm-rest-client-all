package com.infinera.metro.dnam.acceptance.test.node;

import com.infinera.metro.dnam.acceptance.test.node.mib.*;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.*;

/**
 * TODO: Make package private. All classes except interface Node could be package private.
 */

public class NodeImpl implements Node {
    private final NodeRestClient nodeRestClient;

    public NodeImpl(NodeRestClient nodeRestClient) {
        this.nodeRestClient = nodeRestClient;
    }

    public static Node create(NodeAccessData nodeAccessData) {
        return new NodeImpl(
            new NodeRestClient(
                new NodeConnection(nodeAccessData, RestTemplateFactory.REST_TEMPLATE_FACTORY.createRestTemplate())
            )
        );
    }

    public static Node createDefault(String ipAddress) {
        return create(NodeAccessData.createDefault(ipAddress));
    }

    @Override
    public String getIpAddress() {
        return nodeRestClient.getNodeIpAddress();
    }

    /**
     * BoardEntry
     **/

    @Override
    public AnswerObjects createBoard(BoardEntry boardEntry) throws RuntimeException {
        return nodeRestClient.performRestAction(boardEntry, CommandType.CREATE_JSON);
    }

    @Override
    public AnswerObjects getBoard(BoardEntry boardEntry, Attributes attributes) throws RuntimeException {
        return nodeRestClient.performRestAction(boardEntry, CommandType.GET_JSON, attributes);
    }


    @Override
    public AnswerObjects deleteBoard(BoardEntry boardEntry) throws RuntimeException {
        return nodeRestClient.performRestAction(boardEntry, CommandType.DELETE_JSON);
    }

    @Override
    public AnswerObjects setBoardAttributes(BoardEntry boardEntry, Attributes attributes) {
        return nodeRestClient.performRestAction(boardEntry, CommandType.SET_JSON, attributes);
    }


    /**
     * LinePortEntry
     **/

    @Override
    public AnswerObjects setLinePortAttributes(LinePortEntry linePortEntry, Attributes attributes) throws RuntimeException {
        return nodeRestClient.performRestAction(linePortEntry, CommandType.SET_JSON, attributes);
    }

    @Override
    public AnswerObjects getLinePortAttributes(LinePortEntry linePortEntry, Attributes attributes) throws RuntimeException {
        return nodeRestClient.performRestAction(linePortEntry, CommandType.GET_JSON, attributes);
    }

    /**
     * ClientPortEntry
     **/

    @Override
    public AnswerObjects setClientPortAttributes(ClientPortEntry clientPortEntry, Attributes attributes) throws RuntimeException {
        return nodeRestClient.performRestAction(clientPortEntry, CommandType.SET_JSON, attributes);
    }

    @Override
    public AnswerObjects configureClientPortAttributes(ClientPortEntry clientPortEntry, Attributes attributes) throws RuntimeException {
        return nodeRestClient.performRestAction(clientPortEntry, CommandType.CONFIGURE_JSON, attributes);
    }

    @Override
    public AnswerObjects getClientPortAttributes(ClientPortEntry clientPortEntry, Attributes attributes) {
        return nodeRestClient.performRestAction(clientPortEntry, CommandType.GET_JSON, attributes);
    }

    /**
     * AddDropPortEntry
     **/

    @Override
    public AnswerObjects configureAddDropPortAttributes(AddDropPortEntry addDropPortEntry, Attributes attributes) throws RuntimeException {
        return nodeRestClient.performRestAction(addDropPortEntry, CommandType.CONFIGURE_JSON, attributes);
    }

    /**
     * PeerEntry
     **/

    @Override
    public AnswerObjects createPeer(PeerEntry peerEntry) throws RuntimeException {
        return nodeRestClient.performRestAction(peerEntry, CommandType.CREATE_JSON);
    }

    @Override
    public AnswerObjects setPeerAttributes(PeerEntry peerEntry, Attributes attributes) throws RuntimeException {
        return nodeRestClient.performRestAction(peerEntry, CommandType.SET_JSON, attributes);
    }

    @Override
    public AnswerObjects getPeer(PeerEntry peerEntry) throws RuntimeException {
        return nodeRestClient.performRestAction(peerEntry, CommandType.GET_JSON);
    }

    @Override
    public AnswerObjects createInternalConnection(InternalConnectionEntry internalConnectionEntry) throws RuntimeException {
        return nodeRestClient.performRestAction(internalConnectionEntry, CommandType.CREATE_JSON);
    }

    @Override
    public AnswerObjects getInternalConnection(InternalConnectionEntry internalConnectionEntry, Attributes attributes) throws RuntimeException {
        return nodeRestClient.performRestAction(internalConnectionEntry, CommandType.GET_JSON, attributes);
    }
}
