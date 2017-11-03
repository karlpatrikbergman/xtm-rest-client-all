package com.infinera.metro.dnam.acceptance.test.node.configuration.serializedeserialize;

import com.infinera.metro.dnam.acceptance.test.node.mib.Attribute;

public class AttributeSerializeDeserializeTest extends AbstractYamlSerializeDeserializeTest<Attribute> {

    public AttributeSerializeDeserializeTest() {
        super(Attribute.class, Attribute.of("clientIfConfigurationCommand", "lan10GbE yes"), "configuration/attribute.yaml");
    }

}

