package com.infinera.metro.dnam.acceptance.test.node.configuration.serializedeserialize;

import com.fasterxml.jackson.databind.ObjectMapper;

abstract class AbstractYamlSerializeDeserializeTest {
    final protected ObjectFromFileUtil objectFromFileUtil = ObjectFromFileUtilFactory.INSTANCE.getObjectFromFileUtil();
    final protected ObjectMapper mapper = ObjectFromFileUtilJackson.INSTANCE.getMapper();
}
