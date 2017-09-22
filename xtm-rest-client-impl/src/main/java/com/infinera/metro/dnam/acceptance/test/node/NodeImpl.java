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

    public static NodeImpl create(NodeAccessData nodeAccessData) {
        return new NodeImpl(
            new NodeRestClient(
                new NodeConnection(nodeAccessData, RestTemplateFactory.REST_TEMPLATE_FACTORY.createRestTemplate())
            )
        );
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
    public AnswerObjects getBoard(BoardEntry boardEntry) throws RuntimeException {
        return nodeRestClient.performRestAction(boardEntry, CommandType.GET_JSON);
    }


    @Override
    public AnswerObjects deleteBoard(BoardEntry boardEntry) throws RuntimeException {
        return nodeRestClient.performRestAction(boardEntry, CommandType.DELETE_JSON);
    }

    @Override
    public AnswerObjects setBoardAttributes(BoardEntry boardEntry, Configurations configurations) {
        return nodeRestClient.performRestAction(boardEntry, CommandType.SET_JSON, configurations);
    }


    /**
     * LinePortEntry
     **/

    @Override
    public AnswerObjects setLinePortAttributes(LinePortEntry linePortEntry, Configurations configurations) throws RuntimeException {
        return nodeRestClient.performRestAction(linePortEntry, CommandType.SET_JSON, configurations);
    }

    /**
     * ClientPortEntry
     **/

    @Override
    public AnswerObjects setClientPortAttributes(ClientPortEntry clientPortEntry, Configurations configurations) throws RuntimeException {
        return nodeRestClient.performRestAction(clientPortEntry, CommandType.SET_JSON, configurations);
    }

    @Override
    public AnswerObjects configureClientPortAttributes(ClientPortEntry clientPortEntry, Configurations configurations) throws RuntimeException {
        return nodeRestClient.performRestAction(clientPortEntry, CommandType.CONFIGURE_JSON, configurations);
    }

    /**
     * AddDropPortEntry
     **/

    @Override
    public AnswerObjects configureAddDropPortAttributes(AddDropPortEntry addDropPortEntry, Configurations configurations) throws RuntimeException {
        return nodeRestClient.performRestAction(addDropPortEntry, CommandType.CONFIGURE_JSON, configurations);
    }

    /**
     * PeerEntry
     **/

    @Override
    public AnswerObjects createPeer(PeerEntry peerEntry) throws RuntimeException {
        return nodeRestClient.performRestAction(peerEntry, CommandType.CREATE_JSON);
    }

    @Override
    public AnswerObjects setPeerAttributes(PeerEntry peerEntry, Configurations configurations) throws RuntimeException {
        return nodeRestClient.performRestAction(peerEntry, CommandType.SET_JSON, configurations);
    }

    @Override
    public AnswerObjects createInternalConnection(InternalConnectionEntry internalConnectionEntry) throws RuntimeException {
        return nodeRestClient.performRestAction(internalConnectionEntry, CommandType.CREATE_JSON);
    }


}
