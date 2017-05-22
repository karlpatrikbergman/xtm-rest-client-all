package com.infinera.metro.dnam.acceptance.test.node.configuration2;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true) //Needed by Hibernate
@AllArgsConstructor(access = AccessLevel.PUBLIC) //Needed by Orika
@Value
public class Slot {

    @NonNull private final Integer number;
    @NonNull private final String name;
    @NonNull private final Board board;
}
