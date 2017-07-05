package com.infinera.metro.dnam.acceptance.test.flexponder;

import com.infinera.metro.dnam.acceptance.test.node.DontLetGradleRun;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.NodeAccessData;
import com.infinera.metro.dnam.acceptance.test.node.configuration.*;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Board;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Fxp400gotn;
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
public class FlexponderSetupTest {
    private final String ipAddressNodeA = "172.17.0.2";
    private final String ipAddressNodeZ = "172.17.0.3";
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
        .build();

    @Test
    public void test() throws IOException {
        Fxp400gotn fxp400gotn = Fxp400gotn.builder()
            .subrack(1)
            .slot(Slot.slot2)
            .boardEntryAttribute(
                BoardSetAttributes.of(Configuration.builder()
                    .key("adminStatus")
                    .value("up")
                    .build())
            )
            .build();
        Map<Slot, Board> boards = new HashMap<>();
        boards.put(fxp400gotn.getSlot(), fxp400gotn);

        NodeEquipment nodeEquipment = NodeEquipment.builder()
            .boards(boards)
            .build();
        nodeEquipment.applyTo(nodeA);
        nodeEquipment.applyTo(nodeZ);

        final PeerConnection peerConnectionAtoZ = PeerConnection.builder()
            .localNodeIpAddress(ipAddressNodeA)
            .localPortEntry(fxp400gotn.getLinePortEntry(linePort))
            .localMpoIdentifier(MpoIdentifier.NotPresent())
            .remoteNodeIpAddress(ipAddressNodeZ)
            .remotePortEntry(fxp400gotn.getLinePortEntry(linePort))
            .remoteMpoIdentifier(MpoIdentifier.NotPresent())
            .build();
        peerConnectionAtoZ.applyTo(nodeA, nodeZ);
    }
}
