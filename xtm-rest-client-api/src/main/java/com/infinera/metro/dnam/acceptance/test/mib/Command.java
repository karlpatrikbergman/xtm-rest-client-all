package com.infinera.metro.dnam.acceptance.test.mib;

public enum Command {
    GET_JSON("get.json"),
    LIST_JSON("list.json"),
    CREATE_JSON("create.json"),
    DELETE_JSON("delete.json"),
    SET_JSON("set.json");

    private final String value;

    Command(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
