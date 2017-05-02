package com.infinera.metro.dnam.acceptance.test.mib;

public enum Module {
    EQUIPMENT("eq"),
    WDM("wdm"),
    CLIENT("client"),
    TOPO("topo");

    private final String value;

    Module(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
