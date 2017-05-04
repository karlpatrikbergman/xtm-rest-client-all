package com.infinera.metro.dnam.acceptance.test.node;

import com.infinera.metro.dnam.acceptance.test.mib.*;
import com.infinera.metro.dnam.acceptance.test.node.dto.AnswerObjects;

import java.io.IOException;
import java.util.List;

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

    /** BoardEntry **/

    public AnswerObjects createBoard(BoardEntry boardEntry) throws IOException {
        return nodeRestClient.performRestAction(boardEntry, Command.CREATE_JSON);
    }

    public AnswerObjects getBoard(BoardEntry boardEntry) throws IOException {
        return nodeRestClient.performRestAction(boardEntry, Command.GET_JSON);
    }

    public AnswerObjects deleteBoard(BoardEntry boardEntry) throws IOException {
        return nodeRestClient.performRestAction(boardEntry, Command.DELETE_JSON);
   }

    /** LinePortEntry **/

    public AnswerObjects setLinePortConfiguration(LinePortEntry linePortEntry, Configuration configuration) throws IOException {
        return nodeRestClient.performRestAction(linePortEntry, Command.SET_JSON, configuration);
    }

    /** ClientPortEntry **/

    public AnswerObjects setClientPortConfiguration(ClientPortEntry clientPortEntry, Configuration configuration) throws IOException {
        return nodeRestClient.performRestAction(clientPortEntry, Command.CONFIGURE, configuration);
    }

    /** PeerEntry **/

    public AnswerObjects createLocalPeer(PeerEntry peerEntry) throws IOException {
        return nodeRestClient.performRestAction(peerEntry, Command.CREATE_JSON);
    }


    /**
     * 172.17.0.3/mib/topo/peer/peer:1:2:0:3/set.json?topoPeerLocalLabel=1:2:0:3
     * 172.17.0.3/mib/topo/peer/peer:1:2:0:3/set.json?topoPeerRemoteIpAddress=172.17.0.4
     * 172.17.0.3/mib/topo/peer/peer:1:2:0:3/set.json?topoPeerRemoteSubrack=1
     * 172.17.0.3/mib/topo/peer/peer:1:2:0:3/set.json?topoPeerRemoteSlot=2
     * 172.17.0.3/mib/topo/peer/peer:1:2:0:3/set.json?topoPeerRemotePort=4
     * 172.17.0.3/mib/topo/peer/peer:1:2:0:3/set.json?topoPeerRemoteLabel=1:2:4
     *
     * 172.17.0.3/mib/topo/peer/peer:1:2:0:3/set.json?topoPeerLocalLabel=1:2:0:3&topoPeerRemoteIpAddress=172.17.0.4&topoPeerRemoteSubrack=1&topoPeerRemoteSlot=2&topoPeerRemotePort=4&topoPeerRemoteLabel=1:2:4
     */
    public AnswerObjects configureLocalPeer(PeerEntry peerEntry, List<Configuration> configuration) throws IOException {
        return null;
    }

}
