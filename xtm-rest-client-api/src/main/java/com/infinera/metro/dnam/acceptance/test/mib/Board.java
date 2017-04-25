package com.infinera.metro.dnam.acceptance.test.mib;

public enum Board {
    TP10G("tp10g");

    private final String name;

    Board(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
