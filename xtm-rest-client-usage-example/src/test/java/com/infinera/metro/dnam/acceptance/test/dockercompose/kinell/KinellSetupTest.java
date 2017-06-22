package com.infinera.metro.dnam.acceptance.test.dockercompose.kinell;

import com.infinera.metro.dnam.acceptance.test.node.DontLetGradleRun;
import com.infinera.metro.dnam.acceptance.test.node.NodeAccessData;
import com.infinera.metro.dnam.acceptance.test.node.NodeImpl;
import com.infinera.metro.dnam.acceptance.test.node.configuration.NodeConfiguration;
import com.infinera.metro.dnam.acceptance.test.node.configuration.NodeEquipment;
import com.infinera.metro.dnam.acceptance.test.node.configuration.ObjectFromFileUtil;
import com.infinera.metro.dnam.acceptance.test.node.configuration.ObjectFromFileUtilJackson;
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
    private final NodeEquipment liljeholmenNodeEquipment;
    private final NodeAccessData liljeholmenNodeAccessData;
    private final String RESOURCES_PATH = "dockercompose/kinell/";
    private final String NODE_EQUIPMENT_PATH = RESOURCES_PATH + "node_liljeholmen.yml";
    private final String DOCKER_COMPOSE_PATH = RESOURCES_PATH + "docker-compose.yml";
    private final String NODE_ACCESS_DATA_PATH = RESOURCES_PATH + "node_access_data.yaml";

    public KinellSetupTest() {
        final DockerCompose dockerCompose = objectFromFileUtil.getObject(DOCKER_COMPOSE_PATH, DockerCompose.class);
        final Service liljeholmenNode = dockerCompose.getServices().get("liljeholmen");
        final String liljeholmenIpAddress = liljeholmenNode.getNetworks().get("sthlm_network").get("ipv4_address");

        liljeholmenNodeEquipment = objectFromFileUtil.getObject(NODE_EQUIPMENT_PATH, NodeEquipment.class);
        assertNotNull(liljeholmenNodeEquipment);

        liljeholmenNodeAccessData = objectFromFileUtil.getObject(NODE_ACCESS_DATA_PATH, NodeAccessData.class)
                .copyObjectAndChangeIpAddress(liljeholmenIpAddress);
    }

    @Test
    public void test() {
        final NodeConfiguration nodeConfigurationNodeA = NodeConfiguration.builder()
                .node(NodeImpl.create(liljeholmenNodeAccessData))
                .nodeEquipment(liljeholmenNodeEquipment)
                .build();
        nodeConfigurationNodeA.apply();
    }
}
