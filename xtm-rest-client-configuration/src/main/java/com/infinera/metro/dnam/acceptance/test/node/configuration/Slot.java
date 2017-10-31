package com.infinera.metro.dnam.acceptance.test.node.configuration;

public enum Slot {
    slot1(1), slot2(2), slot3(3), slot4(4), slot5(5), slot6(6), slot7(7), slot8(8), slot9(9), slot10(10), slot11(11),
    slot12(12), slot13(13), slot14(14), slot15(15), slot16(16),
    slot17(17), slot18(18), slot19(19), slot20(20);

    private final Integer value;

    Slot(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static Slot fromString(String slotString) {
        for (Slot slot : Slot.values()) {
            if (slot.toString().equalsIgnoreCase(slotString)) {
                return slot;
            }
        }
        throw new IllegalArgumentException("Failed to create Slot from string" + slotString);
    }

    public static Slot fromStringInteger(Integer slotInteger) {
        for (Slot slot : Slot.values()) {
            if (slot.getValue().equals(slotInteger)) {
                return slot;
            }
        }
        throw new IllegalArgumentException("Failed to create Slot from Integer" + slotInteger);
    }
}
