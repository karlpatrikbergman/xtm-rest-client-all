package com.infinera.metro.dnam.acceptance.test.mib;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Slf4j
public class BoardEntryTest {

    @Test
    public void testMibEntryStringFunctions() {
        final String expectedMibEntryPath = "/mib/eq/board/tpd10gbe:1:2";
        final String expectedMibEntryString = "tpd10gbe:1:2";
        MibEntry mibEntry = BoardEntry.builder()
                .board(Board.TPD10GBE)
                .subrack(1)
                .slot(2)
                .build();

        final String actualMibEntryPath = mibEntry.getMibEntryPath();
        assertEquals(expectedMibEntryPath, actualMibEntryPath);
        log.info(actualMibEntryPath);

        final String actualMibEntryString = mibEntry.getMibEntryString();
        assertEquals(expectedMibEntryString, actualMibEntryString);
        log.info(actualMibEntryString);
    }
}
