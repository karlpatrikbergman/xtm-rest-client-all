package com.infinera.metro.dnam.acceptance.test.mib;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import static com.infinera.metro.dnam.acceptance.test.mib.util.MibPathUtil.MIB_PATH_UTIL;

@Value
@Builder
public class BoardEntry implements MibEntry {
    @NonNull private final Board board;
    @NonNull private final Integer subrack;
    @NonNull private final Integer slot;
    @NonNull private final Module module = Module.EQUIPMENT;
    @NonNull private final GroupOrTable groupOrTable = GroupOrTable.BOARD;

    @Override
    public String getMibEntryString() {
        return MIB_PATH_UTIL.getMibEntryString (board.getName(), getSubrack() ,getSlot());
    }

    @Override
    public String getMibEntryPath() {
        return MIB_PATH_UTIL.getMibEntryPath(module, groupOrTable, this);
    }
}
