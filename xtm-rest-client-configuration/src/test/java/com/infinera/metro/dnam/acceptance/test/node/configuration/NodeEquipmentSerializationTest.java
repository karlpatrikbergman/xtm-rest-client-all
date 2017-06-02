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
public class NodeEquipmentSerializationTest {
    @Test
    public void test() throws IOException {
        Tpd10gbe tpd10gbe = Tpd10gbe.builder()
                .subrack(1)
                .slot(Slot.slot2)
                .clientPorts(Arrays.asList(
                        Port.builder()
                                .transmitPort(1)
                                .receivePort(2)
                                .configuration(Configuration.builder()
                                        .key("clientIfConfigurationCommand")
                                        .value("wan10GbE yes")
                                        .build())
                                .build(),
                        Port.builder()
                                .transmitPort(5)
                                .receivePort(6)
                                .configuration(Configuration.builder()
                                        .key("clientIfConfigurationCommand")
                                        .value("wan10GbE yes")
                                        .build())
                                .build()
                ))
                .linePorts(Arrays.asList(
                        Port.builder()
                                .transmitPort(3)
                                .receivePort(4)
                                .configuration(Configuration.builder()
                                        .key("expectedFrequency")
                                        .value("ch926")
                                        .build())
                                .build(),
                        Port.builder()
                                .transmitPort(7)
                                .receivePort(8)
                                .configuration(Configuration.builder()
                                        .key("expectedFrequency")
                                        .value("ch927")
                                        .build())
                                .build()
                ))
                .build();

        Map<Slot, Board> boards = new HashMap<>();
        boards.put(tpd10gbe.getSlot(), tpd10gbe);

        NodeEquipment nodeEquipment = NodeEquipment.builder()
                .boards(boards)
                .build();
        ObjectMapper mapper = ObjectFromFileUtilJackson.INSTANCE.getMapper();

        log.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(nodeEquipment));
    }
}
