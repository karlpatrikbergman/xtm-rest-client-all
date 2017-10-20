package com.infinera.metro.dnam.acceptance.test.node.configuration.deserialize;

import com.infinera.metro.dnam.acceptance.test.node.mib.entry.LinePortEntry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

import java.io.IOException;

@Slf4j
public class LinePortTypeEntryDeserializationTest extends YamlDeserializationTest {

    @Test
    public void test() throws IOException {
        final LinePortEntry linePortEntry = objectFromFileUtil.getObject("configuration/line_port_entry.yaml", LinePortEntry.class);
        log.info(ReflectionToStringBuilder.toString(linePortEntry, ToStringStyle.MULTI_LINE_STYLE));
    }
}
