package com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.line;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.LinePortEntry;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes({
    @JsonSubTypes.Type(value = LinePortSetAttributes.class, name = "linePortSetAttributes"),
    @JsonSubTypes.Type(value = LinePortConfigAttributes.class, name = "linePortConfigAttributes"),
})
public interface LinePortAttributes {
    void applyTo(Node node, LinePortEntry linePortEntry);
}
