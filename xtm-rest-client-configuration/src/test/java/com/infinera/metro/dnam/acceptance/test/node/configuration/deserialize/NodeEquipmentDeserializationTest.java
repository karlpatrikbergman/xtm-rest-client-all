package com.infinera.metro.dnam.acceptance.test.node.configuration.deserialize;

import com.infinera.metro.dnam.acceptance.test.node.configuration.NodeEquipment;
import com.infinera.metro.dnam.acceptance.test.node.configuration.Slot;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Tpd10gbe;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Slf4j
public class NodeEquipmentDeserializationTest extends YamlDeserializationTest {

    @Test
    public void test() {
        NodeEquipment nodeEquipment = objectFromFileUtil.getObject("configuration/node_equipment.yml", NodeEquipment.class);
        assertNotNull(nodeEquipment);

        assertTrue(nodeEquipment.getBoard(Slot.slot2) instanceof Tpd10gbe);
        Tpd10gbe tpd10gbe = (Tpd10gbe) nodeEquipment.getBoard(Slot.slot2);

        assertTrue(tpd10gbe.getClientPorts().size() == 2);

        log.info(nodeEquipment.toString());

    }
}
