package com.infinera.metro.dnam.acceptance.test.node.configuration;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class NodeNetworkApplier {
    @NonNull @Singular private final List<NodeEquipmentApplier> nodeEquipmentAppliers;
    @NonNull @Singular private final List<PeerConnectionApplier> peerConnectionAppliers;
    @NonNull @Singular private final List<InternalConnectionApplier> internalConnectionAppliers;

    public void apply() {
        nodeEquipmentAppliers.forEach(NodeEquipmentApplier::applyToNode);
        peerConnectionAppliers.forEach(PeerConnectionApplier::applyFromNodeAtoNodeZ);
        internalConnectionAppliers.forEach(InternalConnectionApplier::applyToNode);
    }
}
