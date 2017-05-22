package com.infinera.metro.dnam.acceptance.test.node.configuration2;

import lombok.*;
import lombok.experimental.Delegate;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true) //Needed by Hibernate
@AllArgsConstructor(access = AccessLevel.PUBLIC) //Needed by Orika
@Value
public class NodeEquipment2 {

//    private interface SubracksApi {
//     boolean add(Subrack subrack);
//     boolean remove(Subrack subrack);
//   }

    @Delegate
    @NonNull
    private final List<Subrack> subracks;
}
