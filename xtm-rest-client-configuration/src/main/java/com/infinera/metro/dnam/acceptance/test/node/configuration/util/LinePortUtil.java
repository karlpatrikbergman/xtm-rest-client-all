package com.infinera.metro.dnam.acceptance.test.node.configuration.util;

import com.google.common.base.Preconditions;
import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.line.LinePortAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.LinePort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LinePortUtil {

    public static List<LinePort> createLinePortList(Integer startTxPort, Integer endTxPort, List<LinePortAttributes> linePortAttributes) {
        Preconditions.checkNotNull(startTxPort);
        Preconditions.checkNotNull(endTxPort);
        Preconditions.checkArgument(startTxPort % 2 != 0, "Start tx port must be even number");
        Preconditions.checkArgument(endTxPort% 2 != 0, "End tx port must be even number");
        Preconditions.checkArgument(startTxPort<endTxPort, "Start tx port must be greater than end tx port");

        return IntStream.rangeClosed(startTxPort, endTxPort)
            .filter(port -> port %2 != 0)
            .mapToObj(
                txPort -> LinePort.builder()
                    .transmitPort(txPort)
                    .receivePort(txPort+1)
                    .linePortAttributes(linePortAttributes)
                    .build()
            )
            .collect(Collectors.toList());
    }
}
