package com.infinera.metro.dnam.acceptance.test.node.mib;

public interface MibEntry {
    Module getModule();
    GroupOrTable getGroupOrTable();
    String getMibEntryString();
    String getMibEntryPath();
}
