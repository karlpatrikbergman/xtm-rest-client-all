package com.infinera.metro.dnam.acceptance.test.node.configuration.board;

import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Subrack;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.BoardType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Value
public class Tpmr2500 extends AbstractBoard implements Board {


    @Builder
    @java.beans.ConstructorProperties({"subrack", "slot"})
    private Tpmr2500(Subrack subrack, Slot slot) {
        super(BoardType.TPMR2500, subrack, slot, null);
    }
}
