package com.infinera.metro.dnam.acceptance.test.node.configuration2;

import com.infinera.metro.dnam.acceptance.test.node.mib.BoardType;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true) //Needed by Hibernate
@AllArgsConstructor(access = AccessLevel.PUBLIC) //Needed by Orika
@Value
public class Board {
    @NonNull private final BoardType boardType;
    @NonNull private final List<ClientPort> clientPorts;
    @NonNull private final List<LinePort> linePorts;
}
