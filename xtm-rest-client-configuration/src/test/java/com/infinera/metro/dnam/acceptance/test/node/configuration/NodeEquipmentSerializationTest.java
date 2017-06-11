package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infinera.metro.dnam.acceptance.test.node.mib.Configuration;
import com.infinera.metro.dnam.acceptance.test.node.mib.ConfigurationList;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class NodeEquipmentSerializationTest {

    public void foo() {
        List<Port> clientPorts = Arrays.asList(
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
        );
    }

    public void bar() {
        List<Port> linePorts = Arrays.asList(
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
        );
    }

    @Test
    public void test() throws IOException {
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
        Map<Slot, Board> boards = new HashMap<>();
        boards.put(tpd10gbe.getSlot(), tpd10gbe);

        NodeEquipment nodeEquipment = NodeEquipment.builder()
                .boards(boards)
                .build();
        ObjectMapper mapper = ObjectFromFileUtilJackson.INSTANCE.getMapper();

        log.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(nodeEquipment));
    }
}
