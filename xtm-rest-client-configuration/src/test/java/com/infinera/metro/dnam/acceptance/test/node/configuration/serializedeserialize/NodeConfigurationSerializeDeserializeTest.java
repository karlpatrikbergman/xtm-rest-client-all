package com.infinera.metro.dnam.acceptance.test.node.configuration.serializedeserialize;

import com.infinera.metro.dnam.acceptance.test.node.configuration.NodeConfiguration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NodeConfigurationSerializeDeserializeTest extends AbstractYamlSerializeDeserializeTest<NodeConfiguration>{

    public NodeConfigurationSerializeDeserializeTest() {
        super(NodeConfiguration.class, ExpectedTestDataFactory.INSTANCE.getNodeConfiguration(), "configuration/node_network.yaml");
    }
}
