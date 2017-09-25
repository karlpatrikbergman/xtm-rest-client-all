package com.infinera.metro.dnam.acceptance.test.codeonly;

import com.infinera.metro.dnam.acceptance.test.node.DontLetGradleRun;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.NodeAccessData;
import com.infinera.metro.dnam.acceptance.test.node.configuration.*;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Board;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Tpd10gbe;
import com.infinera.metro.dnam.acceptance.test.node.mib.Configuration;
import com.infinera.metro.dnam.acceptance.test.node.mib.MpoIdentifier;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


//TODO: Add docker docker-compose-rule variant, and do node.getPeer to verify test

@Category(DontLetGradleRun.class)
@Slf4j
public class CodeOnlyPeerConnectionTest {

//    @ClassRule
//    public static DockerComposeRule docker = DockerComposeRule.builder()
//            .file("src/test/resources/dockercompose/three-nodes-example-docker-compose-rule/docker-compose.yml")
//            .waitingForService("nodeA", HealthChecks.toHaveAllPortsOpen())
//            .waitingForService("nodeZ", HealthChecks.toHaveAllPortsOpen())
//            .build();

    private final String ipAddressNodeA = "172.17.0.2";
    private final String ipAddressNodeZ = "172.17.0.3";

//    private final String ipAddressNodeA = "172.45.0.101";
//    private final String ipAddressNodeZ = "172.45.0.101";

    private final Node nodeA = Node.defaultImplementation(
        NodeAccessData.builder()
            .ipAddress(ipAddressNodeA)
            .port(80)
            .userName("root")
            .password("root")
            .build()
    );

    private final Node nodeZ = Node.defaultImplementation(
        NodeAccessData.builder()
            .ipAddress(ipAddressNodeZ)
            .port(80)
            .userName("root")
            .password("root")
            .build()
    );

    private final Port linePort = Port.builder()
        .transmitPort(3)
        .receivePort(4)
        .portEntryAttribute( //Remember that a port entry can have both setAttributes and configureAttributes
            LinePortSetAttributes.of(
                Configuration.builder()
                    .key("expectedFrequency")
                    .value("ch926")
                    .build()
            )
        )
        .build();

    @Test
    public void test() throws IOException {
//        String foo = System.getProperty("foo");
//        log.info("foo {}", foo.toString());
//        String bar = System.getProperty("bar");
//        log.info("bar {}", bar.toString());

        Tpd10gbe tpd10gbe = Tpd10gbe.builder()
            .subrack(1)
            .slot(Slot.slot2)
            .boardEntryAttribute(
                BoardSetAttributes.of(Configuration.builder()
                    .key("adminStatus")
                    .value("up")
                    .build())
            )
            .linePort(linePort)
            .build();
        Map<Slot, Board> boards = new HashMap<>();
        boards.put(tpd10gbe.getSlot(), tpd10gbe);

        NodeEquipment nodeEquipment = NodeEquipment.builder()
            .boards(boards)
            .build();
        nodeEquipment.applyTo(nodeA);
        nodeEquipment.applyTo(nodeZ);

        final PeerConnection peerConnectionAtoZ = PeerConnection.builder()
            .localNodeIpAddress(ipAddressNodeA)
            .localPortEntry(tpd10gbe.getLinePortEntry(linePort))
            .localMpoIdentifier(MpoIdentifier.NotPresent())
            .remoteNodeIpAddress(ipAddressNodeZ)
            .remotePortEntry(tpd10gbe.getLinePortEntry(linePort))
            .remoteMpoIdentifier(MpoIdentifier.NotPresent())
            .build();
        peerConnectionAtoZ.applyTo(nodeA, nodeZ);
    }
}
