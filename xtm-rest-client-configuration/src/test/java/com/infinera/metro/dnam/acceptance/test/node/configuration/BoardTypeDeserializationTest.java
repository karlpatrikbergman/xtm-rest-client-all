package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.infinera.metro.dnam.acceptance.test.node.mib.type.BoardType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Slf4j
public class BoardTypeDeserializationTest extends YamlDeserializationTest {

    @Test
    public void deserializeBoardType() throws IOException {
        final BoardType boardType = objectFromFileUtil.getObject("configuration/boardtype.yaml", BoardType.class);
        assertNotNull(boardType);
        assertEquals(BoardType.TPD10GBE, boardType);

        log.info(boardType.toString());
    }
}
