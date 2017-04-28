package com.infinera.metro.dnam.acceptance.test.mib;

public interface MibEntry {
    Module getModule();
    GroupOrTable getGroupOrTable();
    String getMibEntryString();
    String getMibEntryPath();
}
