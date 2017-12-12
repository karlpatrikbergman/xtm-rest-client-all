package com.infinera.metro.dnam.acceptance.test.node.configuration.util;

import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.line.LinePortSetAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.LinePort;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

@Slf4j
public class LinePortUtilTest {

    @Test
    public void createLinePortListWithSameAttributes() {
        LinePortSetAttributes linePortSetAttributes = LinePortSetAttributes.of("vc4", "0");
        List<LinePort> linePorts = LinePortUtil.createLinePortList(1, 15, Collections.singletonList(linePortSetAttributes));

        for (LinePort linePort : linePorts) {
            log.info("tx port: {}", linePort.getTransmitPort().toString());
            log.info("rx port: {}", linePort.getReceivePort().toString());
            log.info(linePort.toString());

        }
    }
}
