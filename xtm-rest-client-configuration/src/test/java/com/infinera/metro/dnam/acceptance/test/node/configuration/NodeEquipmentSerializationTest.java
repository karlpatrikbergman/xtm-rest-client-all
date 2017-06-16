package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Board;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Tpd10gbe;
import com.infinera.metro.dnam.acceptance.test.node.mib.Configuration;
import com.infinera.metro.dnam.acceptance.test.node.mib.Configurations;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class NodeEquipmentSerializationTest {

//    public void foo() {
//        List<Port> clientPorts = Arrays.asList(
//            Port.builder()
//                .transmitPort(1)
//                .receivePort(2)
//                .mibEntryAttributes(
//                    Arrays.asList(
//                        ClientPortConfigAttributes.of(
//                            ConfigurationList.of(Configuration.builder()
//                                .key("clientIfConfigurationCommand")
//                                .value("wan10GbE yes")
//                                .build()
//                            )
//                        ),
//                        ClientPortSetAttributes.of(
//                            ConfigurationList.of(Configuration.builder()
//                                .key("expectedFrequency")
//                                .value("w1530")
//                                .build()
//                            )
//                        )
//                    )
//                )
//                .build(),
//            Port.builder()
//                .transmitPort(5)
//                .receivePort(6)
//                .mibEntryAttributes(
//                    Arrays.asList(
//                        ClientPortConfigAttributes.of(
//                            ConfigurationList.of(Configuration.builder()
//                                .key("clientIfConfigurationCommand")
//                                .value("wan10GbE yes")
//                                .build()
//                            )
//                        ),
//                        ClientPortSetAttributes.of(
//                            ConfigurationList.of(Configuration.builder()
//                                .key("expectedFrequency")
//                                .value("w1530")
//                                .build()
//                            )
//                        )
//                    )
//                )
//                .build()
//        );
//    }
//
//    public void bar() {
//        List<Port> linePorts = Arrays.asList(
//            Port.builder()
//                .transmitPort(3)
//                .receivePort(4)
//                .mibEntryAttributes(
//                    Arrays.asList(
//                        LinePortSetAttributes.of(ConfigurationList.of(
//                            Configuration.builder()
//                                .key("expectedFrequency")
//                                .value("ch926")
//                                .build()
//                            )
//                        )
//                    )
//                )
//                .build(),
//            Port.builder()
//                .transmitPort(7)
//                .receivePort(8)
//                .mibEntryAttributes(
//                    Arrays.asList(
//                        LinePortSetAttributes.of(ConfigurationList.of(
//                            Configuration.builder()
//                                .key("expectedFrequency")
//                                .value("ch927")
//                                .build()
//                            )
//                        )
//                    )
//                )
//                .build()
//        );
//    }

    @Test
    public void test() throws IOException {
        Tpd10gbe tpd10gbe = Tpd10gbe.builder()
            .subrack(1)
            .slot(Slot.slot2)
            .boardEntryAttributes(Arrays.asList(
                BoardSetAttributes.of(
                    Configurations.of(Configuration.builder()
                        .key("adminStatus")
                        .value("up")
                        .build()))
            ))
            .clientPorts(
                Arrays.asList(
                    Port.builder()
                        .transmitPort(1)
                        .receivePort(2)
                        .portEntryAttributes(
                            Arrays.asList(
                                ClientPortConfigAttributes.of(
                                    Configurations.of(Configuration.builder()
                                        .key("clientIfConfigurationCommand")
                                        .value("wan10GbE yes")
                                        .build()
                                    )
                                ),
                                ClientPortSetAttributes.of(
                                    Configurations.of(Configuration.builder()
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
                        .portEntryAttributes(
                            Arrays.asList(
                                ClientPortConfigAttributes.of(
                                    Configurations.of(Configuration.builder()
                                        .key("clientIfConfigurationCommand")
                                        .value("wan10GbE yes")
                                        .build()
                                    )
                                ),
                                ClientPortSetAttributes.of(
                                    Configurations.of(Configuration.builder()
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
                        .portEntryAttributes(
                            Arrays.asList(
                                LinePortSetAttributes.of(Configurations.of(
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
                        .portEntryAttributes(
                            Arrays.asList(
                                LinePortSetAttributes.of(Configurations.of(
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
        Map<Slot, Board> boards = new HashMap<>();
        boards.put(tpd10gbe.getSlot(), tpd10gbe);

        NodeEquipment nodeEquipment = NodeEquipment.builder()
            .boards(boards)
            .build();
        ObjectMapper mapper = ObjectFromFileUtilJackson.INSTANCE.getMapper();

        log.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(nodeEquipment));
    }
}
