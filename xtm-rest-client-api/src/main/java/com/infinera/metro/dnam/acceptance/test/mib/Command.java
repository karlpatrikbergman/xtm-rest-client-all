package com.infinera.metro.dnam.acceptance.test.mib;

public enum Command {
    CREATE_JSON("create.json", Operation.CREATE),
    GET_JSON("get.json", Operation.GET),
    SET_JSON("set.json", Operation.SET),
    DELETE_JSON("delete.json", Operation.DELETE);

    private final String value;
    private final Operation operation;

    Command(String value, Operation operation) {
        this.value = value;
        this.operation = operation;
    }

    public String getValue() {
        return value;
    }

    public Operation getOperation() {
        return operation;
    }
}
