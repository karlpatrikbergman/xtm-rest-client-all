//package com.infinera.metro.dnam.acceptance.test.node.configuration;
//
//import com.infinera.metro.dnam.acceptance.test.node.Node;
//import com.infinera.metro.dnam.acceptance.test.node.mib.Configuration;
//import com.infinera.metro.dnam.acceptance.test.node.mib.ConfigurationList;
//import com.infinera.metro.dnam.acceptance.test.node.mib.PeerEntry;
//import lombok.Value;
//
//import java.util.Arrays;
//
//@Value
//public class PeerConfiguration {
//    private final NodeConfiguration transmitNodeConfig;
//    private final NodeConfiguration receiveNodeConfig;
//    private final PeerEntry transmitPeerEntry;
//    private final PeerEntry receivePeerEntry;
//    private final ConfigurationList transmitPeerConfig;
//    private final ConfigurationList receivePeerConfig;
//
//    public PeerConfiguration(NodeConfiguration transmitNodeConfig, NodeConfiguration receiveNodeConfig) {
//        this.transmitNodeConfig = transmitNodeConfig;
//        this.receiveNodeConfig = receiveNodeConfig;
//        this.transmitPeerEntry =  createTransmitPeerEntry(transmitNodeConfig, receiveNodeConfig);
//        this.receivePeerEntry = this.transmitPeerEntry.invert();
//        this.transmitPeerConfig = createPeerConfig(transmitPeerEntry);
//        this.receivePeerConfig = createPeerConfig(receivePeerEntry);
//    }
//
//    public void apply() throws RuntimeException {
//        applyTransmitNodePeerConfig();
//        applyReceiveNodePeerConfig();
//    }
//
//    private void applyTransmitNodePeerConfig() throws RuntimeException {
//        Node transmitNode = transmitNodeConfig.getNode();
//        transmitNode.createLocalPeer(transmitPeerEntry);
//        transmitNode.setLocalPeerConfiguration(transmitPeerEntry, transmitPeerConfig);
//    }
//
//    private void applyReceiveNodePeerConfig() throws RuntimeException {
//        Node receiveNode = receiveNodeConfig.getNode();
//        receiveNode.createLocalPeer(receivePeerEntry);
//        receiveNode.setLocalPeerConfiguration(receivePeerEntry, receivePeerConfig);
//    }
//
//    private ConfigurationList createPeerConfig(PeerEntry peerEntry) {
//        return ConfigurationList.builder()
//                .parameterList(Arrays.asList(
//                        Configuration.builder()
//                                .key("topoPeerLocalLabel")
//                                .value(peerEntry.getPeerLocalLabel())
//                                .build(),
//                        Configuration.builder()
//                                .key("topoPeerRemoteIpAddress")
//                                .value(peerEntry.getPeerRemoteIpAddress())
//                                .build(),
//                        Configuration.builder()
//                                .key("topoPeerRemoteLabel")
//                                .value(peerEntry.getPeerRemoteLabel())
//                                .build()
//                ))
//                .build();
//    }
//
//    private PeerEntry createTransmitPeerEntry(NodeConfiguration transmitNodeConfig, NodeConfiguration receiveNodeConfig) {
//        return PeerEntry.builder()
//                .localLinePortEntry(transmitNodeConfig.getNodeEquipment().getLinePortEntry())
//                .remoteLinePortEntry(receiveNodeConfig.getNodeEquipment().getLinePortEntry())
//                .nodeIpAddress(transmitNodeConfig.getNode().getIpAddress())
//                .remoteNodeIpAddress(receiveNodeConfig.getNode().getIpAddress())
//                .localMpoIdentifier(transmitNodeConfig.getNodeEquipment().getMpoIdentifier())
//                .remoteMpoIdentifier(receiveNodeConfig.getNodeEquipment().getMpoIdentifier())
//                .isTransmitSide(true)
//                .build();
//    }
//
//}
