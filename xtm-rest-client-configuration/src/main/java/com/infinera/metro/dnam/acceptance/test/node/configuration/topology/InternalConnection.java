package com.infinera.metro.dnam.acceptance.test.node.configuration.topology;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.InternalConnectionEntry;
import lombok.Builder;
import lombok.Value;

@Value
public class InternalConnection {
    private final Peer fromPeer;
    private final Peer toPeer;

    @JsonCreator
    @Builder
    public InternalConnection(@JsonProperty("fromPeer") Peer fromPeer, @JsonProperty("toPeer") Peer toPeer) {
        this.fromPeer = fromPeer;
        this.toPeer = toPeer;
    }

    public void applyTo(Node node) {
        node.createInternalConnection(getInternalConnectionEntry());
    }

    public InternalConnection invert() {
        return InternalConnection.builder()
            .fromPeer(toPeer.invert())
            .toPeer(fromPeer.invert())
            .build();
    }

    @JsonIgnore
    InternalConnectionEntry getInternalConnectionEntry() {
        return InternalConnectionEntry.builder()
            .fromSubrack(fromPeer.getSubrack().getValue())
            .fromSlot(fromPeer.getSlot().getValue())
            .fromMpoIdentifier(fromPeer.getMpoIdentifier())
            .fromPort(fromPeer.getPort())
            .toSubrack(toPeer.getSubrack().getValue())
            .toSlot(toPeer.getSlot().getValue())
            .toMpoIdentifier(toPeer.getMpoIdentifier())
            .toPort(toPeer.getPort())
            .build();
    }
}
