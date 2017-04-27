package com.infinera.metro.dnam.acceptance.test.mib;

public enum GroupOrTable {
    BOARD("board"),
    IF("if");

    private final String value;

    GroupOrTable(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
