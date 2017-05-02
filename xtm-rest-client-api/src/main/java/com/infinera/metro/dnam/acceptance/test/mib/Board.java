package com.infinera.metro.dnam.acceptance.test.mib;

//Corresponds to first part of "entry" in mib table (without subrack:slot). Find a better name?
public enum Board {
    TP10G("tp10g"),
    TPD10GBE("tpd10gbe");

    private final String name;

    Board(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
