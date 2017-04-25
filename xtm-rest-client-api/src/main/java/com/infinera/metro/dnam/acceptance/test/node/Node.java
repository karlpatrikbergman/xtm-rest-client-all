package com.infinera.metro.dnam.acceptance.test.node;

import com.infinera.metro.dnam.acceptance.test.mib.BoardEntry;
import com.infinera.metro.dnam.acceptance.test.node.dto.AnswerObjects;

import java.io.IOException;

public interface Node {
    AnswerObjects createBoard(BoardEntry boardEntry) throws IOException;
    AnswerObjects getBoard(BoardEntry boardEntry) throws IOException;
    AnswerObjects deleteBoard(BoardEntry boardEntry) throws IOException;
}
