package com.infinera.metro.dnam.acceptance.test.node;

import com.infinera.metro.dnam.acceptance.test.mib.*;
import com.infinera.metro.dnam.acceptance.test.node.dto.AnswerObjects;

import java.io.IOException;
import java.util.List;

public interface Node {
    AnswerObjects createBoard(BoardEntry boardEntry) throws IOException;
    AnswerObjects getBoard(BoardEntry boardEntry) throws IOException;
    AnswerObjects deleteBoard(BoardEntry boardEntry) throws IOException;
    AnswerObjects setLinePortConfiguration(LinePortEntry linePortEntry, Configuration configuration) throws IOException;
    AnswerObjects setClientPortConfiguration(ClientPortEntry clientPortEntry, Configuration clientPortConfiguration) throws IOException;
    AnswerObjects createLocalPeer(PeerEntry peerEntry) throws IOException;
    AnswerObjects configureLocalPeer(PeerEntry peerEntry, List<Configuration> configuration) throws IOException;
}
