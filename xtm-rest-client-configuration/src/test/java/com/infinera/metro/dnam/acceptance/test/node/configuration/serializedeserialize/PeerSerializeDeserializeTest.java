package com.infinera.metro.dnam.acceptance.test.node.configuration.serializedeserialize;

import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Subrack;
import com.infinera.metro.dnam.acceptance.test.node.configuration.topology.Peer;
import com.infinera.metro.dnam.acceptance.test.node.mib.MpoIdentifier;
import com.infinera.metro.dnam.acceptance.test.util.ResourceString;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class PeerSerializeDeserializeTest extends AbstractYamlSerializeDeserializeTest {

    private final Peer peer = Peer.builder()
        .subrack(Subrack.subrack1)
        .slot(Slot.slot2)
        .mpoIdentifier(MpoIdentifier.NotPresent())
        .port(3)
        .build();

    @Test
    public void serializePeer() throws IOException {
        final String expectedPeerString = new ResourceString("configuration/peer.yaml").toString();
        final String actualPeerString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(peer);
        assertEquals(expectedPeerString, actualPeerString);
    }

    @Test
    public void deserializePeer() {
        final Peer actualPeer = objectFromFileUtil.getObject("configuration/peer.yaml", Peer.class);
        assertEquals(peer, actualPeer);
    }
}
