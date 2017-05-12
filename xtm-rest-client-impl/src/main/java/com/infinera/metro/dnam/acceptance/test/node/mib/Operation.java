package com.infinera.metro.dnam.acceptance.test.node.mib;

/**
 * Values of the field AnswerObject.operation
 * Corresponds to some extend to enum "Command" (get.json, create.json, set.json)
 *
 * IMPORTANT NOTE:
 * If the operation initially performed was get/set/delete/configure, and it failed, the attribute
 * AnswerObject.operation will be "error"and NOT "get/set/delete/configure".
 */
public enum Operation {
    CREATE("create"),
    DELETE("delete"),
    GET("get"),
    SET("set"),
    ERROR("error"),
    CONFIGURE("configure");

    private final String name;

    Operation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
