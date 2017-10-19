package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Board;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Tpd10gbe;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attribute;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attributes;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Doesn't actually test anything
 */
@Slf4j
public class NodeEquipmentSerializationTest {

    @Test
    public void test() throws IOException {
        Tpd10gbe tpd10gbe = Tpd10gbe.builder()
            .subrack(1)
            .slot(Slot.slot2)
            .boardEntryAttribute(
                BoardSetAttributes.of(Attribute.builder()
                    .key("adminStatus")
                    .value("up")
                    .build())
            )
            .clientPort(
                Port.builder()
                    .transmitPort(1)
                    .receivePort(2)
                    .portEntryAttribute(
                        ClientPortConfigAttributes.of(
                            Attributes.of(Attribute.builder()
                                .key("clientIfConfigurationCommand")
                                .value("wan10GbE yes")
                                .build()
                            )
                        )
                    )
                    .portEntryAttribute(
                        ClientPortSetAttributes.of(
                            Attributes.of(Attribute.builder()
                                .key("expectedFrequency")
                                .value("w1530")
                                .build()
                            )
                        )
                    )
                    .build())
            .clientPort(
                Port.builder()
                    .transmitPort(5)
                    .receivePort(6)
                    .portEntryAttribute(
                        ClientPortConfigAttributes.of(
                            Attributes.of(Attribute.builder()
                                .key("clientIfConfigurationCommand")
                                .value("wan10GbE yes")
                                .build()
                            )
                        )
                    )
                    .portEntryAttribute(
                        ClientPortSetAttributes.of(
                            Attributes.of(Attribute.builder()
                                .key("expectedFrequency")
                                .value("w1530")
                                .build()
                            )
                        )
                    )
                    .build()
            )
            .linePort(
                Port.builder()
                    .transmitPort(3)
                    .receivePort(4)
                    .portEntryAttributes(
                        Arrays.asList(
                            LinePortSetAttributes.of(Attributes.of(
                                Attribute.builder()
                                    .key("expectedFrequency")
                                    .value("ch926")
                                    .build()
                                )
                            )
                        )
                    )
                    .build()
            )
            .linePort(
                Port.builder()
                    .transmitPort(7)
                    .receivePort(8)
                    .portEntryAttributes(
                        Arrays.asList(
                            LinePortSetAttributes.of(Attributes.of(
                                Attribute.builder()
                                    .key("expectedFrequency")
                                    .value("ch927")
                                    .build()
                                )
                            )
                        )
                    )
                    .build()
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
