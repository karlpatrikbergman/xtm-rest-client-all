package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.MibEntry;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClientPortSetAttribute.class, name = "clientPortSetAttribute"),
        @JsonSubTypes.Type(value = ClientPortConfigAttribute.class, name = "clientPortConfigAttribute"),
        @JsonSubTypes.Type(value = LinePortSetAttribute.class, name = "linePortSetAttribute"),
        @JsonSubTypes.Type(value = LinePortConfigAttribute.class, name = "linePortConfigAttribute")
})
public interface PortAttribute {
    void applyTo(Node node, MibEntry mibEntry);
}
