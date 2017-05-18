package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.databind.ObjectReader;
import com.infinera.metro.dnam.acceptance.test.node.mib.Configuration;
import com.infinera.metro.dnam.acceptance.test.util.ResourceInputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class ConfigurationDeserializationTest {

    @Test
    public void test() throws IOException {
        ObjectReader reader = JacksonYamlUtil.INSTANCE.getReader().forType(Configuration.class);
        InputStream in = new ResourceInputStream("configuration/configuration.yaml");
        Configuration configuration = reader.readValue(in);
        log.info(ReflectionToStringBuilder.toString(configuration, ToStringStyle.MULTI_LINE_STYLE));
    }
}
