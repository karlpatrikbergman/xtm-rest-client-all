package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.BoardEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.BoardType;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Tpd10gbe.class, name = "tpd10gbe"),
        @JsonSubTypes.Type(value = Tpmr2500.class, name = "tpmr2500")
})
public interface Board {
    BoardType getBoardType();
    BoardEntry getBoardEntry();

    void applyTo(Node node);
}
