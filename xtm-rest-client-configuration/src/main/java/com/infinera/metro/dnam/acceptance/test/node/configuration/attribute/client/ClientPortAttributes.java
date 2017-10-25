package com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.client;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.ClientPortEntry;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ClientPortSetAttributes.class, name = "clientPortSetAttributes"),
    @JsonSubTypes.Type(value = ClientPortConfigAttributes.class, name = "clientPortConfigAttributes"),
})
public interface ClientPortAttributes {
    void applyTo(Node node, ClientPortEntry clientPortEntry);
}
