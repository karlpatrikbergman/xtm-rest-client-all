package com.infinera.metro.dnam.acceptance.test.dockercompose.kinell;

import com.infinera.metro.dnam.acceptance.test.node.DontLetGradleRun;
import com.infinera.metro.dnam.acceptance.test.node.NodeAccessData;
import com.infinera.metro.dnam.acceptance.test.node.NodeImpl;
import com.infinera.metro.dnam.acceptance.test.node.configuration.*;
import com.infinera.metro.dnam.acceptance.test.node.configuration.board.Board;
import com.infinera.metro.dnam.acceptance.test.node.dockercompose.DockerCompose;
import com.infinera.metro.dnam.acceptance.test.node.dockercompose.Service;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.assertNotNull;

@Category(DontLetGradleRun.class)
@Slf4j
public class KinellSetupTest {

    final ObjectFromFileUtil objectFromFileUtil = ObjectFromFileUtilJackson.INSTANCE;
    private final NodeEquipment nodeEquipmentNodeA;
    private final NodeAccessData nodeAccessDataNodeA;
    private final String RESOURCES_PATH = "dockercompose/kinell/";
    private final String NODE_EQUIPMENT_PATH = RESOURCES_PATH + "node_liljeholmen.yml";
    private final String DOCKER_COMPOSE_PATH = RESOURCES_PATH + "docker-compose.yml";
//    private final Roadm1x2G50 roadm1x2G50;

    public KinellSetupTest() {
        final DockerCompose dockerCompose = objectFromFileUtil.getObject(DOCKER_COMPOSE_PATH, DockerCompose.class);
        final Service serviceNodeA = dockerCompose.getServices().get("liljeholmen");
        final String dockerComposeIpAddressNodeA = serviceNodeA.getNetworks().get("sthlm_network").get("ipv4_address");

        nodeEquipmentNodeA = objectFromFileUtil.getObject(NODE_EQUIPMENT_PATH, NodeEquipment.class);
        assertNotNull(nodeEquipmentNodeA);

        Board boardSlot2 = nodeEquipmentNodeA.getBoard(Slot.slot2);
        assertNotNull(boardSlot2);

        nodeAccessDataNodeA = objectFromFileUtil.getObject("dockercompose/test-case-x-node-config/node_a_access_data.yaml", NodeAccessData.class)
                .copyObjectAndChangeIpAddress(dockerComposeIpAddressNodeA);

    }

    @Test
    public void test() {
        final NodeConfiguration nodeConfigurationNodeA = NodeConfiguration.builder()
                .node(NodeImpl.create(nodeAccessDataNodeA))
                .nodeEquipment(nodeEquipmentNodeA)
                .build();
        nodeConfigurationNodeA.apply();

    }

}
