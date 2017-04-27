package com.infinera.metro.dnam.acceptance.test.mib;

public enum Operation {
    CREATE("create"),
    GET("get"),
    SET("set"),
    ERROR("error");

    private final String name;

    Operation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
