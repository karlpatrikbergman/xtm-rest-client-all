package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.infinera.metro.dnam.acceptance.test.node.mib.entry.LinePortEntry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

import java.io.IOException;

@Slf4j
public class LinePortTypeEntryDeserializationTest {

    public static final String PATH = "configuration/line_port_entry.yaml";

    @Test
    public void test() throws IOException {
        final ObjectFromFileUtil objectFromFileUtil = ObjectFromFileUtilFactory.getObjectFromFileUtil();
        final LinePortEntry linePortEntry = objectFromFileUtil.getObject(PATH, LinePortEntry.class);

//        JacksonYamlUtil jacksonYamlUtil = JacksonYamlUtil.INSTANCE;
//        LinePortEntry linePortEntry = jacksonYamlUtil.getReader().forType(LinePortEntry.class).readValue(new ResourceInputStream(PATH));

        log.info(ReflectionToStringBuilder.toString(linePortEntry, ToStringStyle.MULTI_LINE_STYLE));
    }
}
