package com.infinera.metro.dnam.acceptance.test.node.configuration.serializedeserialize;

import com.infinera.metro.dnam.acceptance.test.node.configuration.NodeNetwork;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NodeNetworkSerializeDeserializeTest extends AbstractYamlSerializeDeserializeTest<NodeNetwork>{

    public NodeNetworkSerializeDeserializeTest() {
        super(NodeNetwork.class, ExpectedTestDataFactory.INSTANCE.getNodeNetwork(), "configuration/node_network.yaml");
    }
}
