package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.AddDropPortConfigAttributes;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.MibEntry;

/**
 * Interface for mib entry attributes that can be set using REST api.
 * Some may be applied using "set.json" and others may use "config.json"
 * For details see ENM RESt api specification.
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClientPortSetAttributes.class, name = "clientPortSetAttributes"),
        @JsonSubTypes.Type(value = ClientPortConfigAttributes.class, name = "clientPortConfigAttributes"),
        @JsonSubTypes.Type(value = LinePortSetAttributes.class, name = "linePortSetAttributes"),
        @JsonSubTypes.Type(value = LinePortConfigAttributes.class, name = "linePortConfigAttributes"),
        @JsonSubTypes.Type(value = BoardSetAttributes.class, name = "boardSetAttributes"),
        @JsonSubTypes.Type(value = AddDropPortConfigAttributes.class, name = "addDropPortConfigAttributes"),
})
public interface MibEntryAttributes {
    void applyTo(Node node, MibEntry mibEntry);
}
