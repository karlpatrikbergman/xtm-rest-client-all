package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.NodeAccessData;
import com.infinera.metro.dnam.acceptance.test.node.NodeImpl;
import com.infinera.metro.dnam.acceptance.test.node.configuration.topology.InternalConnection;
import com.infinera.metro.dnam.acceptance.test.node.configuration.topology.PeerConnection;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Value
@Builder
public class NodeNetwork {
    private final @NonNull
    @Singular("accessDataForNode")
    Map<String, NodeAccessData> nodeAccessDataMap;
    private final @NonNull
    @Singular("nodeEquipmentForNode")
    Map<String, NodeEquipment> nodeEquipmentMap;
    private final @NonNull
    @Singular("internalConnectionForNode")
    Map<String, List<InternalConnection>> internalConnectionMap;
    private final @NonNull
    @Singular("peerConnectionForPeers")
    Map<Peers, List<PeerConnection>> peerConnectionMap;

    public void apply() {
        Map<String, Node> nodesMap = createNodesMapFromNodeAccessDataMap();
        nodeEquipmentMap.forEach((nodeName, nodeEquipment) -> nodeEquipment.applyTo(nodesMap.get(nodeName)));
        internalConnectionMap.forEach((nodeName, internalConnectionList) -> internalConnectionList.forEach(internalConnection -> internalConnection.applyTo(nodesMap.get(nodeName))));
        peerConnectionMap.forEach((peers, peerConnectionList) -> peerConnectionList.forEach(peerConnection -> peerConnection.applyTo(nodesMap.get(peers.getFromNode()), nodesMap.get(peers.getToNode()))));
    }

    private Map<String, Node> createNodesMapFromNodeAccessDataMap() {
        return nodeAccessDataMap.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> NodeImpl.create(entry.getValue()))
            );
    }
}
