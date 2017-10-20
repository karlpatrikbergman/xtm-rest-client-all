package com.infinera.metro.dnam.acceptance.test.includenodestart;

import com.infinera.metro.dnam.acceptance.test.XtmDockerRunner;
import com.infinera.metro.dnam.acceptance.test.node.DontLetGradleRun;
import com.infinera.metro.dnam.acceptance.test.node.NodeAccessData;
import com.infinera.metro.dnam.acceptance.test.node.NodeImpl;
import com.infinera.metro.dnam.acceptance.test.node.configuration.*;
import com.infinera.metro.dnam.acceptance.test.node.configuration.deserialize.ObjectFromFileUtil;
import com.infinera.metro.dnam.acceptance.test.node.configuration.deserialize.ObjectFromFileUtilFactory;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

@Category(DontLetGradleRun.class)
@Slf4j
public class UseXtmRestClientIncludeNodeStartExample {
    private final NodeAccessData nodeAccessDataNodeA, nodeAccessDataNodeZ, nodeAccessDataNodeX;
    private final NodeEquipment nodeEquipmentNodeA, nodeEquipmentNodeZ, nodeEquipmentNodeX;
    private final ObjectFromFileUtil objectFromFileUtil = ObjectFromFileUtilFactory.INSTANCE.getObjectFromFileUtil();

    public UseXtmRestClientIncludeNodeStartExample() throws IOException, InterruptedException, DockerException, DockerCertificateException {
        final XtmDockerRunner xtmDockerRunner = XtmDockerRunner.INSTANCE;

        final String ipAddressNodeA = xtmDockerRunner.runDockerContainer("latest", "nodeA");
        nodeAccessDataNodeA = objectFromFileUtil.getObject("includenodestart/three-nodes-example/node_a_access_data.yaml", NodeAccessData.class)
                .copyObjectAndChangeIpAddress(ipAddressNodeA);
        nodeEquipmentNodeA = objectFromFileUtil.getObject("includenodestart/three-nodes-example/node_a_equipment.yaml", NodeEquipment.class);

        final String ipAddressNodeX = xtmDockerRunner.runDockerContainer("latest", "nodeX");
        nodeAccessDataNodeX = objectFromFileUtil.getObject("includenodestart/three-nodes-example/node_x_access_data.yaml", NodeAccessData.class)
            .copyObjectAndChangeIpAddress(ipAddressNodeX);
        nodeEquipmentNodeX = objectFromFileUtil.getObject("includenodestart/three-nodes-example/node_x_equipment.yaml", NodeEquipment.class);

        final String ipAddressNodeZ = xtmDockerRunner.runDockerContainer("latest", "nodeZ");
        nodeAccessDataNodeZ = objectFromFileUtil.getObject("includenodestart/three-nodes-example/node_z_access_data.yaml", NodeAccessData.class)
                .copyObjectAndChangeIpAddress(ipAddressNodeZ);
        nodeEquipmentNodeZ = objectFromFileUtil.getObject("includenodestart/three-nodes-example/node_z_equipment.yaml", NodeEquipment.class);

    }

    //TODO: Can we make configuration a transaction (=atomic)?
    @Test
    public void test() throws IOException, InterruptedException, DockerException, DockerCertificateException {
        final NodeConfiguration nodeConfigurationNodeA = NodeConfiguration.builder()
                .node(NodeImpl.create(nodeAccessDataNodeA))
                .nodeEquipment(nodeEquipmentNodeA)
                .build();
        nodeConfigurationNodeA.apply();

        final NodeConfiguration nodeConfigurationNodeX = NodeConfiguration.builder()
            .node(NodeImpl.create(nodeAccessDataNodeX))
            .nodeEquipment(nodeEquipmentNodeX)
            .build();
        nodeConfigurationNodeX.apply();

        final NodeConfiguration nodeConfigurationNodeZ = NodeConfiguration.builder()
                .node(NodeImpl.create(nodeAccessDataNodeZ))
                .nodeEquipment(nodeEquipmentNodeZ)
                .build();
        nodeConfigurationNodeZ.apply();

//        PeerConfiguration peerConfigurationAtoZ = new PeerConfiguration(nodeConfigurationNodeA, nodeConfigurationNodeZ);
//        peerConfigurationAtoZ.apply();
//
//        PeerConfiguration peerConfigurationZtoX = new PeerConfiguration(nodeConfigurationNodeZ, nodeConfigurationNodeX);
//        peerConfigurationZtoX.apply();
//
//        PeerConfiguration peerConfigurationXtoA = new PeerConfiguration(nodeConfigurationNodeX, nodeConfigurationNodeA);
//        peerConfigurationXtoA.apply();
//
//        final Node nodeA = nodeConfigurationNodeA.getNode();
//        BoardEntry expectedBoardEntry = nodeConfigurationNodeA.getNodeEquipment().getBoardEntry();
//        nodeA.getBoard(expectedBoardEntry);
    }
}
