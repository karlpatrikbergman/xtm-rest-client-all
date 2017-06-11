package com.infinera.metro.dnam.acceptance.test.node;

import com.infinera.metro.dnam.acceptance.test.node.mib.*;

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

    public String getIpAddress() {
        return nodeRestClient.getNodeIpAddress();
    }

    /** BoardEntry **/

    public AnswerObjects createBoard(BoardEntry boardEntry) throws RuntimeException {
        return nodeRestClient.performRestAction(boardEntry, Command.CREATE_JSON);
    }

    public AnswerObjects getBoard(BoardEntry boardEntry) throws RuntimeException {
        return nodeRestClient.performRestAction(boardEntry, Command.GET_JSON);
    }

    public AnswerObjects deleteBoard(BoardEntry boardEntry) throws RuntimeException {
        return nodeRestClient.performRestAction(boardEntry, Command.DELETE_JSON);
   }

    /** LinePortEntry **/

    public AnswerObjects setLinePortAttributes(LinePortEntry linePortEntry, ConfigurationList configurationList) throws RuntimeException {
        return nodeRestClient.performRestAction(linePortEntry, Command.SET_JSON, configurationList);
    }

    /** ClientPortEntry **/

    public AnswerObjects setClientPortAttributes(ClientPortEntry clientPortEntry, ConfigurationList configurationList)  throws RuntimeException {
        return nodeRestClient.performRestAction(clientPortEntry, Command.SET_JSON, configurationList);
    }

    public AnswerObjects configureClientPortAttributes(ClientPortEntry clientPortEntry, ConfigurationList configurationList) throws RuntimeException {
        return nodeRestClient.performRestAction(clientPortEntry, Command.CONFIGURE_JSON, configurationList);
    }

    /** PeerEntry **/

    public AnswerObjects createLocalPeer(PeerEntry peerEntry) throws RuntimeException {
        return nodeRestClient.performRestAction(peerEntry, Command.CREATE_JSON);
    }

    public AnswerObjects setLocalPeerConfiguration(PeerEntry peerEntry, ConfigurationList configurationList) throws RuntimeException {
        return nodeRestClient.performRestAction(peerEntry, Command.SET_JSON, configurationList);
    }

}
