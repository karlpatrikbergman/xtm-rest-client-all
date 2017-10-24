package com.infinera.metro.dnam.acceptance.test.node.configuration.attribute;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.BoardEntry;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes({
    @JsonSubTypes.Type(value = BoardSetAttributes.class, name = "boardSetAttributes"),
})
public interface BoardAttributes {
    void applyTo(Node node, BoardEntry boardEntry);
}
