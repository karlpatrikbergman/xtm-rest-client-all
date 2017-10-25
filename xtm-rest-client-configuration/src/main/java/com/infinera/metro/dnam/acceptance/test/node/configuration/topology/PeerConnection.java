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
public class PeerConnection {
    @NonNull private final PeerEntry localPeerEntry;
    @NonNull private final PeerEntry remotePeerEntry;

    @JsonCreator
    @Builder
    public PeerConnection(@JsonProperty("localPortEntry") AbstractPortEntry localPortEntry,
                          @JsonProperty("localMpoIdentifier") MpoIdentifier localMpoIdentifier,
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
    }

    public void applyTo(Node nodeA, Node nodeZ) {

        final Attributes localPeerConfiguration = Attributes.builder()
            .attribute(Attribute.of("topoPeerLocalLabel", localPeerEntry.getLocalLabel()))
            .attribute(Attribute.of("topoPeerRemoteIpAddress", nodeZ.getIpAddress()))
            .attribute(Attribute.of("topoPeerRemoteLabel", remotePeerEntry.getLocalLabel()))
            .build();

        final Attributes  remotePeerConfiguration = Attributes.builder()
            .attribute(Attribute.of("topoPeerLocalLabel", remotePeerEntry.getLocalLabel()))
            .attribute(Attribute.of("topoPeerRemoteIpAddress", nodeA.getIpAddress()))
            .attribute(Attribute.of("topoPeerRemoteLabel", localPeerEntry.getLocalLabel()))
            .build();

        nodeA.createPeer(localPeerEntry);
        nodeA.setPeerAttributes(localPeerEntry, localPeerConfiguration);
        nodeZ.createPeer(remotePeerEntry);
        nodeZ.setPeerAttributes(remotePeerEntry, remotePeerConfiguration);
    }
}
