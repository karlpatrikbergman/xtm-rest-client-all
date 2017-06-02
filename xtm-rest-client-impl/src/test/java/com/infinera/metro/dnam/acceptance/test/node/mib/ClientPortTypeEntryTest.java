package com.infinera.metro.dnam.acceptance.test.node.mib;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Slf4j
public class ClientPortTypeEntryTest {

    private final MibEntry mibEntry = ClientPortEntry.builder()
            .moduleType(ModuleType.CLIENT)
            .groupOrTableType(GroupOrTableType.IF)
            .clientPortType(ClientPortType.CLIENT)
            .subrack(1)
            .slot(2)
            .transmitPort(1)
            .receivePort(2)
            .build();

    @Test(expected=NullPointerException.class)
    public void testClientPortEntryBuilder() {
        ClientPortEntry clientPortEntry = ClientPortEntry.builder()
                .clientPortType(ClientPortType.CLIENT)
                .subrack(1)
                .slot(2)
                .build();
    }

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
}
