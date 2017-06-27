package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.Configuration;
import com.infinera.metro.dnam.acceptance.test.node.mib.Configurations;
import com.infinera.metro.dnam.acceptance.test.node.mib.MpoIdentifier;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.LinePortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.PeerEntry;
import lombok.Value;

@Value
public class PeerConfiguration {
    private final Node transmitNode;
    private final Node receiveNode;
    private final PeerEntry transmitPeerEntry;
    private final PeerEntry receivePeerEntry;
    private final Configurations transmitPeerConfig;
    private final Configurations receivePeerConfig;

    public PeerConfiguration(Node transmitNode, LinePortEntry transmitLinePortEntry, Node receiveNode, LinePortEntry receiveLinePortEntry) {
        this.transmitNode = transmitNode;
        this.receiveNode = receiveNode;
        this.transmitPeerEntry = createTransmitPeerEntry(transmitLinePortEntry, transmitNode, receiveLinePortEntry, receiveNode);
        this.receivePeerEntry = this.transmitPeerEntry.invert();
        this.transmitPeerConfig = createPeerConfig(transmitPeerEntry);
        this.receivePeerConfig = createPeerConfig(receivePeerEntry);
    }

    public void apply() throws RuntimeException {
        applyTransmitNodePeerConfig();
        applyReceiveNodePeerConfig();
    }

    private void applyTransmitNodePeerConfig() throws RuntimeException {
        transmitNode.createLocalPeer(transmitPeerEntry);
        transmitNode.setLocalPeerConfiguration(transmitPeerEntry, transmitPeerConfig);
    }

    private void applyReceiveNodePeerConfig() throws RuntimeException {
        receiveNode.createLocalPeer(receivePeerEntry);
        receiveNode.setLocalPeerConfiguration(receivePeerEntry, receivePeerConfig);
    }

    private Configurations createPeerConfig(PeerEntry peerEntry) {
        return Configurations.builder()
            .configuration(
                Configuration.builder()
                    .key("topoPeerLocalLabel")
                    .value(peerEntry.getPeerLocalLabel())
                    .build()
            )
            .configuration(
                Configuration.builder()
                    .key("topoPeerRemoteIpAddress")
                    .value(peerEntry.getPeerRemoteIpAddress())
                    .build()
            )
            .configuration(
                Configuration.builder()
                    .key("topoPeerRemoteLabel")
                    .value(peerEntry.getPeerRemoteLabel())
                    .build()
            )
            .build();
    }

    private PeerEntry createTransmitPeerEntry(LinePortEntry transmitLinePortEntry, Node transmitNode, LinePortEntry receiveLinePortEntry, Node receiveNode) {
        assert transmitLinePortEntry != null;
        assert transmitNode != null;
        assert receiveLinePortEntry != null;
        assert receiveLinePortEntry != null;

        return PeerEntry.builder()
            .localLinePortEntry(transmitLinePortEntry)
            .remoteLinePortEntry(receiveLinePortEntry)
            .nodeIpAddress(transmitNode.getIpAddress())
            .remoteNodeIpAddress(receiveNode.getIpAddress())
            .localMpoIdentifier(MpoIdentifier.createMpoIdentifierModuleNotPresent()) //TODO: Fix proper handling of MpoIdentifier
            .remoteMpoIdentifier(MpoIdentifier.createMpoIdentifierModuleNotPresent())
            .isTransmitSide(true)
            .build();
    }
}
