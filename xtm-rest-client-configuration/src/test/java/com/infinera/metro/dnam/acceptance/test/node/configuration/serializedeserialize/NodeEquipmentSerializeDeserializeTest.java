package com.infinera.metro.dnam.acceptance.test.node.configuration.serializedeserialize;

import com.infinera.metro.dnam.acceptance.test.node.configuration.NodeEquipment;

public class NodeEquipmentSerializeDeserializeTest extends AbstractYamlSerializeDeserializeTest<NodeEquipment> {

    public NodeEquipmentSerializeDeserializeTest() {
        super(NodeEquipment.class, ExpectedTestDataFactory.INSTANCE.getNodeEquipment(), "configuration/node_equipment.yaml");
    }
}
