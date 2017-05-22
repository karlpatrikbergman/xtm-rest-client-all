package com.infinera.metro.dnam.acceptance.test.node.configuration2;

import com.infinera.metro.dnam.acceptance.test.node.mib.Configuration;
import com.infinera.metro.dnam.acceptance.test.node.mib.LinePortType;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true) //Needed by Hibernate
@AllArgsConstructor(access = AccessLevel.PUBLIC) //Needed by Orika
@Value
public class LinePort {
    @NonNull private final LinePortType linePortType;
    @NonNull private final Integer transmitPort;
    @NonNull private final Integer receivePort;
    @NonNull private final Configuration configuration;

}
