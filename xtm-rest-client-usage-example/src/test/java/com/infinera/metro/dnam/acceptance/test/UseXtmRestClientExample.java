package com.infinera.metro.dnam.acceptance.test;

import com.infinera.metro.dnam.acceptance.test.node.IntegrationTest;
import com.infinera.metro.dnam.acceptance.test.node.NodeAccessData;
import com.infinera.metro.dnam.acceptance.test.node.NodeImpl;
import com.infinera.metro.dnam.acceptance.test.node.configuration.NodeConfiguration;
import com.infinera.metro.dnam.acceptance.test.node.configuration.NodeEquipment;
import com.infinera.metro.dnam.acceptance.test.node.configuration.ObjectFromFileUtil;
import com.infinera.metro.dnam.acceptance.test.node.configuration.PeerConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

@Category(IntegrationTest.class)
@Slf4j
public class UseXtmRestClientExample {

    private final NodeAccessData nodeAccessDataNodeA, nodeAccessDataNodeZ;
    private final NodeEquipment nodeEquipmentNodeA, nodeEquipmentNodeZ;

//    @ClassRule
//    public static DockerComposeRule docker = DockerComposeRule.builder()
//            .file("src/test/resources/docker-compose.yml")
//            .waitingForService("node1", HealthChecks.toHaveAllPortsOpen())
//            .build();

    public UseXtmRestClientExample() throws IOException {
        nodeAccessDataNodeA = ObjectFromFileUtil.INSTANCE.getObject("test-case-x-node-config/node_a_access_data.yaml", NodeAccessData.class);
        nodeEquipmentNodeA = ObjectFromFileUtil.INSTANCE.getObject("test-case-x-node-config/node_a_equipment.yaml", NodeEquipment.class);

        nodeAccessDataNodeZ = ObjectFromFileUtil.INSTANCE.getObject("test-case-x-node-config/node_z_access_data.yaml", NodeAccessData.class);
        nodeEquipmentNodeZ = ObjectFromFileUtil.INSTANCE.getObject("test-case-x-node-config/node_z_equipment.yaml", NodeEquipment.class);
    }

    //TODO: Can we make configuration a transaction (=atomic)?
    @Test
    public void test() throws IOException {
        final NodeConfiguration nodeConfigurationNodeA = NodeConfiguration.builder()
                .node(NodeImpl.create(nodeAccessDataNodeA))
                .nodeEquipment(nodeEquipmentNodeA)
                .build();
        nodeConfigurationNodeA.apply();

        final NodeConfiguration nodeConfigurationNodeZ = NodeConfiguration.builder()
                .node(NodeImpl.create(nodeAccessDataNodeZ))
                .nodeEquipment(nodeEquipmentNodeZ)
                .build();
        nodeConfigurationNodeZ.apply();

        PeerConfiguration peerConfiguration = new PeerConfiguration(nodeConfigurationNodeA, nodeConfigurationNodeZ);
        peerConfiguration.apply();
    }
}
