package com.infinera.metro.dnam.acceptance.test.mib;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MtoIdentifierTest {

    @Test
    public void createMtoIdentifierXtmVersionEqualOrHigherThan27() {
        MtoIdentifier mtoIdentifier = new MtoIdentifier(0);
        assertEquals(":0", mtoIdentifier.getMtoIdentifier());
    }

    @Test
    public void createMtoIdentifierXtmVersionEqualLowerThan27() {
        MtoIdentifier mtoIdentifier = MtoIdentifier.createbelowXtmVersion27();
        assertEquals("", mtoIdentifier.getMtoIdentifier());
    }
}
