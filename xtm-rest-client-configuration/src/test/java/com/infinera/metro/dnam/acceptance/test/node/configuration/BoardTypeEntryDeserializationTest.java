package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.infinera.metro.dnam.acceptance.test.node.mib.entry.BoardEntry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

@Slf4j
public class BoardTypeEntryDeserializationTest {
    private final ObjectFromFileUtil objectFromFileUtil = ObjectFromFileUtilFactory.getObjectFromFileUtil();

    @Test
    public void test() throws IOException {
        final BoardEntry boardEntry = objectFromFileUtil.getObject("configuration/board_entry.yaml", BoardEntry.class);
        assertNotNull(boardEntry);
        log.info(ReflectionToStringBuilder.toString(boardEntry, ToStringStyle.MULTI_LINE_STYLE));
    }
}
