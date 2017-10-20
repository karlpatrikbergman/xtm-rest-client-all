package com.infinera.metro.dnam.acceptance.test.node.configuration.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infinera.metro.dnam.acceptance.test.node.configuration.NodeEquipment;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.BoardSetAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.ClientPortConfigAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.ClientPortSetAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.LinePortSetAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Board;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Tpd10gbe;
import com.infinera.metro.dnam.acceptance.test.node.configuration.deserialize.ObjectFromFileUtilJackson;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.Port;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attribute;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attributes;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
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
            .boardAttribute(
                BoardSetAttributes.of(Attribute.builder()
                    .key("adminStatus")
                    .value("up")
                    .build())
            )
            .clientPort(
                Port.builder()
                    .transmitPort(1)
                    .receivePort(2)
                    .portAttribute(
                        ClientPortConfigAttributes.of(
                            Attributes.of(Attribute.builder()
                                .key("clientIfConfigurationCommand")
                                .value("wan10GbE yes")
                                .build()
                            )
                        )
                    )
                    .portAttribute(
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
                    .portAttribute(
                        ClientPortConfigAttributes.of(
                            Attributes.of(Attribute.builder()
                                .key("clientIfConfigurationCommand")
                                .value("wan10GbE yes")
                                .build()
                            )
                        )
                    )
                    .portAttribute(
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
                    .portAttribute(
                        LinePortSetAttributes.of(Attributes.of(
                            Attribute.builder()
                                .key("expectedFrequency")
                                .value("ch926")
                                .build()
                            )
                        )
                    )
                    .build()
            )
            .linePort(
                Port.builder()
                    .transmitPort(7)
                    .receivePort(8)
                    .portAttribute(
                        LinePortSetAttributes.of(Attributes.of(
                            Attribute.builder()
                                .key("expectedFrequency")
                                .value("ch927")
                                .build()
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
