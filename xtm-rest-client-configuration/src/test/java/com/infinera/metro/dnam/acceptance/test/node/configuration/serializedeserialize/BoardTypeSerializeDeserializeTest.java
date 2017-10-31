package com.infinera.metro.dnam.acceptance.test.node.configuration.serializedeserialize;

import com.infinera.metro.dnam.acceptance.test.node.mib.type.BoardType;
import com.infinera.metro.dnam.acceptance.test.util.ResourceString;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class BoardTypeSerializeDeserializeTest extends AbstractYamlSerializeDeserializeTest {

    private final BoardType boardType = BoardType.TPD10GBE;

    @Ignore
    @Test
    public void serializeBoardType() throws IOException {
        final String expectedBoardTypeString = new ResourceString("configuration/boardtype.yaml").toString();
        final String actualBoardTypeString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(boardType);
        assertEquals(expectedBoardTypeString, actualBoardTypeString);
    }

    @Test
    public void deserializeBoardType() {
        final BoardType actualBoardType = objectFromFileUtil.getObject("configuration/boardtype.yaml", BoardType.class);
        assertEquals(BoardType.TPD10GBE, actualBoardType);
    }
}
