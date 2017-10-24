package com.infinera.metro.dnam.acceptance.test.node.configuration;

public enum Subrack {
    subrack1(1), subrack2(2), subrack3(3), subrack4(4),;

    private final Integer value;

    Subrack(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static Subrack fromString(String subrackString) {
        for (Subrack subrack : Subrack.values()) {
            if (subrack.toString().equalsIgnoreCase(subrackString)) {
                return subrack;
            }
        }
        throw new IllegalArgumentException("Failed to create Subrack from string" + subrackString);
    }
}
