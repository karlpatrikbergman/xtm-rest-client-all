package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.NodeAccessData;
import com.infinera.metro.dnam.acceptance.test.node.NodeImpl;
import com.infinera.metro.dnam.acceptance.test.node.configuration.serializedeserialize.ObjectFromFileUtilJackson;
import com.infinera.metro.dnam.acceptance.test.node.configuration.topology.InternalConnection;
import com.infinera.metro.dnam.acceptance.test.node.configuration.topology.PeerConnection;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * TODO: Create enums to be used as Map keys (must solve problem with Jackson serialization/deserialization first)
 */
@Value
@Builder
public class NodeConfiguration {
    private final @NonNull
    @Singular("accessDataForNode")
    Map<String, NodeAccessData> nodeAccessDataMap;
    private final @NonNull
    @Singular("nodeEquipmentForNode")
    Map<String, NodeEquipment> nodeEquipmentMap;
    private final
    @Singular("internalConnectionForNode")
    Map<String, List<InternalConnection>> internalConnectionMap;
    private final
    @Singular("peerConnectionForPeers")
    Map<Peers, List<PeerConnection>> peerConnectionMap;

    public void apply() {
        Map<String, Node> nodesMap = createNodesMapFromNodeAccessDataMap(this.nodeAccessDataMap);
        applyNodeNetwork(nodesMap);
    }

    public void apply(Map<String, NodeAccessData> nodeAccessDataMap) {
        Map<String, Node> nodesMap = createNodesMapFromNodeAccessDataMap(nodeAccessDataMap);
        applyNodeNetwork(nodesMap);
    }

    public void delete() {
        Map<String, Node> nodesMap = createNodesMapFromNodeAccessDataMap(this.nodeAccessDataMap);
        deleteNodeNetwork(nodesMap);
    }

    public void delete(Map<String, NodeAccessData> nodeAccessDataMap) {
        Map<String, Node> nodesMap = createNodesMapFromNodeAccessDataMap(nodeAccessDataMap);
        deleteNodeNetwork(nodesMap);
    }

    private void applyNodeNetwork(Map<String, Node> nodesMap) {
        nodeEquipmentMap.forEach((nodeName, nodeEquipment) -> nodeEquipment.applyTo(nodesMap.get(nodeName)));
        if (internalConnectionMap != null) {
            internalConnectionMap.forEach(
                (nodeName, internalConnectionList) -> internalConnectionList.forEach(
                    internalConnection -> internalConnection.applyTo(nodesMap.get(nodeName))));
        }
        if (peerConnectionMap != null) {
            peerConnectionMap.forEach(
                (peers, peerConnectionList) -> peerConnectionList.forEach(
                    peerConnection -> peerConnection.applyTo(nodesMap.get(peers.getFromNode()), nodesMap.get(peers.getToNode()))));
        }
    }

    private void deleteNodeNetwork(Map<String, Node> nodesMap) {
        nodeEquipmentMap.forEach((nodeName, nodeEquipment) -> nodeEquipment.deleteFrom(nodesMap.get(nodeName)));
    }

    private Map<String, Node> createNodesMapFromNodeAccessDataMap(Map<String, NodeAccessData> nodeAccessDataMap) {
        return nodeAccessDataMap.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> NodeImpl.create(entry.getValue()))
            );
    }

    public NodeConfiguration copyWithNew(Map<String, NodeAccessData> nodeAccessDataMap) {
        NodeConfigurationBuilder nodeConfigurationBuilder = NodeConfiguration.builder()
            .nodeAccessDataMap(nodeAccessDataMap)
            .nodeEquipmentMap(this.nodeEquipmentMap);
        if(this.peerConnectionMap != null)
            nodeConfigurationBuilder.peerConnectionMap(this.peerConnectionMap);
        if(this.internalConnectionMap != null) {
            nodeConfigurationBuilder.internalConnectionMap(this.internalConnectionMap);
        }
        return nodeConfigurationBuilder.build();
    }

    public void writeToFile(String path) throws IOException {
        final ObjectMapper mapper = ObjectFromFileUtilJackson.INSTANCE.getMapper();
        final String serializedObject = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this).trim();
        Files.write(Paths.get(path), serializedObject.getBytes());
    }
}
