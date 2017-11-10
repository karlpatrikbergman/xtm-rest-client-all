package com.infinera.metro.dnam.acceptance.test.node.configuration;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PeersTest {

    @Test
    public void isValidPeersConstructorStringArgument() {
        assertTrue(Peers.isValidPeersConstructorStringArgument("nodeA_nodeZ"));
        assertTrue(Peers.isValidPeersConstructorStringArgument("node1_node2"));
        assertFalse(Peers.isValidPeersConstructorStringArgument("node"));
        assertFalse(Peers.isValidPeersConstructorStringArgument("_nodeA"));
        assertFalse(Peers.isValidPeersConstructorStringArgument("nodeZ_"));
        assertFalse(Peers.isValidPeersConstructorStringArgument("nodeA_nodeZ_nodeX"));
    }

    @Test
    public void constructPeersFromString() {
        final String peersConstructorArgumentString = "nodeA_nodeZ";
        Peers peers = new Peers(peersConstructorArgumentString);
        assertEquals("nodeA", peers.getFromNode());
        assertEquals("nodeZ", peers.getToNode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructPeersFromIllegalString() {
        final String peersConstructorArgumentString = "nodeA_nodeZ_";
        new Peers(peersConstructorArgumentString);
    }

}
