package com.infinera.metro.dnam.acceptance.test.node.configuration.serializedeserialize;

import com.infinera.metro.dnam.acceptance.test.node.mib.Attribute;
import com.infinera.metro.dnam.acceptance.test.util.ResourceString;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class AttributeSerializeDeserializeTest extends AbstractYamlSerializeDeserializeTest {

    private final Attribute attribute = Attribute.of("clientIfConfigurationCommand", "lan10GbE yes");

    @Test
    public void serializeAttribute() throws IOException {
        final String expectedAttributeString = new ResourceString("configuration/attribute.yaml").toString();
        final String actualAttributeString = mapper.writeValueAsString(attribute).trim();
        assertEquals(expectedAttributeString, actualAttributeString);
    }

    @Test
    public void deserializeAttribute() {
        Attribute actualAttribute = objectFromFileUtil.getObject("configuration/attribute.yaml", Attribute.class);
        assertEquals(attribute, actualAttribute);
    }
}

