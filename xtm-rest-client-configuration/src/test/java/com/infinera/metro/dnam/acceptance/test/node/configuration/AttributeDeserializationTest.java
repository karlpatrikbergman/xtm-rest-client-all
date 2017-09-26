package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.infinera.metro.dnam.acceptance.test.node.mib.Attribute;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@Slf4j
public class AttributeDeserializationTest {

    @Test
    public void test() throws IOException {
        ObjectFromFileUtil objectFromFileUtil = ObjectFromFileUtilFactory.getObjectFromFileUtil();
        Attribute attribute = objectFromFileUtil.getObject("configuration/attribute.yaml", Attribute.class);
        assertEquals("clientIfConfigurationCommand", attribute.getKey());
        assertEquals("lan10GbE yes", attribute.getValue());

        log.info(ReflectionToStringBuilder.toString(attribute, ToStringStyle.MULTI_LINE_STYLE));
    }
}

