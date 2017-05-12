package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.databind.ObjectReader;
import com.infinera.metro.dnam.acceptance.test.node.mib.MpoIdentifier;
import com.infinera.metro.dnam.acceptance.test.util.ResourceInputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class MpoIdentifierDeserializationTest {

    @Test
    public void test() throws IOException {
        ObjectReader reader = JacksonYamlUtil.INSTANCE.getReader().forType(MpoIdentifier.class);
        InputStream in = new ResourceInputStream("yaml-files/mpo_identifier.yaml");
        MpoIdentifier mpoIdentifier = reader.readValue(in);
        log.info(ReflectionToStringBuilder.toString(mpoIdentifier, ToStringStyle.MULTI_LINE_STYLE));
    }

}
