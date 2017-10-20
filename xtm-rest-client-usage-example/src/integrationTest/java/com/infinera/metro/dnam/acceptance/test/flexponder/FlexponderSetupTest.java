package com.infinera.metro.dnam.acceptance.test.flexponder;

import com.infinera.metro.dnam.acceptance.test.node.DontLetGradleRun;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.NodeImpl;
import com.infinera.metro.dnam.acceptance.test.node.configuration.*;
import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.BoardSetAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Board;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Fxp400gotn;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.Port;
import com.infinera.metro.dnam.acceptance.test.node.configuration.topology.PeerConnection;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attribute;
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
    private final Node nodeA = NodeImpl.createDefault(ipAddressNodeA);
    private final Node nodeZ = NodeImpl.createDefault(ipAddressNodeZ);
    private final Port linePort = Port.builder()
        .transmitPort(3)
        .receivePort(4)
        .build();

    @Test
    public void test() throws IOException {
        Fxp400gotn fxp400gotn = Fxp400gotn.builder()
            .subrack(1)
            .slot(Slot.slot2)
            .boardAttribute(
                BoardSetAttributes.of(Attribute.builder()
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
