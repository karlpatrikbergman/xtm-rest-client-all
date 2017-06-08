package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infinera.metro.dnam.acceptance.test.node.mib.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class KinellNodeEquipmentSerializationTest {
    @Test
    public void test() throws IOException {
        Roadm1x2G50 roadm1x2G50 = Roadm1x2G50.builder()
                .subrack(1)
                .slot(Slot.slot2)
                .addDropPorts(Arrays.asList(
                        Port.builder()
                                .transmitPort(1)
                                .receivePort(2)
                                .configuration(Configuration.builder()
                                        .key("addChannel")
                                        .value("ch937")
                                        .build())
                                .build(),
                        Port.builder()
                                .transmitPort(3)
                                .receivePort(4)
                                .configuration(Configuration.builder()
                                        .key("addChannel")
                                        .value("ch9185")
                                        .build())
                                .build()
                ))
                .build();

        Map<Slot, Board> boards = new HashMap<>();
        boards.put(roadm1x2G50.getSlot(), roadm1x2G50);

        NodeEquipment nodeEquipment = NodeEquipment.builder()
                .boards(boards)
                .build();
        ObjectMapper mapper = ObjectFromFileUtilJackson.INSTANCE.getMapper();

        log.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(nodeEquipment));
    }
}
