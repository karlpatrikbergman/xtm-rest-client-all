package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.infinera.metro.dnam.acceptance.test.node.NodeAccessData;
import com.infinera.metro.dnam.acceptance.test.node.configuration.topology.InternalConnection;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Value
public class InternalConnectionApplier extends AbstractNodeApplier {
    @NonNull
    private final List<InternalConnection> internalConnections;

    @JsonCreator
    @Builder
    public InternalConnectionApplier(@JsonProperty("nodeAccessData") NodeAccessData nodeAccessData,
                                     @JsonProperty("internalConnections") @Singular List<InternalConnection> internalConnections) {
        super(nodeAccessData);
        this.internalConnections = internalConnections;
    }

    public void applyToNode() {
        internalConnections.forEach(internalConnection -> internalConnection.applyTo(getNode()));
    }
}
