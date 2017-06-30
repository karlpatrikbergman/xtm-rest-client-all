package com.infinera.metro.dnam.acceptance.test.node.mib.entry;

import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;

public interface MibEntry {
    ModuleType getModuleType();
    GroupOrTableType getGroupOrTableType();
    Integer getSubrack();
    Integer getSlot();
    String getMibEntryString();
    String getMibEntryPath();
}
