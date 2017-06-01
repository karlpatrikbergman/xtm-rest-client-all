package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.infinera.metro.dnam.acceptance.test.node.mib.BoardEntry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

@Slf4j
public class BoardTypeEntryDeserializationTest {
    private final String PATH = "configuration/board_entry.yaml";
    private final ObjectFromFileUtil objectFromFileUtil = ObjectFromFileUtilFactory.getObjectFromFileUtil();

    @Test
    public void test() throws IOException {
        final BoardEntry boardEntry = objectFromFileUtil.getObject(PATH, BoardEntry.class);
        assertNotNull(boardEntry);
        log.info(ReflectionToStringBuilder.toString(boardEntry, ToStringStyle.MULTI_LINE_STYLE));
    }
}