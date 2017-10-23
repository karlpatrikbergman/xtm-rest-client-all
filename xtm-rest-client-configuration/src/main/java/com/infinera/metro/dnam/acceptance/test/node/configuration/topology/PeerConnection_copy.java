package com.infinera.metro.dnam.acceptance.test.node.configuration.topology;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attribute;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attributes;
import com.infinera.metro.dnam.acceptance.test.node.mib.MpoIdentifier;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.AbstractPortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.PeerEntry;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

//TODO: Make serialization work the same as deserialization does. It must work the same both ways

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@Value
public class PeerConnection_copy {
    @NonNull private final PeerEntry localPeerEntry;
    @NonNull private final PeerEntry remotePeerEntry;
    private final Attributes localPeerConfiguration;
    private final Attributes remotePeerConfiguration;

    @JsonCreator
    @Builder
    public PeerConnection_copy(@JsonProperty("localNodeIpAddress") String localNodeIpAddress,
                               @JsonProperty("localPortEntry") AbstractPortEntry localPortEntry,
                               @JsonProperty("localMpoIdentifier") MpoIdentifier localMpoIdentifier,
                               @JsonProperty("remoteNodeIpAddress") String remoteNodeIpAddress,
                               @JsonProperty("remotePortEntry") AbstractPortEntry remotePortEntry,
                               @JsonProperty("remoteMpoIdentifier") MpoIdentifier remoteMpoIdentifier) {
        this.localPeerEntry = PeerEntry.builder()
            .subrack(localPortEntry.getSubrack())
            .slot(localPortEntry.getSlot())
            .port(localPortEntry.getTransmitPort())
            .mpoIdentifier(localMpoIdentifier)
            .build();
        this.remotePeerEntry = PeerEntry.builder()
            .subrack(remotePortEntry.getSubrack())
            .slot(remotePortEntry.getSlot())
            .port(remotePortEntry.getReceivePort())
            .mpoIdentifier(remoteMpoIdentifier)
            .build();
        this.localPeerConfiguration = Attributes.builder()
            .attribute(
                Attribute.builder()
                    .key("topoPeerLocalLabel")
                    .value(localPeerEntry.getLocalLabel())
                    .build()
            )
            .attribute(
                Attribute.builder()
                    .key("topoPeerRemoteIpAddress")
                    .value(remoteNodeIpAddress) //This node is not expected to be running in this test
                    .build()
            )
            .attribute(
                Attribute.builder()
                    .key("topoPeerRemoteLabel")
                    .value(remotePeerEntry.getLocalLabel())
                    .build()
            )
            .build();
        this.remotePeerConfiguration = Attributes.builder()
            .attribute(
                Attribute.builder()
                    .key("topoPeerLocalLabel")
                    .value(remotePeerEntry.getLocalLabel())
                    .build()
            )
            .attribute(
                Attribute.builder()
                    .key("topoPeerRemoteIpAddress")
                    .value(localNodeIpAddress)
                    .build()
            )
            .attribute(
                Attribute.builder()
                    .key("topoPeerRemoteLabel")
                    .value(localPeerEntry.getLocalLabel())
                    .build()
            )
            .build();
    }

    public void applyTo(Node nodeA, Node nodeZ) {
        nodeA.createPeer(localPeerEntry);
        nodeA.setPeerAttributes(localPeerEntry, localPeerConfiguration);
        nodeZ.createPeer(remotePeerEntry);
        nodeZ.setPeerAttributes(remotePeerEntry, remotePeerConfiguration);
    }
}
