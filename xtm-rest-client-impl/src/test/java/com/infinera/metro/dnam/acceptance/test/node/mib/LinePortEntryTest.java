package com.infinera.metro.dnam.acceptance.test.node.mib;

import com.infinera.metro.dnam.acceptance.test.node.mib.entry.LinePortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.MibEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.LinePortType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Slf4j
public class LinePortEntryTest {

    private final MibEntry mibEntry = LinePortEntry.builder()
            .moduleType(ModuleType.WDM)
            .groupOrTableType(GroupOrTableType.IF)
            .linePortType(LinePortType.WDM)
            .subrack(1)
            .slot(2)
            .transmitPort(3)
            .receivePort(4)
            .build();

    @Test
    public void testMibEntryPath() {
        final String expectedMibEntryPath = "/mib/wdm/if/wdm:1:2:3-4";
        final String actualMibEntryPath = mibEntry.getMibEntryPath();
        assertEquals(expectedMibEntryPath, actualMibEntryPath);
        log.info(actualMibEntryPath);
    }

    @Test
    public void testMibEntryString() {
        final String expectedMibEntryString = "wdm:1:2:3-4";
        final String actualMibEntryString = mibEntry.getMibEntryString();
        assertEquals(expectedMibEntryString, actualMibEntryString);
        log.info(actualMibEntryString);
    }

    @Test(expected=NullPointerException.class)
    public void testLinePortEntryBuilder() {
        LinePortEntry.builder()
            .linePortType(LinePortType.WDM)
            .subrack(1)
            .slot(2)
            .build();
    }
}
