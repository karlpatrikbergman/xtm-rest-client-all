//package com.infinera.metro.dnam.acceptance.test.dockercompose.testcasex;
//
//import com.infinera.metro.dnam.acceptance.test.node.IntegrationTest;
//import com.infinera.metro.dnam.acceptance.test.node.Node;
//import com.infinera.metro.dnam.acceptance.test.node.NodeAccessData;
//import com.infinera.metro.dnam.acceptance.test.node.NodeImpl;
//import com.infinera.metro.dnam.acceptance.test.node.configuration.*;
//import com.infinera.metro.dnam.acceptance.test.node.dockercompose.DockerCompose;
//import com.infinera.metro.dnam.acceptance.test.node.dockercompose.Service;
//import com.infinera.metro.dnam.acceptance.test.node.mib.entry.BoardEntry;
//import com.infinera.metro.dnam.acceptance.test.node.mib.entry.LinePortEntry;
//import com.palantir.docker.compose.DockerComposeRule;
//import com.palantir.docker.compose.connection.waiting.HealthChecks;
//import com.spotify.docker.client.exceptions.DockerCertificateException;
//import com.spotify.docker.client.exceptions.DockerException;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.ClassRule;
//import org.junit.Test;
//import org.junit.experimental.categories.Category;
//
//import java.io.IOException;
//
///**
// * This use case expects XTM docker nodes to be running, maybe started by docker-compose
// */
//@Category(IntegrationTest.class)
//@Slf4j
//public class UseXtmRestClientWithDockerComposeExample {
//    private final NodeAccessData nodeAccessDataNodeA, nodeAccessDataNodeZ;
//    private final NodeEquipment nodeEquipmentNodeA, nodeEquipmentNodeZ;
//    private final ObjectFromFileUtil objectFromFileUtil = ObjectFromFileUtilFactory.getObjectFromFileUtil();
//
//    @ClassRule
//    public static DockerComposeRule docker = DockerComposeRule.builder()
//            .file("src/test/resources/dockercompose/test-case-x-node-config-docker-compose-rule/docker-compose.yml")
//            .waitingForService("nodeA", HealthChecks.toHaveAllPortsOpen())
//            .waitingForService("nodeZ", HealthChecks.toHaveAllPortsOpen())
//            .build();
//
//    public UseXtmRestClientWithDockerComposeExample() throws IOException {
//        final DockerCompose dockerCompose = objectFromFileUtil.getObject("dockercompose/test-case-x-node-config-docker-compose-rule/docker-compose.yml", DockerCompose.class);
//        final Service serviceNodeA = dockerCompose.getServices().get("nodeA");
//        final String dockerComposeIpAddressNodeA = serviceNodeA.getNetworks().get("xtm_rest_client_network").get("ipv4_address");
//
//        nodeAccessDataNodeA = objectFromFileUtil.getObject("dockercompose/test-case-x-node-config-docker-compose-rule/node_a_access_data.yaml", NodeAccessData.class)
//                .copyObjectAndChangeIpAddress(dockerComposeIpAddressNodeA);
//        nodeEquipmentNodeA = objectFromFileUtil.getObject("dockercompose/test-case-x-node-config-docker-compose-rule/node_a_equipment.yaml_old", NodeEquipment.class);
//
//        final Service serviceNodeZ = dockerCompose.getServices().get("nodeZ");
//        final String dockerComposeIpAddressNodeZ = serviceNodeZ.getNetworks().get("xtm_rest_client_network").get("ipv4_address");
//        nodeAccessDataNodeZ = objectFromFileUtil.getObject("dockercompose/test-case-x-node-config-docker-compose-rule/node_z_access_data.yaml", NodeAccessData.class)
//                .copyObjectAndChangeIpAddress(dockerComposeIpAddressNodeZ);
//        nodeEquipmentNodeZ = objectFromFileUtil.getObject("dockercompose/test-case-x-node-config-docker-compose-rule/node_z_equipment.yaml", NodeEquipment.class);
//    }
//
//    //TODO: Can we make configuration a transaction (=atomic)?
//    @Test
//    public void test() throws IOException, InterruptedException, DockerException, DockerCertificateException {
//        final NodeConfiguration nodeConfigurationNodeA = NodeConfiguration.builder()
//                .node(NodeImpl.create(nodeAccessDataNodeA))
//                .nodeEquipment(nodeEquipmentNodeA)
//                .build();
//        nodeConfigurationNodeA.apply();
//
//        final NodeConfiguration nodeConfigurationNodeZ = NodeConfiguration.builder()
//                .node(NodeImpl.create(nodeAccessDataNodeZ))
//                .nodeEquipment(nodeEquipmentNodeZ)
//                .build();
//        nodeConfigurationNodeZ.apply();
//
//        final LinePortEntry linePortEntryA = nodeConfigurationNodeA.getNodeEquipment().getBoard(Slot.slot2).
//
//        //public PeerConfiguration(Node transmitNode, LinePortEntry transmitLinePortEntry, Node receiveNode, LinePortEntry receiveLinePortEntry) {
//        PeerConfiguration peerConfiguration = new PeerConfiguration(nodeConfigurationNodeA.getNode(), nodeConfigurationNodeZ);
//        peerConfiguration.apply();
//
//        final Node nodeA = nodeConfigurationNodeA.getNode();
//        BoardEntry expectedBoardEntry = nodeConfigurationNodeA.getNodeEquipment().getBoard(Slot.slot2).;
//        nodeA.getBoard(expectedBoardEntry);
//    }
//}
