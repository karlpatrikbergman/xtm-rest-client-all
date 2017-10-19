package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.infinera.metro.dnam.acceptance.test.node.mib.entry.ClientPortEntry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

import java.io.IOException;

@Slf4j
public class ClientPortTypeEntryDeserializationTest extends YamlDeserializationTest {

    @Test
    public void test() throws IOException {
        ClientPortEntry clientPortEntry = objectFromFileUtil.getObject("configuration/client_port_entry.yaml", ClientPortEntry.class);
        log.info(ReflectionToStringBuilder.toString(clientPortEntry, ToStringStyle.MULTI_LINE_STYLE));
    }
}
