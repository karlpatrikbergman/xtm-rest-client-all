package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.infinera.metro.dnam.acceptance.test.node.Node;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class NodeConfiguration {
    @NonNull Node node;
    @NonNull NodeEquipment nodeEquipment;

    public void apply() throws RuntimeException {
        nodeEquipment.applyTo(node);
    }
}