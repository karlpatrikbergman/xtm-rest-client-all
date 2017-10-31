package com.infinera.metro.dnam.acceptance.test.node.configuration.topology;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attribute;
import com.infinera.metro.dnam.acceptance.test.node.mib.Attributes;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.PeerEntry;
import lombok.Builder;
import lombok.Value;
//TODO: Share stuff with InternalConnection?
@Value
public class PeerConnection {
    private final Peer localPeer;
    private final Peer remotePeer;

    @JsonCreator
    @Builder
    public PeerConnection(@JsonProperty("localPeer") Peer localPeer, @JsonProperty("remotePeer") Peer remotePeer) {
        this.localPeer = localPeer;
        this.remotePeer = remotePeer;
    }

    public void applyTo(Node nodeA, Node nodeZ) {
        final PeerEntry localPeerEntry = getLocalPeerEntry();
        final PeerEntry remotePeerEntry = getRemotePeerEntry();
        final Attributes localPeerConfiguration = getPeerConfiguration(localPeerEntry, nodeZ, remotePeerEntry);
        final Attributes remotePeerConfiguration = getPeerConfiguration(remotePeerEntry, nodeA, localPeerEntry);

        nodeA.createPeer(localPeerEntry);
        nodeA.setPeerAttributes(localPeerEntry, localPeerConfiguration);
        nodeZ.createPeer(remotePeerEntry);
        nodeZ.setPeerAttributes(remotePeerEntry, remotePeerConfiguration);
    }

    public PeerConnection invert() {
        return PeerConnection.builder()
            .localPeer(this.remotePeer.invert())
            .remotePeer(this.localPeer.invert())
            .build();
    }

    PeerEntry getLocalPeerEntry() {
        return toPeerEntry(localPeer);
    }

    PeerEntry getRemotePeerEntry() {
        return toPeerEntry(remotePeer);
    }

    private PeerEntry toPeerEntry(Peer peer) {
        return PeerEntry.builder()
            .subrack(peer.getSubrack().getValue())
            .slot(peer.getSlot().getValue())
            .port(peer.getPort())
            .mpoIdentifier(peer.getMpoIdentifier())
            .build();
    }

    Attributes getPeerConfiguration(PeerEntry localPeerEntry, Node node, PeerEntry remotePeerEntry) {
        return Attributes.builder()
            .attribute(Attribute.of("topoPeerLocalLabel", localPeerEntry.getLocalLabel()))
            .attribute(Attribute.of("topoPeerRemoteIpAddress", node.getIpAddress()))
            .attribute(Attribute.of("topoPeerRemoteLabel", remotePeerEntry.getLocalLabel()))
            .build();
    }


}
