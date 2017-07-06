package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.MpoIdentifier;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.AbstractPortEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.InternalConnectionEntry;
import lombok.Builder;
import lombok.Value;

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@Value
public class InternalConnection {

    private final InternalConnectionEntry internalConnectionEntry;

    @JsonCreator
    @Builder
    public InternalConnection(@JsonProperty("fromPort") AbstractPortEntry fromPort,
                              @JsonProperty("fromMpoIdentifier") MpoIdentifier fromMpoIdentifier,
                              @JsonProperty("toPort") AbstractPortEntry toPort,
                              @JsonProperty("toMpoIdentifier") MpoIdentifier toMpoIdentifier) {
        this.internalConnectionEntry = InternalConnectionEntry.builder()
            .fromSubrack(fromPort.getSubrack())
            .fromSlot(fromPort.getSlot())
            .fromMpoIdentifier(fromMpoIdentifier)
            .fromPort(fromPort.getTransmitPort())
            .toSubrack(toPort.getSubrack())
            .toSlot(toPort.getSlot())
            .toMpoIdentifier(toMpoIdentifier)
            .toPort(toPort.getReceivePort())
            .build();
    }

    public void applyTo(Node node) {
        node.createInternalConnection(internalConnectionEntry);
    }
}
