package com.infinera.metro.dnam.acceptance.test.node.configuration;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Slf4j
public class NodeEquipmentDeserializationTest {
    private final String PATH = "configuration/nodeequipment.yml";

    @Test
    public void test() {
        ObjectFromFileUtil objectFromFileUtil = ObjectFromFileUtilJackson.INSTANCE;
        NodeEquipment nodeEquipment = objectFromFileUtil.getObject(PATH, NodeEquipment.class);
        assertNotNull(nodeEquipment);

        assertTrue(nodeEquipment.getBoard(Slot.slot2) instanceof Tpd10gbe);
        Tpd10gbe tpd10gbe = (Tpd10gbe) nodeEquipment.getBoard(Slot.slot2);

        List<Port> ports = tpd10gbe.getClientPorts();
        assertTrue(tpd10gbe.getClientPorts().size() == 2);

        log.info(nodeEquipment.toString());

    }
}
