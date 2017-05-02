package com.infinera.metro.dnam.acceptance.test.mib;

public enum Peer {
    PEER("peer");

    private final String name;

    Peer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

