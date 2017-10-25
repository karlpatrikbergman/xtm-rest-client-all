package com.infinera.metro.dnam.acceptance.test.node.configuration.topology;

import com.infinera.metro.dnam.acceptance.test.node.DontLetGradleRun;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.NodeImpl;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Subrack;
import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.board.BoardSetAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.client.ClientPortConfigAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.client.ClientPortSetAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.line.LinePortSetAttributes;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Tpd10gbe;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.ClientPort;
import com.infinera.metro.dnam.acceptance.test.node.configuration.port.LinePort;
import com.infinera.metro.dnam.acceptance.test.node.mib.MpoIdentifier;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * /topo/peer/peer:1:2:0:3/create.json?_RFLAGS_=RASEMGNOT&_AFLAGS_=AVNP /topo/peer/peer:1:2:0:3/set.json?localLabel=peer:1:2:3&_RFLAGS_=SEMGNITB&_AFLAGS_=AVRS
 * /topo/peer/peer:1:2:0:3/set.json?remoteLabel=peer:1:2:4&_RFLAGS_=SEMGNITB&_AFLAGS_=AVRS
 * /topo/peer/peer:1:2:0:3/set.json?remoteIp=172.17.0.3&_RFLAGS_=SEMGNITB&_AFLAGS_=AVRS
 */

@Category(DontLetGradleRun.class)
public class Workbench {

    @Ignore
    @Test
    public void foo() {

        Node nodeA = NodeImpl.createDefault("172.17.0.2");

        LinePort linePortTx3Rx4 = LinePort.builder()
            .transmitPort(3)
            .receivePort(4)
            .linePortAttribute(LinePortSetAttributes.of("expectedFrequency", "ch939"))
            .build();

        final Tpd10gbe tpd10gbe = Tpd10gbe.builder()
            .subrack(Subrack.subrack1)
            .slot(Slot.slot2)
            .boardAttribute(BoardSetAttributes.of("adminStatus", "up")) //Admin status could be set to up by default?
            .clientPort(
                ClientPort.builder()
                    .transmitPort(1)
                    .receivePort(2)
                    .clientPortAttribute(ClientPortSetAttributes.of("clientIfExpectedTxFrequency", "w1530"))
                    .clientPortAttribute(ClientPortConfigAttributes.of("clientIfConfigurationCommand", "wan10GbE yes"))
                    .build()
            )
            .linePort(linePortTx3Rx4)
            .build();

        PeerConnection peerConnection = PeerConnection.builder()
            .localPortEntry(tpd10gbe.getLinePortEntry(linePortTx3Rx4))
            .localMpoIdentifier(MpoIdentifier.NotPresent())
            .remotePortEntry(tpd10gbe.getLinePortEntry(linePortTx3Rx4))
            .remoteMpoIdentifier(MpoIdentifier.NotPresent())
            .build();
        peerConnection.applyTo(null, null);

    }
}
