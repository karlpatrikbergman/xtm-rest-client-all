package com.infinera.metro.dnam.acceptance.test.node.mib;

import com.infinera.metro.dnam.acceptance.test.node.mib.entry.MibEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.PeerEntry;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Slf4j
public class PeerEntryTest {

    private final MibEntry mibEntry = PeerEntry.builder()
            .subrack(1)
            .slot(2)
            .port(3)
            .mpoIdentifier(MpoIdentifier.NotPresent())
            .build();

    @Test
    public void testMibEntryPath() {
        final String expectedMibEntryPath = "/mib/topo/peer/peer:1:2:0:3";
        final String actualMibEntryPath = mibEntry.getMibEntryPath();
        assertEquals(expectedMibEntryPath, actualMibEntryPath);
        log.info(actualMibEntryPath);
    }

    @Test
    public void testMibEntryString() {
        final String expectedMibEntryString = "peer:1:2:0:3";
        final String actualMibEntryString = mibEntry.getMibEntryString();
        assertEquals(expectedMibEntryString, actualMibEntryString);
        log.info(actualMibEntryString);
    }

    @Test
    public void testPeerEntryLocalLabel() {
        PeerEntry peerEntry = (PeerEntry) mibEntry;
        final String expectedLocalLabel = "1:2:3";
        final String actualLocalLabel = peerEntry.getLocalLabel();
        assertEquals(expectedLocalLabel, actualLocalLabel);
        log.info(actualLocalLabel);
    }

    @Test(expected=NullPointerException.class)
    public void testLinePortEntryBuilder() {
        PeerEntry.builder()
            .subrack(1)
            .slot(null)
            .build();
    }
}
