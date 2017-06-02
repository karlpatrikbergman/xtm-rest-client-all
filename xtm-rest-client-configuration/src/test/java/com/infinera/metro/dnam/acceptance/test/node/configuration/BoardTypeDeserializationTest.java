package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.infinera.metro.dnam.acceptance.test.node.mib.BoardType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Slf4j
public class BoardTypeDeserializationTest {
    private final String PATH = "configuration/boardtype.yaml";
    private final ObjectFromFileUtil objectFromFileUtil = ObjectFromFileUtilFactory.getObjectFromFileUtil();

    @Test
    public void deserializeBoardType() throws IOException {
        final BoardType boardType = objectFromFileUtil.getObject(PATH, BoardType.class);
        assertNotNull(boardType);
        assertEquals(BoardType.TPD10GBE, boardType);

        log.info(boardType.toString());
    }
}
