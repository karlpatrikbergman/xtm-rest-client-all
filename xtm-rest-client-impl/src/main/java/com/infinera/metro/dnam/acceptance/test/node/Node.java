package com.infinera.metro.dnam.acceptance.test.node;

import com.infinera.metro.dnam.acceptance.test.node.mib.Attribute;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attributes;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.*;

public interface Node {
    String getIpAddress();
    AnswerObjects createBoard(BoardEntry boardEntry) throws RuntimeException;
    AnswerObjects getBoard(BoardEntry boardEntry, Attributes attributes) throws RuntimeException;
    AnswerObjects deleteBoard(BoardEntry boardEntry) throws RuntimeException;
    AnswerObjects setBoardAttributes(BoardEntry boardEntry, Attributes attributes);
    AnswerObjects setBoardAttribute(BoardEntry boardEntry, Attribute attribute);

    AnswerObjects setLinePortAttributes(LinePortEntry linePortEntry, Attributes attributes) throws RuntimeException;
    AnswerObjects setLinePortAttribute(LinePortEntry linePortEntry, Attribute attribute) throws RuntimeException;
    AnswerObjects configureLinePortAttributes(LinePortEntry linePortEntry, Attributes linePortConfigAttributes);

    AnswerObjects getLinePortAttributes(LinePortEntry linePortEntry, Attributes attributes) throws RuntimeException;
    AnswerObjects setClientPortAttributes(ClientPortEntry clientPortEntry, Attributes attributes) throws RuntimeException;
    AnswerObjects setClientPortAttribute(ClientPortEntry clientPortEntry, Attribute attribute) throws RuntimeException;
    AnswerObjects configureClientPortAttributes(ClientPortEntry clientPortEntry, Attributes attributes) throws RuntimeException;
    AnswerObjects configureClientPortAttribute(ClientPortEntry clientPortEntry, Attribute attribute) throws RuntimeException;

    AnswerObjects getClientPortAttributes(ClientPortEntry clientPortEntry, Attributes attributes);

    AnswerObjects configureAddDropPortAttributes(AddDropPortEntry addDropPortEntry, Attributes attributes) throws RuntimeException;
    AnswerObjects createPeer(PeerEntry peerEntry) throws RuntimeException;
    AnswerObjects setPeerAttributes(PeerEntry peerEntry, Attributes attributes) throws RuntimeException;
    AnswerObjects setPeerAttribute(PeerEntry peerEntry, Attribute attribute) throws RuntimeException;

    AnswerObjects getPeer(PeerEntry peerEntry) throws RuntimeException;
    AnswerObjects createInternalConnection(InternalConnectionEntry internalConnectionEntry) throws RuntimeException;

    AnswerObjects getInternalConnection(InternalConnectionEntry internalConnectionEntry, Attributes attributes) throws RuntimeException;
}
