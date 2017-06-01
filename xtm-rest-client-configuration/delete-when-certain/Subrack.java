package com.infinera.metro.dnam.acceptance.test.node.configuration;

import lombok.*;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true) //Needed by Hibernate
@AllArgsConstructor(access = AccessLevel.PUBLIC) //Needed by Orika
@Value
public class Subrack {
    @NonNull private final Map<String, Slot> slots;

    public Slot getSlot(String slotKey) {
        return slots.get(slotKey);
    }
}
