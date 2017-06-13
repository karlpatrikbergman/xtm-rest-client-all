package com.infinera.metro.dnam.acceptance.test.node.configuration.board;

import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.BoardType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Value
public class Mdu40EvenL extends AbstractBoard implements Board {
    @Builder
    @java.beans.ConstructorProperties({"subrack", "slot"})
    private Mdu40EvenL(Integer subrack, Slot slot) {
        super(BoardType.MDU40EVENL, subrack, slot);
    }

    @Override
    public void applyTo(Node node) {
        createBoard(node);
    }
}
