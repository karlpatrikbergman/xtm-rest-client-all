package com.infinera.metro.dnam.acceptance.test.node.configuration.board;

import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.BoardType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Value
public class Tphex10gotn  extends AbstractBoard implements Board {
    @Builder
    @java.beans.ConstructorProperties({"subrack", "slot"})
    private Tphex10gotn(Integer subrack, Slot slot) {
        super(BoardType.TPHEX10GOTN, subrack, slot, null);
    }
}
