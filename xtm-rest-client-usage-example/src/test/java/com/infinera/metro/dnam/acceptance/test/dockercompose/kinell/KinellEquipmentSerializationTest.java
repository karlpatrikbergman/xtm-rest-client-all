package com.infinera.metro.dnam.acceptance.test.dockercompose.kinell;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infinera.metro.dnam.acceptance.test.node.configuration.*;
import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.AddDropPortConfigAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.*;
import com.infinera.metro.dnam.acceptance.test.node.configuration.deserialize.ObjectFromFileUtilJackson;
import com.infinera.metro.dnam.acceptance.test.node.configuration.util.AddDropChannelUtil;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attribute;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attributes;
import com.infinera.metro.dnam.acceptance.test.node.mib.MpoIdentifier;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class KinellEquipmentSerializationTest {

    @Test
    public void test() throws IOException, URISyntaxException {

        final List<String> port1_2_addDropChannels = Arrays.asList("ch932", "ch937");
        final List<String> port_3_4_addDropChannels = AddDropChannelUtil.createAddDropChannelStrings(918.5, 958.0);
        port_3_4_addDropChannels.removeAll(port1_2_addDropChannels);
        port_3_4_addDropChannels.removeIf(value -> value.equals("ch933"));

        final NodeEquipment nodeEquipment = NodeEquipment.builder()
            .board(Slot.slot2,
                Roadm1x2G50.builder()
                    .subrack(1)
                    .slot(Slot.slot2)
                    .boardEntryAttribute(
                        BoardSetAttributes.of(
                            Attributes.of(Attribute.builder()
                                .key("adminStatus")
                                .value("up")
                                .build())
                        )
                    )
                    .addDropPort(
                        Port.builder()
                            .transmitPort(1)
                            .receivePort(2)
                            .portEntryAttribute(
                                AddDropPortConfigAttributes.of(
                                    Attributes.of("addChannel", port1_2_addDropChannels)
                                )
                            )
                            .build()
                    )
                    .addDropPort(
                        Port.builder()
                            .transmitPort(3)
                            .receivePort(4)
                            .portEntryAttribute(
                                AddDropPortConfigAttributes.of(
                                    Attributes.of("addChannel", port_3_4_addDropChannels)
                                )
                            )
                            .build()
                    )
                    .build()
            )
            .board(Slot.slot3,
                Oa2x17.builder()
                    .subrack(1)
                    .slot(Slot.slot3)
                    .boardEntryAttribute(
                        BoardSetAttributes.of(
                            Attributes.of(Attribute.builder()
                                .key("adminStatus")
                                .value("up")
                                .build())
                        )
                    )
                    .ports(Collections.emptyList())
                    .build()
            )
            .board(Slot.slot4,
                Roadm1x2G50.builder()
                    .subrack(1)
                    .slot(Slot.slot4)
                    .boardEntryAttribute(
                        BoardSetAttributes.of(
                            Attributes.of(Attribute.builder()
                                .key("adminStatus")
                                .value("up")
                                .build())
                        )
                    )
                    .build()
            )
            .board(Slot.slot5,
                Ocm2p.builder()
                    .subrack(1)
                    .slot(Slot.slot5)
                    .boardEntryAttribute(
                        BoardSetAttributes.of(
                            Attributes.of(Attribute.builder()
                                .key("adminStatus")
                                .value("up")
                                .build())
                        )
                    )
                    .build()
            )
            .board(Slot.slot15,
                Ad4Even50.builder()
                    .subrack(1)
                    .slot(Slot.slot15)
                    .boardEntryAttribute(
                        BoardSetAttributes.of(
                            Attributes.of(Attribute.builder()
                                .key("adminStatus")
                                .value("up")
                                .build())
                        )
                    )
                    .build())
            .board(Slot.slot16,
                Oiuc50100.builder()
                    .subrack(1)
                    .slot(Slot.slot16)
                    .build()
            )
            .board(Slot.slot18,
                Ocu2.builder()
                    .subrack(1)
                    .slot(Slot.slot18)
                    .build()
            )
            .board(Slot.slot19,
                Oiuc50100.builder()
                    .subrack(1)
                    .slot(Slot.slot19)
                    .build()
            )
            .board(Slot.slot20,
                Ad4Even50.builder()
                    .subrack(1)
                    .slot(Slot.slot20)
                    .boardEntryAttribute(
                        BoardSetAttributes.of(
                            Attributes.of(Attribute.builder()
                                .key("adminStatus")
                                .value("up")
                                .build())
                        )
                    )
                    .build())
            .build();

        //To apply directly
//        nodeEquipment.applyTo(node);

        //Get yaml string from node equipment object
        final ObjectMapper mapper = ObjectFromFileUtilJackson.INSTANCE.getMapper();
        final String yamlString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(nodeEquipment);
        log.info(yamlString);

        //Create yaml configuration file out of yaml string
        //TODO: Create file util class?
        Path newFilePath = Paths.get("src/test/resources/dockercompose/kinell/node_liljeholmen.yml");
        Files.write(newFilePath, yamlString.getBytes());

        Roadm1x2G50 roadmFrom = (Roadm1x2G50) nodeEquipment.getBoard(Slot.slot2);
        Roadm1x2G50 roadmTo = (Roadm1x2G50) nodeEquipment.getBoard(Slot.slot4);


        InternalConnection internalConnection = InternalConnection.builder()
            .fromPort(
                roadmFrom.getAddDropPortEntry(
                    Port.builder()
                        .transmitPort(3)
                        .receivePort(4)
                        .build()
                )
            )
            .fromMpoIdentifier(MpoIdentifier.NotPresent())
            .toPort(
                roadmTo.getAddDropPortEntry(
                    Port.builder()
                        .transmitPort(3)
                        .receivePort(4)
                        .build()
                )
            )
            .toMpoIdentifier(MpoIdentifier.NotPresent())
            .build();

        final String internalConnectionString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(internalConnection);
        log.info(internalConnectionString);

    }
}