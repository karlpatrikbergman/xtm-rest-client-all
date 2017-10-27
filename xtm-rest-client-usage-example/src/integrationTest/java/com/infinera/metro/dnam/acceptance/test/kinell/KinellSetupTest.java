package com.infinera.metro.dnam.acceptance.test.kinell;

import com.infinera.metro.dnam.acceptance.test.node.DontLetGradleRun;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.NodeAccessData;
import com.infinera.metro.dnam.acceptance.test.node.NodeImpl;
import com.infinera.metro.dnam.acceptance.test.node.configuration.NodeConfiguration;
import com.infinera.metro.dnam.acceptance.test.node.configuration.NodeEquipment;
import com.infinera.metro.dnam.acceptance.test.node.configuration.deserialize.ObjectFromFileUtil;
import com.infinera.metro.dnam.acceptance.test.node.configuration.deserialize.ObjectFromFileUtilJackson;
import com.infinera.metro.dnam.acceptance.test.node.dockercompose.DockerCompose;
import com.infinera.metro.dnam.acceptance.test.node.dockercompose.Service;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.assertNotNull;

@Category(DontLetGradleRun.class)
@Slf4j
public class KinellSetupTest {

    private final NodeEquipment liljeholmenNodeEquipment;
    private final Node  liljeholmen;

    public KinellSetupTest() {
        ObjectFromFileUtil objectFromFileUtil = ObjectFromFileUtilJackson.INSTANCE;
        String RESOURCES_PATH = "dockercompose/kinell/";
        String DOCKER_COMPOSE_PATH = RESOURCES_PATH + "docker-compose.yml";
        final DockerCompose dockerCompose = objectFromFileUtil.getObject(DOCKER_COMPOSE_PATH, DockerCompose.class);
        final Service liljeholmenNode = dockerCompose.getServices().get("liljeholmen");
        final String liljeholmenIpAddress = liljeholmenNode.getNetworks().get("sthlm_network").get("ipv4_address");

        String NODE_EQUIPMENT_PATH = RESOURCES_PATH + "node_liljeholmen.yml";
        liljeholmenNodeEquipment = objectFromFileUtil.getObject(NODE_EQUIPMENT_PATH, NodeEquipment.class);
        assertNotNull(liljeholmenNodeEquipment);

        String NODE_ACCESS_DATA_PATH = RESOURCES_PATH + "node_access_data.yaml";
        final NodeAccessData liljeholmenNodeAccessData = objectFromFileUtil.getObject(NODE_ACCESS_DATA_PATH, NodeAccessData.class)
                .copyObjectAndChangeIpAddress(liljeholmenIpAddress);
        liljeholmen = NodeImpl.create(liljeholmenNodeAccessData);
    }

    @Test
    public void applyEquipmentToNode() {
        final NodeConfiguration nodeConfigurationNodeA = NodeConfiguration.builder()
                .node(liljeholmen)
                .nodeEquipment(liljeholmenNodeEquipment)
                .build();
        nodeConfigurationNodeA.apply();
    }

    @Ignore
    @Test
    public void deleteBoards() {
        liljeholmenNodeEquipment.deleteBoards(liljeholmen);
    }
}
