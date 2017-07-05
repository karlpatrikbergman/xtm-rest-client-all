package com.infinera.metro.dnam.acceptance.test.node;

import com.infinera.metro.dnam.acceptance.test.node.mib.Configurations;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.*;

/**
 * TODO: Change method signature for configuration to match configure.json and set.json
 *
 * Client signal format
 * /client/if/client:1:2:1-2/configure.json?clientIfConfigurationCommand=wan10GbE yes
 *  configureLinePort()
 *
 * Client expected frequency
 * /client/if/client:1:2:1-2/set.json?expectedFrequency=w1530
 *  setLinePortConfig()?
 *
 * Line settings
 * /wdm/if/wdm:1:2:3-4/set.json&expectedFrequency=w1530"
 */
public interface Node {
    String getIpAddress();
    AnswerObjects createBoard(BoardEntry boardEntry) throws RuntimeException;
    AnswerObjects getBoard(BoardEntry boardEntry) throws RuntimeException;
    AnswerObjects deleteBoard(BoardEntry boardEntry) throws RuntimeException;
    AnswerObjects setBoardAttributes(BoardEntry boardEntry, Configurations configurations);

    AnswerObjects setLinePortAttributes(LinePortEntry linePortEntry, Configurations configurations) throws RuntimeException;

    AnswerObjects setClientPortAttributes(ClientPortEntry clientPortEntry, Configurations configurations) throws RuntimeException;
    AnswerObjects configureClientPortAttributes(ClientPortEntry clientPortEntry, Configurations configurations) throws RuntimeException;

    AnswerObjects configureAddDropPortAttributes(AddDropPortEntry addDropPortEntry, Configurations configurations) throws RuntimeException;

    AnswerObjects createPeer(PeerEntry peerEntry) throws RuntimeException;

    //TODO: Rename to setPeerAttributes
    AnswerObjects setLocalPeerConfiguration(PeerEntry peerEntry, Configurations configurations) throws RuntimeException;

    static Node defaultImplementation(NodeAccessData nodeAccessData) {
        return new NodeImpl(
            new NodeRestClient(
                new NodeConnection(nodeAccessData, RestTemplateFactory.REST_TEMPLATE_FACTORY.createRestTemplate())
            )
        );
    }
}
