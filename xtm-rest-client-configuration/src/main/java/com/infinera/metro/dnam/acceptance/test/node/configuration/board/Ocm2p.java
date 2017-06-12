package com.infinera.metro.dnam.acceptance.test.node.configuration.board;


import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.BoardType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

//TODO: Figure out how ports for this device work, and what can be set

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Value
public class Ocm2p extends AbstractBoard implements Board {

    @Builder
    @java.beans.ConstructorProperties({"subrack", "slot"})
    private Ocm2p(Integer subrack, Slot slot) {
        super(BoardType.OCM2P, subrack, slot);
    }

    @Override
    public void applyTo(Node node) {
        createBoard(node);
    }
}
