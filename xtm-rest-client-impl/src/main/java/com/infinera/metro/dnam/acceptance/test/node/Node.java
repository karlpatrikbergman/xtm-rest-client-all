package com.infinera.metro.dnam.acceptance.test.node;

import com.infinera.metro.dnam.acceptance.test.node.mib.*;

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
    AnswerObjects setLinePortConfiguration(LinePortEntry linePortEntry, ConfigurationList configurationList) throws RuntimeException;
    AnswerObjects setClientPortConfiguration(ClientPortEntry clientPortEntry, ConfigurationList configurationList) throws RuntimeException;
    AnswerObjects createLocalPeer(PeerEntry peerEntry) throws RuntimeException;
    AnswerObjects setLocalPeerConfiguration(PeerEntry peerEntry, ConfigurationList configurationList) throws RuntimeException;
}
