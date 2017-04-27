package com.infinera.metro.dnam.acceptance.test.mib;

public enum LinePort {
    WDM("wdm");

    private final String name;

    LinePort(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

