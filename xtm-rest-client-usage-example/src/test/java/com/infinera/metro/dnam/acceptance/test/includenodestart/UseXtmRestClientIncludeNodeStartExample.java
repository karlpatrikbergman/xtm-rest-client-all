package com.infinera.metro.dnam.acceptance.test.includenodestart;

import com.infinera.metro.dnam.acceptance.test.XtmDockerRunner;
import com.infinera.metro.dnam.acceptance.test.node.*;
import com.infinera.metro.dnam.acceptance.test.node.configuration.NodeConfiguration;
import com.infinera.metro.dnam.acceptance.test.node.configuration.NodeEquipment;
import com.infinera.metro.dnam.acceptance.test.node.configuration.ObjectFromFileUtil;
import com.infinera.metro.dnam.acceptance.test.node.configuration.PeerConfiguration;
import com.infinera.metro.dnam.acceptance.test.node.mib.BoardEntry;
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
public class UseXtmRestClientIncludeNodeStartExample {
    private final NodeAccessData nodeAccessDataNodeA, nodeAccessDataNodeZ;
    private final NodeEquipment nodeEquipmentNodeA, nodeEquipmentNodeZ;

    public UseXtmRestClientIncludeNodeStartExample() throws IOException, InterruptedException, DockerException, DockerCertificateException {
        final XtmDockerRunner xtmDockerRunnerNodeA = XtmDockerRunner.builder()
                .xtmDockerVersion("latest")
                .port(80)
                .userName("root")
                .password("root")
                .build();
        final String ipAddressNodeA = xtmDockerRunnerNodeA.runDockerContainer();
        nodeAccessDataNodeA = ObjectFromFileUtil.INSTANCE.getObject("dockercompose/test-case-x-node-config/node_a_access_data.yaml", NodeAccessData.class)
                .copyObjectAndChangeIpAddress(ipAddressNodeA);
        nodeEquipmentNodeA = ObjectFromFileUtil.INSTANCE.getObject("dockercompose/test-case-x-node-config/node_a_equipment.yaml", NodeEquipment.class);

        final XtmDockerRunner xtmDockerRunnerNodeZ = XtmDockerRunner.builder()
                .xtmDockerVersion("latest")
                .port(80)
                .userName("root")
                .password("root")
                .build();
        String ipAddressNodeZ = xtmDockerRunnerNodeZ.runDockerContainer();
        nodeAccessDataNodeZ = ObjectFromFileUtil.INSTANCE.getObject("dockercompose/test-case-x-node-config/node_z_access_data.yaml", NodeAccessData.class)
                .copyObjectAndChangeIpAddress(ipAddressNodeZ);
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
