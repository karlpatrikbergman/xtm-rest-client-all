package com.infinera.metro.dnam.acceptance.test.node.configuration;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class NodeEquipmentDeserializationTest {
    private final String PATH = "configuration/nodeequipment2.yml";

    @Test
    public void test2() {
        ObjectFromFileUtil objectFromFileUtil = ObjectFromFileUtilJackson.INSTANCE;
        NodeEquipment nodeEquipment = objectFromFileUtil.getObject(PATH, NodeEquipment.class);
        log.info(nodeEquipment.toString());
    }
}
