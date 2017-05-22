package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.databind.ObjectReader;
import com.infinera.metro.dnam.acceptance.test.node.mib.ClientPortEntry;
import com.infinera.metro.dnam.acceptance.test.util.ResourceInputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class ClientPortTypeEntryDeserializationTest {
    @Test
    public void test() throws IOException {
        ObjectReader reader = JacksonYamlUtil.INSTANCE.getReader().forType(ClientPortEntry.class);
        InputStream in = new ResourceInputStream("configuration/client_port_entry.yaml");
        ClientPortEntry clientPortEntry = reader.readValue(in);
        log.info(ReflectionToStringBuilder.toString(clientPortEntry, ToStringStyle.MULTI_LINE_STYLE));
    }
}
