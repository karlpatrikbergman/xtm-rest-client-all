package com.infinera.metro.dnam.acceptance.test.node.configuration2;

import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true) //Needed by Hibernate
@AllArgsConstructor(access = AccessLevel.PUBLIC) //Needed by Orika
@Value
public class Subrack {

    @NonNull private final Integer number;
    @NonNull private final String name;
    @lombok.experimental.Delegate
    @NonNull
    private final List<Slot> slots;
}
