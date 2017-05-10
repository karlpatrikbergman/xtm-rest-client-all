package com.infinera.metro.dnam.acceptance.test.node.config;

import com.infinera.metro.dnam.acceptance.test.mib.Configuration;
import com.infinera.metro.dnam.acceptance.test.mib.ParameterList;
import com.infinera.metro.dnam.acceptance.test.mib.PeerEntry;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import lombok.Value;

import java.io.IOException;
import java.util.Arrays;

@Value
public class PeerConfig {
    private final NodeConfig transmitNodeConfig;
    private final NodeConfig receiveNodeConfig;
    private final PeerEntry transmitPeerEntry;
    private final PeerEntry receivePeerEntry;
    private final ParameterList transmitPeerConfig;
    private final ParameterList receivePeerConfig;

    public PeerConfig(NodeConfig transmitNodeConfig, NodeConfig receiveNodeConfig) {
        this.transmitNodeConfig = transmitNodeConfig;
        this.receiveNodeConfig = receiveNodeConfig;
        this.transmitPeerEntry =  createTransmitPeerEntry(transmitNodeConfig, receiveNodeConfig);
        this.receivePeerEntry = this.transmitPeerEntry.invert();
        this.transmitPeerConfig = createPeerConfig(transmitPeerEntry);
        this.receivePeerConfig = createPeerConfig(receivePeerEntry);
    }

    public void apply() throws IOException {
        applyTransmitNodePeerConfig();
        applyReceiveNodePeerConfig();
    }

    private void applyTransmitNodePeerConfig() throws IOException {
        Node transmitNode = transmitNodeConfig.getNode();
        transmitNode.createLocalPeer(transmitPeerEntry);
        transmitNode.setLocalPeerConfiguration(transmitPeerEntry, transmitPeerConfig);
    }

    private void applyReceiveNodePeerConfig() throws IOException {
        Node receiveNode = receiveNodeConfig.getNode();
        receiveNode.createLocalPeer(receivePeerEntry);
        receiveNode.setLocalPeerConfiguration(receivePeerEntry, receivePeerConfig);
    }

    private ParameterList createPeerConfig(PeerEntry peerEntry) {
        return ParameterList.builder()
                .parameterList(Arrays.asList(
                        Configuration.builder()
                                .key("topoPeerLocalLabel")
                                .value(peerEntry.getPeerLocalLabel())
                                .build(),
                        Configuration.builder()
                                .key("topoPeerRemoteIpAddress")
                                .value(peerEntry.getPeerRemoteIpAddress())
                                .build(),
                        Configuration.builder()
                                .key("topoPeerRemoteLabel")
                                .value(peerEntry.getPeerRemoteLabel())
                                .build()
                ))
                .build();
    }

    private PeerEntry createTransmitPeerEntry(NodeConfig transmitNodeConfig, NodeConfig receiveNodeConfig) {
        return PeerEntry.builder()
                .localLinePortEntry(transmitNodeConfig.getLinePortEntry())
                .remoteLinePortEntry(receiveNodeConfig.getLinePortEntry())
                .nodeIpAddress(transmitNodeConfig.getNode().getIpAddress())
                .remoteNodeIpAddress(receiveNodeConfig.getNode().getIpAddress())
                .localMpoIdentifier(transmitNodeConfig.getMpoIdentifier())
                .remoteMpoIdentifier(receiveNodeConfig.getMpoIdentifier())
                .isTransmitSide(true)
                .build();
    }

}
