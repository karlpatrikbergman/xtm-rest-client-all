package com.infinera.metro.dnam.acceptance.test.node.configuration.serializedeserialize;

import com.infinera.metro.dnam.acceptance.test.node.configuration.NodeNetworkApplier;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NodeNetworkApplierSerializeDeserializeTest extends AbstractYamlSerializeDeserializeTest<NodeNetworkApplier> {

    public NodeNetworkApplierSerializeDeserializeTest() {
        super(NodeNetworkApplier.class, ExpectedDataFactory.INSTANCE.getNodeNetworkApplier(), "configuration/node_network.yaml");
    }

}
