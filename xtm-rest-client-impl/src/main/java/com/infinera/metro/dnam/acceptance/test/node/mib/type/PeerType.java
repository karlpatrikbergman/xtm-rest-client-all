package com.infinera.metro.dnam.acceptance.test.node.mib.type;

public enum PeerType {
    PEER("peer");

    private final String name;

    PeerType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

