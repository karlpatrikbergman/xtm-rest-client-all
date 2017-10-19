package com.infinera.metro.dnam.acceptance.test.testcasex;

import com.infinera.metro.dnam.acceptance.test.node.DontLetGradleRun;
import com.infinera.metro.dnam.acceptance.test.node.NodeAccessData;
import com.infinera.metro.dnam.acceptance.test.node.NodeImpl;
import com.infinera.metro.dnam.acceptance.test.node.configuration.NodeConfiguration;
import com.infinera.metro.dnam.acceptance.test.node.configuration.NodeEquipment;
import com.infinera.metro.dnam.acceptance.test.node.configuration.ObjectFromFileUtil;
import com.infinera.metro.dnam.acceptance.test.node.configuration.deserialize.ObjectFromFileUtilFactory;
import com.infinera.metro.dnam.acceptance.test.node.dockercompose.DockerCompose;
import com.infinera.metro.dnam.acceptance.test.node.dockercompose.Service;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

/**
 * This use case expects XTM docker nodes to be running, maybe started by docker-compose
 */
@Category(DontLetGradleRun.class)
@Slf4j
public class UseXtmRestClientWithDockerComposeExample2 {
    private final NodeAccessData nodeAccessDataNodeA;
    private final NodeEquipment nodeEquipmentNodeA;
    private final ObjectFromFileUtil objectFromFileUtil = ObjectFromFileUtilFactory.INSTANCE.getObjectFromFileUtil();

    public UseXtmRestClientWithDockerComposeExample2() throws IOException {
        final DockerCompose dockerCompose = objectFromFileUtil.getObject("dockercompose/test-case-x-node-config-docker-compose-rule/docker-compose.yml", DockerCompose.class);
        final Service serviceNodeA = dockerCompose.getServices().get("nodeA");
        final String dockerComposeIpAddressNodeA = serviceNodeA.getNetworks().get("xtm_rest_client_network").get("ipv4_address");

        nodeAccessDataNodeA = objectFromFileUtil.getObject("dockercompose/test-case-x-node-config-docker-compose-rule/node_a_access_data.yaml", NodeAccessData.class)
                .copyObjectAndChangeIpAddress(dockerComposeIpAddressNodeA);
        nodeEquipmentNodeA = objectFromFileUtil.getObject("dockercompose/test-case-x-node-config-docker-compose-rule/node_a_equipment.yaml", NodeEquipment.class);
    }

    //TODO: Can we make configuration a transaction (=atomic)?
    @Test
    public void test() throws IOException, InterruptedException, DockerException, DockerCertificateException {
        final NodeConfiguration nodeConfigurationNodeA = NodeConfiguration.builder()
                .node(NodeImpl.create(nodeAccessDataNodeA))
                .nodeEquipment(nodeEquipmentNodeA)
                .build();
        nodeConfigurationNodeA.apply();
    }
}

