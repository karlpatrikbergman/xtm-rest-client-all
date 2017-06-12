package com.infinera.metro.dnam.acceptance.test.dockercompose.kinell;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infinera.metro.dnam.acceptance.test.node.configuration.*;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Board;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Oa2x17;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Roadm1x2G50;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class KinellEquipmentSerializationTest {

    @Test
    public void test() throws IOException {
        Roadm1x2G50 roadm1x2G50 = Roadm1x2G50.builder()
            .subrack(1)
            .slot(Slot.slot2)
            .addDropPorts(Collections.emptyList())
            .linePorts(Collections.emptyList())
            .build();

        Oa2x17 oa2x17 = Oa2x17.builder()
            .subrack(1)
            .slot(Slot.slot3)
            .ports(Collections.emptyList())
            .build();

        /*
        Tpd10gbe tpd10gbe = Tpd10gbe.builder()
            .subrack(1)
            .slot(Slot.slot2)
            .clientPorts(
                Arrays.asList(
                    Port.builder()
                        .transmitPort(1)
                        .receivePort(2)
                        .portAttributes(
                            Arrays.asList(
                                ClientPortConfigAttribute.of(
                                    ConfigurationList.of(Configuration.builder()
                                        .key("clientIfConfigurationCommand")
                                        .value("wan10GbE yes")
                                        .build()
                                    )
                                ),
                                ClientPortSetAttribute.of(
                                    ConfigurationList.of(Configuration.builder()
                                        .key("expectedFrequency")
                                        .value("w1530")
                                        .build()
                                    )
                                )
                            )
                        )
                        .build(),
                    Port.builder()
                        .transmitPort(5)
                        .receivePort(6)
                        .portAttributes(
                            Arrays.asList(
                                ClientPortConfigAttribute.of(
                                    ConfigurationList.of(Configuration.builder()
                                        .key("clientIfConfigurationCommand")
                                        .value("wan10GbE yes")
                                        .build()
                                    )
                                ),
                                ClientPortSetAttribute.of(
                                    ConfigurationList.of(Configuration.builder()
                                        .key("expectedFrequency")
                                        .value("w1530")
                                        .build()
                                    )
                                )
                            )
                        )
                        .build()
                )
            )
            .linePorts(
                Arrays.asList(
                    Port.builder()
                        .transmitPort(3)
                        .receivePort(4)
                        .portAttributes(
                            Arrays.asList(
                                LinePortSetAttribute.of(ConfigurationList.of(
                                    Configuration.builder()
                                        .key("expectedFrequency")
                                        .value("ch926")
                                        .build()
                                    )
                                )
                            )
                        )
                        .build(),
                    Port.builder()
                        .transmitPort(7)
                        .receivePort(8)
                        .portAttributes(
                            Arrays.asList(
                                LinePortSetAttribute.of(ConfigurationList.of(
                                    Configuration.builder()
                                        .key("expectedFrequency")
                                        .value("ch927")
                                        .build()
                                    )
                                )
                            )
                        )
                        .build()
                )
            )
            .build();
        */
        Map<Slot, Board> boards = new HashMap<>();
        boards.put(roadm1x2G50.getSlot(), roadm1x2G50);
        boards.put(oa2x17.getSlot(), oa2x17);

        NodeEquipment nodeEquipment = NodeEquipment.builder()
            .boards(boards)
            .build();
        ObjectMapper mapper = ObjectFromFileUtilJackson.INSTANCE.getMapper();

        log.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(nodeEquipment));

    }
}
