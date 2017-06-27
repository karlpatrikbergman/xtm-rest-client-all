package com.infinera.metro.dnam.acceptance.test.node.mib.entry;

import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.MibType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;
import com.infinera.metro.dnam.acceptance.test.node.mib.util.MibPathUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
class AbstractMibEntry implements MibEntry {
    @Getter @NonNull private final ModuleType moduleType;
    @Getter @NonNull private final GroupOrTableType groupOrTableType;
    @Getter @NonNull private final MibType mibType;
    @Getter @NonNull private final Integer subrack;
    @Getter @NonNull private final Integer slot;

    @Override
    public String getMibEntryString() {
        return MibPathUtil.MIB_PATH_UTIL.getMibEntryString (mibType.getValue(), getSubrack(), getSlot());
    }

    @Override
    public String getMibEntryPath() {
        return MibPathUtil.MIB_PATH_UTIL.getMibEntryPath(moduleType, groupOrTableType, this);
    }
}
