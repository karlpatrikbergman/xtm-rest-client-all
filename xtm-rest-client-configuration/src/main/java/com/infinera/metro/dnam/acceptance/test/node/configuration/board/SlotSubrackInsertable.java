package com.infinera.metro.dnam.acceptance.test.node.configuration.board;

import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Subrack;

public interface SlotSubrackInsertable {
    Subrack getSubrack();
    Slot getSlot();
}
