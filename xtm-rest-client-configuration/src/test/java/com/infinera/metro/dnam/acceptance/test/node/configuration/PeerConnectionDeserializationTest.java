package com.infinera.metro.dnam.acceptance.test.node.configuration;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

@Slf4j
public class PeerConnectionDeserializationTest {
    private final String PATH = "configuration/peer_connection.yaml";

    @Test
    public void test() {
        ObjectFromFileUtil objectFromFileUtil = ObjectFromFileUtilJackson.INSTANCE;
        PeerConnection peerConnection = objectFromFileUtil.getObject(PATH, PeerConnection.class);
        assertNotNull(peerConnection);
//
//        assertTrue(peerConnection.getBoard(Slot.slot2) instanceof Tpd10gbe);
//        Tpd10gbe tpd10gbe = (Tpd10gbe) peerConnection.getBoard(Slot.slot2);
//
//        List<Port> ports = tpd10gbe.getClientPorts();
//        assertTrue(tpd10gbe.getClientPorts().size() == 2);

        log.info(peerConnection.toString());

    }
}
