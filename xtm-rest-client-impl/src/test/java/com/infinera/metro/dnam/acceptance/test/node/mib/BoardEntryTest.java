package com.infinera.metro.dnam.acceptance.test.node.mib;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

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
        Assert.assertEquals(expectedMibEntryPath, actualMibEntryPath);
        log.info(actualMibEntryPath);

        final String actualMibEntryString = mibEntry.getMibEntryString();
        Assert.assertEquals(expectedMibEntryString, actualMibEntryString);
        log.info(actualMibEntryString);
    }
}
