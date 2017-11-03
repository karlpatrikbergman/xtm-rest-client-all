package com.infinera.metro.dnam.acceptance.test.node.configuration.serializedeserialize;

import com.infinera.metro.dnam.acceptance.test.node.configuration.NodeEquipmentApplier;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NodeEquipmentApplierSerializeDeserializeTest extends AbstractYamlSerializeDeserializeTest<NodeEquipmentApplier> {

    public NodeEquipmentApplierSerializeDeserializeTest() {
        super(NodeEquipmentApplier.class, ExpectedDataFactory.INSTANCE.getNodeEquipmentApplier(), "configuration/node_equipment_applier.yaml");
    }

}
