package com.infinera.metro.dnam.acceptance.test.node.mib;

public enum Command {
    CREATE_JSON("create.json", OperationType.CREATE),
    GET_JSON("get.json", OperationType.GET),
    SET_JSON("set.json", OperationType.SET),
    DELETE_JSON("delete.json", OperationType.DELETE),
    CONFIGURE_JSON("configure.json", OperationType.CONFIGURE);

    private final String value;
    private final OperationType operationType;

    Command(String value, OperationType operationType) {
        this.value = value;
        this.operationType = operationType;
    }

    public String getValue() {
        return value;
    }

    public OperationType getOperation() {
        return operationType;
    }
}
