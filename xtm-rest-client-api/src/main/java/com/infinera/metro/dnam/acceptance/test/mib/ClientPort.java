package com.infinera.metro.dnam.acceptance.test.mib;

//Corresponds to first part of "entry" in mib table (without subrack:slot). Find a better name?
public enum ClientPort {
    CLIENT("client");

    private final String name;

    ClientPort(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
