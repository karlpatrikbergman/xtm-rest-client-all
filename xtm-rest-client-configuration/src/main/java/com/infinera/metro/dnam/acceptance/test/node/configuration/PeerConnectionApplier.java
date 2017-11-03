package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.infinera.metro.dnam.acceptance.test.node.NodeAccessData;
import com.infinera.metro.dnam.acceptance.test.node.configuration.topology.PeerConnection;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Value
public class PeerConnectionApplier extends AbstractNodesApplier {
    @NonNull
    private final List<PeerConnection> peerConnections;

    @JsonCreator
    @Builder
    public PeerConnectionApplier(@JsonProperty("fromNodeAccessData") NodeAccessData fromNodeAccessData,
                                 @JsonProperty("toNodeAccessData") NodeAccessData toNodeAccessData,
                                 @JsonProperty("peerConnections") @Singular List<PeerConnection> peerConnections) {
        super(fromNodeAccessData, toNodeAccessData);
        this.peerConnections = peerConnections;
    }

    public void applyFromNodeAtoNodeZ() {
        peerConnections.forEach(peerConnection -> peerConnection.applyTo(getFromNode(), getToNode()));
    }
}
