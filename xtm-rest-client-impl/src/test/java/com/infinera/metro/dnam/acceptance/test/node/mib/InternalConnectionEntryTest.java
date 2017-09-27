package com.infinera.metro.dnam.acceptance.test.node.mib;

import com.infinera.metro.dnam.acceptance.test.node.mib.entry.InternalConnectionEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.MibEntry;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Slf4j
public class InternalConnectionEntryTest {
    private final MibEntry mibEntry = InternalConnectionEntry.builder()
        .fromSubrack(1)
        .fromSlot(2)
        .fromMpoIdentifier(MpoIdentifier.NotPresent())
        .fromPort(1)
        .toSubrack(1)
        .toSlot(19)
        .toMpoIdentifier(MpoIdentifier.NotPresent())
        .toPort(4)
        .build();

    @Test
    public void testMibEntryString() {
        final String expectedMibEntryString = "int:1:2:0:1:1:19:0:4";
        final String actualMibEntryString = mibEntry.getMibEntryString();
        assertEquals(expectedMibEntryString, actualMibEntryString);
        log.info(actualMibEntryString);
    }

    @Test
    public void testMibEntryPath() {
        final String expectedMibEntryPath = "/mib/topo/internal/int:1:2:0:1:1:19:0:4";
        final String actualMibEntryPath = mibEntry.getMibEntryPath();
        assertEquals(expectedMibEntryPath, actualMibEntryPath);
        log.info(actualMibEntryPath);
    }

    @Test
    public void testInternalConnectionEntryReverse() {
        InternalConnectionEntry mibEntryReversed = ((InternalConnectionEntry) mibEntry).reverse();
        final String expectedMibEntryString = "int:1:19:0:3:1:2:0:2";
        final String actualMibEntryString = mibEntryReversed.getMibEntryString();
        assertEquals(expectedMibEntryString, actualMibEntryString);
        log.info(actualMibEntryString);
    }
}
