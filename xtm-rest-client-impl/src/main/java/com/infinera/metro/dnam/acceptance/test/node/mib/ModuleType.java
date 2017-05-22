package com.infinera.metro.dnam.acceptance.test.node.mib;

public enum ModuleType {
    EQUIPMENT("eq"),
    WDM("wdm"),
    CLIENT("client"),
    TOPO("topo");

    private final String value;

    ModuleType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
