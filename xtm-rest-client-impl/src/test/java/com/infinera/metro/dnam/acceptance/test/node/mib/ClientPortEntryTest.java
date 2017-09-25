package com.infinera.metro.dnam.acceptance.test.node.mib;

import com.infinera.metro.dnam.acceptance.test.node.mib.entry.ClientPortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.MibEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ClientPortType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Slf4j
public class ClientPortEntryTest {

    private final MibEntry mibEntry = ClientPortEntry.builder()
            .moduleType(ModuleType.CLIENT)
            .groupOrTableType(GroupOrTableType.IF)
            .clientPortType(ClientPortType.CLIENT)
            .subrack(1)
            .slot(2)
            .transmitPort(1)
            .receivePort(2)
            .build();

    @Test
    public void testMibEntryPath() {
        final String expectedMibEntryPath = "/mib/client/if/client:1:2:1-2";
        final String actualMibEntryPath = mibEntry.getMibEntryPath();
        assertEquals(expectedMibEntryPath, actualMibEntryPath);
        log.info(actualMibEntryPath);
    }

    @Test
    public void testMibEntryString() {
        final String expectedMibEntryString = "client:1:2:1-2";
        final String actualMibEntryString = mibEntry.getMibEntryString();
        assertEquals(expectedMibEntryString, actualMibEntryString);
        log.info(actualMibEntryString);
    }

    @Test(expected=NullPointerException.class)
    public void testClientPortEntryBuilder() {
       ClientPortEntry.builder()
            .clientPortType(ClientPortType.CLIENT)
            .subrack(1)
            .slot(2)
            .build();
    }
}
