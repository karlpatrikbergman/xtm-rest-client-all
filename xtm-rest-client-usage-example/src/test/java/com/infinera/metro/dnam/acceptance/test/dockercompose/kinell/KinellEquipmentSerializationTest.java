package com.infinera.metro.dnam.acceptance.test.dockercompose.kinell;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infinera.metro.dnam.acceptance.test.node.configuration.*;
import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.AddDropPortConfigAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.*;
import com.infinera.metro.dnam.acceptance.test.node.configuration.util.AddDropChannelUtil;
import com.infinera.metro.dnam.acceptance.test.node.mib.Configuration;
import com.infinera.metro.dnam.acceptance.test.node.mib.Configurations;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
public class KinellEquipmentSerializationTest {

    @Test
    public void test() throws IOException, URISyntaxException {

        final List<String> port1_2_addDropChannels = Arrays.asList("ch932", "ch937");
        final List<String> port_3_4_addDropChannels = AddDropChannelUtil.createAddDropChannelStrings(918.5, 958.0);
        port_3_4_addDropChannels.removeAll(port1_2_addDropChannels);
        port_3_4_addDropChannels.removeIf(value -> value.equals("ch933"));

        Roadm1x2G50 roadm1x2G50_1 = Roadm1x2G50.builder()
            .subrack(1)
            .slot(Slot.slot2)
            .boardEntryAttribute(
                BoardSetAttributes.of(
                    Configurations.of(Configuration.builder()
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
                            Configurations.of("addChannel", port1_2_addDropChannels)
                        )
                    )
                    .build()
            )
            .addDropPort(
                Port.builder()
                    .transmitPort(3)
                    .receivePort(4)
                    .portEntryAttributes(
                        Arrays.asList(
                            AddDropPortConfigAttributes.of(
                                Configurations.of("addChannel", port_3_4_addDropChannels)
                            )
                        )
                    )
                    .build()
            )
            .build();

        Oa2x17 oa2x17 = Oa2x17.builder()
            .subrack(1)
            .slot(Slot.slot3)
            .boardEntryAttribute(
                BoardSetAttributes.of(
                    Configurations.of(Configuration.builder()
                        .key("adminStatus")
                        .value("up")
                        .build())
                )
            )
            .ports(Collections.emptyList())
            .build();

        Roadm1x2G50 roadm1x2G50_2 = Roadm1x2G50.builder()
            .subrack(1)
            .slot(Slot.slot4)
            .boardEntryAttribute(
                BoardSetAttributes.of(
                    Configurations.of(Configuration.builder()
                        .key("adminStatus")
                        .value("up")
                        .build())
                )
            )
            .build();

        Ocm2p ocm2p = Ocm2p.builder()
            .subrack(1)
            .slot(Slot.slot5)
            .boardEntryAttribute(
                BoardSetAttributes.of(
                    Configurations.of(Configuration.builder()
                        .key("adminStatus")
                        .value("up")
                        .build())
                )
            )
            .build();
//
//        Tphex10gotn tphex10gotn = Tphex10gotn.builder()
//            .subrack(1)
//            .slot(Slot.slot6)
//            .build();
//
//        Tp100gotn tp100gotn = Tp100gotn.builder()
//            .subrack(1)
//            .slot(Slot.slot7)
//            .build();
//
//        Gbe10Emxp10Gii gbe10Emxp10Gii = Gbe10Emxp10Gii.builder()
//            .subrack(1)
//            .slot(Slot.slot14)
//            .build();
//
        Mdu40EvenL mdu40EvenL = Mdu40EvenL.builder()
            .subrack(1)
            .slot(Slot.slot15)
            .build();

        //Here I get error: "Can't create board, Board to the left has multi-slot with"
//        Oiuc50100 oiuc50100 = Oiuc50100.builder()
//            .subrack(1)
//            .slot(Slot.slot18)
//            .build();

        final Map<Slot, Board> boards = new LinkedHashMap<>();
        boards.put(roadm1x2G50_1.getSlot(), roadm1x2G50_1);
        boards.put(oa2x17.getSlot(), oa2x17);
        boards.put(roadm1x2G50_2.getSlot(), roadm1x2G50_2);
        boards.put(ocm2p.getSlot(), ocm2p);
//        boards.put(tphex10gotn.getSlot(), tphex10gotn);
//        boards.put(tp100gotn.getSlot(), tp100gotn);
//        boards.put(gbe10Emxp10Gii.getSlot(), gbe10Emxp10Gii);
//        boards.put(mdu40EvenL.getSlot(), mdu40EvenL);
//        boards.put(oiuc50100.getSlot(), oiuc50100);

        final NodeEquipment nodeEquipment = NodeEquipment.builder()
            .boards(boards)
            .build();
        final ObjectMapper mapper = ObjectFromFileUtilJackson.INSTANCE.getMapper();
        final String yamlString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(nodeEquipment);

        log.info(yamlString);

        //TODO: Create file util class?
        Path newFilePath = Paths.get("src/test/resources/dockercompose/kinell/node_liljeholmen.yml");
        Files.write(newFilePath, yamlString.getBytes());

    }
}

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
                                ClientPortConfigAttributes.of(
                                    ConfigurationList.of(Configuration.builder()
                                        .key("clientIfConfigurationCommand")
                                        .value("wan10GbE yes")
                                        .build()
                                    )
                                ),
                                ClientPortSetAttributes.of(
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
                                ClientPortConfigAttributes.of(
                                    ConfigurationList.of(Configuration.builder()
                                        .key("clientIfConfigurationCommand")
                                        .value("wan10GbE yes")
                                        .build()
                                    )
                                ),
                                ClientPortSetAttributes.of(
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
                                LinePortSetAttributes.of(ConfigurationList.of(
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
                                LinePortSetAttributes.of(ConfigurationList.of(
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