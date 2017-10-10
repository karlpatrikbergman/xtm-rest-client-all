//package com.infinera.metro.dnam.acceptance.test.ringnetwork;
//
//import com.google.common.base.Preconditions;
//import com.infinera.metro.dnam.acceptance.test.XtmDockerRunner;
//import com.infinera.metro.dnam.acceptance.test.node.DontLetGradleRun;
//import com.infinera.metro.dnam.acceptance.test.node.configuration.*;
//import com.spotify.docker.CLIENT.exceptions.DockerCertificateException;
//import com.spotify.docker.CLIENT.exceptions.DockerException;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.experimental.categories.Category;
//
//import java.io.IOException;
//import java.util.Comparator;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//@Category(DontLetGradleRun.class)
//@Slf4j
//public class UseXtmRestClientRingNetworkExample {
//    private final ObjectFromFileUtil objectFromFileUtil = ObjectFromFileUtilFactory.getObjectFromFileUtil();
//
//    @Test
//    public void test() throws InterruptedException, DockerException, DockerCertificateException, IOException {
//        List<NodeConfiguration> nodeConfigurationList = IntStream.rangeClosed(1, 8)
//                .parallel()
//                .mapToObj(i -> createNodeConfigurator("node" + i))
//                .map(NodeConfigurator::runAndConfigureNode)
//                .collect(Collectors.toList());
//
//        applyPeerConfiguration(nodeConfigurationList);
//    }
//
//    private NodeConfigurator createNodeConfigurator(String containerName) {
//        final XtmDockerRunner xtmDockerRunner = XtmDockerRunner.INSTANCE;
//        final NodeEquipment nodeEquipment = objectFromFileUtil.getObject("ringnetwork/node_equipment.yaml", NodeEquipment.class);
//        final NodeConfiguration.NodeConfigurationBuilder nodeConfigurationBuilder = NodeConfiguration.builder()
//                .nodeEquipment(nodeEquipment);
//        return NodeConfigurator.builder()
//                .xtmDockerRunner(xtmDockerRunner)
//                .xtmVersion("latest")
//                .containerName(containerName)
//                .nodeConfigurationBuilder(nodeConfigurationBuilder)
//                .build();
//    }
//
//    /**
//     * TODO: Add sort field to NodeConfig. ExecutorService did not work, why?
//     */
//    private void applyPeerConfiguration(List<NodeConfiguration> nodeConfigurationList) throws IOException, InterruptedException {
//        Preconditions.checkArgument(nodeConfigurationList.size() > 1, "We must have at least two nodes");
//        nodeConfigurationList.sort(Comparator.comparing(p -> ipToLong(p.getNode().getIpAddress())));
////        ExecutorService executor = Executors.newCachedThreadPool();
//
//        for(int i=0; i<nodeConfigurationList.size(); i++) {
//            log.info("apply peer iteration " + i);
//            final boolean onLastElement = (i == nodeConfigurationList.size()-1);
//            final NodeConfiguration nodeConfigurationTransmit = nodeConfigurationList.get(i);
//            final NodeConfiguration nodeConfigurationReceive = (onLastElement) ? nodeConfigurationList.get(0) : nodeConfigurationList.get(i+1);
//            final PeerConfiguration peerConfiguration = new PeerConfiguration(nodeConfigurationTransmit, nodeConfigurationReceive);
//
//            log.info("Create peers for nodes " + nodeConfigurationTransmit.getNode().getIpAddress() + " " + nodeConfigurationReceive.getNode().getIpAddress());
//
////            executor.submit(peerConfiguration::apply);
//            peerConfiguration.apply();
//        }
//    }
//
//    private long ipToLong(String ipAddress) {
//        String[] ipAddressInArray = ipAddress.split("\\.");
//        long result = 0;
//        for (int i = 0; i < ipAddressInArray.length; i++) {
//            int power = 3 - i;
//            int ip = Integer.parseInt(ipAddressInArray[i]);
//            result += ip * Math.pow(256, power);
//        }
//        return result;
//    }
//}
