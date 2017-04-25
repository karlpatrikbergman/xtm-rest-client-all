package com.infinera.metro.dnam.acceptance.test.mib;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BoardEntry implements MibEntry{
    private final Board board;
    private final int subrack;
    private final int slot;

    @Override
    public String getMibString() {
        return getBoard().getName() + ":" + getSubrack() + ":" + getSlot();
    }
}
