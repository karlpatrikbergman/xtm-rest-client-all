package com.infinera.metro.dnam.acceptance.test.node.configuration.attribute.adddrop;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes({
    @JsonSubTypes.Type(value = AddDropPortConfigAttributes.class, name = "addDropPortConfigAttributes"),
})
interface AddDropPortAttributes {
}
