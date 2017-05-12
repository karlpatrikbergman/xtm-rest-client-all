package com.infinera.metro.dnam.acceptance.test.node.mib;

public enum GroupOrTable {
    BOARD("board"),
    IF("if"),
    PEER("peer");

    private final String value;

    GroupOrTable(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
