package com.infinera.metro.dnam.acceptance.test.xtmrest;

import com.infinera.metro.dnam.acceptance.test.mib.BoardEntry;
import com.infinera.metro.dnam.acceptance.test.mib.MibEntry;
import com.infinera.metro.dnam.acceptance.test.mib.Module;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static com.infinera.metro.dnam.acceptance.test.mib.Board.TP10G;
import static com.infinera.metro.dnam.acceptance.test.mib.Command.GET_JSON;
import static com.infinera.metro.dnam.acceptance.test.mib.GroupOrTable.BOARD;
import static org.junit.Assert.assertEquals;

@Slf4j
public class XtmRestMibUtilTest {
    private final XtmRestMibUtil xtmRestUtil =  XtmRestMibUtil.INSTANCE;

    private final MibEntry mibEntry = BoardEntry.builder()
            .board(TP10G)
            .subrack(1)
            .slot(2)
            .build();

    @Test
    public void createMibPath() {
        String expectedMibPath = "/mib/eq/board/tp10g:1:2/get.json";
        String actualMibPath = xtmRestUtil.mibRestUrl(Module.EQUIPMENT, BOARD, mibEntry, GET_JSON);
        assertEquals(expectedMibPath, actualMibPath);
        log.info("Mib path: {}", actualMibPath);
    }
}
