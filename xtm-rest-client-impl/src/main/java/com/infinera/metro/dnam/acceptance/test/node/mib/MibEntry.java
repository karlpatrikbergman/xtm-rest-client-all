package com.infinera.metro.dnam.acceptance.test.node.mib;

public interface MibEntry {
    ModuleType getModuleType();
    GroupOrTableType getGroupOrTableType();
    String getMibEntryString();
    String getMibEntryPath();
}
