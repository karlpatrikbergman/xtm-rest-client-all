package com.infinera.metro.dnam.acceptance.test.node;

import com.infinera.metro.dnam.acceptance.test.node.mib.*;

public interface Node {
    String getIpAddress();
    AnswerObjects createBoard(BoardEntry boardEntry) throws RuntimeException;
    AnswerObjects getBoard(BoardEntry boardEntry) throws RuntimeException;
    AnswerObjects deleteBoard(BoardEntry boardEntry) throws RuntimeException;
    AnswerObjects setLinePortConfiguration(LinePortEntry linePortEntry, ParameterList parameterList) throws RuntimeException;
    AnswerObjects setClientPortConfiguration(ClientPortEntry clientPortEntry, ParameterList parameterList) throws RuntimeException;
    AnswerObjects createLocalPeer(PeerEntry peerEntry) throws RuntimeException;
    AnswerObjects setLocalPeerConfiguration(PeerEntry peerEntry, ParameterList parameterList) throws RuntimeException;
}
