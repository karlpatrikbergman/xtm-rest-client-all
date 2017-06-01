package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.infinera.metro.dnam.acceptance.test.node.mib.ClientPortType;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true) //Needed by Hibernate
@AllArgsConstructor(access = AccessLevel.PUBLIC) //Needed by Orika
@Value
public class ClientPort {
    @NonNull private final ClientPortType clientPortType;
    @NonNull private final Integer transmitPort;
    @NonNull private final Integer receivePort;

}
