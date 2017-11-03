package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.infinera.metro.dnam.acceptance.test.node.NodeAccessData;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class NodeEquipmentApplier extends AbstractNodeApplier {
    @NonNull
    NodeEquipment nodeEquipment;

    @JsonCreator
    @Builder
    public NodeEquipmentApplier(@JsonProperty("nodeAccessData") NodeAccessData nodeAccessData,
                                @JsonProperty("nodeEquipment") NodeEquipment nodeEquipment) {
        super(nodeAccessData);
        this.nodeEquipment = nodeEquipment;
    }

    public void applyToNode() throws RuntimeException {
        nodeEquipment.applyTo(getNode());
    }
}