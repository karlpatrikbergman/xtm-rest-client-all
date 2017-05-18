package com.infinera.metro.dnam.acceptance.test.dockercompose;

import com.infinera.metro.dnam.acceptance.test.node.IntegrationTest;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.NodeAccessData;
import com.infinera.metro.dnam.acceptance.test.node.NodeImpl;
import com.infinera.metro.dnam.acceptance.test.node.configuration.NodeConfiguration;
import com.infinera.metro.dnam.acceptance.test.node.configuration.NodeEquipment;
import com.infinera.metro.dnam.acceptance.test.node.configuration.ObjectFromFileUtil;
import com.infinera.metro.dnam.acceptance.test.node.configuration.PeerConfiguration;
import com.infinera.metro.dnam.acceptance.test.node.dockercompose.DockerCompose;
import com.infinera.metro.dnam.acceptance.test.node.dockercompose.Service;
import com.infinera.metro.dnam.acceptance.test.node.mib.BoardEntry;
import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.connection.waiting.HealthChecks;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

/**
 * This use case expects XTM docker nodes to be running, maybe started by docker-compose
 */
@Category(IntegrationTest.class)
@Slf4j
public class UseXtmRestClientWithDockerComposeExample {
    private final NodeAccessData nodeAccessDataNodeA, nodeAccessDataNodeZ;
    private final NodeEquipment nodeEquipmentNodeA, nodeEquipmentNodeZ;

    @ClassRule
    public static DockerComposeRule docker = DockerComposeRule.builder()
            .file("src/test/resources/dockercompose/test-case-x-node-config/docker-compose.yml")
            .waitingForService("nodeA", HealthChecks.toHaveAllPortsOpen())
            .waitingForService("nodeZ", HealthChecks.toHaveAllPortsOpen())
            .build();

    public UseXtmRestClientWithDockerComposeExample() throws IOException {
        final DockerCompose dockerCompose = ObjectFromFileUtil.INSTANCE.getObject("dockercompose/test-case-x-node-config/docker-compose.yml", DockerCompose.class);
        final Service serviceNodeA = dockerCompose.getServices().get("nodeA");
        final String dockerComposeIpAddressNodeA = serviceNodeA.getNetworks().get("xtm_rest_client_network").get("ipv4_address");

        nodeAccessDataNodeA = ObjectFromFileUtil.INSTANCE.getObject("dockercompose/test-case-x-node-config/node_a_access_data.yaml", NodeAccessData.class)
                .copyObjectAndChangeIpAddress(dockerComposeIpAddressNodeA);
        nodeEquipmentNodeA = ObjectFromFileUtil.INSTANCE.getObject("dockercompose/test-case-x-node-config/node_a_equipment.yaml", NodeEquipment.class);

        final Service serviceNodeZ = dockerCompose.getServices().get("nodeZ");
        final String dockerComposeIpAddressNodeZ = serviceNodeZ.getNetworks().get("xtm_rest_client_network").get("ipv4_address");
        nodeAccessDataNodeZ = ObjectFromFileUtil.INSTANCE.getObject("dockercompose/test-case-x-node-config/node_z_access_data.yaml", NodeAccessData.class)
                .copyObjectAndChangeIpAddress(dockerComposeIpAddressNodeZ);
        nodeEquipmentNodeZ = ObjectFromFileUtil.INSTANCE.getObject("dockercompose/test-case-x-node-config/node_z_equipment.yaml", NodeEquipment.class);
    }

    //TODO: Can we make configuration a transaction (=atomic)?
    @Test
    public void test() throws IOException, InterruptedException, DockerException, DockerCertificateException {
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

        final Node nodeA = nodeConfigurationNodeA.getNode();
        BoardEntry expectedBoardEntry = nodeConfigurationNodeA.getNodeEquipment().getBoardEntry();
        nodeA.getBoard(expectedBoardEntry);
    }
}
