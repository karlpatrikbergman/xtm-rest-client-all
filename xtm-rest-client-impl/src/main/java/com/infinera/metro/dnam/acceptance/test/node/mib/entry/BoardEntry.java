package com.infinera.metro.dnam.acceptance.test.node.mib.entry;

import com.infinera.metro.dnam.acceptance.test.node.mib.type.BoardType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;
import com.infinera.metro.dnam.acceptance.test.node.mib.util.MibPathUtil;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value //Needs jackson > 2.8
@Builder
public class BoardEntry implements MibEntry {
    private final ModuleType moduleType = ModuleType.EQ; //The same for all boards?
    private final GroupOrTableType groupOrTableType = GroupOrTableType.BOARD; //The same for all boards?
    @NonNull private final BoardType boardType;
    @NonNull private final Integer subrack;
    @NonNull private final Integer slot;

    @Override
    public String getMibEntryString() {
        assert boardType != null;
        return MibPathUtil.MIB_PATH_UTIL.getMibEntryString (boardType.getValue(), getSubrack(), getSlot());
    }

    @Override
    public String getMibEntryPath() {
        return MibPathUtil.MIB_PATH_UTIL.getMibEntryPath(moduleType, groupOrTableType, this);
    }
}
