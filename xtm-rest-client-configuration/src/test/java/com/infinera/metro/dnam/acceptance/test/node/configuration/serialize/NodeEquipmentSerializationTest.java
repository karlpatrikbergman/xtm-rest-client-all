package com.infinera.metro.dnam.acceptance.test.node.configuration.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infinera.metro.dnam.acceptance.test.node.configuration.NodeEquipment;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Subrack;
import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.board.BoardSetAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.client.ClientPortConfigAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.client.ClientPortSetAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.line.LinePortSetAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Mdu40EvenL;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Tpd10gbe;
import com.infinera.metro.dnam.acceptance.test.node.configuration.deserialize.ObjectFromFileUtilJackson;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.ClientPort;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.LinePort;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attribute;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attributes;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;

/**
 * Doesn't actually test anything
 */
@Slf4j
public class NodeEquipmentSerializationTest {

    @Test
    public void test() throws IOException {
        final Tpd10gbe tpd10gbe = Tpd10gbe.builder()
            .subrack(Subrack.subrack1)
            .slot(Slot.slot2)
            .boardAttribute(
                BoardSetAttributes.of(Attribute.builder()
                    .key("adminStatus")
                    .value("up")
                    .build())
            )
            .clientPort(
                ClientPort.builder()
                    .transmitPort(1)
                    .receivePort(2)
                    .clientPortAttribute(
                        ClientPortConfigAttributes.of("clientIfConfigurationCommand","wan10GbE yes") //One alternative
                    )
                    .clientPortAttribute(
                        ClientPortSetAttributes.of( //Another, more verbose but a little safer?
                            Attributes.of(Attribute.builder()
                                .key("expectedFrequency")
                                .value("w1530")
                                .build()
                            )
                        )
                    )
                    .build())
            .clientPort(
                ClientPort.builder()
                    .transmitPort(5)
                    .receivePort(6)
                    .clientPortAttribute(
                        ClientPortConfigAttributes.of(
                            Attributes.of(Attribute.builder()
                                .key("clientIfConfigurationCommand")
                                .value("wan10GbE yes")
                                .build()
                            )
                        )
                    )
                    .clientPortAttribute(
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
                LinePort.builder()
                    .transmitPort(3)
                    .receivePort(4)
                    .linePortAttribute(
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
                LinePort.builder()
                    .transmitPort(7)
                    .receivePort(8)
                    .linePortAttribute(
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

        final Mdu40EvenL mdu40EvenL = Mdu40EvenL.builder()
            .subrack(Subrack.subrack1)
            .slot(Slot.slot3)
            .boardAttribute(
                BoardSetAttributes.of(Attribute.builder()
                    .key("adminStatus")
                    .value("up")
                    .build())
            )
            .clientPort(
                ClientPort.builder()
                    .transmitPort(41)
                    .receivePort(42)
                    .clientPortAttribute(
                        ClientPortSetAttributes.of(
                            Attributes.of(Attribute.builder()
                                .key("descr")
                                .value("Some relevant description")
                                .build()
                            )
                        )
                    )
                    .build())
            .linePort(
                LinePort.builder()
                    .transmitPort(81)
                    .receivePort(82)
                    .linePortAttribute(
                        LinePortSetAttributes.of(Attributes.of(
                            Attribute.builder()
                                .key("descr")
                                .value("Some relevant description")
                                .build()
                            )
                        )
                    )
                    .build()
            )
            .build();

        final NodeEquipment nodeEquipment = NodeEquipment.builder()
            .board(tpd10gbe)
            .board(mdu40EvenL)
            .build();

        final ObjectMapper mapper = ObjectFromFileUtilJackson.INSTANCE.getMapper();
        log.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(nodeEquipment));
    }
}
