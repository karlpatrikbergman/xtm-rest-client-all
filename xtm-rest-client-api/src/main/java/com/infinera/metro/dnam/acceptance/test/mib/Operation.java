package com.infinera.metro.dnam.acceptance.test.mib;

public enum Operation {
    create("create"),
    get("get"),
    error("error");

    private final String name;

    Operation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
