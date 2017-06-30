package com.infinera.metro.dnam.acceptance.test.node.mib;

import com.infinera.metro.dnam.acceptance.test.node.mib.entry.BoardEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.MibEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.BoardType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class BoardEntryTest {

    private final MibEntry mibEntry = BoardEntry.builder()
            .boardType(BoardType.TPD10GBE)
            .subrack(1)
            .slot(2)
            .build();

    @Test
    public void testMibEntryPath() {
        final String expectedMibEntryPath = "/mib/eq/board/tpd10gbe:1:2";
        final String actualMibEntryPath = mibEntry.getMibEntryPath();
        Assert.assertEquals(expectedMibEntryPath, actualMibEntryPath);
        log.info(actualMibEntryPath);
    }

    @Test
    public void testMibEntryString() {
        final String expectedMibEntryString = "tpd10gbe:1:2";
        final String actualMibEntryString = mibEntry.getMibEntryString();
        Assert.assertEquals(expectedMibEntryString, actualMibEntryString);
        log.info(actualMibEntryString);
    }
}
