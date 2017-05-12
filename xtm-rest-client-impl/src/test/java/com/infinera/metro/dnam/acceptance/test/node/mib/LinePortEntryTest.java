package com.infinera.metro.dnam.acceptance.test.node.mib;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Slf4j
public class LinePortEntryTest {

    @Test(expected=NullPointerException.class)
    public void testLinePortEntryBuilder() {
        LinePortEntry linePortEntry = LinePortEntry.builder()
                .linePort(LinePort.WDM)
                .subrack(1)
                .slot(2)
                .build();
    }

    @Test
    public void testMibEntryStringFunctions() {
        final String expectedMibEntryPath = "/mib/wdm/if/wdm:1:2:3-4";
        final String expectedMibEntryString = "wdm:1:2:3-4";
        MibEntry mibEntry = LinePortEntry.builder()
                .linePort(LinePort.WDM)
                .subrack(1)
                .slot(2)
                .transmitPort(3)
                .receivePort(4)
                .build();

        final String actualMibEntryPath = mibEntry.getMibEntryPath();
        assertEquals(expectedMibEntryPath, actualMibEntryPath);
        log.info(actualMibEntryPath);

        final String actualMibEntryString = mibEntry.getMibEntryString();
        assertEquals(expectedMibEntryString, actualMibEntryString);
        log.info(actualMibEntryString);
    }
}
