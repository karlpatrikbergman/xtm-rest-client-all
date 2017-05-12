package com.infinera.metro.dnam.acceptance.test.node.mib;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MpoIdentifierTest {

    @Test
    public void createMtoIdentifierXtmVersionEqualOrHigherThan27() {
        MpoIdentifier mpoIdentifier = new MpoIdentifier(0);
        assertEquals(":0", mpoIdentifier.getMtoIdentifier());
    }

    @Test
    public void createMtoIdentifierXtmVersionEqualLowerThan27() {
        MpoIdentifier mpoIdentifier = MpoIdentifier.createBelowXtmVersion27();
        assertEquals("", mpoIdentifier.getMtoIdentifier());
    }
}
